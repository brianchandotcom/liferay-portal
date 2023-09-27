/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.mercanet.internal.connector;

/**
 * @author Luca Pellizzon
 */
public enum Environment {

	PROD(
		"https://payment-webinit.mercanet.bnpparibas.net/rs-services/v2" +
			"/paymentInit"),
	TEST(
		"https://payment-webinit-mercanet.test.sips-atos.com/rs-services/v2" +
			"/paymentInit");

	public String getUrl() {
		return _url;
	}

	private Environment(String url) {
		_url = url;
	}

	private final String _url;

}