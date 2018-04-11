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

package com.liferay.dynamic.data.mapping.internal.security.permission.support;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDMPermissionSupport.class)
public class DDMPermissionSupportImpl implements DDMPermissionSupport {

	@Override
	public void checkAddStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		if (!containsAddStructurePermission(
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

	public void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassNameId);

		_checkAddTemplatePermission(
			permissionChecker, groupId, classNameId,
			templatePermissionSupportServiceWrapper);
	}

	public void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassName);

		_checkAddTemplatePermission(
			permissionChecker, groupId, classNameId,
			templatePermissionSupportServiceWrapper);
	}

	@Override
	public boolean containsAddStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMStructurePermissionSupportServiceWrapper(classNameId);

		return _ddmPermissionSupportHelper.contains(
			permissionChecker,
			getResourceName(structurePermissionSupportServiceWrapper), groupId,
			getAddStructureActionId(structurePermissionSupportServiceWrapper));
	}

	@Override
	public boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassNameId);

		return _containsAddTemplatePermission(
			permissionChecker, groupId, classNameId,
			templatePermissionSupportServiceWrapper);
	}

	@Override
	public boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassName);

		return _containsAddTemplatePermission(
			permissionChecker, groupId, classNameId,
			templatePermissionSupportServiceWrapper);
	}

	@Override
	public String getStructureModelResourceName(long classNameId)
		throws PortalException {

		return getStructureModelResourceName(_portal.getClassName(classNameId));
	}

	@Override
	public String getStructureModelResourceName(String className)
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
	public String getTemplateModelResourceName(long resourceClassNameId)
		throws PortalException {

		String resourceClassName = _portal.getClassName(resourceClassNameId);

		return getTemplateModelResourceName(resourceClassName);
	}

	@Override
	public String getTemplateModelResourceName(String resourceClassName)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper =
				_ddmPermissionSupportTracker.
					getDDMTemplatePermissionSupportServiceWrapper(
						resourceClassName);

		return _getTemplateModelResourceName(
			resourceClassName, templatePermissionSupportServiceWrapper);
	}

	protected String getAddStructureActionId(
		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper) {

		Map<String, Object> properties =
			structurePermissionSupportServiceWrapper.getProperties();

		return MapUtil.getString(
			properties, "add.structure.action.id", DDMActionKeys.ADD_STRUCTURE);
	}

	protected String getAddTemplateActionId(
		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper) {

		Map<String, Object> properties =
			templatePermissionSupportServiceWrapper.getProperties();

		return MapUtil.getString(
			properties, "add.template.action.id", DDMActionKeys.ADD_TEMPLATE);
	}

	protected String getResourceName(
		ServiceWrapper<DDMStructurePermissionSupport>
			structurePermissionSupportServiceWrapper) {

		DDMStructurePermissionSupport structurePermissionSupport =
			structurePermissionSupportServiceWrapper.getService();

		return structurePermissionSupport.getResourceName();
	}

	protected String getResourceName(
		ServiceWrapper<DDMTemplatePermissionSupport>
			templatePermissionSupportServiceWrapper,
		long classNameId) {

		DDMTemplatePermissionSupport templatePermissionSupport =
			templatePermissionSupportServiceWrapper.getService();

		return templatePermissionSupport.getResourceName(classNameId);
	}

	private void _checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			ServiceWrapper<DDMTemplatePermissionSupport>
				templatePermissionSupportServiceWrapper)
		throws PortalException {

		if (!_containsAddTemplatePermission(
				permissionChecker, groupId, classNameId,
				templatePermissionSupportServiceWrapper)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				getResourceName(
					templatePermissionSupportServiceWrapper, classNameId),
				groupId,
				getAddTemplateActionId(
					templatePermissionSupportServiceWrapper));
		}
	}

	private boolean _containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			ServiceWrapper<DDMTemplatePermissionSupport>
				templatePermissionSupportServiceWrapper)
		throws PortalException {

		String resourceName = getResourceName(
			templatePermissionSupportServiceWrapper, classNameId);

		List<String> portletNames = ResourceActionsUtil.getPortletNames();

		if (portletNames.contains(resourceName)) {
			return PortletPermissionUtil.contains(
				permissionChecker, groupId, null, resourceName,
				getAddTemplateActionId(
					templatePermissionSupportServiceWrapper));
		}

		return _ddmPermissionSupportHelper.contains(
			permissionChecker, resourceName, groupId,
			getAddTemplateActionId(templatePermissionSupportServiceWrapper));
	}

	private String _getTemplateModelResourceName(
			String resourceClassName,
			ServiceWrapper<DDMTemplatePermissionSupport>
				templatePermissionSupportServiceWrapper)
		throws PortalException {

		Map<String, Object> properties =
			templatePermissionSupportServiceWrapper.getProperties();

		boolean defaultModelResourceName = MapUtil.getBoolean(
			properties, "default.model.resource.name");

		if (defaultModelResourceName) {
			return DDMTemplate.class.getName();
		}

		return ResourceActionsUtil.getCompositeModelName(
			resourceClassName, DDMTemplate.class.getName());
	}

	@Reference
	private DDMPermissionSupportHelper _ddmPermissionSupportHelper;

	@Reference
	private DDMPermissionSupportTracker _ddmPermissionSupportTracker;

	@Reference
	private Portal _portal;

}