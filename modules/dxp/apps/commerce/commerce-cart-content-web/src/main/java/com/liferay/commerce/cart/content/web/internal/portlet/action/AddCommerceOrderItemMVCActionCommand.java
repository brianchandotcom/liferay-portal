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
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
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
		"mvc.command.name=addCommerceOrderItem"
	},
	service = MVCActionCommand.class
)
public class AddCommerceOrderItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");
		int quantity = ParamUtil.getInteger(actionRequest, "quantity");
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		try {
			CommerceOrder commerceOrder =
				_commerceOrderHttpHelper.getCurrentCommerceOrder(
					httpServletRequest);

			if (commerceOrder == null) {
				commerceOrder = _commerceOrderHttpHelper.addCommerceOrder(
					httpServletRequest);
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceOrderItem.class.getName(), httpServletRequest);

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.addCommerceOrderItem(
					commerceOrder.getCommerceOrderId(), cpInstanceId, quantity,
					0, ddmFormValues, null, serviceContext);

			int commerceOrderItemsQuantity =
				_commerceOrderItemService.getCommerceOrderItemsQuantity(
					commerceOrder.getCommerceOrderId());

			jsonObject.put(
				"commerceOrderItemId",
				commerceOrderItem.getCommerceOrderItemId());
			jsonObject.put(
				"commerceOrderItemsQuantity", commerceOrderItemsQuantity);
			jsonObject.put("success", true);
		}
		catch (CommerceOrderValidatorException cove) {
			List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
				cove.getCommerceOrderValidatorResults();

			JSONArray errorArray = _jsonFactory.createJSONArray();

			for (CommerceOrderValidatorResult commerceOrderValidatorResult :
					commerceOrderValidatorResults) {

				JSONObject errorObject = _jsonFactory.createJSONObject();

				errorObject.put(
					"message", commerceOrderValidatorResult.getMessage());

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

		writeJSON(actionResponse, jsonObject);
	}

	protected void writeJSON(ActionResponse actionResponse, Object jsonObj)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonObj.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCommerceOrderItemMVCActionCommand.class);

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}