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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderPaymentConstants;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentEngineResult;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderPaymentLocalService;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.commerce.util.CommercePaymentHelper;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommercePaymentHelperImpl implements CommercePaymentHelper {

	@Override
	public void cancelPayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine = _getCommercePaymentEngine(
			commerceOrder);

		if (commercePaymentEngine == null) {
			return;
		}

		String content = null;
		int status = CommerceOrderPaymentConstants.STATUS_CANCELLED;

		try {
			CommercePaymentEngineResult commercePaymentEngineResult =
				commercePaymentEngine.cancelPayment(
					commerceOrder, serviceContext);

			content = commercePaymentEngineResult.getContent();
		}
		catch (CommercePaymentEngineException cpee) {
			_log.error(
				"Unable to cancel payment of order " +
					commerceOrder.getCommerceOrderId(),
				cpee);

			content = _getContent(cpee);
		}

		_commerceOrderPaymentLocalService.addCommerceOrderPayment(
			commerceOrder.getCommerceOrderId(), status, content,
			serviceContext);
	}

	@Override
	public void completePayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine = _getCommercePaymentEngine(
			commerceOrder);

		if (commercePaymentEngine == null) {
			return;
		}

		String content = null;
		int status = CommerceOrderPaymentConstants.STATUS_COMPLETED;

		try {
			CommercePaymentEngineResult commercePaymentEngineResult =
				commercePaymentEngine.completePayment(
					commerceOrder, serviceContext);

			content = commercePaymentEngineResult.getContent();

			_commerceOrderLocalService.updatePaymentStatus(
				commerceOrder.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID,
				CommerceOrderConstants.ORDER_STATUS_TRANSMITTED);
		}
		catch (CommercePaymentEngineException cpee) {
			_log.error(
				"Unable to complete payment of order " +
					commerceOrder.getCommerceOrderId(),
				cpee);

			content = _getContent(cpee);
			status = CommerceOrderPaymentConstants.STATUS_FAILED;
		}

		_commerceOrderPaymentLocalService.addCommerceOrderPayment(
			commerceOrder.getCommerceOrderId(), status, content,
			serviceContext);
	}

	@Override
	public String startPayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine = _getCommercePaymentEngine(
			commerceOrder);

		if (commercePaymentEngine == null) {
			_commerceOrderLocalService.updatePaymentStatus(
				commerceOrder.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID,
				CommerceOrderConstants.ORDER_STATUS_TRANSMITTED);

			return null;
		}

		StringBundler sb = new StringBundler(13);

		sb.append(serviceContext.getPortalURL());
		sb.append(_portal.getPathModule());
		sb.append(CharPool.SLASH);
		sb.append(CommerceConstants.PAYMENT_SERVLET_PATH);
		sb.append(CharPool.QUESTION);
		sb.append("groupId=");
		sb.append(commerceOrder.getGroupId());
		sb.append("&uuid=");
		sb.append(URLCodec.encodeURL(commerceOrder.getUuid()));

		String redirect = ParamUtil.getString(serviceContext, "redirect");

		if (Validator.isNotNull(redirect)) {
			sb.append("&redirect=");
			sb.append(URLCodec.encodeURL(redirect));
		}

		String returnURL = sb.toString();

		sb.append("&cancel=");
		sb.append(StringPool.TRUE);

		String cancelURL = sb.toString();

		String output = null;

		String content = null;
		int status = CommerceOrderPaymentConstants.STATUS_PENDING;

		try {
			CommercePaymentEngineResult.StartPayment startPayment =
				commercePaymentEngine.startPayment(
					commerceOrder, cancelURL, returnURL, serviceContext);

			content = startPayment.getContent();
			output = startPayment.getOutput();
		}
		catch (CommercePaymentEngineException cpee) {
			_log.error(
				"Unable to start payment of order " +
					commerceOrder.getCommerceOrderId(),
				cpee);

			content = _getContent(cpee);
			status = CommerceOrderPaymentConstants.STATUS_FAILED;
		}

		_commerceOrderPaymentLocalService.addCommerceOrderPayment(
			commerceOrder.getCommerceOrderId(), status, content,
			serviceContext);

		return output;
	}

	private CommercePaymentEngine _getCommercePaymentEngine(
			CommerceOrder commerceOrder)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commerceOrder.getCommercePaymentMethod();

		if (commercePaymentMethod == null) {
			return null;
		}

		if (!commercePaymentMethod.isActive()) {
			throw new NoSuchPaymentMethodException(
				"Payment method " + commercePaymentMethod + " is not active");
		}

		return _commercePaymentEngineRegistry.getCommercePaymentEngine(
			commercePaymentMethod.getEngineKey());
	}

	private String _getContent(CommercePaymentEngineException cpee) {
		return StackTraceUtil.getStackTrace(cpee);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentHelperImpl.class);

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderPaymentLocalService _commerceOrderPaymentLocalService;

	@Reference
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

	@Reference
	private Portal _portal;

}