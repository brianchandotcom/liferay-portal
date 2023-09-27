/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.jaas;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), with no replacement
 */
@Deprecated
public class PortalLoginModule implements LoginModule {

	public PortalLoginModule() {
		try {
			Class<?> clazz = Class.forName(
				_CLASS_NAME, true, PortalClassLoaderUtil.getClassLoader());

			_loginModule = (LoginModule)clazz.newInstance();
		}
		catch (Exception exception) {
			throw new AssertionError(exception);
		}
	}

	@Override
	public boolean abort() throws LoginException {
		return _loginModule.abort();
	}

	@Override
	public boolean commit() throws LoginException {
		return _loginModule.commit();
	}

	@Override
	public void initialize(
		Subject subject, CallbackHandler callbackHandler,
		Map<String, ?> sharedState, Map<String, ?> options) {

		_loginModule.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	public boolean login() throws LoginException {
		return _loginModule.login();
	}

	@Override
	public boolean logout() throws LoginException {
		return _loginModule.logout();
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.security.jaas.PortalLoginModule";

	private final LoginModule _loginModule;

}