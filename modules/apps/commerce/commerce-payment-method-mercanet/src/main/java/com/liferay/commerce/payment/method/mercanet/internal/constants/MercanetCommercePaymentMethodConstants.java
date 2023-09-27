/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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