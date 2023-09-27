/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.servlet;

import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentMethodConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.util.CommercePaymentHelper;
import com.liferay.commerce.payment.util.CommercePaymentHttpHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

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
	property = {
		"osgi.http.whiteboard.context.path=/" + CommercePaymentMethodConstants.SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.liferay.commerce.payment.internal.servlet.CommercePaymentServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + CommercePaymentMethodConstants.SERVLET_PATH + "/*"
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

			URL portalURL = new URL(_portal.getPortalURL(httpServletRequest));

			_nextUrl = ParamUtil.getString(httpServletRequest, "nextStep");

			URL nextURL = new URL(_nextUrl);

			if (!Objects.equals(portalURL.getHost(), nextURL.getHost())) {
				throw new ServletException();
			}

			CommerceOrder commerceOrder =
				_commercePaymentHttpHelper.getCommerceOrder(httpServletRequest);

			_commerceOrderId = commerceOrder.getCommerceOrderId();

			if (_commerceCheckoutStepHttpHelper.isCommercePaymentComplete(
					httpServletRequest, commerceOrder)) {

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

			if ((CommercePaymentMethodConstants.TYPE_OFFLINE ==
					commercePaymentMethodType) ||
				(commercePaymentMethodType == -1)) {

				_commercePaymentEngine.completePayment(
					_commerceOrderId, null, httpServletRequest);

				httpServletResponse.sendRedirect(_nextUrl);
			}

			if (commercePaymentResult.isSuccess() &&
				(CommercePaymentMethodConstants.TYPE_ONLINE_STANDARD ==
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
			_log.error(exception);

			// Payment Failed

			try {
				_commercePaymentEngine.updateOrderPaymentStatus(
					_commerceOrderId,
					CommerceOrderPaymentConstants.STATUS_FAILED,
					StringPool.BLANK, StringPool.BLANK);

				httpServletResponse.sendRedirect(_nextUrl);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	private Map<String, String> _getQueryMap(String query) {
		String[] params = query.split(StringPool.AMPERSAND);

		Map<String, String> map = new HashMap<>();

		for (String param : params) {
			String name = param.split(StringPool.EQUAL)[0];
			String value = param.split(StringPool.EQUAL)[1];

			map.put(
				StringUtil.toUpperCase(
					CamelCaseUtil.fromCamelCase(name, CharPool.UNDERLINE)),
				value);
		}

		return map;
	}

	private CommercePaymentResult _startPayment(
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			_commerceOrderId);

		if (commerceOrder.isSubscriptionOrder() &&
			!_commercePaymentHelper.isDeliveryOnlySubscription(commerceOrder)) {

			return _commerceSubscriptionEngine.processRecurringPayment(
				_commerceOrderId, _nextUrl, httpServletRequest);
		}

		return _commercePaymentEngine.processPayment(
			_commerceOrderId, _nextUrl, httpServletRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentServlet.class);

	@Reference
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	private long _commerceOrderId;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePaymentHelper _commercePaymentHelper;

	@Reference
	private CommercePaymentHttpHelper _commercePaymentHttpHelper;

	@Reference
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	private String _nextUrl;

	@Reference
	private Portal _portal;

}