/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class HttpPrincipal implements Serializable {

	public HttpPrincipal() {
		this(null, null, null, true);
	}

	public HttpPrincipal(String url) {
		this(url, null, null, true);
	}

	public HttpPrincipal(String url, String login, String password) {
		this(url, login, password, false);
	}

	public HttpPrincipal(
		String url, String login, String password, boolean digested) {

		_url = url;
		_login = login;

		if (digested) {
			_password = password;
		}
		else {
			try {
				_password = PasswordEncryptorUtil.encrypt(password);
			}
			catch (PwdEncryptorException pwdEncryptorException) {
				_log.error(pwdEncryptorException);
			}
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getLogin() {
		return _login;
	}

	public String getPassword() {
		return _password;
	}

	public String getUrl() {
		return _url;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private static final Log _log = LogFactoryUtil.getLog(HttpPrincipal.class);

	private long _companyId;
	private final String _login;
	private String _password;
	private final String _url;

}