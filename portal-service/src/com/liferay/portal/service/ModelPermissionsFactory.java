/*
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissionsFactory {

    public static final String MODEL_PERMISSIONS_PREFIX = "modelPermissions.";

    public static ModelPermissions create(
            long companyId, long groupId, String[] groupPermissions,
            String[] guestPermissions)
        throws PortalException {

        ModelPermissions modelPermissions = new ModelPermissions();

        // Group permissions

        if ((groupId > 0) && (groupPermissions != null) &&
            (groupPermissions.length > 0)){

            Role defaultGroupRole =
                RoleLocalServiceUtil.getDefaultGroupRole(groupId);

            modelPermissions.addRolePermissions(
                defaultGroupRole, groupPermissions);
        }

        // Guest permissions

        if ((guestPermissions != null) && (guestPermissions.length > 0)) {
            Role guestRole = RoleLocalServiceUtil.getRole(
                companyId, RoleConstants.GUEST);

            if (guestPermissions == null) {
                guestPermissions = new String[0];
            }

            modelPermissions.addRolePermissions(guestRole, guestPermissions);
        }

        return modelPermissions;
    }

    public static ModelPermissions create(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();

        return create(parameterMap);
    }

    public static ModelPermissions create(Map<String, String[]> parameterMap) {
        ModelPermissions modelPermissions = new ModelPermissions();

        for (String param : parameterMap.keySet()) {
            if (!param.startsWith(MODEL_PERMISSIONS_PREFIX)) {
                continue;
            }

            String roleName = param.substring(
                MODEL_PERMISSIONS_PREFIX.length());
            String[] actionIds = parameterMap.get(param);

            Role role = null;

            try {
                role = RoleLocalServiceUtil.getRole(
                    CompanyThreadLocal.getCompanyId(), roleName);
            }
            catch (PortalException pe) {
                if (_log.isInfoEnabled()) {
                    _log.info(
                        "Couldn't find role named " + roleName +
                            ". Ignoring it.");
                }

                continue;
            }

            modelPermissions.addRolePermissions(role, actionIds);
        }

        return modelPermissions;
    }

    private static final Log _log = LogFactoryUtil.getLog(
        ModelPermissionsFactory.class);


}
