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

import com.liferay.commerce.cart.CommerceCartValidatorResult;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.CommerceCartValidatorException;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
		"mvc.command.name=addCommerceCartItem"
	},
	service = MVCActionCommand.class
)
public class AddCommerceCartItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		int type = ParamUtil.getInteger(
			actionRequest, "type", CommerceConstants.COMMERCE_CART_TYPE_CART);
		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");
		int quantity = ParamUtil.getInteger(
			actionRequest, "quantity",
			CommerceConstants.COMMERCE_CART_TYPE_CART);
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		try {
			CommerceCart commerceCart =
				_commerceCartHelper.getCurrentCommerceCart(
					httpServletRequest, httpServletResponse, type);

			if (commerceCart == null) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						CommerceCart.class.getName(), httpServletRequest);

				commerceCart = _commerceCartService.addCommerceCart(
					CommerceConstants.COMMERCE_CART_DEFAULT_TITLE, type,
					serviceContext);

				if (!serviceContext.isSignedIn()) {
					_commerceCartHelper.setGuestCommerceCart(
						httpServletRequest, httpServletResponse, commerceCart);
				}
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceCartItem.class.getName(), httpServletRequest);

			CommerceCartItem commerceCartItem =
				_commerceCartItemService.addCommerceCartItem(
					commerceCart.getCommerceCartId(), cpDefinitionId,
					cpInstanceId, quantity, ddmFormValues, serviceContext);

			int commerceCartItemsCount =
				_commerceCartItemService.getCommerceCartItemsCount(
					commerceCart.getCommerceCartId());

			jsonObject.put(
				"commerceCartItemId", commerceCartItem.getCommerceCartItemId());
			jsonObject.put("commerceCartItemsCount", commerceCartItemsCount);
			jsonObject.put("success", true);
		}
		catch (CommerceCartValidatorException ccve) {
			List<CommerceCartValidatorResult> commerceCartValidatorResults =
				ccve.getCommerceCommerceCartValidatorResults();

			JSONArray errorArray = _jsonFactory.createJSONArray();

			for (CommerceCartValidatorResult commerceCartValidatorResult :
					commerceCartValidatorResults) {

				JSONObject errorObject = _jsonFactory.createJSONObject();

				errorObject.put(
					"message", commerceCartValidatorResult.getMessage());

				errorArray.put(errorObject);
			}

			jsonObject.put("success", false);
			jsonObject.put("validatorErrors", errorArray);
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("error", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object jsonObj)
		throws IOException {

		HttpServletResponse response = _portal.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(response, jsonObj.toString());

		response.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCommerceCartItemMVCActionCommand.class);

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}