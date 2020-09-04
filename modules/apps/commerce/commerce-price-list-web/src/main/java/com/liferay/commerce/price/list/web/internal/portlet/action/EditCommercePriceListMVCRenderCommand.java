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

import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListPortletKeys;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.web.internal.display.context.CommercePriceListDisplayContext;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePriceListPortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommercePriceList"
	},
	service = MVCRenderCommand.class
)
public class EditCommercePriceListMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		CommercePriceListDisplayContext commercePriceListDisplayContext =
			new CommercePriceListDisplayContext(
				_commercePriceListActionHelper, _commerceAccountService,
				_commerceAccountGroupLocalService, _commerceCatalogLocalService,
				_commerceCurrencyLocalService,
				_commercePriceListAccountRelService,
				_commercePriceListCommerceAccountGroupRelService,
				_commercePriceListModelResourcePermission,
				_commercePriceListService,
				_portal.getHttpServletRequest(renderRequest), _itemSelector);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commercePriceListDisplayContext);

		return "/edit_price_list_screen_navigation.jsp";
	}

	@Reference
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}