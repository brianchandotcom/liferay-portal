/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cart.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
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
import com.liferay.portal.kernel.language.LanguageUtil;
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

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceOrderItem.class.getName(), httpServletRequest);

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.addCommerceOrderItem(
					commerceOrder.getCommerceOrderId(), cpInstanceId, quantity,
					0, ddmFormValues, null, commerceContext, serviceContext);

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
					"message",
					getCommerceOrderValidatorResultMessage(
						commerceOrderValidatorResult, httpServletRequest));

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

	protected String getCommerceOrderValidatorResultMessage(
		CommerceOrderValidatorResult commerceOrderValidatorResult,
		HttpServletRequest httpServletRequest) {

		if (commerceOrderValidatorResult.hasArgument()) {
			return LanguageUtil.format(
				httpServletRequest, commerceOrderValidatorResult.getMessage(),
				commerceOrderValidatorResult.getArgument());
		}
		else {
			return LanguageUtil.get(
				httpServletRequest, commerceOrderValidatorResult.getMessage());
		}
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