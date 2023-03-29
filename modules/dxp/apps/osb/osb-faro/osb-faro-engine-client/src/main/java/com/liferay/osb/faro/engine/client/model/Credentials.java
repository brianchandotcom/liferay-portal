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

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.osb.faro.engine.client.model.credentials.BasicCredentials;
import com.liferay.osb.faro.engine.client.model.credentials.DummyCredentials;
import com.liferay.osb.faro.engine.client.model.credentials.OAuth1Credentials;
import com.liferay.osb.faro.engine.client.model.credentials.OAuth2Credentials;
import com.liferay.osb.faro.engine.client.model.credentials.TokenCredentials;

/**
 * @author Matthew Kong
 */
@JsonSubTypes(
	{
		@JsonSubTypes.Type(
			name = BasicCredentials.TYPE, value = BasicCredentials.class
		),
		@JsonSubTypes.Type(
			name = DummyCredentials.TYPE, value = TokenCredentials.class
		),
		@JsonSubTypes.Type(
			name = OAuth1Credentials.TYPE, value = OAuth1Credentials.class
		),
		@JsonSubTypes.Type(
			name = OAuth2Credentials.TYPE, value = OAuth2Credentials.class
		),
		@JsonSubTypes.Type(
			name = TokenCredentials.TYPE, value = TokenCredentials.class
		)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type",
	use = JsonTypeInfo.Id.NAME
)
public interface Credentials {

	public default void clearPasswords() {
	}

	public String getType();

}