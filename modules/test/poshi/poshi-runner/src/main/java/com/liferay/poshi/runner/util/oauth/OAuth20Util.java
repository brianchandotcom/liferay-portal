/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util.oauth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * @author Leslie Wong
 */
public class OAuth20Util {

	public static String createRequest(
			String accessTokenEndpoint, String accessTokenString, String apiKey,
			String apiSecret, String authorizationBaseURL, String callbackURL,
			String requestURL)
		throws Exception {

		ServiceBuilder serviceBuilder = new ServiceBuilder();

		serviceBuilder.apiKey(apiKey);
		serviceBuilder.apiSecret(apiSecret);
		serviceBuilder.callback(callbackURL);

		OAuth20Service oAuth20Service = serviceBuilder.build(
			new OAuth20APIImpl(accessTokenEndpoint, authorizationBaseURL));

		OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
			accessTokenString);

		OAuthRequest oAuthRequest = new OAuthRequest(
			Verb.GET, requestURL, oAuth20Service);

		oAuth20Service.signRequest(oAuth2AccessToken, oAuthRequest);

		Response response = oAuthRequest.send();

		if (!response.isSuccessful()) {
			throw new Exception("Response is not successful");
		}

		return response.getBody();
	}

}