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

package com.liferay.commerce.payment.engine.worldpay.internal.constants;

import com.liferay.petra.string.CharPool;
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