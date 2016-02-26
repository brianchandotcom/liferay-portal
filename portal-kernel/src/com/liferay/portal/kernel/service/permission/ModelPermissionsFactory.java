/**
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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissionsFactory {

	public static final String MODEL_PERMISSIONS_PREFIX = "modelPermissions";

	public static ModelPermissions create(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		return create(parameterMap);
	}

	public static ModelPermissions create(Map<String, String[]> parameterMap) {
		ModelPermissions modelPermissions = new ModelPermissions();

		for (String parameterName : parameterMap.keySet()) {
			if (!parameterName.startsWith(MODEL_PERMISSIONS_PREFIX)) {
				continue;
			}

			String roleName = parameterName.substring(
				MODEL_PERMISSIONS_PREFIX.length());

			Role role = null;

			try {
				role = RoleLocalServiceUtil.getRole(
					CompanyThreadLocal.getCompanyId(), roleName);
			}
			catch (PortalException pe) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to get role " + roleName);
				}

				continue;
			}

			String[] actionIds = parameterMap.get(parameterName);

			modelPermissions.addRolePermissions(role.getName(), actionIds);
		}

		return modelPermissions;
	}

	public static ModelPermissions create(PortletRequest portletRequest) {
		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		return create(parameterMap);
	}

	public static ModelPermissions create(
		String[] groupPermissions, String[] guestPermissions) {

		ModelPermissions modelPermissions = new ModelPermissions();

		if ((groupPermissions != null) && (groupPermissions.length > 0)) {
			modelPermissions.addRolePermissions(
				RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE, groupPermissions);
		}

		if ((guestPermissions != null) && (guestPermissions.length > 0)) {
			modelPermissions.addRolePermissions(
				RoleConstants.GUEST, guestPermissions);
		}

		return modelPermissions;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelPermissionsFactory.class);

}