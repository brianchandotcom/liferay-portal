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
		@GridLayout.Row({"serverURL", "anonymous"}),
		@GridLayout.Row({"user", "password"})
	}
)
public class BasicDataStore implements Serializable {

	public boolean getAnonymous() {
		return anonymous;
	}

	public String getPassword() {
		return password;
	}

	public java.net.URL getServerURL() {
		return serverURL;
	}

	public String getUser() {
		return user;
	}

	public BasicDataStore setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;

		return this;
	}

	public BasicDataStore setPassword(String password) {
		this.password = password;

		return this;
	}

	public BasicDataStore setServerURL(java.net.URL serverURL) {
		this.serverURL = serverURL;

		return this;
	}

	public BasicDataStore setUser(String user) {
		this.user = user;

		return this;
	}

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private boolean anonymous;

	@Credential
	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String password;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private java.net.URL serverURL;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String user;

}