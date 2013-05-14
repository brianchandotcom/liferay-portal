/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.jaas.ext;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.IOException;

import java.security.Principal;

import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author Brian Wing Shun Chan
 */
public class BasicLoginModule implements LoginModule {

	@Override
	public boolean abort() {
		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean commit() throws LoginException {
		Principal principal = getPrincipal();

		if (principal != null) {
			Subject subject = getSubject();

			Set<Principal> principals = subject.getPrincipals();

			principals.add(getPrincipal());

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void initialize(
		Subject subject, CallbackHandler callbackHandler,
		Map<String, ?> sharedState, Map<String, ?> options) {

		_subject = subject;
		_callbackHandler = callbackHandler;
	}

	@Override
	public boolean login() throws LoginException {
		String[] credentials = null;

		try {
			credentials = authenticate();
		}
		catch (Exception e) {
			_log.error(e.getMessage());

			throw new LoginException();
		}

		if ((credentials != null) && (credentials.length == 2)) {
			setPrincipal(getPortalPrincipal(credentials[0]));
			setPassword(credentials[1]);

			return true;
		}
		else {
			throw new LoginException();
		}
	}

	@Override
	public boolean logout() {
		Subject subject = getSubject();

		Set<Principal> principals = subject.getPrincipals();

		principals.clear();

		return true;
	}

	protected String[] authenticate()
		throws IOException, UnsupportedCallbackException {

		NameCallback nameCallback = new NameCallback("name: ");
		PasswordCallback passwordCallback = new PasswordCallback(
			"password: ", false);

		_callbackHandler.handle(
			new Callback[] {nameCallback, passwordCallback});

		String name = nameCallback.getName();

		String password = null;
		char[] passwordChar = passwordCallback.getPassword();

		if (passwordChar != null) {
			password = new String(passwordChar);
		}

		if (name == null) {
			return new String[] {StringPool.BLANK, StringPool.BLANK};
		}

		try {
			long userId = GetterUtil.getLong(name);

			if (UserLocalServiceUtil.authenticateForJAAS(userId, password)) {
				return new String[] {name, password};
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	protected String getPassword() {
		return _password;
	}

	@SuppressWarnings("unused")
	protected Principal getPortalPrincipal(String name) throws LoginException {
		return new PortalPrincipal(name);
	}

	protected Principal getPrincipal() {
		return _principal;
	}

	protected Subject getSubject() {
		return _subject;
	}

	protected void setPassword(String password) {
		_password = password;
	}

	protected void setPrincipal(Principal principal) {
		_principal = principal;
	}

	private static Log _log = LogFactoryUtil.getLog(BasicLoginModule.class);

	private CallbackHandler _callbackHandler;
	private String _password;
	private Principal _principal;
	private Subject _subject;

}