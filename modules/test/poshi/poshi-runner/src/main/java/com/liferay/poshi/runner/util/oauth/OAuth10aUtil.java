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