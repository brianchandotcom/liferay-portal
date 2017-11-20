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

package com.liferay.commerce.price.list.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.exception.NoSuchPriceListException;
import com.liferay.commerce.price.CommercePriceListQualificationTypeRegistry;
import com.liferay.commerce.price.list.web.internal.display.context.CommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.service.CommercePriceListQualificationTypeRelService;
import com.liferay.commerce.service.CommercePriceListService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommercePriceList"
	},
	service = MVCRenderCommand.class
)
public class EditCommercePriceListMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			CommercePriceListDisplayContext commercePriceListDisplayContext =
				new CommercePriceListDisplayContext(
					_commercePriceListActionHelper, _commerceCurrencyService,
					_commercePriceListQualificationTypeRegistry,
					_commercePriceListQualificationTypeRelService,
					_commercePriceListService, httpServletRequest,
					_itemSelector);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commercePriceListDisplayContext);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPriceListException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/edit_price_list_screen_navigation.jsp";
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference
	private CommercePriceListQualificationTypeRegistry
		_commercePriceListQualificationTypeRegistry;

	@Reference
	private CommercePriceListQualificationTypeRelService
		_commercePriceListQualificationTypeRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}