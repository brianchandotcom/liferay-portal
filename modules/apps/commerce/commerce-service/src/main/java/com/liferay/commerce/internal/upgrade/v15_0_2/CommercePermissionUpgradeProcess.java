/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v15_0_2;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Crescenzo Rega
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				CommerceConstants.RESOURCE_NAME_COMMERCE_AVAILABILITY,
				"MANAGE_COMMERCE_AVAILABILITY_ESTIMATES");

		if (resourceAction == null) {
			return;
		}

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					CommerceConstants.RESOURCE_NAME_COMMERCE_AVAILABILITY)) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) ||
				(resourcePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				continue;
			}

			_addResourcePermission(
				ActionKeys.DELETE, resourcePermission.getCompanyId(),
				CommerceAvailabilityEstimate.class.getName(),
				String.valueOf(resourcePermission.getCompanyId()),
				resourcePermission.getRoleId(),
				ResourceConstants.SCOPE_COMPANY);
			_addResourcePermission(
				ActionKeys.PERMISSIONS, resourcePermission.getCompanyId(),
				CommerceAvailabilityEstimate.class.getName(),
				String.valueOf(resourcePermission.getCompanyId()),
				resourcePermission.getRoleId(),
				ResourceConstants.SCOPE_COMPANY);
			_addResourcePermission(
				ActionKeys.UPDATE, resourcePermission.getCompanyId(),
				CommerceAvailabilityEstimate.class.getName(),
				String.valueOf(resourcePermission.getCompanyId()),
				resourcePermission.getRoleId(),
				ResourceConstants.SCOPE_COMPANY);
			_addResourcePermission(
				ActionKeys.VIEW, resourcePermission.getCompanyId(),
				CommerceAvailabilityEstimate.class.getName(),
				String.valueOf(resourcePermission.getCompanyId()),
				resourcePermission.getRoleId(),
				ResourceConstants.SCOPE_COMPANY);
			_addResourcePermission(
				CommerceActionKeys.ADD_COMMERCE_AVAILABILITY_ESTIMATE,
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getPrimKey(), resourcePermission.getRoleId(),
				resourcePermission.getScope());
			_addResourcePermission(
				CommerceActionKeys.VIEW_COMMERCE_AVAILABILITY_ESTIMATES,
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getPrimKey(), resourcePermission.getRoleId(),
				resourcePermission.getScope());

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(),
				"MANAGE_COMMERCE_AVAILABILITY_ESTIMATES");
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	private void _addResourcePermission(
			String actionId, long companyId, String name, String primKey,
			long roleId, int scope)
		throws Exception {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				companyId, name, scope, primKey, roleId, actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				companyId, name, scope, primKey, roleId, actionId);
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}