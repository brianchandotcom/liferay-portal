/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

/**
 * @author Zsolt Berentey
 */
public class RemoteAuthException extends AuthException {

	public static final int WRONG_SHARED_SECRET = 101;

	public RemoteAuthException() {
	}

	public RemoteAuthException(String msg) {
		super(msg);
	}

	public RemoteAuthException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RemoteAuthException(Throwable throwable) {
		super(throwable);
	}

	public String getURL() {
		return _url;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String _url;

}