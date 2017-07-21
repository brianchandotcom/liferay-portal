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

import com.liferay.commerce.cart.constants.CommerceCartConstants;
import com.liferay.commerce.cart.constants.CommerceCartPortletKeys;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.CommerceCartItemService;
import com.liferay.commerce.cart.service.CommerceCartService;
import com.liferay.commerce.cart.util.CommerceCartHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
		"javax.portlet.name=" + CommerceCartPortletKeys.COMMERCE_CART_CONTENT,
		"javax.portlet.name=" + CommerceCartPortletKeys.COMMERCE_CART_CONTENT_MINI,
		"mvc.command.name=editCommerceCartItem"
	},
	service = MVCResourceCommand.class
)
public class EditCommerceCartItemMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			Locale locale = resourceRequest.getLocale();

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			int type = ParamUtil.getInteger(
				resourceRequest, "type",
				CommerceCartConstants.COMMERCE_CART_TYPE_CART);

			long cpDefinitionId = ParamUtil.getLong(
				resourceRequest, "cpDefinitionId");

			long cpInstanceId = ParamUtil.getLong(
				resourceRequest, "cpInstanceId");

			int quantity = ParamUtil.getInteger(
				resourceRequest, "quantity",
				CommerceCartConstants.COMMERCE_CART_TYPE_CART);

			String ddmFormValues = ParamUtil.getString(
				resourceRequest, "ddmFormValues");

			String json = _cpInstanceHelper.toJSON(
				cpDefinitionId, locale, ddmFormValues);

			CommerceCart commerceCart = _commerceCartHelper.getCurrentCart(
				httpServletRequest, type);

			if (commerceCart == null) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						CommerceCart.class.getName(), httpServletRequest);

				commerceCart = _commerceCartService.addCommerceCart(
					CommerceCartConstants.COMMERCE_CART_DEFAULT_TITLE, type,
					serviceContext);
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceCartItem.class.getName(), httpServletRequest);

			CommerceCartItem commerceCartItem =
				_commerceCartItemService.addCommerceCartItem(
					commerceCart.getCommerceCartId(), cpDefinitionId,
					cpInstanceId, quantity, json, serviceContext);

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("success", true);

			jsonObject.put(
				"commerceCartItemId", commerceCartItem.getCommerceCartItemId());

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(
				httpServletResponse, jsonObject.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceCartItemMVCResourceCommand.class);

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}