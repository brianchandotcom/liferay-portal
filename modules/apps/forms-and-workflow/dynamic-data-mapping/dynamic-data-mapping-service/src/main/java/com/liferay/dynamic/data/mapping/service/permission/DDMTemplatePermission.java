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
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Levente Hudák
 */
@Component(immediate = true, service = DDMTemplatePermission.class)
public class DDMTemplatePermission extends BaseResourcePermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, template, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getTemplateModelResourceName(template.getResourceClassName()),
				template.getTemplateId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			DDMTemplate template, String portletId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, template, portletId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getTemplateModelResourceName(template.getResourceClassName()),
				template.getTemplateId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getTemplate(templateId);

		if (!contains(
				permissionChecker, groupId, template, portletId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getTemplateModelResourceName(template.getResourceClassName()),
				templateId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getTemplate(templateId);

		if (!contains(permissionChecker, template, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getTemplateModelResourceName(template.getResourceClassName()),
				templateId, actionId);
		}
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

		String templateModelResourceName = getTemplateModelResourceName(
			template.getResourceClassName());

		if (permissionChecker.hasOwnerPermission(
				template.getCompanyId(), templateModelResourceName,
				template.getTemplateId(), template.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			template.getGroupId(), templateModelResourceName,
			template.getTemplateId(), actionId);
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

		return contains(permissionChecker, template, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			DDMTemplate template, String portletId, String actionId)
		throws PortalException {

		if (Validator.isNotNull(portletId)) {
			Boolean hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, groupId,
				getTemplateModelResourceName(template.getResourceClassName()),
				template.getTemplateId(), portletId, actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		return contains(permissionChecker, template, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getTemplate(templateId);

		return contains(
			permissionChecker, groupId, template, portletId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long templateId,
			String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getTemplate(templateId);

		return contains(permissionChecker, template, actionId);
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

		return contains(permissionChecker, templateId, actionId);
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
			return contains(permissionChecker, classPK, actionId);
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

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}


	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplatePermission.class);

	private static DDMTemplateLocalService _ddmTemplateLocalService;
	private static DDMPermissionSupport _ddmPermissionSupport;

}