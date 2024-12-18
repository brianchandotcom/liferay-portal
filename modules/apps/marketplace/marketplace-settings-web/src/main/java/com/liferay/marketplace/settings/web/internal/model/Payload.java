/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.model;

/**
 * @author Keven Leone
 */
public class Payload {

	public Payload(
		String clientId, String code, String codeVerifier,
		String marketplaceSettings, String redirect, String url) {

		this.clientId = clientId;
		this.code = code;
		this.codeVerifier = codeVerifier;
		this.marketplaceSettings = marketplaceSettings;
		this.redirect = redirect;
		this.url = url;
	}

	public String clientId;
	public String code;
	public String codeVerifier;
	public String marketplaceSettings;
	public String redirect;
	public String url;

}