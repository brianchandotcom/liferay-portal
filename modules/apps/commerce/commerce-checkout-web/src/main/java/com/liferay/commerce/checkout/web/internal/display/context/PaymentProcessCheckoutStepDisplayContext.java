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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.internal.display.context.util.CommerceCheckoutRequestHelper;
import com.liferay.commerce.checkout.web.internal.util.OrderConfirmationCommerceCheckoutStep;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStepServicesTracker;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;

import java.security.Key;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 */
public class PaymentProcessCheckoutStepDisplayContext {

	public PaymentProcessCheckoutStepDisplayContext(
		CommerceCheckoutStepServicesTracker commerceCheckoutStepServicesTracker,
		CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
		Portal portal) {

		_commerceCheckoutRequestHelper = new CommerceCheckoutRequestHelper(
			httpServletRequest);
		_commerceCheckoutStepServicesTracker =
			commerceCheckoutStepServicesTracker;
		_commerceOrder = commerceOrder;
		_portal = portal;
	}

	public String getPaymentServletUrl() throws Exception {
		StringBundler sb = new StringBundler(16);

		sb.append(_getPortalUrl());
		sb.append(_getPathModule());
		sb.append(CharPool.SLASH);
		sb.append(_getPaymentServletPath());
		sb.append(CharPool.QUESTION);
		sb.append("groupId=");
		sb.append(_commerceOrder.getGroupId());
		sb.append(StringPool.AMPERSAND);
		sb.append("uuid=");
		sb.append(_commerceOrder.getUuid());
		sb.append(StringPool.AMPERSAND);

		if (_commerceOrder.isGuestOrder()) {
			sb.append("guestToken=");
			sb.append(_getGuestToken(_commerceOrder.getCommerceOrderId()));
			sb.append(StringPool.AMPERSAND);
		}

		sb.append("nextStep=");
		sb.append(URLCodec.encodeURL(_getOrderConfirmationCheckoutStepUrl()));

		return sb.toString();
	}

	private String _getGuestToken(long commerceOrderId) throws Exception {
		Company company = _commerceCheckoutRequestHelper.getCompany();

		Key key = company.getKeyObj();

		return Encryptor.encrypt(key, String.valueOf(commerceOrderId));
	}

	private String _getOrderConfirmationCheckoutStepUrl() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceCheckoutRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"commerceOrderUuid", String.valueOf(_commerceOrder.getUuid()));

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getCommerceCheckoutStep(
				OrderConfirmationCommerceCheckoutStep.NAME);

		portletURL.setParameter(
			"checkoutStepName", commerceCheckoutStep.getName());

		return portletURL.toString();
	}

	private String _getPathModule() {
		return _portal.getPathModule();
	}

	private String _getPaymentServletPath() {
		return CommercePaymentConstants.SERVLET_PATH;
	}

	private String _getPortalUrl() {
		return _portal.getPortalURL(
			_commerceCheckoutRequestHelper.getRequest());
	}

	private final CommerceCheckoutRequestHelper _commerceCheckoutRequestHelper;
	private final CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;
	private final CommerceOrder _commerceOrder;
	private final Portal _portal;

}