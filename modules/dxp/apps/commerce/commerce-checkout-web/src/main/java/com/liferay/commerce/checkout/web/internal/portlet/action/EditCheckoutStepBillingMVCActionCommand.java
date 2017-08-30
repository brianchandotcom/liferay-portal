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

package com.liferay.commerce.checkout.web.internal.portlet.action;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutPortletKeys;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceCartBillingAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceCheckoutPortletKeys.COMMERCE_CHECKOUT,
		"mvc.command.name=editCheckoutStepBilling"
	},
	service = MVCActionCommand.class
)
public class EditCheckoutStepBillingMVCActionCommand
	extends BaseMVCActionCommand {

	protected CommerceAddress addCommerceAddress(
			long addressUserId, ActionRequest actionRequest)
		throws PortalException {

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		return _commerceAddressService.addCommerceAddress(
			addressUserId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber, false, false,
			serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCommerceCartBilling(actionRequest);

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceAddressCityException ||
				e instanceof CommerceAddressCountryException ||
				e instanceof CommerceAddressNameException ||
				e instanceof CommerceAddressStreetException ||
				e instanceof CommerceCartBillingAddressException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "viewCheckoutStepBilling");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceCartBilling(ActionRequest actionRequest)
		throws PortalException {

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		CommerceCart commerceCart = _commerceCartService.getCommerceCart(
			commerceCartId);

		long billingAddressId = ParamUtil.getLong(
			actionRequest, "billingAddressId");

		if (billingAddressId <= 0) {
			CommerceAddress commerceAddress = addCommerceAddress(
				commerceCart.getUserId(), actionRequest);

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

}