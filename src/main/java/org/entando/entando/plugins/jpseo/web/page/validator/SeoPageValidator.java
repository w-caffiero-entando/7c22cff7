/*
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package org.entando.entando.plugins.jpseo.web.page.validator;

import com.agiletec.apsadmin.portal.PageAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.entando.entando.aps.system.exception.RestServerError;
import org.entando.entando.plugins.jpseo.aps.system.services.mapping.FriendlyCodeVO;
import org.entando.entando.plugins.jpseo.aps.system.services.mapping.ISeoMappingManager;
import org.entando.entando.web.page.validator.PageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeoPageValidator extends PageValidator {
    private static final Logger logger =  LoggerFactory.getLogger(SeoPageValidator.class);

    @Autowired
    private ISeoMappingManager seoMappingManager;

    public boolean checkFriendlyCode(String friendlyCode) {
        if (null != friendlyCode && friendlyCode.trim().length() > 100) {
            logger.error("Invalid friendly Code {}", friendlyCode);
            return false;
        }
        if (null != friendlyCode && friendlyCode.trim().length() > 0) {
            Pattern pattern = Pattern.compile("([a-z0-9_])+");
            Matcher matcher = pattern.matcher(friendlyCode);
            if (!matcher.matches()) {
                logger.error("Invalid friendly Code {}", friendlyCode);
                return false;
            }
        }
        if (null != friendlyCode && friendlyCode.trim().length() > 0) {
            FriendlyCodeVO vo = seoMappingManager.getReference(friendlyCode);
            if (null != vo && (vo.getPageCode() == null || !vo.getPageCode().equals(friendlyCode))) {
                logger.error("Invalid friendly Code {}", friendlyCode);
                return false;
            }
        }
        return true;
    }


}
