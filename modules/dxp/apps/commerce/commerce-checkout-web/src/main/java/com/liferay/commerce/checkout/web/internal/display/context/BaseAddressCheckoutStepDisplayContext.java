/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseAddressCheckoutStepDisplayContext {

	public BaseAddressCheckoutStepDisplayContext(
			CommerceAddressService commerceAddressService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commerceAddressService = commerceAddressService;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		return _commerceAddressService.fetchCommerceAddress(commerceAddressId);
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		return _commerceAddressService.getCommerceAddresses(
			_commerceOrder.getGroupId(), _commerceOrder.getClassName(),
			_commerceOrder.getClassPK());
	}

	public abstract String getCommerceCountrySelectionColumnName();

	public abstract String getCommerceCountrySelectionMethodName();

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public abstract long getDefaultCommerceAddressId() throws PortalException;

	public abstract String getParamName();

	public abstract String getTitle();

	private final CommerceAddressService _commerceAddressService;
	private final CommerceOrder _commerceOrder;

}