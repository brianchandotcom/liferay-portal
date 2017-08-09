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

package com.liferay.sharepoint.repository.internal.oauth2;

import com.liferay.document.library.repository.authorization.capability.AuthorizationException;
import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.sharepoint.repository.internal.oauth2.configuration.SharepointOAuth2Configuration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public class SharepointOAuth2AuthorizationServer {

	public SharepointOAuth2AuthorizationServer(
		SharepointOAuth2Configuration sharepointOAuth2Configuration) {

		_sharepointOAuth2Configuration = sharepointOAuth2Configuration;
	}

	public Token refreshAccessToken(Token token)
		throws AuthorizationException, IOException {

		try {
			HttpRequestWithBody httpRequestWithBody = Unirest.post(
				_sharepointOAuth2Configuration.authorizationTokenEndpoint());

			httpRequestWithBody.field(
				"client_id",
				_sharepointOAuth2Configuration.clientId() + "@" +
					_sharepointOAuth2Configuration.tenantId());
			httpRequestWithBody.field(
				"client_secret", _sharepointOAuth2Configuration.clientSecret());
			httpRequestWithBody.field("refresh_token", token.getRefreshToken());
			httpRequestWithBody.field("grant_type", "refresh_token");
			httpRequestWithBody.field(
				"resource", _sharepointOAuth2Configuration.resource());

			HttpResponse<String> httpResponse =
				httpRequestWithBody.asString();

			return SharepointOAuth2Token.fromJSONString(
				httpResponse.getBody(), token);
		}
		catch (JSONException | UnirestException e) {
			throw new IOException(e);
		}
	}

	public Token requestAccessToken(String code, String redirectUrl)
		throws AuthorizationException, IOException {

		try {
			HttpRequestWithBody httpRequestWithBody = Unirest.post(
				_sharepointOAuth2Configuration.authorizationTokenEndpoint());

			httpRequestWithBody.field(
				"client_id",
				_sharepointOAuth2Configuration.clientId() + "@" +
					_sharepointOAuth2Configuration.tenantId());
			httpRequestWithBody.field(
				"client_secret", _sharepointOAuth2Configuration.clientSecret());
			httpRequestWithBody.field("code", code);
			httpRequestWithBody.field("grant_type", "authorization_code");
			httpRequestWithBody.field("redirect_uri", redirectUrl);
			httpRequestWithBody.field(
				"resource", _sharepointOAuth2Configuration.resource());

			HttpResponse<String> httpResponse =
				httpRequestWithBody.asString();

			return SharepointOAuth2Token.fromJSONString(
				httpResponse.getBody());
		}
		catch (JSONException | UnirestException e) {
			throw new IOException(e);
		}
	}

	private final SharepointOAuth2Configuration _sharepointOAuth2Configuration;

}