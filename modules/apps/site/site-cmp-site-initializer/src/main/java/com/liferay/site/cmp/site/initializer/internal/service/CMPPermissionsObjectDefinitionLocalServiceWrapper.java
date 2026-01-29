/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.service;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.site.cms.site.initializer.util.PermissionUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = ServiceWrapper.class)
public class CMPPermissionsObjectDefinitionLocalServiceWrapper
	extends ObjectDefinitionLocalServiceWrapper {

	@Override
	public ObjectDefinition publishSystemObjectDefinition(
			long userId, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition = super.publishSystemObjectDefinition(
			userId, objectDefinitionId);

		if (!Objects.equals(
				objectDefinition.getObjectFolderExternalReferenceCode(),
				"L_CMP_PROJECT_MANAGEMENT_DEFINITIONS")) {

			return objectDefinition;
		}

		try {
			Role role = PermissionUtil.getOrAddCMSAdministratorRole(
				objectDefinition.getCompanyId(), objectDefinition.getUserId());

			_addResourcePermission(
				objectDefinition,
				String.valueOf(objectDefinition.getCompanyId()), role.getName(),
				ResourceConstants.SCOPE_COMPANY);

			_resourcePermissionLocalService.setResourcePermissions(
				objectDefinition.getCompanyId(),
				objectDefinition.getClassName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(objectDefinition.getCompanyId()),
				role.getRoleId(),
				new String[] {
					ActionKeys.DELETE, ActionKeys.PERMISSIONS,
					ActionKeys.UPDATE, ActionKeys.VIEW
				});

			if (Objects.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_CMP_TASK")) {

				_addResourcePermission(
					objectDefinition, "0",
					DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
					ResourceConstants.SCOPE_GROUP_TEMPLATE);
				_addResourcePermission(
					objectDefinition, "0",
					DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
					ResourceConstants.SCOPE_GROUP_TEMPLATE);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return objectDefinition;
	}

	private void _addResourcePermission(
			ObjectDefinition objectDefinition, String primKey, String roleName,
			int scope)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			objectDefinition.getCompanyId(), roleName);

		if (role == null) {
			return;
		}

		_resourcePermissionLocalService.addResourcePermission(
			objectDefinition.getCompanyId(), objectDefinition.getResourceName(),
			scope, primKey, role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMPPermissionsObjectDefinitionLocalServiceWrapper.class);

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}