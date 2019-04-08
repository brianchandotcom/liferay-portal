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

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Checkable;
import org.talend.sdk.component.api.configuration.condition.ActiveIf;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Igor Beslic
 */
@Checkable("checkInputDataStore")
@DataStore("InputDataStore")
@Documentation("Aggregator data store for authentication stores")
@GridLayout(
	{
		@GridLayout.Row("authenticationMethod"),
		@GridLayout.Row("basicDataStore"), @GridLayout.Row("oAuthDataStore")
	}
)
public class InputDataStore {

	public AuthenticationMethod getAuthenticationMethod() {
		return authenticationMethod;
	}

	public BasicDataStore getBasicDataStore() {
		return basicDataStore;
	}

	public OAuthDataStore getoAuthDataStore() {
		return oAuthDataStore;
	}

	public InputDataStore setAuthenticationMethod(
		AuthenticationMethod authenticationMethod) {

		this.authenticationMethod = authenticationMethod;

		return this;
	}

	public InputDataStore setBasicDataStore(BasicDataStore dataStore) {
		this.basicDataStore = dataStore;

		return this;
	}

	public InputDataStore setoAuthDataStore(OAuthDataStore oAuthDataStore) {
		this.oAuthDataStore = oAuthDataStore;

		return this;
	}

	@DefaultValue("BASIC")
	@Documentation("Authentication Method")
	@Option
	@Required
	private AuthenticationMethod authenticationMethod;

	@ActiveIf(target = "authenticationMethod", value = "BASIC")
	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private BasicDataStore basicDataStore;

	@ActiveIf(target = "authenticationMethod", value = "OAUTH2")
	@Documentation("OAuth2 Data Store")
	@Option
	private OAuthDataStore oAuthDataStore;

}