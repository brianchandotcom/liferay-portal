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

package com.liferay.portal.vulcan.yaml.config;

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