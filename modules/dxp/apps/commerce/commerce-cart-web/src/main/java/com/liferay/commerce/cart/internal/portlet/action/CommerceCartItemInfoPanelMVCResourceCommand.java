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

package com.liferay.commerce.cart.internal.portlet.action;

import com.liferay.commerce.cart.constants.CommerceCartPortletKeys;
import com.liferay.commerce.cart.constants.CommerceCartWebKeys;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceCartPortletKeys.COMMERCE_CART,
		"mvc.command.name=commerceCartItemInfoPanel"
	},
	service = MVCResourceCommand.class
)
public class CommerceCartItemInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<CommerceCartItem> commerceCartItems =
			_actionHelper.getCommerceCartItems(resourceRequest);

		resourceRequest.setAttribute(
			CommerceCartWebKeys.COMMERCE_CART_ITEMS, commerceCartItems);

		include(resourceRequest, resourceResponse, "/cart_item_info_panel.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

}