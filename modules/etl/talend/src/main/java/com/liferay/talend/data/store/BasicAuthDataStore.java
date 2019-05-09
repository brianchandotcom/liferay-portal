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

package com.liferay.talend.data.store;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;

/**
 * @author Igor Beslic
 */
@DataStore("BasicAuthDataStore")
@GridLayout(
	names = GridLayout.FormType.MAIN,
	value = {@GridLayout.Row({"user", "password"})}
)
public class BasicAuthDataStore implements Serializable {

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	public BasicAuthDataStore setPassword(String password) {
		this.password = password;

		return this;
	}

	public BasicAuthDataStore setUser(String user) {
		this.user = user;

		return this;
	}

	@Credential
	@DefaultValue("\"test\"")
	@Option
	protected String password;

	@DefaultValue("\"test@liferay.com\"")
	@Option
	protected String user;

}