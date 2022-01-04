/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.handler.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;

import org.apache.directory.api.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class SaslCallbackHandler implements CallbackHandler {

	public Dn getName() {
		return _name;
	}

	public String getRealm() {
		return _realm;
	}

	@Override
	public void handle(Callback[] callbacks)
		throws IOException, UnsupportedCallbackException {

		for (Callback callback : callbacks) {
			try {
				handle(callback);
			}
			catch (IOException ioException) {
				throw ioException;
			}
			catch (UnsupportedCallbackException unsupportedCallbackException) {
				throw unsupportedCallbackException;
			}
			catch (Exception exception) {
				throw new IOException(exception);
			}
		}
	}

	protected void handle(Callback callback) throws Exception {
		if (callback instanceof AuthorizeCallback) {
			AuthorizeCallback authorizeCallback = (AuthorizeCallback)callback;

			_handleAuthorizeCallback(authorizeCallback);
		}
		else if (callback instanceof NameCallback) {
			NameCallback nameCallback = (NameCallback)callback;

			_handleNameCallback(nameCallback);
		}
		else if (callback instanceof PasswordCallback) {
			PasswordCallback passwordCallback = (PasswordCallback)callback;

			_handlePasswordCallback(passwordCallback);
		}
		else if (callback instanceof RealmCallback) {
			RealmCallback realmCallback = (RealmCallback)callback;

			_handleRealmCallback(realmCallback);
		}
		else {
			throw new UnsupportedCallbackException(callback);
		}
	}

	private void _handleAuthorizeCallback(AuthorizeCallback authorizeCallback) {
		authorizeCallback.setAuthorized(true);
	}

	private void _handleNameCallback(NameCallback nameCallback)
		throws Exception {

		_name = new Dn(nameCallback.getDefaultName());
	}

	private void _handlePasswordCallback(PasswordCallback passwordCallback) {
		String password = "hellojon";

		passwordCallback.setPassword(password.toCharArray());
	}

	private void _handleRealmCallback(RealmCallback realmCallback) {
		_realm = realmCallback.getDefaultText();
	}

	private Dn _name;
	private String _realm;

}