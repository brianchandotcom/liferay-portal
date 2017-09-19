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

import com.liferay.commerce.checkout.web.internal.display.context.CheckoutStepBillingDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceCartBillingAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.name=billing",
		"commerce.checkout.step.order:Integer=5"
	},
	service = CommerceCheckoutStep.class
)
public class BillingCommerceCheckoutStep implements CommerceCheckoutStep {

	public boolean action(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			updateCommerceCartBilling(httpServletRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceAddressCityException ||
				e instanceof CommerceAddressCountryException ||
				e instanceof CommerceAddressNameException ||
				e instanceof CommerceAddressStreetException ||
				e instanceof CommerceCartBillingAddressException) {

				SessionErrors.add(httpServletRequest, e.getClass());

				return false;
			}
			else {
				throw e;
			}
		}

		return true;
	}

	public String getLabel(Locale locale) {
		return "BILLING_TO_CHANGE";
	}

	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return true;
	}

	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return true;
	}

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CheckoutStepBillingDisplayContext checkoutStepBillingDisplayContext =
			new CheckoutStepBillingDisplayContext(
				_commerceAddressService, httpServletRequest);

		httpServletRequest.setAttribute(
			"CommerceCheckoutStepDisplayContext",
			checkoutStepBillingDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/billing.jsp");
	}

	protected CommerceAddress addCommerceAddress(
			long addressUserId, HttpServletRequest httpServletRequest)
		throws PortalException {

		String name = ParamUtil.getString(httpServletRequest, "name");
		String description = ParamUtil.getString(
			httpServletRequest, "description");
		String street1 = ParamUtil.getString(httpServletRequest, "street1");
		String street2 = ParamUtil.getString(httpServletRequest, "street2");
		String street3 = ParamUtil.getString(httpServletRequest, "street3");
		String city = ParamUtil.getString(httpServletRequest, "city");
		String zip = ParamUtil.getString(httpServletRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			httpServletRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			httpServletRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(
			httpServletRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), httpServletRequest);

		return _commerceAddressService.addCommerceAddress(
			addressUserId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber, false, false,
			serviceContext);
	}

	protected void updateCommerceCartBilling(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceCartId = ParamUtil.getLong(
			httpServletRequest, "commerceCartId");

		CommerceCart commerceCart = _commerceCartService.getCommerceCart(
			commerceCartId);

		long billingAddressId = ParamUtil.getLong(
			httpServletRequest, "billingAddressId");

		if (billingAddressId <= 0) {
			CommerceAddress commerceAddress = addCommerceAddress(
				commerceCart.getUserId(), httpServletRequest);

			billingAddressId = commerceAddress.getCommerceAddressId();
		}

		_commerceCartService.updateCommerceCart(
			commerceCart.getCommerceCartId(), billingAddressId,
			commerceCart.getShippingAddressId());
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private JSPRenderer _jspRenderer;

}