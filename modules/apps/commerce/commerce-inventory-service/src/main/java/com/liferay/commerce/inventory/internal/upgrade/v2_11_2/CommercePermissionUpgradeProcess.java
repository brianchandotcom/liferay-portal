/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_11_2;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
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
				CommerceInventoryConstants.RESOURCE_NAME,
				CommerceInventoryActionKeys.MANAGE_INVENTORY);

		if (resourceAction == null) {
			return;
		}

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					CommerceInventoryConstants.RESOURCE_NAME)) {

			if (_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) &&
				(resourcePermission.getScope() !=
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(),
					CommerceInventoryWarehouse.class.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(), ActionKeys.VIEW);
			}
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}