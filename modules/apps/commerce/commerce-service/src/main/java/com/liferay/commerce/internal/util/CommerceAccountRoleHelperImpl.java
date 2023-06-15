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

package com.liferay.commerce.internal.util;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.util.CommerceAccountRoleHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortletKeys;

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
@Component(service = CommerceAccountRoleHelper.class)
public class CommerceAccountRoleHelperImpl
	implements CommerceAccountRoleHelper {

	@Override
	public void checkCommerceAccountRoles(ServiceContext serviceContext)
		throws PortalException {

		_checkAccountRole(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
			serviceContext);
		_checkAccountRole(
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER, serviceContext);
		_checkAccountRole(
			AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER,
			serviceContext);

		if (FeatureFlagManagerUtil.isEnabled("COMMERCE-10890")) {
			_checkRole(AccountRoleConstants.ROLE_NAME_SUPPLIER, serviceContext);
			_checkAccountRole(
				AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER,
				serviceContext);
		}
	}

	private void _checkAccountRole(String name, ServiceContext serviceContext)
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

		if (AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR.
				equals(name)) {

			_setRolePermissions(role, serviceContext);
		}
	}

	private void _checkRole(String name, ServiceContext serviceContext)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			serviceContext.getCompanyId(), name);

		if (role == null) {
			role = _roleLocalService.addRole(
				serviceContext.getUserId(), null, 0, name,
				Collections.singletonMap(serviceContext.getLocale(), name),
				Collections.emptyMap(), RoleConstants.TYPE_REGULAR, null,
				serviceContext);

			_setRolePermissions(role, serviceContext);
		}
	}

	private void _setRolePermissions(
			long companyId, String primaryKey,
			Map<String, String[]> resourceActionIds, Role role, int scope)
		throws PortalException {

		for (Map.Entry<String, String[]> entry : resourceActionIds.entrySet()) {
			_resourceActionLocalService.checkResourceActions(
				entry.getKey(), Arrays.asList(entry.getValue()));

			for (String actionId : entry.getValue()) {
				_resourcePermissionLocalService.addResourcePermission(
					companyId, entry.getKey(), scope, primaryKey,
					role.getRoleId(), actionId);
			}
		}
	}

	private void _setRolePermissions(Role role, ServiceContext serviceContext)
		throws PortalException {

		Map<String, String[]> companyResourceActionIds = new HashMap<>();
		Map<String, String[]> groupResourceActionIds = new HashMap<>();

		String name = role.getName();

		if (name.equals(
				AccountRoleConstants.
					REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR)) {

			companyResourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});

			groupResourceActionIds.put(
				AccountEntry.class.getName(),
				new String[] {
					AccountActionKeys.MANAGE_ADDRESSES,
					AccountActionKeys.ASSIGN_USERS, ActionKeys.UPDATE,
					ActionKeys.VIEW, AccountActionKeys.VIEW_ADDRESSES,
					AccountActionKeys.VIEW_USERS
				});
			groupResourceActionIds.put(
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
		else if (name.equals(AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER)) {
			companyResourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});

			groupResourceActionIds.put(
				AccountEntry.class.getName(),
				new String[] {
					AccountActionKeys.MANAGE_ADDRESSES,
					AccountActionKeys.VIEW_ADDRESSES
				});
			groupResourceActionIds.put(
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
					AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER)) {

			companyResourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});

			groupResourceActionIds.put(
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
		else if (name.equals(AccountRoleConstants.ROLE_NAME_SUPPLIER)) {
			for (String portletId : _SUPPLIER_CONTROL_PANEL_PORTLET_IDS) {
				companyResourceActionIds.put(
					portletId,
					new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL});
			}

			companyResourceActionIds.put(
				PortletKeys.PORTAL,
				new String[] {ActionKeys.VIEW_CONTROL_PANEL});
		}

		_setRolePermissions(
			serviceContext.getCompanyId(),
			String.valueOf(serviceContext.getCompanyId()),
			companyResourceActionIds, role, ResourceConstants.SCOPE_COMPANY);
		_setRolePermissions(
			serviceContext.getCompanyId(),
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			groupResourceActionIds, role,
			ResourceConstants.SCOPE_GROUP_TEMPLATE);
	}

	private static final String[] _SUPPLIER_CONTROL_PANEL_PORTLET_IDS = {
		CommercePortletKeys.COMMERCE_ORDER,
		CommercePricingPortletKeys.COMMERCE_DISCOUNT,
		CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
		CommercePricingPortletKeys.COMMERCE_PROMOTION,
		CPPortletKeys.COMMERCE_CATALOGS, CPPortletKeys.COMMERCE_CHANNELS,
		CPPortletKeys.COMMERCE_INVENTORY,
		CPPortletKeys.COMMERCE_INVENTORY_WAREHOUSE, CPPortletKeys.CP_DEFINITIONS
	};

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}