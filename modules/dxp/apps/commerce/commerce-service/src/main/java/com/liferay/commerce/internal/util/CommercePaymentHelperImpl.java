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
import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.commerce.util.CommercePaymentHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommercePaymentHelperImpl implements CommercePaymentHelper {

	@Override
	public void completePayment(
			CommerceOrder commerceOrder, Map<String, String[]> parameterMap)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine = _getCommercePaymentEngine(
			commerceOrder);

		if (commercePaymentEngine == null) {
			return;
		}

		commercePaymentEngine.completePayment(commerceOrder, parameterMap);
	}

	@Override
	public String getPaymentURL(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine = _getCommercePaymentEngine(
			commerceOrder);

		if (commercePaymentEngine == null) {
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

		return commercePaymentEngine.getPaymentURL(
			commerceOrder, cancelURL, returnURL, serviceContext.getLocale());
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

	@Reference
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

	@Reference
	private Portal _portal;

}