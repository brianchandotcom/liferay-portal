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
import com.liferay.commerce.exception.NoSuchCartException;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

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
	service = MVCActionCommand.class
)
public class EditCommerceWishListMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceWishLists(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceWishListIds = null;

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		if (commerceCartId > 0) {
			deleteCommerceWishListIds = new long[] {commerceCartId};
		}
		else {
			deleteCommerceWishListIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceWishListIds"),
				0L);
		}

		for (long deleteCommerceWishListId : deleteCommerceWishListIds) {
			_commerceCartService.deleteCommerceCart(deleteCommerceWishListId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceWishList(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceWishLists(actionRequest);
			}
			else if (cmd.equals(Constants.VIEW)) {
				setCurrentWishList(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCartException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void setCurrentWishList(ActionRequest actionRequest) {
		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		if (commerceCartId > 0) {
			PortletSession portletSession = actionRequest.getPortletSession();

			portletSession.setAttribute(
				CommerceWebKeys.WISH_LIST_COMMERCE_CART_ID, commerceCartId);
		}
	}

	protected void updateCommerceWishList(ActionRequest actionRequest)
		throws PortalException {

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean defaultCart = ParamUtil.getBoolean(
			actionRequest, "defaultCart");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCart.class.getName(), actionRequest);

		if (commerceCartId > 0) {
			_commerceCartService.updateCommerceCart(
				commerceCartId, name, defaultCart);
		}
		else {
			_commerceCartService.addCommerceCart(
				name, defaultCart, CommerceCartConstants.TYPE_WISH_LIST,
				serviceContext);
		}
	}

	@Reference
	private CommerceCartService _commerceCartService;

}