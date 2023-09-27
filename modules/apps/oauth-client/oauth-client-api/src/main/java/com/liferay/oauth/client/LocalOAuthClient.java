/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.model.OAuth2Application;

import java.util.function.Consumer;

/**
 * @author Arthur Chan
 */
@ProviderType
public interface LocalOAuthClient {

	public void consumeAccessToken(
		Consumer<String> accessTokenConsumer,
		OAuth2Application oAuth2Application, long userId);

	public String requestTokens(
		OAuth2Application oAuth2Application, long userId);

}