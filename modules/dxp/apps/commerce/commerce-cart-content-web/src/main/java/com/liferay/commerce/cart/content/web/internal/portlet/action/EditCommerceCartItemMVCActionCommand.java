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
import com.liferay.commerce.exception.CommerceCartValidatorException;
import com.liferay.commerce.exception.NoSuchCartItemException;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_WISH_LIST_CONTENT,
		"mvc.command.name=editCommerceCartItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCartItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commerceCartItemId = ParamUtil.getLong(
			actionRequest, "commerceCartItemId");

		try {
			if (cmd.equals(Constants.DELETE)) {
				_commerceCartItemService.deleteCommerceCartItem(
					commerceCartItemId);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				int quantity = ParamUtil.getInteger(actionRequest, "quantity");

				CommerceCartItem commerceCartItem =
					_commerceCartItemService.getCommerceCartItem(
						commerceCartItemId);

				_commerceCartItemService.updateCommerceCartItem(
					commerceCartItem.getCommerceCartItemId(), quantity,
					commerceCartItem.getJson());
			}
		}
		catch (CommerceCartValidatorException ccve) {
			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, ccve.getClass(), ccve);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCartItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommerceCartItemService _commerceCartItemService;

}