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

package com.liferay.journal.web.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = {})
public class DDMTemplatePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, DDMTemplate ddmTemplate,
			String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, ddmTemplate, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ddmTemplateId,
			String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, ddmTemplateId, actionId);
	}

	public static boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		return _ddmPermissionSupport.containsAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassNameId);
	}

	public static String getTemplateModelResourceName(long resourceClassnameId)
		throws PortalException {

		return _ddmPermissionSupport.getTemplateModelResourceName(
			resourceClassnameId);
	}

	@Reference(unbind = "-")
	protected void setDDMPermissionSupport(
		DDMPermissionSupport ddmPermissionSupport) {

		_ddmPermissionSupport = ddmPermissionSupport;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DDMTemplate> modelResourcePermission) {

		_ddmTemplateModelResourcePermission = modelResourcePermission;
	}

	private static DDMPermissionSupport _ddmPermissionSupport;
	private static ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;

}