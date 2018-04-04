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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Levente Hudák
 * @deprecated As of 3.0.0, with no direct replacement
 */
@Component(immediate = true, service = DDMTemplatePermission.class)
@Deprecated
public class DDMTemplatePermission extends BaseResourcePermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		_ddmTemplateModelResourcePermission.contains(
			permissionChecker, template, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			DDMTemplate template, String portletId, String actionId)
		throws PortalException {

		_ddmTemplateModelResourcePermission.check(
			permissionChecker, template, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException {

		_ddmTemplateModelResourcePermission.check(
			permissionChecker, templateId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException {

		_ddmTemplateModelResourcePermission.check(
			permissionChecker, templateId, actionId);
	}

	public static void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		_ddmPermissionSupport.checkAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassNameId);
	}

	public static void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException {

		_ddmPermissionSupport.checkAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassName);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, template, actionId);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #contains(PermissionChecker,
	 *             DDMTemplate, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, DDMTemplate template,
			String portletId, String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, template, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			DDMTemplate template, String portletId, String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, template, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, templateId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, templateId, actionId);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #contains(PermissionChecker,
	 *             long, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long templateId,
			String portletId, String actionId)
		throws PortalException {

		return _ddmTemplateModelResourcePermission.contains(
			permissionChecker, templateId, actionId);
	}

	public static boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		return _ddmPermissionSupport.containsAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassNameId);
	}

	public static boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException {

		return _ddmPermissionSupport.containsAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassName);
	}

	public static String getTemplateModelResourceName(long resourceClassNameId)
		throws PortalException {

		return _ddmPermissionSupport.getTemplateModelResourceName(
			resourceClassNameId);
	}

	public static String getTemplateModelResourceName(String resourceClassName)
		throws PortalException {

		return _ddmPermissionSupport.getTemplateModelResourceName(
			resourceClassName);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		try {
			return _ddmTemplateModelResourcePermission.contains(
				permissionChecker, classPK, actionId);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplatePermission.class);

	private static DDMPermissionSupport _ddmPermissionSupport;
	private static ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;

}