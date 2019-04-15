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

import java.net.URL;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Checkable;
import org.talend.sdk.component.api.configuration.condition.ActiveIf;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@Checkable("checkInputDataStore")
@DataStore("InputDataStore")
@GridLayout(
	{
		@GridLayout.Row("_authenticationMethod"), @GridLayout.Row("_serverURL"),
		@GridLayout.Row("_basicDataStore"), @GridLayout.Row("_oAuthDataStore")
	}
)
public class InputDataStore {

	public AuthenticationMethod getAuthenticationMethod() {
		return _authenticationMethod;
	}

	public BasicDataStore getBasicDataStore() {
		return _basicDataStore;
	}

	public OAuthDataStore getoAuthDataStore() {
		return _oAuthDataStore;
	}

	public URL getServerURL() {
		return _serverURL;
	}

	public InputDataStore setAuthenticationMethod(
		AuthenticationMethod authenticationMethod) {

		_authenticationMethod = authenticationMethod;

		return this;
	}

	public InputDataStore setBasicDataStore(BasicDataStore dataStore) {
		_basicDataStore = dataStore;

		return this;
	}

	public InputDataStore setoAuthDataStore(OAuthDataStore oAuthDataStore) {
		_oAuthDataStore = oAuthDataStore;

		return this;
	}

	public InputDataStore setServerURL(URL serverURL) {
		_serverURL = serverURL;

		return this;
	}

	@DefaultValue("BASIC")
	@Option
	@Required
	private AuthenticationMethod _authenticationMethod;

	@ActiveIf(target = "_authenticationMethod", value = "BASIC")
	@Option
	private BasicDataStore _basicDataStore;

	@ActiveIf(target = "_authenticationMethod", value = "OAUTH2")
	@Option
	private OAuthDataStore _oAuthDataStore;

	@DefaultValue(
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/openapi.yaml"
	)
	@Option
	private URL _serverURL;

}