/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v6_3_0;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
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
				CPConstants.RESOURCE_NAME_PRODUCT,
				"MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS");

		if (resourceAction == null) {
			return;
		}

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					CPConstants.RESOURCE_NAME_PRODUCT)) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) ||
				(resourcePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				continue;
			}

			for (String actionId :
					new String[] {
						CPActionKeys.ADD_COMMERCE_PRODUCT_MEASUREMENT_UNIT,
						CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS
					}) {

				if (!_resourcePermissionLocalService.hasResourcePermission(
						resourcePermission.getCompanyId(),
						resourcePermission.getName(),
						resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(), actionId)) {

					_resourcePermissionLocalService.addResourcePermission(
						resourcePermission.getCompanyId(),
						resourcePermission.getName(),
						resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(), actionId);
				}
			}

			for (String actionId :
					new String[] {
						ActionKeys.DELETE, ActionKeys.PERMISSIONS,
						ActionKeys.UPDATE, ActionKeys.VIEW
					}) {

				if (!_resourcePermissionLocalService.hasResourcePermission(
						resourcePermission.getCompanyId(),
						CPMeasurementUnit.class.getName(),
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(resourcePermission.getCompanyId()),
						resourcePermission.getRoleId(), actionId)) {

					_resourcePermissionLocalService.addResourcePermission(
						resourcePermission.getCompanyId(),
						CPMeasurementUnit.class.getName(),
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(resourcePermission.getCompanyId()),
						resourcePermission.getRoleId(), actionId);
				}
			}

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(),
				"MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS");
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}