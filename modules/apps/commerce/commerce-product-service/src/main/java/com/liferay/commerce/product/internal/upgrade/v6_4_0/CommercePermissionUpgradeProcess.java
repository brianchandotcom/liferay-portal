/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v6_4_0;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;

/**
 * @author Tancredi Covioli
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
		CompanyLocalService companyLocalService,
		CPTaxCategoryLocalService cpTaxCategoryLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_companyLocalService = companyLocalService;
		_cpTaxCategoryLocalService = cpTaxCategoryLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				"com.liferay.commerce.tax",
				"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES");

		if (resourceAction == null) {
			return;
		}

		_resourceActionLocalService.checkResourceActions(
			CPTaxCategory.class.getName(),
			Arrays.asList(
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.VIEW),
			true);
		_resourceActionLocalService.checkResourceActions(
			"com.liferay.commerce.tax",
			Arrays.asList(
				CPActionKeys.ADD_COMMERCE_PRODUCT_TAX_CATEGORIES,
				CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES),
			true);

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					"com.liferay.commerce.tax")) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) ||
				(resourcePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				continue;
			}

			_addResourcePermission(
				CPActionKeys.ADD_COMMERCE_PRODUCT_TAX_CATEGORIES,
				"com.liferay.commerce.tax", resourcePermission);
			_addResourcePermission(
				CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
				"com.liferay.commerce.tax", resourcePermission);
			_addResourcePermission(
				ActionKeys.DELETE, CPTaxCategory.class.getName(),
				resourcePermission);
			_addResourcePermission(
				ActionKeys.PERMISSIONS, CPTaxCategory.class.getName(),
				resourcePermission);
			_addResourcePermission(
				ActionKeys.UPDATE, CPTaxCategory.class.getName(),
				resourcePermission);
			_addResourcePermission(
				ActionKeys.VIEW, CPTaxCategory.class.getName(),
				resourcePermission);

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(),
				"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES");
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);

		_companyLocalService.forEachCompany(
			company -> {
				User user = company.getGuestUser();

				for (CPTaxCategory cpTaxCategory :
						_cpTaxCategoryLocalService.getCPTaxCategories(
							company.getCompanyId())) {

					long count =
						_resourcePermissionLocalService.
							getResourcePermissionsCount(
								cpTaxCategory.getCompanyId(),
								CPTaxCategory.class.getName(),
								ResourceConstants.SCOPE_INDIVIDUAL,
								String.valueOf(cpTaxCategory.getPrimaryKey()));

					if (count > 0) {
						continue;
					}

					_resourceLocalService.addResources(
						cpTaxCategory.getCompanyId(), 0, user.getUserId(),
						CPTaxCategory.class.getName(),
						cpTaxCategory.getCPTaxCategoryId(), false, false,
						false);
				}
			});
	}

	private void _addResourcePermission(
			String actionId, String name, ResourcePermission resourcePermission)
		throws Exception {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				resourcePermission.getCompanyId(), name,
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				resourcePermission.getCompanyId(), name,
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), actionId);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final CPTaxCategoryLocalService _cpTaxCategoryLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}