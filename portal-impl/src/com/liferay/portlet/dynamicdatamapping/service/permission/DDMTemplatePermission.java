/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

/**
 * @author Eduardo Lundgren
 */
public class DDMTemplatePermission {

	public static void check(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, template, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, templateId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMTemplate template,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				template.getCompanyId(), DDMTemplate.class.getName(),
				template.getTemplateId(), template.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			template.getGroupId(), DDMTemplate.class.getName(),
			template.getTemplateId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException, SystemException {

		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			templateId);

		return contains(permissionChecker, template, actionId);
	}

}