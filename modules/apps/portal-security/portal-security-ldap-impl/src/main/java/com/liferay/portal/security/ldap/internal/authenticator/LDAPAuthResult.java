/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.authenticator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;

import javax.naming.ldap.Control;

/**
 * @author Scott Lee
 */
public class LDAPAuthResult {

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getResponseControl() {
		return _responseControl;
	}

	public boolean isAuthenticated() {
		return _authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		_authenticated = authenticated;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setResponseControl(Control[] response) {
		if (ArrayUtil.isNotEmpty(response)) {
			_responseControl = response[0].getID();
		}
	}

	private boolean _authenticated;
	private String _errorMessage;
	private String _responseControl = StringPool.BLANK;

}