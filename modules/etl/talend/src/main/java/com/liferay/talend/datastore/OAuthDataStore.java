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
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Igor Beslic
 */
@DataStore("OAuthDataStore")
@Documentation("TODO fill the documentation for this configuration")
@GridLayout(
	names = GridLayout.FormType.MAIN,
	value = {
		@GridLayout.Row({"serverURL"}),
		@GridLayout.Row({"consumerKey", "consumerSecret"})
	}
)
public class OAuthDataStore implements Serializable {

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public java.net.URL getServerURL() {
		return serverURL;
	}

	public OAuthDataStore setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;

		return this;
	}

	public OAuthDataStore setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;

		return this;
	}

	public OAuthDataStore setServerURL(java.net.URL serverURL) {
		this.serverURL = serverURL;

		return this;
	}

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String consumerKey;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private String consumerSecret;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private java.net.URL serverURL;

}