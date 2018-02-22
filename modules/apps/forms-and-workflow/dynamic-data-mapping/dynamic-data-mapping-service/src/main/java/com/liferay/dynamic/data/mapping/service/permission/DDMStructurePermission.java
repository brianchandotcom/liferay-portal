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
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = DDMStructurePermission.class)
public class DDMStructurePermission extends BaseResourcePermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, DDMStructure structure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, structure, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getStructureModelResourceName(structure.getClassName()),
				structure.getStructureId(), actionId);
		}
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #check(PermissionChecker, DDMStructure, String)}
	 */
	@Deprecated
	public static void check(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String structureKey, String actionId)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey, true);

		check(permissionChecker, structure, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long structureId,
			String actionId)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			structureId);

		check(permissionChecker, structure, actionId);
	}

	public static void checkAddStruturePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		if (!containsAddStruturePermission(
				permissionChecker, groupId, classNameId)) {

			ServiceWrapper<DDMStructurePermissionSupport>
				structurePermissionSupportServiceWrapper =
					_ddmPermissionSupportTracker.
						getDDMStructurePermissionSupportServiceWrapper(
							classNameId);

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getResourceName(structurePermissionSupportServiceWrapper),
				groupId,
				getAddStructureActionId(
					structurePermissionSupportServiceWrapper));
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMStructure structure,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, structure, null, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMStructure structure,
			String portletId, String actionId)
		throws PortalException {

		String structureModelResourceName = getStructureModelResourceName(
			structure.getClassName());

		if (Validator.isNotNull(portletId)) {
			Boolean hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, structure.getGroupId(),
				structureModelResourceName, structure.getStructureId(),
				portletId, actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (permissionChecker.hasOwnerPermission(
				structure.getCompanyId(), structureModelResourceName,
				structure.getStructureId(), structure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			structure.getGroupId(), structureModelResourceName,
			structure.getStructureId(), actionId);
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #contains(PermissionChecker, DDMStructure, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String structureKey, String actionId)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey, true);

		return contains(permissionChecker, structure, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long structureId,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, structureId, null, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long structureId,
			String portletId, String actionId)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			structureId);

		return contains(permissionChecker, structure, portletId, actionId);
	}

	public static boolean containsAddStruturePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMStructurePermissionSupportServiceWrapper(classNameId);

		return contains(
			permissionChecker,
			getResourceName(structurePermissionSupportServiceWrapper), groupId,
			getAddStructureActionId(structurePermissionSupportServiceWrapper));
	}

	public static String getStructureModelResourceName(long classNameId)
		throws PortalException {

		return getStructureModelResourceName(
			PortalUtil.getClassName(classNameId));
	}

	public static String getStructureModelResourceName(String className)
		throws PortalException {

		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMStructurePermissionSupportServiceWrapper(className);

		Map<String, Object> properties =
			structurePermissionSupportServiceWrapper.getProperties();

		boolean defaultModelResourceName = MapUtil.getBoolean(
			properties, "default.model.resource.name");

		if (defaultModelResourceName) {
			return DDMStructure.class.getName();
		}

		return ResourceActionsUtil.getCompositeModelName(
			className, DDMStructure.class.getName());
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

	protected static String getAddStructureActionId(
		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper) {

		Map<String, Object> properties =
			structurePermissionSupportServiceWrapper.getProperties();

		return MapUtil.getString(
			properties, "add.structure.action.id", DDMActionKeys.ADD_STRUCTURE);
	}

	protected static String getResourceName(
		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper) {

		DDMStructurePermissionSupport structurePermissionSupport =
			structurePermissionSupportServiceWrapper.getService();

		return structurePermissionSupport.getResourceName();
	}

	@Reference(unbind = "-")
	protected void setDDMPermissionSupportTracker(
		DDMPermissionSupportTracker ddmPermissionSupportTracker) {

		_ddmPermissionSupportTracker = ddmPermissionSupportTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructurePermission.class);

	private static DDMPermissionSupportTracker _ddmPermissionSupportTracker;
	private static DDMStructureLocalService _ddmStructureLocalService;

}