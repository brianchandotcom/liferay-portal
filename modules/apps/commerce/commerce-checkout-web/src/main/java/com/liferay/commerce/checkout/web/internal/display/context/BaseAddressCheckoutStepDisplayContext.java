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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.util.comparator.CommerceAddressNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 */
public abstract class BaseAddressCheckoutStepDisplayContext {

	public BaseAddressCheckoutStepDisplayContext(
		CommerceAddressService commerceAddressService,
		HttpServletRequest httpServletRequest) {

		this.commerceAddressService = commerceAddressService;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		return commerceAddressService.fetchCommerceAddress(commerceAddressId);
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		return commerceAddressService.getCommerceAddressesByCompanyId(
			_commerceOrder.getCompanyId(), CommerceAccount.class.getName(),
			_commerceOrder.getCommerceAccountId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CommerceAddressNameComparator());
	}

	public abstract String getCommerceCountrySelectionColumnName();

	public abstract String getCommerceCountrySelectionMethodName();

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public abstract long getDefaultCommerceAddressId() throws PortalException;

	public abstract String getParamName();

	public abstract String getTitle();

	public boolean isShippingUsedAsBilling() throws PortalException {
		CommerceAccount commerceAccount = _commerceOrder.getCommerceAccount();
		CommerceAddress shippingAddress = _commerceOrder.getShippingAddress();
		CommerceAddress billingAddress = _commerceOrder.getBillingAddress();

		if ((commerceAccount != null) &&
			(commerceAccount.getDefaultBillingAddressId() ==
				commerceAccount.getDefaultShippingAddressId()) &&
			(billingAddress != null) && (shippingAddress != null) &&
			(shippingAddress.getCommerceAddressId() ==
				billingAddress.getCommerceAddressId())) {

			return true;
		}

		return false;
	}

	protected final CommerceAddressService commerceAddressService;

	private final CommerceOrder _commerceOrder;

}