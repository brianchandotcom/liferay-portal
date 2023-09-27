/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.config;

/**
 * @author Peter Shin
 */
public class Security {

	public String getBasicAuth() {
		return _basicAuth;
	}

	public String getGuestAllowed() {
		return _guestAllowed;
	}

	public String getOAuth2() {
		return _oAuth2;
	}

	public void setBasicAuth(String basicAuth) {
		_basicAuth = basicAuth;
	}

	public void setGuestAllowed(String guestAllowed) {
		_guestAllowed = guestAllowed;
	}

	public void setOAuth2(String oAuth2) {
		_oAuth2 = oAuth2;
	}

	private String _basicAuth;
	private String _guestAllowed;
	private String _oAuth2;

}