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

package com.liferay.commerce.cart.content.web.internal.portlet;

import com.liferay.commerce.cart.constants.CommerceCartConstants;
import com.liferay.commerce.cart.constants.CommerceCartPortletKeys;
import com.liferay.commerce.cart.constants.CommerceCartWebKeys;
import com.liferay.commerce.cart.content.web.internal.display.context.CommerceCartContentDisplayContext;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.CommerceCartItemLocalService;
import com.liferay.commerce.cart.service.CommerceCartLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-cart-content",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce Cart Content",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/cart/view.jsp",
		"javax.portlet.name=" + CommerceCartPortletKeys.COMMERCE_CART_CONTENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CommerceCartContentPortlet.class, Portlet.class}
)
public class CommerceCartContentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			int type = ParamUtil.getInteger(
				httpServletRequest, "type",
				CommerceCartConstants.COMMERCE_CART_TYPE_CART);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			CommerceCart commerceCart =
				_commerceCartLocalService.getUserCurrentCommerceCart(
					type, serviceContext);

			renderRequest.setAttribute(
				CommerceCartWebKeys.COMMERCE_CART, commerceCart);

			CommerceCartContentDisplayContext
				commerceCartContentDisplayContext =
					new CommerceCartContentDisplayContext(
						httpServletRequest, _commerceCartItemLocalService,
						_commerceCartLocalService,
						_cpFriendlyURLEntryLocalService, _portal,
						CommerceCartContentPortlet.class.getSimpleName());

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceCartContentDisplayContext);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCartContentPortlet.class);

	@Reference
	private CommerceCartItemLocalService _commerceCartItemLocalService;

	@Reference
	private CommerceCartLocalService _commerceCartLocalService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}