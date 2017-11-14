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

package com.liferay.commerce.cart.web.internal.portlet.action;

import com.liferay.commerce.cart.web.internal.display.context.CommerceCartItemDisplayContext;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART,
		"mvc.command.name=viewCommerceCartItems"
	},
	service = MVCRenderCommand.class
)
public class ViewCommerceCartItemsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			CommerceCartItemDisplayContext commerceCartItemDisplayContext =
				new CommerceCartItemDisplayContext(
					_actionHelper, httpServletRequest, _commerceCartItemService,
					_commercePriceCalculator, _commercePriceFormatter,
					_itemSelector);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceCartItemDisplayContext);
		}
		catch (PortalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());
		}

		return "/view_cart_items.jsp";
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommercePriceCalculator _commercePriceCalculator;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}