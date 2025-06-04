/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.helper;

import com.liferay.commerce.helper.CommerceUserRoleHelper;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = CommerceUserRoleHelper.class)
public class CommerceUserRoleHelperImpl implements CommerceUserRoleHelper {

	@Override
	public void checkCommerceUserRoles(ServiceContext serviceContext)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled("LPD-10562")) {
			_setRolePermissions(
				_roleLocalService.fetchRole(
					serviceContext.getCompanyId(), RoleConstants.USER),
				serviceContext);
		}
	}

	@Override
	public boolean hasCommerceUserPermissions(long companyId)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(companyId, RoleConstants.USER);

		for (String objectDefinitionName :
				_RETURNS_MANAGER_OBJECT_DEFINITION_NAMES) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					role.getCompanyId(), objectDefinitionName);

			if (objectDefinition == null) {
				continue;
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, objectDefinition.getResourceName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					role.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY)) {

				return false;
			}
		}

		for (String externalReferenceCode :
				_RETURNS_MANAGER_LIST_TYPE_DEFINITION_EXTERNAL_REFERENCE_CODES) {

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.
					fetchListTypeDefinitionByExternalReferenceCode(
						externalReferenceCode, role.getCompanyId());

			if (listTypeDefinition == null) {
				continue;
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, listTypeDefinition.getModelClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(
						listTypeDefinition.getListTypeDefinitionId()),
					role.getRoleId(), ActionKeys.VIEW)) {

				return false;
			}
		}

		return true;
	}

	private void _setRolePermissions(Role role, ServiceContext serviceContext)
		throws PortalException {

		for (String objectDefinitionName :
				_RETURNS_MANAGER_OBJECT_DEFINITION_NAMES) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					role.getCompanyId(), objectDefinitionName);

			if (objectDefinition == null) {
				continue;
			}

			_resourcePermissionLocalService.addResourcePermission(
				serviceContext.getCompanyId(),
				objectDefinition.getResourceName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(serviceContext.getCompanyId()), role.getRoleId(),
				ObjectActionKeys.ADD_OBJECT_ENTRY);
		}

		for (String externalReferenceCode :
				_RETURNS_MANAGER_LIST_TYPE_DEFINITION_EXTERNAL_REFERENCE_CODES) {

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.
					fetchListTypeDefinitionByExternalReferenceCode(
						externalReferenceCode, role.getCompanyId());

			if (listTypeDefinition == null) {
				continue;
			}

			_resourcePermissionLocalService.setResourcePermissions(
				serviceContext.getCompanyId(),
				listTypeDefinition.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(listTypeDefinition.getListTypeDefinitionId()),
				role.getRoleId(), new String[] {ActionKeys.VIEW});
		}
	}

	private static final String[]
		_RETURNS_MANAGER_LIST_TYPE_DEFINITION_EXTERNAL_REFERENCE_CODES = {
			"L_COMMERCE_RETURN_ITEM_STATUSES", "L_COMMERCE_RETURN_REASONS",
			"L_COMMERCE_RETURN_RESOLUTION_METHODS", "L_COMMERCE_RETURN_STATUSES"
		};

	private static final String[] _RETURNS_MANAGER_OBJECT_DEFINITION_NAMES = {
		"CommerceReturn", "CommerceReturnItem"
	};

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}