/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.mixin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.osb.faro.engine.client.model.credentials.BasicCredentials;
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
public abstract class CredentialsMixin {
}