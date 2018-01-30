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

package com.liferay.commerce.cart.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_WISH_LISTS,
		"mvc.command.name=editCommerceWishList"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceWishListMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CommerceCart commerceCart = _actionHelper.getCommerceCart(
				renderRequest);

			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_CART, commerceCart);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return "/wish_list/edit_wish_list.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceWishListMVCRenderCommand.class);

	@Reference
	private ActionHelper _actionHelper;

}