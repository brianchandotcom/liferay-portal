/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.model;

/**
 * @author Keven Leone
 */
public class Payload {

	public Payload(
		String code, String codeVerifier, String serviceURL, String settings) {

		this.code = code;
		this.codeVerifier = codeVerifier;
		this.serviceURL = serviceURL;
		this.settings = settings;
	}

	public String code;
	public String codeVerifier;
	public String serviceURL;
	public String settings;

}