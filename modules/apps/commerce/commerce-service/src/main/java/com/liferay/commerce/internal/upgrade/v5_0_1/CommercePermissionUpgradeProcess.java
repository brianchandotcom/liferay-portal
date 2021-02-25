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

package com.liferay.commerce.internal.upgrade.v5_0_1;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Riccardo Alberti
 */
public class CommercePermissionUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	public CommercePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, String> commerceResourceActionMap =
			_getCommerceResourceActionMap();

		StringBundler sb = new StringBundler(6);

		sb.append("select ResourcePermissionId from ResourcePermission where ");
		sb.append("name in ('90', '");
		sb.append(_COMMERCE_DISCOUNT_WEB_PORTLET);
		sb.append("', '");
		sb.append(_COMMERCE_PRICE_LIST_WEB_PORTLET);
		sb.append("')");

		try (Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(sb.toString())) {

			while (rs.next()) {
				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						rs.getLong(1));

				String resourceName = resourcePermission.getName();

				if (Objects.equals(
						resourceName, _COMMERCE_DISCOUNT_WEB_PORTLET)) {

					_replaceCommerceDiscountResourcePermission(
						resourcePermission);
				}
				else if (Objects.equals(
							resourceName, _COMMERCE_PRICE_LIST_WEB_PORTLET)) {

					_replaceCommercePriceListResourcePermission(
						resourcePermission);
				}
				else if (Objects.equals(resourceName, "90")) {
					_replaceResourcePermission(
						commerceResourceActionMap, resourcePermission);
				}
			}
		}

		_deleteResourceActions(_COMMERCE_DISCOUNT_WEB_PORTLET);
		_deleteResourceActions(_COMMERCE_PRICE_LIST_WEB_PORTLET);
		_deleteResourceActions();
	}

	private void _deleteResourceActions() {
		for (String commerceActionId : _COMMERCE_ACTION_IDS) {
			ResourceAction resourceAction =
				_resourceActionLocalService.fetchResourceAction(
					"90", commerceActionId);

			if (resourceAction == null) {
				continue;
			}

			_resourceActionLocalService.deleteResourceAction(resourceAction);
		}
	}

	private void _deleteResourceActions(String name) {
		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(name);

		for (ResourceAction resourceAction : resourceActions) {
			_resourceActionLocalService.deleteResourceAction(resourceAction);
		}
	}

	private Map<String, String> _getCommerceResourceActionMap()
		throws Exception {

		Map<String, String> commerceResourceActionMap = new HashMap<>();

		String sql = _replaceCommerceActionIds(
			"select actionId, name from ResourceAction where actionId in " +
				"([$COMMERCE_ACTION_IDS$]) and name != '90'",
			"[$COMMERCE_ACTION_IDS$]");

		try (Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(sql)) {

			while (rs.next()) {
				commerceResourceActionMap.put(
					rs.getString("actionId"), rs.getString("name"));
			}
		}

		return commerceResourceActionMap;
	}

	private String _replaceCommerceActionIds(
		String sql, String queryPlaceholder) {

		StringBundler sb = new StringBundler(_COMMERCE_ACTION_IDS.length * 3);

		for (int i = 0; i < _COMMERCE_ACTION_IDS.length; i++) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(_COMMERCE_ACTION_IDS[i]);
			sb.append(StringPool.APOSTROPHE);

			if (i != (_COMMERCE_ACTION_IDS.length - 1)) {
				sb.append(", ");
			}
		}

		return StringUtil.replace(sql, queryPlaceholder, sb.toString());
	}

	private void _replaceCommerceDiscountResourcePermission(
			ResourcePermission resourcePermission)
		throws Exception {

		_setResourcePermission(
			resourcePermission.getCompanyId(),
			_COMMERCE_DISCOUNT_PRICING_PORTLET, _COMMERCE_DISCOUNT_WEB_PORTLET,
			resourcePermission.getPrimKey(), resourcePermission.getRoleId(),
			_resourceActionLocalService.getResourceActions(
				resourcePermission.getName()),
			resourcePermission.getScope());

		_resourcePermissionLocalService.deleteResourcePermission(
			resourcePermission);
	}

	private void _replaceCommercePriceListResourcePermission(
			ResourcePermission resourcePermission)
		throws Exception {

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(
				resourcePermission.getName());

		_setResourcePermission(
			resourcePermission.getCompanyId(),
			_COMMERCE_PRICE_LIST_PRICING_PORTLET,
			_COMMERCE_PRICE_LIST_WEB_PORTLET, resourcePermission.getPrimKey(),
			resourcePermission.getRoleId(), resourceActions,
			resourcePermission.getScope());

		_setResourcePermission(
			resourcePermission.getCompanyId(),
			_COMMERCE_PROMOTION_PRICING_PORTLET,
			_COMMERCE_PRICE_LIST_WEB_PORTLET, resourcePermission.getPrimKey(),
			resourcePermission.getRoleId(), resourceActions,
			resourcePermission.getScope());

		_resourcePermissionLocalService.deleteResourcePermission(
			resourcePermission);
	}

	private void _replaceResourcePermission(
			Map<String, String> commerceResourceActionMap,
			ResourcePermission resourcePermission)
		throws Exception {

		for (String commerceActionId : _COMMERCE_ACTION_IDS) {
			if (!resourcePermission.hasActionId(commerceActionId)) {
				continue;
			}

			String resourceActionName = commerceResourceActionMap.get(
				commerceActionId);

			if (resourceActionName == null) {
				continue;
			}

			resourcePermission.removeResourceAction(commerceActionId);

			_resourcePermissionLocalService.setResourcePermissions(
				resourcePermission.getCompanyId(), resourceActionName,
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(),
				new String[] {commerceActionId});
		}
	}

	private void _setResourcePermission(
			long companyId, String newName, String oldName, String primKey,
			long roleId, List<ResourceAction> resourceActions, int scope)
		throws Exception {

		if (primKey.equals(oldName)) {
			primKey = newName;
		}

		Stream<ResourceAction> stream = resourceActions.stream();

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, newName, scope, primKey, roleId,
			stream.map(
				ResourceAction::getActionId
			).toArray(
				String[]::new
			));
	}

	private static final String[] _COMMERCE_ACTION_IDS = {
		"ADD_ACCOUNT", "ADD_ACCOUNT_GROUP", "ADD_COMMERCE_BRAND",
		"ADD_COMMERCE_BOM_DEFINITION", "ADD_COMMERCE_BOM_FOLDER",
		"ADD_COMMERCE_CATALOG", "ADD_COMMERCE_CHANNEL",
		"ADD_COMMERCE_DATA_INTEGRATION_PROCESS", "ADD_COMMERCE_DISCOUNT",
		"ADD_COMMERCE_MODEL", "ADD_COMMERCE_PRICE_LIST",
		"ADD_COMMERCE_PRICING_CLASS", "ADD_COMMERCE_PRODUCT_OPTION",
		"ADD_COMMERCE_PRODUCT_OPTION_CATEGORY",
		"ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION", "ADD_WAREHOUSE",
		"MANAGE_ALL_ACCOUNTS", "MANAGE_AVAILABLE_ACCOUNTS",
		"MANAGE_COMMERCE_AVAILABILITY_ESTIMATES", "MANAGE_COMMERCE_COUNTRIES",
		"MANAGE_COMMERCE_CURRENCIES", "MANAGE_COMMERCE_HEALTH_STATUS",
		"MANAGE_COMMERCE_ORDER_PRICES",
		"MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS",
		"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES", "MANAGE_COMMERCE_SHIPMENTS",
		"MANAGE_COMMERCE_SUBSCRIPTIONS", "MANAGE_INVENTORY",
		"VIEW_COMMERCE_ACCOUNT_GROUPS", "VIEW_COMMERCE_CATALOGS",
		"VIEW_COMMERCE_CHANNELS", "VIEW_COMMERCE_DISCOUNTS"
	};

	private static final String _COMMERCE_DISCOUNT_PRICING_PORTLET =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommerceDiscountPortlet";

	private static final String _COMMERCE_DISCOUNT_WEB_PORTLET =
		"com_liferay_commerce_discount_web_internal_portlet_" +
			"CommerceDiscountPortlet";

	private static final String _COMMERCE_PRICE_LIST_PRICING_PORTLET =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommercePriceListPortlet";

	private static final String _COMMERCE_PRICE_LIST_WEB_PORTLET =
		"com_liferay_commerce_price_list_web_internal_portlet_" +
			"CommercePriceListPortlet";

	private static final String _COMMERCE_PROMOTION_PRICING_PORTLET =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommercePromotionPortlet";

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}