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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.CartMinQuantityException;
import com.liferay.shopping.exception.CouponActiveException;
import com.liferay.shopping.exception.CouponEndDateException;
import com.liferay.shopping.exception.CouponStartDateException;
import com.liferay.shopping.exception.NoSuchCouponException;
import com.liferay.shopping.exception.NoSuchItemException;
import com.liferay.shopping.model.ShoppingCart;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.service.ShoppingCartLocalService;
import com.liferay.shopping.service.ShoppingItemLocalService;
import com.liferay.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/cart"
	},
	service = MVCActionCommand.class
)
public class CartMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCart(actionRequest, actionResponse);

			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CartMinQuantityException ||
					 e instanceof CouponActiveException ||
					 e instanceof CouponEndDateException ||
					 e instanceof CouponStartDateException ||
					 e instanceof NoSuchCouponException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setShoppingCartLocalService(
		ShoppingCartLocalService shoppingCartLocalService) {

		_shoppingCartLocalService = shoppingCartLocalService;
	}

	@Reference(unbind = "-")
	protected void setShoppingItemLocalService(
		ShoppingItemLocalService shoppingItemLocalService) {

		_shoppingItemLocalService = shoppingItemLocalService;
	}

	protected void updateCart(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ShoppingCart cart = ShoppingUtil.getCart(actionRequest);

		if (cmd.equals(Constants.ADD)) {
			long itemId = ParamUtil.getLong(actionRequest, "itemId");

			String fields = ParamUtil.getString(actionRequest, "fields");

			if (Validator.isNotNull(fields)) {
				fields = "|" + fields;
			}

			ShoppingItem item = _shoppingItemLocalService.getItem(itemId);

			if (item.getMinQuantity() > 0) {
				for (int i = 0; i < item.getMinQuantity(); i++) {
					cart.addItemId(itemId, fields);
				}
			}
			else {
				cart.addItemId(itemId, fields);
			}
		}
		else {
			String itemIds = ParamUtil.getString(actionRequest, "itemIds");
			String couponCodes = ParamUtil.getString(
				actionRequest, "couponCodes");
			int altShipping = ParamUtil.getInteger(
				actionRequest, "altShipping");
			boolean insure = ParamUtil.getBoolean(actionRequest, "insure");

			cart.setItemIds(itemIds);
			cart.setCouponCodes(couponCodes);
			cart.setAltShipping(altShipping);
			cart.setInsure(insure);
		}

		_shoppingCartLocalService.updateCart(
			cart.getUserId(), cart.getGroupId(), cart.getItemIds(),
			cart.getCouponCodes(), cart.getAltShipping(), cart.isInsure());

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			addSuccessMessage(actionRequest, actionResponse);
		}
	}

	private ShoppingCartLocalService _shoppingCartLocalService;
	private ShoppingItemLocalService _shoppingItemLocalService;

}