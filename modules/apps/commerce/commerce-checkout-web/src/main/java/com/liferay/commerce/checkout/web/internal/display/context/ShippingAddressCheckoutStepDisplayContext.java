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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alec Sloan
 */
public class ShippingAddressCheckoutStepDisplayContext
	extends BaseAddressCheckoutStepDisplayContext {

	public ShippingAddressCheckoutStepDisplayContext(
		AccountRoleLocalService accountRoleLocalService,
		ModelResourcePermission<AccountEntry>
			accountEntryModelResourcePermission,
		CommerceAddressService commerceAddressService,
		HttpServletRequest httpServletRequest,
		PortletResourcePermission portletResourcePermission) {

		super(
			accountRoleLocalService, accountEntryModelResourcePermission,
			commerceAddressService, httpServletRequest,
			portletResourcePermission);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return commerceAddressService.getShippingCommerceAddresses(
			commerceOrder.getCompanyId(), AccountEntry.class.getName(),
			commerceOrder.getCommerceAccountId());
	}

	@Override
	public String getCommerceCountrySelectionColumnName() {
		return "shippingAllowed";
	}

	@Override
	public String getCommerceCountrySelectionMethodName() {
		return "get-shipping-countries";
	}

	@Override
	public long getDefaultCommerceAddressId() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		long shippingAddressId = commerceOrder.getShippingAddressId();

		if (shippingAddressId == 0) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			if (commerceAccount != null) {
				shippingAddressId =
					commerceAccount.getDefaultShippingAddressId();
			}
		}

		return shippingAddressId;
	}

	@Override
	public String getParamName() {
		return CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME;
	}

	@Override
	public String getTitle() {
		return "shipping-address";
	}

}