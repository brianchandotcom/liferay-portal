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

package com.liferay.commerce.payment.internal.servlet;

import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.util.CommercePaymentHttpHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.math.BigDecimal;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/" + CommercePaymentConstants.SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.liferay.commerce.payment.internal.servlet.CommercePaymentServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + CommercePaymentConstants.SERVLET_PATH + "/*"
	},
	service = Servlet.class
)
public class CommercePaymentServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			if (PortalSessionThreadLocal.getHttpSession() == null) {
				PortalSessionThreadLocal.setHttpSession(
					httpServletRequest.getSession());
			}

			CommerceOrder commerceOrder =
				_commercePaymentHttpHelper.getCommerceOrder(httpServletRequest);

			URL portalURL = new URL(_portal.getPortalURL(httpServletRequest));

			_nextUrl = ParamUtil.getString(httpServletRequest, "nextStep");

			URL nextURL = new URL(_nextUrl);

			if (!Objects.equals(portalURL.getHost(), nextURL.getHost())) {
				throw new ServletException();
			}

			_commerceOrderId = commerceOrder.getCommerceOrderId();

			if (BigDecimal.ZERO.compareTo(commerceOrder.getTotal()) == 0) {
				_commercePaymentEngine.completePayment(
					_commerceOrderId, null, httpServletRequest);

				httpServletResponse.sendRedirect(_nextUrl);

				return;
			}

			CommercePaymentResult commercePaymentResult = _startPayment(
				httpServletRequest);

			if (commercePaymentResult.isSuccess() &&
				commercePaymentResult.isOnlineRedirect()) {

				URL redirectURL = new URL(
					commercePaymentResult.getRedirectUrl());

				if (Objects.equals(
						portalURL.getHost(), redirectURL.getHost())) {

					Map<String, String> paramsMap = _getQueryMap(
						redirectURL.getQuery());

					Set<Map.Entry<String, String>> entries =
						paramsMap.entrySet();

					for (Map.Entry<String, String> param : entries) {
						httpServletRequest.setAttribute(
							param.getKey(), param.getValue());
					}

					RequestDispatcher requestDispatcher =
						httpServletRequest.getRequestDispatcher(
							redirectURL.getPath());

					requestDispatcher.forward(
						httpServletRequest, httpServletResponse);
				}
				else {
					httpServletResponse.sendRedirect(redirectURL.toString());
				}
			}

			// Offline methods, payment complete

			int commercePaymentMethodType =
				_commercePaymentEngine.getCommercePaymentMethodType(
					_commerceOrderId);

			if ((CommercePaymentConstants.
					COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE ==
						commercePaymentMethodType) ||
				(commercePaymentMethodType == -1)) {

				_commercePaymentEngine.completePayment(
					_commerceOrderId, null, httpServletRequest);

				httpServletResponse.sendRedirect(_nextUrl);
			}

			if (commercePaymentResult.isSuccess() &&
				(CommercePaymentConstants.
					COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_STANDARD ==
						commercePaymentMethodType)) {

				if (commerceOrder.isSubscriptionOrder()) {
					_commerceSubscriptionEngine.completeRecurringPayment(
						_commerceOrderId,
						commercePaymentResult.getAuthTransactionId(),
						httpServletRequest);
				}
				else {
					_commercePaymentEngine.completePayment(
						_commerceOrderId,
						commercePaymentResult.getAuthTransactionId(),
						httpServletRequest);
				}

				httpServletResponse.sendRedirect(_nextUrl);
			}

			if (!commercePaymentResult.isSuccess() &&
				!httpServletResponse.isCommitted()) {

				httpServletResponse.sendRedirect(_nextUrl);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			// Payment Failed

			try {
				_commercePaymentEngine.updateOrderPaymentStatus(
					_commerceOrderId,
					CommerceOrderPaymentConstants.STATUS_FAILED,
					StringPool.BLANK, StringPool.BLANK);

				httpServletResponse.sendRedirect(_nextUrl);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}
	}

	private Map<String, String> _getQueryMap(String query) {
		String[] params = query.split(StringPool.AMPERSAND);

		Map<String, String> map = new HashMap<>();

		for (String param : params) {
			String name = param.split(StringPool.EQUAL)[0];
			String value = param.split(StringPool.EQUAL)[1];

			map.put(name, value);
		}

		return map;
	}

	private boolean _isDeliveryOnlySubscription(CommerceOrder commerceOrder) {
		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if (Validator.isNotNull(commerceOrderItem.getSubscriptionType())) {
				return false;
			}
		}

		return true;
	}

	private CommercePaymentResult _startPayment(
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			_commerceOrderId);

		if (commerceOrder.isSubscriptionOrder() &&
			!_isDeliveryOnlySubscription(commerceOrder)) {

			return _commerceSubscriptionEngine.processRecurringPayment(
				_commerceOrderId, _nextUrl, httpServletRequest);
		}

		return _commercePaymentEngine.processPayment(
			_commerceOrderId, _nextUrl, httpServletRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentServlet.class);

	private long _commerceOrderId;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePaymentHttpHelper _commercePaymentHttpHelper;

	@Reference
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	private String _nextUrl;

	@Reference
	private Portal _portal;

}