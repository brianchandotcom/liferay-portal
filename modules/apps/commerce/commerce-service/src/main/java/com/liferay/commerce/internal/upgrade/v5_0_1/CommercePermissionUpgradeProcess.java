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

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
		Map<String, String> resourceActionNames = _getResourceActionNames();

		StringBundler sb = new StringBundler(6);

		sb.append("select ResourcePermissionId from ResourcePermission where ");
		sb.append("name in ('90', '");
		sb.append(_PORTLET_NAME_COMMERCE_DISCOUNT);
		sb.append("', '");
		sb.append(_PORTLET_NAME_COMMERCE_PRICE_LIST);
		sb.append("')");

		try (Statement statement = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sb.toString())) {

			while (resultSet.next()) {
				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						resultSet.getLong(1));

				if (Objects.equals(
						resourcePermission.getName(),
						_PORTLET_NAME_COMMERCE_DISCOUNT)) {

					_setResourcePermissions(
						resourcePermission.getCompanyId(),
						_PORTLET_NAME_COMMERCE_DISCOUNT_PRICING,
						_PORTLET_NAME_COMMERCE_DISCOUNT,
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(),
						_resourceActionLocalService.getResourceActions(
							resourcePermission.getName()),
						resourcePermission.getScope());

					_resourcePermissionLocalService.deleteResourcePermission(
						resourcePermission);
				}
				else if (Objects.equals(
							resourcePermission.getName(),
							_PORTLET_NAME_COMMERCE_PRICE_LIST)) {

					List<ResourceAction> resourceActions =
						_resourceActionLocalService.getResourceActions(
							resourcePermission.getName());

					_setResourcePermissions(
						resourcePermission.getCompanyId(),
						_PORTLET_NAME_COMMERCE_PRICE_LIST_PRICING,
						_PORTLET_NAME_COMMERCE_PRICE_LIST,
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(), resourceActions,
						resourcePermission.getScope());

					_setResourcePermissions(
						resourcePermission.getCompanyId(),
						_PORTLET_NAME_COMMERCE_PROMOTION_PRICING,
						_PORTLET_NAME_COMMERCE_PRICE_LIST,
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(), resourceActions,
						resourcePermission.getScope());

					_resourcePermissionLocalService.deleteResourcePermission(
						resourcePermission);
				}
				else if (Objects.equals(resourcePermission.getName(), "90")) {
					_setResourcePermissions(
						resourceActionNames, resourcePermission);
				}
			}
		}

		_deleteResourceActions(_PORTLET_NAME_COMMERCE_DISCOUNT);
		_deleteResourceActions(_PORTLET_NAME_COMMERCE_PRICE_LIST);
		_deleteResourceActions();
	}

	private void _deleteResourceActions() {
		for (String actionId : _ACTION_IDS) {
			ResourceAction resourceAction =
				_resourceActionLocalService.fetchResourceAction("90", actionId);

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

	private Map<String, String> _getResourceActionNames() throws Exception {
		Map<String, String> resourceActionNames = new HashMap<>();

		StringBundler sb = new StringBundler();

		sb.append("select name, actionId from ResourceAction where name != ");
		sb.append("'90' and actionId in (");

		for (int i = 0; i < _ACTION_IDS.length; i++) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(_ACTION_IDS[i]);
			sb.append(StringPool.APOSTROPHE);

			if (i != (_ACTION_IDS.length - 1)) {
				sb.append(", ");
			}
		}

		sb.append(")");

		try (Statement statement = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sb.toString())) {

			while (resultSet.next()) {
				resourceActionNames.put(
					resultSet.getString("actionId"),
					resultSet.getString("name"));
			}
		}

		return resourceActionNames;
	}

	private void _setResourcePermissions(
			long companyId, String newName, String oldName, String primKey,
			long roleId, List<ResourceAction> resourceActions, int scope)
		throws Exception {

		if (primKey.equals(oldName)) {
			primKey = newName;
		}

		Stream<ResourceAction> stream = resourceActions.stream();

		List<String> resourceActionIds = stream.map(
			ResourceAction::getActionId
		).collect(
			Collectors.toList()
		);

		_resourceActionLocalService.checkResourceActions(
			newName, resourceActionIds);

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, newName, scope, primKey, roleId,
			resourceActionIds.toArray(new String[0]));
	}

	private void _setResourcePermissions(
			Map<String, String> resourceActionNames,
			ResourcePermission resourcePermission)
		throws Exception {

		for (String actionId : _ACTION_IDS) {
			if (!resourcePermission.hasActionId(actionId)) {
				continue;
			}

			String resourceActionName = resourceActionNames.get(actionId);

			if (resourceActionName == null) {
				continue;
			}

			resourcePermission.removeResourceAction(actionId);

			_resourcePermissionLocalService.setResourcePermissions(
				resourcePermission.getCompanyId(), resourceActionName,
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), new String[] {actionId});
		}
	}

	private static final String[] _ACTION_IDS = {
		"ADD_ACCOUNT", "ADD_ACCOUNT_GROUP", "ADD_COMMERCE_BRAND",
		"ADD_COMMERCE_BOM_DEFINITION", "ADD_COMMERCE_BOM_FOLDER",
		"ADD_COMMERCE_CATALOG", "ADD_COMMERCE_CHANNEL",
		"ADD_COMMERCE_DATA_INTEGRATION_PROCESS", "ADD_COMMERCE_DISCOUNT",
		"ADD_COMMERCE_MODEL", "ADD_COMMERCE_PRICE_LIST",
		"ADD_COMMERCE_PRICING_CLASS", "ADD_COMMERCE_PRODUCT_OPTION",
		"ADD_COMMERCE_PRODUCT_OPTION_CATEGORY",
		"ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION", "ADD_WAREHOUSE",
		"MANAGE_ALL_ACCOUNTS", "MANAGE_AVAILABLE_ACCOUNTS",
		"MANAGE_COMMERCE_AVAILABILITY_ESTIMATES", "MANAGE_COMMERCE_CURRENCIES",
		"MANAGE_COMMERCE_HEALTH_STATUS", "MANAGE_COMMERCE_ORDER_PRICES",
		"MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS",
		"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES", "MANAGE_COMMERCE_SHIPMENTS",
		"MANAGE_COMMERCE_SUBSCRIPTIONS", "MANAGE_INVENTORY",
		"VIEW_COMMERCE_ACCOUNT_GROUPS", "VIEW_COMMERCE_CATALOGS",
		"VIEW_COMMERCE_CHANNELS", "VIEW_COMMERCE_DISCOUNTS"
	};

	private static final String _PORTLET_NAME_COMMERCE_DISCOUNT =
		"com_liferay_commerce_discount_web_internal_portlet_" +
			"CommerceDiscountPortlet";

	private static final String _PORTLET_NAME_COMMERCE_DISCOUNT_PRICING =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommerceDiscountPortlet";

	private static final String _PORTLET_NAME_COMMERCE_PRICE_LIST =
		"com_liferay_commerce_price_list_web_internal_portlet_" +
			"CommercePriceListPortlet";

	private static final String _PORTLET_NAME_COMMERCE_PRICE_LIST_PRICING =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommercePriceListPortlet";

	private static final String _PORTLET_NAME_COMMERCE_PROMOTION_PRICING =
		"com_liferay_commerce_pricing_web_internal_portlet_" +
			"CommercePromotionPortlet";

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}