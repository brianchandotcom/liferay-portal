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

package com.liferay.commerce.payment.method.mercanet.internal.constants;

import com.liferay.commerce.payment.method.mercanet.internal.connector.Environment;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luca Pellizzon
 */
public class MercanetCommercePaymentMethodConstants {

	public static final String[] ENVIRONMENTS = {
		StringUtil.toLowerCase(Environment.PROD.name()),
		StringUtil.toLowerCase(Environment.TEST.name())
	};

	public static final String SERVICE_NAME =
		"com.liferay.commerce.payment.method.mercanet";

	public static final String SERVLET_PATH = "mercanet-payment";

}