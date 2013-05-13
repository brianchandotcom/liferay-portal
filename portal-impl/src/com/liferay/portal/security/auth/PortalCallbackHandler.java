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

package com.liferay.portal.security.auth;

import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalCallbackHandler implements CallbackHandler, Serializable {

	public PortalCallbackHandler(String name, String password) {
		_name = name;
		_password = password;
	}

	@Override
	public void handle(Callback[] callbacks) {
		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof NameCallback) {
				NameCallback nameCallback = (NameCallback)callbacks[i];
				nameCallback.setName(_name);
			}
			else if (callbacks[i] instanceof PasswordCallback) {
				PasswordCallback passCallback = (PasswordCallback)callbacks[i];
				passCallback.setPassword(_password.toCharArray());
			}
		}
	}

	private String _name;
	private String _password;

}