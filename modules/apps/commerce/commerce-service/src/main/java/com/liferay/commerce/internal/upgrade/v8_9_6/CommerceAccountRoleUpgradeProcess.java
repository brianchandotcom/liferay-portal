/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_9_6;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys;
import com.liferay.commerce.discount.constants.CommerceDiscountActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Brian I. Kim
 */
public class CommerceAccountRoleUpgradeProcess extends UpgradeProcess {

	public CommerceAccountRoleUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				_updateCommerceCategoryManagerRolePermissions(
					companyId, _ROLE_NAME_ACCOUNT_CATEGORY_MANAGER);
				_updateCommerceDiscountManagerRolePermissions(
					companyId,
					AccountRoleConstants.ROLE_NAME_ACCOUNT_DISCOUNT_MANAGER);
			});
	}

	private void _addResourcePermission(
			long companyId, Role role, String resourceName, String actionId)
		throws PortalException {

		_resourcePermissionLocalService.addResourcePermission(
			companyId, resourceName, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), actionId);
	}

	private void _updateCommerceCategoryManagerRolePermissions(
			long companyId, String name)
		throws PortalException {

		_updateCommerceRoles(
			companyId, name, "com.liferay.asset.categories",
			ListUtil.fromString(ActionKeys.PERMISSIONS));

		_updateCommerceRoles(
			companyId, name, "com.liferay.asset.kernel.model.AssetCategory",
			ListUtil.fromArray(_ASSET_CATEGORY_ACTION_IDS));

		_updateCommerceRoles(
			companyId, name, "com.liferay.asset.kernel.model.AssetVocabulary",
			ListUtil.fromArray(_SHARED_MODEL_ACTION_IDS));

		_updateCommerceRoles(
			companyId, name, _PORTLET_NAME_ASSET_CATEGORIES_ADMIN,
			ListUtil.fromArray(_ASSET_CATEGORIES_ADMIN_PORTLET_ACTION_IDS));
	}

	private void _updateCommerceDiscountManagerRolePermissions(
			long companyId, String name)
		throws PortalException {

		_updateCommerceRoles(
			companyId, name, "com.liferay.commerce.currency",
			ListUtil.fromString(
				CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES));

		_updateCommerceRoles(
			companyId, name, "com.liferay.commerce.discount",
			ListUtil.fromArray(_COMMERCE_DISCOUNT_ACTION_IDS));

		_updateCommerceRoles(
			companyId, name,
			"com.liferay.commerce.discount.model.CommerceDiscount",
			ListUtil.fromArray(_SHARED_MODEL_ACTION_IDS));

		_updateCommerceRoles(
			companyId, name, _PORTLET_NAME_COMMERCE_DISCOUNT_PRICING,
			ListUtil.fromArray(_COMMERCE_DISCOUNT_PORTLET_ACTION_IDS));
	}

	private void _updateCommerceRoles(
			long companyId, String name, String resourceName,
			List<String> actionIds)
		throws PortalException {

		_resourceActionLocalService.checkResourceActions(
			resourceName, actionIds);

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role == null) {
			return;
		}

		for (String actionId : actionIds) {
			_addResourcePermission(companyId, role, resourceName, actionId);
		}
	}

	private static final String[] _ASSET_CATEGORIES_ADMIN_PORTLET_ACTION_IDS = {
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES
	};

	private static final String[] _ASSET_CATEGORY_ACTION_IDS = {
		ActionKeys.ADD_CATEGORY, ActionKeys.DELETE, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.VIEW
	};

	private static final String[] _COMMERCE_DISCOUNT_ACTION_IDS = {
		CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT,
		ActionKeys.PERMISSIONS,
		CommerceDiscountActionKeys.VIEW_COMMERCE_DISCOUNTS
	};

	private static final String[] _COMMERCE_DISCOUNT_PORTLET_ACTION_IDS = {
		ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW
	};

	private static final String _PORTLET_NAME_ASSET_CATEGORIES_ADMIN =
		"com_liferay_asset_categories_admin_web_portlet_" +
			"AssetCategoriesAdminPortlet";

	private static final String _PORTLET_NAME_COMMERCE_DISCOUNT_PRICING =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommerceDiscountPortlet";

	private static final String _ROLE_NAME_ACCOUNT_CATEGORY_MANAGER =
		"Category Manager";

	private static final String[] _SHARED_MODEL_ACTION_IDS = {
		ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
		ActionKeys.VIEW
	};

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}