/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util.oauth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * @author Leslie Wong
 */
public class OAuth10aUtil {

	public static String createRequest(
			String accessTokenEndpoint, String accessTokenString,
			String accessTokenSecret, String apiKey, String apiSecret,
			String authorizationURL, String requestTokenEndpoint,
			String requestURL)
		throws Exception {

		ServiceBuilder serviceBuilder = new ServiceBuilder();

		serviceBuilder.apiKey(apiKey);
		serviceBuilder.apiSecret(apiSecret);

		OAuth10aService oAuthService = serviceBuilder.build(
			new OAuth10aAPIImpl(
				accessTokenEndpoint, authorizationURL, requestTokenEndpoint));

		OAuth1AccessToken oAuth1AccessToken = new OAuth1AccessToken(
			accessTokenString, accessTokenSecret);

		OAuthRequest oAuthRequest = new OAuthRequest(
			Verb.GET, requestURL, oAuthService);

		oAuthService.signRequest(oAuth1AccessToken, oAuthRequest);

		Response response = oAuthRequest.send();

		if (!response.isSuccessful()) {
			throw new Exception("Response is not successful");
		}

		return response.getBody();
	}

	public static byte[] tokenToByteArray(String token) {
		token = token.substring(1, token.length() - 1);

		String[] tokenParts = token.split(",");

		byte[] bytes = new byte[tokenParts.length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = Byte.parseByte(tokenParts[i].trim());
		}

		return bytes;
	}

}