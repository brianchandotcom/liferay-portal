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

package com.liferay.talend.datastore;

import java.io.Serializable;

import java.net.URL;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Igor Beslic
 */
@DataStore("BasicDataStore")
@Documentation("TODO fill the documentation for this configuration")
@GridLayout(
	names = GridLayout.FormType.MAIN,
	value = {
		@GridLayout.Row({"_serverURL", "_anonymous"}),
		@GridLayout.Row({"_user", "_password"})
	}
)
public class BasicDataStore implements Serializable {

	public boolean getAnonymous() {
		return _anonymous;
	}

	public String getPassword() {
		return _password;
	}

	public URL getServerURL() {
		return _serverURL;
	}

	public String getUser() {
		return _user;
	}

	public BasicDataStore setAnonymous(boolean anonymous) {
		_anonymous = anonymous;

		return this;
	}

	public BasicDataStore setPassword(String password) {
		_password = password;

		return this;
	}

	public BasicDataStore setServerURL(URL serverURL) {
		_serverURL = serverURL;

		return this;
	}

	public BasicDataStore setUser(String user) {
		_user = user;

		return this;
	}

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private boolean _anonymous;

	@Credential
	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String _password;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private URL _serverURL;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String _user;

}