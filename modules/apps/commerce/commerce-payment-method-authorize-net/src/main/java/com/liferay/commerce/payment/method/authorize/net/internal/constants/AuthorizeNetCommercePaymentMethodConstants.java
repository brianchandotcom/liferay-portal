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

package com.liferay.commerce.payment.method.authorize.net.internal.constants;

import com.liferay.portal.kernel.util.StringUtil;

import net.authorize.Environment;

/**
 * @author Luca Pellizzon
 */
public class AuthorizeNetCommercePaymentMethodConstants {

	public static final String COMPLETE_PAYMENT_SERVLET_PATH =
		"complete-authorizenet-payment";

	public static final String[] ENVIRONMENTS = {
		StringUtil.toLowerCase(Environment.PRODUCTION.name()),
		StringUtil.toLowerCase(Environment.SANDBOX.name())
	};

	public static final String PRODUCTION_REDIRECT_URL =
		"https://accept.authorize.net/payment/payment";

	public static final String SANDBOX_REDIRECT_URL =
		"https://test.authorize.net/payment/payment";

	public static final String SERVICE_NAME =
		"com.liferay.commerce.payment.method.authorize.net";

	public static final String START_PAYMENT_SERVLET_PATH =
		"start-authorizenet-payment";

}