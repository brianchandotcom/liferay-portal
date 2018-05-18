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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.service.CommerceOrderPaymentLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class OrderConfirmationCheckoutStepDisplayContext {

	public OrderConfirmationCheckoutStepDisplayContext(
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		CommerceOrderPaymentLocalService commerceOrderPaymentLocalService,
		CommerceOrderService commerceOrderService,
		HttpServletRequest httpServletRequest) {

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_commerceOrderPaymentLocalService = commerceOrderPaymentLocalService;
		_commerceOrderService = commerceOrderService;
		_httpServletRequest = httpServletRequest;
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		_commerceOrder = (CommerceOrder)_httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		long commerceOrderId = ParamUtil.getLong(
			_httpServletRequest, "order_confirmation.jsp-commerceOrderId");

		_commerceOrder = _commerceOrderService.fetchCommerceOrder(
			commerceOrderId);

		return _commerceOrder;
	}

	public CommerceOrderPayment getCommerceOrderPayment()
		throws PortalException {

		return
			_commerceOrderPaymentLocalService.fetchLatestCommerceOrderPayment(
				getCommerceOrderId());
	}

	public String getOrderDetailURL() throws PortalException {
		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				_httpServletRequest, getCommerceOrder());

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		return portletURL.toString();
	}

	protected long getCommerceOrderId() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return 0;
		}

		return commerceOrder.getCommerceOrderId();
	}

	private CommerceOrder _commerceOrder;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private final CommerceOrderPaymentLocalService
		_commerceOrderPaymentLocalService;
	private final CommerceOrderService _commerceOrderService;
	private final HttpServletRequest _httpServletRequest;

}