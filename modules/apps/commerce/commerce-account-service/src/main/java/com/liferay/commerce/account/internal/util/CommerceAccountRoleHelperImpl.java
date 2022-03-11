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

package com.liferay.commerce.account.internal.util;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = CommerceAccountRoleHelper.class
)
public class CommerceAccountRoleHelperImpl
	implements CommerceAccountRoleHelper {

	@Override
	public void checkCommerceAccountRoles(ServiceContext serviceContext)
		throws PortalException {

		_checkRole(
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR,
			serviceContext);
		_checkRole(
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_BUYER, serviceContext);
		_checkRole(
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER,
			serviceContext);
	}

	private void _checkRole(String name, ServiceContext serviceContext)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			serviceContext.getCompanyId(), name);

		if (role == null) {
			AccountRole accountRole = _accountRoleLocalService.addAccountRole(
				serviceContext.getUserId(),
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, name,
				Collections.singletonMap(serviceContext.getLocale(), name),
				Collections.emptyMap());

			role = accountRole.getRole();

			_setRolePermissions(role, serviceContext);
		}

		if (CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR.equals(
				name)) {

			_setRolePermissions(role, serviceContext);
		}
	}

	private void _setRolePermissions(
			Role role, Map<String, String[]> resourceActionIds,
			ServiceContext serviceContext)
		throws PortalException {

		for (Map.Entry<String, String[]> entry : resourceActionIds.entrySet()) {
			_resourceActionLocalService.checkResourceActions(
				entry.getKey(), Arrays.asList(entry.getValue()));

			for (String actionId : entry.getValue()) {
				_resourcePermissionLocalService.addResourcePermission(
					serviceContext.getCompanyId(), entry.getKey(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					role.getRoleId(), actionId);
			}
		}
	}

	private void _setRolePermissions(Role role, ServiceContext serviceContext)
		throws PortalException {

		Map<String, String[]> resourceActionIds = new HashMap<>();

		String name = role.getName();

		if (name.equals(
				CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR)) {

			resourceActionIds.put(
				"com.liferay.commerce.account.model.CommerceAccount",
				new String[] {
					CommerceAccountActionKeys.MANAGE_ADDRESSES,
					CommerceAccountActionKeys.MANAGE_MEMBERS, ActionKeys.UPDATE,
					ActionKeys.VIEW, CommerceAccountActionKeys.VIEW_ADDRESSES,
					CommerceAccountActionKeys.VIEW_MEMBERS
				});
			resourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});
			resourceActionIds.put(
				"com.liferay.commerce.order",
				new String[] {
					"ADD_COMMERCE_ORDER", "APPROVE_OPEN_COMMERCE_ORDERS",
					"CHECKOUT_OPEN_COMMERCE_ORDERS", "DELETE_COMMERCE_ORDERS",
					"MANAGE_COMMERCE_ORDER_DELIVERY_TERMS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_METHODS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_TERMS",
					"MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS",
					"MANAGE_COMMERCE_ORDERS", "VIEW_BILLING_ADDRESS",
					"VIEW_COMMERCE_ORDERS", "VIEW_OPEN_COMMERCE_ORDERS"
				});
		}
		else if (name.equals(
					CommerceAccountConstants.ROLE_NAME_ACCOUNT_BUYER)) {

			resourceActionIds.put(
				AccountEntry.class.getName(),
				new String[] {
					AccountActionKeys.MANAGE_ADDRESSES,
					AccountActionKeys.VIEW_ADDRESSES
				});
			resourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});
			resourceActionIds.put(
				"com.liferay.commerce.order",
				new String[] {
					"ADD_COMMERCE_ORDER", "CHECKOUT_OPEN_COMMERCE_ORDERS",
					"MANAGE_COMMERCE_ORDER_DELIVERY_TERMS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_METHODS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_TERMS",
					"MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS",
					"VIEW_BILLING_ADDRESS", "VIEW_COMMERCE_ORDERS",
					"VIEW_OPEN_COMMERCE_ORDERS"
				});
		}
		else if (name.equals(
					CommerceAccountConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER)) {

			resourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});

			resourceActionIds.put(
				"com.liferay.commerce.order",
				new String[] {
					"ADD_COMMERCE_ORDER", "APPROVE_OPEN_COMMERCE_ORDERS",
					"CHECKOUT_OPEN_COMMERCE_ORDERS", "DELETE_COMMERCE_ORDERS",
					"MANAGE_COMMERCE_ORDER_DELIVERY_TERMS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_METHODS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_TERMS",
					"MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS",
					"MANAGE_COMMERCE_ORDERS", "VIEW_BILLING_ADDRESS",
					"VIEW_COMMERCE_ORDERS", "VIEW_OPEN_COMMERCE_ORDERS"
				});
		}

		_setRolePermissions(role, resourceActionIds, serviceContext);
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}