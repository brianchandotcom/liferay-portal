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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.order:Integer=1",
		"javax.portlet.name=" + CommerceCheckoutPortletKeys.COMMERCE_CHECKOUT,
		"mvc.command.name=viewCheckoutStepBilling"
	},
	service = MVCRenderCommand.class
)
public class ViewCheckoutStepBillingMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			"view_checkout_step.jsp-jspPage", "/checkout_step/billing.jsp");
		renderRequest.setAttribute(
			"view_checkout_step.jsp-mvcActionCommandName",
			"editCheckoutStepBilling");

		return "/view_checkout_step.jsp";
	}

}