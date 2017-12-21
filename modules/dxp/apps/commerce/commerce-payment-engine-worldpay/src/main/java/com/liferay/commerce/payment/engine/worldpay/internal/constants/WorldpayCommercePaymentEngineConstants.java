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

package com.liferay.commerce.payment.engine.worldpay.internal.constants;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class WorldpayCommercePaymentEngineConstants {

	public static final String[] PAYMENT_METHOD_CODES = {
		"ALIPAY-SSL", "ALIPAYMOBILE-SSL", "AMEX-SSL", "ASTROPAYCARD-SSL",
		"MISTERCASH-SSL", "BOKU-SSL", "BOLETO-SSL", "CB-SSL", "CARTEBLEUE-SSL",
		"CASHU-SSL", "CHINAUNIONPAY-SSL", "DANKORT-SSL",
		"DINEROMAIL_7ELEVEN-SSL", "DINEROMAIL_ONLINE_BT-SSL",
		"DINEROMAIL_OXXO-SSL", "DINERS-SSL", "DISCOVER-SSL", "EPS-SSL",
		"EUTELLER-SSL", "GIROPAY-SSL", "IDEAL-SSL", "INIPAY-SSL", "JCB-SSL",
		"KLARNA-SSL", "KONBINI-SSL", "MAESTRO-SSL", "ECMC-SSL", "MONETA-SSL",
		"MULTIBANCO-SSL", "Ebetalning-SSL", "PAGA_VERVE-SSL", "PAGA-SSL",
		"PRZELEWY-SSL", "PAYPAL-EXPRESS", "PAYSAFECARD-SSL",
		"POLI-SSL, POLINZ-SSL", "POSTEPAY-SSL", "QIWI-SSL", "SAFETYPAY-SSL",
		"SEPA_DIRECT_DEBIT-SSL", "SOFORT-SSL", "TRUSTPAY_CZ-SSL",
		"TRUSTPAY_EE-SSL", "TRUSTPAY_SK-SSL", "WEBMONEY-SSL", "YANDEXMONEY-SSL",
		"VISA-SSL"
	};

	public static final String SERVICE_NAME =
		"com.liferay.commerce.payment.engine.worldpay";

	public static String getPaymentMethodLabel(String paymentMethodCode) {
		String label = StringUtil.replace(
			StringUtil.toLowerCase(paymentMethodCode), CharPool.UNDERLINE,
			CharPool.DASH);

		return "payment-method-" + label;
	}

}