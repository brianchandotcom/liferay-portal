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
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceCheckoutPortletKeys.COMMERCE_CHECKOUT,
		"mvc.command.name=completeCheckout"
	},
	service = MVCActionCommand.class
)
public class CompleteCheckoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		CommerceOrder commerceOrder =
			_commerceOrderService.addCommerceOrderFromCart(
				commerceCartId, serviceContext);

		sendRedirect(
			actionRequest, actionResponse,
			getRedirect(actionResponse, commerceOrder));
	}

	protected String getRedirect(
		ActionResponse actionResponse, CommerceOrder commerceOrder) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "viewCheckoutStatus");
		portletURL.setParameter(
			"commerceOrderId",
			String.valueOf(commerceOrder.getCommerceOrderId()));

		return portletURL.toString();
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

}