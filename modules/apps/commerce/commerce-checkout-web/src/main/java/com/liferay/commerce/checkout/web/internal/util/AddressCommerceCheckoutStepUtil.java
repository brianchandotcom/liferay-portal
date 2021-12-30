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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.ActionRequest;

/**
 * @author Luca Pellizzon
 */
public class AddressCommerceCheckoutStepUtil {

	public AddressCommerceCheckoutStepUtil(
		CommerceAccountLocalService commerceAccountLocalService,
		int commerceAddressType, CommerceOrderService commerceOrderService,
		CommerceAddressService commerceAddressService,
		ModelResourcePermission<CommerceOrder>
			commerceOrderModelResourcePermission) {

		_commerceAccountLocalService = commerceAccountLocalService;
		_commerceAddressType = commerceAddressType;
		_commerceOrderService = commerceOrderService;
		_commerceAddressService = commerceAddressService;
		_commerceOrderModelResourcePermission =
			commerceOrderModelResourcePermission;
	}

	protected CommerceOrder updateCommerceOrderAddress(
			ActionRequest actionRequest, String paramName)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String commerceOrderUuid = ParamUtil.getString(
			actionRequest, "commerceOrderUuid");

		CommerceOrder commerceOrder =
			_commerceOrderService.getCommerceOrderByUuidAndGroupId(
				commerceOrderUuid, commerceContext.getCommerceChannelGroupId());

		boolean newAddress = ParamUtil.getBoolean(actionRequest, "newAddress");

		long commerceAddressId = ParamUtil.getLong(actionRequest, paramName);

		if (newAddress) {
			CommerceAddress commerceAddress = _addCommerceAddress(
				commerceOrder, actionRequest);

			commerceAddressId = commerceAddress.getCommerceAddressId();
		}

		_commerceOrderModelResourcePermission.check(
			themeDisplay.getPermissionChecker(), commerceOrder,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		boolean useAsBilling = ParamUtil.getBoolean(
			actionRequest, "use-as-billing");

		if (useAsBilling) {
			_commerceAddressType =
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;
		}

		if (Objects.equals(
				CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME,
				paramName) &&
			useAsBilling) {

			CommerceAddress commerceAddress =
				_commerceAddressService.getCommerceAddress(commerceAddressId);

			_commerceAddressService.updateCommerceAddress(
				commerceAddressId, commerceAddress.getName(),
				commerceAddress.getDescription(), commerceAddress.getStreet1(),
				commerceAddress.getStreet2(), commerceAddress.getStreet3(),
				commerceAddress.getCity(), commerceAddress.getZip(),
				commerceAddress.getRegionId(), commerceAddress.getCountryId(),
				commerceAddress.getPhoneNumber(), _commerceAddressType, null);

			commerceOrder.setBillingAddressId(commerceAddressId);

			actionRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);

			return updateCommerceOrderAddress(
				commerceOrder, commerceAddressId, commerceAddressId,
				commerceContext);
		}

		if (Objects.equals(
				CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME,
				paramName) &&
			Objects.equals(
				commerceOrder.getShippingAddressId(),
				commerceOrder.getBillingAddressId()) &&
			!useAsBilling) {

			return updateCommerceOrderAddress(
				commerceOrder, 0, commerceAddressId, commerceContext);
		}

		if (Objects.equals(
				CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME,
				paramName) &&
			!Objects.equals(
				commerceOrder.getShippingAddressId(),
				commerceOrder.getBillingAddressId()) &&
			!useAsBilling) {

			return updateCommerceOrderAddress(
				commerceOrder, commerceOrder.getBillingAddressId(),
				commerceAddressId, commerceContext);
		}

		if (Objects.equals(
				CommerceCheckoutWebKeys.BILLING_ADDRESS_PARAM_NAME,
				paramName)) {

			return updateCommerceOrderAddress(
				commerceOrder, commerceAddressId,
				commerceOrder.getShippingAddressId(), commerceContext);
		}

		return commerceOrder;
	}

	protected CommerceOrder updateCommerceOrderAddress(
			CommerceOrder commerceOrder, long billingAddressId,
			long shippingAddressId, CommerceContext commerceContext)
		throws Exception {

		return _commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(), billingAddressId,
			shippingAddressId, commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingAmount(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus(), commerceContext);
	}

	private CommerceAddress _addCommerceAddress(
			CommerceOrder commerceOrder, ActionRequest actionRequest)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());

		boolean useAsBilling = ParamUtil.getBoolean(
			actionRequest, "use-as-billing");

		if (useAsBilling) {
			_commerceAddressType =
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;
		}

		if (commerceOrder.isGuestOrder()) {
			String email = ParamUtil.getString(actionRequest, "email");

			CommerceAccount commerceAccount =
				_commerceAccountLocalService.addCommerceAccount(
					name, CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
					email, null, CommerceAccountConstants.ACCOUNT_TYPE_GUEST,
					true, null, serviceContext);

			commerceOrder.setCommerceAccountId(
				commerceAccount.getCommerceAccountId());

			commerceOrder = _commerceOrderService.updateCommerceOrder(
				commerceOrder);
		}

		return _commerceAddressService.addCommerceAddress(
			AccountEntry.class.getName(), commerceOrder.getCommerceAccountId(),
			name, description, street1, street2, street3, city, zip, regionId,
			countryId, phoneNumber, _commerceAddressType, serviceContext);
	}

	private final CommerceAccountLocalService _commerceAccountLocalService;
	private final CommerceAddressService _commerceAddressService;
	private int _commerceAddressType;
	private final ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;
	private final CommerceOrderService _commerceOrderService;

}