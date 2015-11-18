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

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Levente Hudák
 */
@Component(immediate = true, service = DDMTemplatePermission.class)
public class DDMTemplatePermission {

	public static void check(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, template, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getTemplateModelResourceName(template.getResourceClassNameId()),
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
				getTemplateModelResourceName(template.getResourceClassNameId()),
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
				getTemplateModelResourceName(template.getResourceClassNameId()),
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
				getTemplateModelResourceName(template.getResourceClassNameId()),
				templateId, actionId);
		}
	}

	public static void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		if (!containsAddTemplatePermission(
				permissionChecker, groupId, classNameId, resourceClassNameId)) {

			ServiceWrapper<DDMTemplatePermissionSupport>
				templatePermissionSupportServiceWrapper =
					_ddmPermissionSupportTracker.
						getDDMTemplatePermissionSupportServiceWrapper(
							resourceClassNameId);

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getResourceName(
					templatePermissionSupportServiceWrapper, classNameId),
				groupId,
				getAddTemplateActionId(
					templatePermissionSupportServiceWrapper));
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		String templateModelResourceName = getTemplateModelResourceName(
			template.getResourceClassNameId());

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
	 * @deprecated As of 7.0.0, replaced by {@link #contains(PermissionChecker,
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
				getTemplateModelResourceName(template.getResourceClassNameId()),
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
	 * @deprecated As of 7.0.0, replaced by {@link #contains(PermissionChecker,
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

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassNameId);

		return permissionChecker.hasPermission(
			groupId,
			getResourceName(
				templatePermissionSupportServiceWrapper, classNameId),
			groupId,
			getAddTemplateActionId(templatePermissionSupportServiceWrapper));
	}

	public static String getTemplateModelResourceName(long resourceClassNameId)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassNameId);

		Map<String, Object> properties =
			templatePermissionSupportServiceWrapper.getProperties();

		boolean defaultModelResourceName = MapUtil.getBoolean(
			properties, "default.model.resource.name");

		if (defaultModelResourceName) {
			return DDMTemplate.class.getName();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(PortalUtil.getClassName(resourceClassNameId));
		sb.append(ResourceActionsUtil.getCompositeModelNameSeparator());
		sb.append(DDMStructure.class.getName());

		return sb.toString();
	}

	protected static String getAddTemplateActionId(
		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper) {

		Map<String, Object> properties =
			templatePermissionSupportServiceWrapper.getProperties();

		return MapUtil.getString(
			properties, "add.template.action.id", DDMActionKeys.ADD_TEMPLATE);
	}

	protected static String getResourceName(
		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper, long classNameId) {

		DDMTemplatePermissionSupport templatePermissionSupport =
			templatePermissionSupportServiceWrapper.getService();

		return templatePermissionSupport.getResourceName(classNameId);
	}

	@Reference(unbind = "-")
	protected void setDDMPermissionSupportTracker(
		DDMPermissionSupportTracker ddmPermissionSupportTracker) {

		_ddmPermissionSupportTracker = ddmPermissionSupportTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	private static DDMPermissionSupportTracker _ddmPermissionSupportTracker;
	private static DDMTemplateLocalService _ddmTemplateLocalService;

}