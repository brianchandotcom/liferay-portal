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

package com.liferay.commerce.checkout.web.internal.portlet;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutPortletKeys;
import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.util.CommerceCheckoutStepRegistry;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.DynamicRenderRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-checkout",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce Checkout",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + CommerceCheckoutPortletKeys.COMMERCE_CHECKOUT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CommerceCheckoutPortlet.class, Portlet.class}
)
public class CommerceCheckoutPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			String mvcRenderCommandName = ParamUtil.getString(
				renderRequest, "mvcRenderCommandName");

			if (Validator.isNull(mvcRenderCommandName)) {
				mvcRenderCommandName =
					_commerceCheckoutStepRegistry.
						getFirstMVCRenderCommandName();

				renderRequest = new DynamicRenderRequest(
					renderRequest,
					Collections.singletonMap(
						"mvcRenderCommandName",
						new String[] {mvcRenderCommandName}));
			}

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			CommerceCart commerceCart =
				_commerceCartHelper.getCurrentCommerceCart(
					httpServletRequest, httpServletResponse);

			renderRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CART, commerceCart);

			String previousMVCRenderCommandName =
				_commerceCheckoutStepRegistry.getPreviousMVCRenderCommandName(
					mvcRenderCommandName);

			renderRequest.setAttribute(
				CommerceCheckoutWebKeys.BACK_URL,
				getCheckoutStepUrl(
					renderResponse, commerceCart,
					previousMVCRenderCommandName));

			String nextMVCRenderCommandName =
				_commerceCheckoutStepRegistry.getNextMVCRenderCommandName(
					mvcRenderCommandName);

			renderRequest.setAttribute(
				WebKeys.REDIRECT,
				getCheckoutStepUrl(
					renderResponse, commerceCart, nextMVCRenderCommandName));

			super.render(renderRequest, renderResponse);
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	protected String getCheckoutStepUrl(
		RenderResponse renderResponse, CommerceCart commerceCart,
		String mvcRenderCommandName) {

		if ((commerceCart == null) || Validator.isNull(mvcRenderCommandName)) {
			return null;
		}

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		portletURL.setParameter(
			"commerceCartId", String.valueOf(commerceCart.getCommerceCartId()));

		return portletURL.toString();
	}

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceCheckoutStepRegistry _commerceCheckoutStepRegistry;

	@Reference
	private Portal _portal;

}