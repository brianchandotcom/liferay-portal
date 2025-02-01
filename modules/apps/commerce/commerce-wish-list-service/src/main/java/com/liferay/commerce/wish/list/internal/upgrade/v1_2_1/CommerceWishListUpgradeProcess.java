/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.internal.upgrade.v1_2_1;

import com.liferay.commerce.wish.list.constants.CommerceWishListActionKeys;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Crescenzo Rega
 */
public class CommerceWishListUpgradeProcess extends UpgradeProcess {

	public CommerceWishListUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				"com.liferay.commerce.wish.list", "MANAGE_COMMERCE_WISH_LISTS");

		if (resourceAction == null) {
			return;
		}

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					"com.liferay.commerce.wish.list")) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) ||
				(resourcePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				continue;
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(),
					resourcePermission.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					CommerceWishListActionKeys.ADD_COMMERCE_WISH_LIST)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(),
					resourcePermission.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					CommerceWishListActionKeys.ADD_COMMERCE_WISH_LIST);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(),
					resourcePermission.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					CommerceWishListActionKeys.VIEW_COMMERCE_WISH_LISTS)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(),
					resourcePermission.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					CommerceWishListActionKeys.VIEW_COMMERCE_WISH_LISTS);
			}

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(),
				resourcePermission.getName(), resourcePermission.getScope(),
				resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(),
				"MANAGE_COMMERCE_WISH_LISTS");
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}