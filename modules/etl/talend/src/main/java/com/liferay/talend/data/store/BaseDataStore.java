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
@Checkable("checkBaseDataStore")
@DataStore("BaseDataStore")
@GridLayout(
	{
		@GridLayout.Row("_authenticationMethod"),
		@GridLayout.Row("_openAPISpecURL"),
		@GridLayout.Row("_basicAuthDataStore"),
		@GridLayout.Row("_oAuth2DataStore")
	}
)
public class BaseDataStore {

	public AuthenticationMethod getAuthenticationMethod() {
		return _authenticationMethod;
	}

	public BasicAuthDataStore getBasicDataStore() {
		return _basicAuthDataStore;
	}

	public OAuth2DataStore getoAuthDataStore() {
		return _oAuth2DataStore;
	}

	public URL getOpenAPISpecURL() {
		return _openAPISpecURL;
	}

	public BaseDataStore setAuthenticationMethod(
		AuthenticationMethod authenticationMethod) {

		_authenticationMethod = authenticationMethod;

		return this;
	}

	public BaseDataStore setBasicDataStore(BasicAuthDataStore dataStore) {
		_basicAuthDataStore = dataStore;

		return this;
	}

	public BaseDataStore setoAuthDataStore(OAuth2DataStore oAuth2DataStore) {
		_oAuth2DataStore = oAuth2DataStore;

		return this;
	}

	public BaseDataStore setOpenAPISpecURL(URL openAPISpecURL) {
		_openAPISpecURL = openAPISpecURL;

		return this;
	}

	@DefaultValue("BASIC")
	@Option
	@Required
	private AuthenticationMethod _authenticationMethod;

	@ActiveIf(target = "_authenticationMethod", value = "BASIC")
	@Option
	private BasicAuthDataStore _basicAuthDataStore;

	@ActiveIf(target = "_authenticationMethod", value = "OAUTH2")
	@Option
	private OAuth2DataStore _oAuth2DataStore;

	@DefaultValue(
		"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/openapi.yaml"
	)
	@Option
	@Required
	private URL _openAPISpecURL;

}