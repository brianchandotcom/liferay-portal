/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.notification.constants.CommerceNotificationActionKeys;
import com.liferay.commerce.price.list.constants.CommercePriceListActionKeys;
import com.liferay.commerce.pricing.constants.CommercePricingClassActionKeys;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.product.constants.CPActionKeys;
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
			_checkAccountRole(
				AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER,
				serviceContext);
			_checkRole(AccountRoleConstants.ROLE_NAME_SUPPLIER, serviceContext);
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
					"MANAGE_COMMERCE_ORDER_PAYMENT_STATUSES",
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
					"MANAGE_COMMERCE_ORDER_PAYMENT_STATUSES",
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
			companyResourceActionIds.put(
				"com.liferay.commerce.catalog",
				new String[] {CPActionKeys.ADD_COMMERCE_CATALOG});
			companyResourceActionIds.put(
				"com.liferay.commerce.channel",
				new String[] {
					CommerceNotificationActionKeys.
						VIEW_COMMERCE_NOTIFICATION_QUEUE_ENTRIES,
					CommerceNotificationActionKeys.
						ADD_COMMERCE_NOTIFICATION_TEMPLATE
				});
			companyResourceActionIds.put(
				"com.liferay.commerce.model.CommerceOrderType",
				new String[] {ActionKeys.VIEW});
			companyResourceActionIds.put(
				"com.liferay.commerce.order",
				new String[] {
					"MANAGE_COMMERCE_ORDER_DELIVERY_TERMS",
					"MANAGE_COMMERCE_ORDER_NOTES",
					"MANAGE_COMMERCE_ORDER_PAYMENT_METHODS",
					"MANAGE_COMMERCE_ORDER_PAYMENT_STATUSES",
					"MANAGE_COMMERCE_ORDER_PAYMENT_TERMS",
					"MANAGE_COMMERCE_ORDER_PRICES",
					"MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES",
					"MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS"
				});
			companyResourceActionIds.put(
				"com.liferay.commerce.price.list",
				new String[] {
					CommercePriceListActionKeys.ADD_COMMERCE_PRICE_LIST
				});
			companyResourceActionIds.put(
				"com.liferay.commerce.pricing",
				new String[] {
					CommercePricingClassActionKeys.ADD_COMMERCE_PRICING_CLASS
				});
			companyResourceActionIds.put(
				"com.liferay.commerce.pricing.model.CommercePricingClass",
				new String[] {ActionKeys.VIEW});
			companyResourceActionIds.put(
				"com.liferay.commerce.product",
				new String[] {
					CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION,
					CPActionKeys.ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION,
					CPActionKeys.MANAGE_COMMERCE_PRODUCT_ATTACHMENTS,
					CPActionKeys.MANAGE_COMMERCE_PRODUCT_IMAGES,
					CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS,
					CPActionKeys.VIEW_COMMERCE_PRODUCT_ATTACHMENTS,
					CPActionKeys.VIEW_COMMERCE_PRODUCT_IMAGES
				});
			companyResourceActionIds.put(
				"com.liferay.commerce.shipment",
				new String[] {CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS});
			companyResourceActionIds.put(
				"com.liferay.commerce.tax",
				new String[] {
					CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES
				});
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
		CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
		CommercePricingPortletKeys.COMMERCE_PROMOTION,
		CPPortletKeys.COMMERCE_CATALOGS, CPPortletKeys.COMMERCE_CHANNELS,
		CPPortletKeys.CP_DEFINITIONS
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