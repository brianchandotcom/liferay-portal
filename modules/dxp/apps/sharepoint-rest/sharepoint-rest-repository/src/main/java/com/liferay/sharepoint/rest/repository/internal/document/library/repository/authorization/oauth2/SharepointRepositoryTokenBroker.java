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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.capability.AuthorizationException;
import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;

import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public class SharepointRepositoryTokenBroker {

	public SharepointRepositoryTokenBroker(
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration) {

		_sharepointRepositoryConfiguration = sharepointRepositoryConfiguration;
	}

	public Token refreshAccessToken(Token token)
		throws AuthorizationException, IOException {

		try {
			HttpRequestWithBody httpRequestWithBody = Unirest.post(
				_sharepointRepositoryConfiguration.
					authorizationTokenEndpoint());

			MultipartBody multipartBody = httpRequestWithBody.field(
				"client_id",
				_sharepointRepositoryConfiguration.clientId() + "@" +
					_sharepointRepositoryConfiguration.tenantId());

			multipartBody = multipartBody.field(
				"client_secret",
				_sharepointRepositoryConfiguration.clientSecret());
			multipartBody = multipartBody.field(
				"refresh_token", token.getRefreshToken());
			multipartBody = multipartBody.field("grant_type", "refresh_token");
			multipartBody = multipartBody.field(
				"resource", _sharepointRepositoryConfiguration.resource());

			HttpResponse<String> httpResponse = multipartBody.asString();

			return SharepointRepositoryToken.newInstance(
				httpResponse.getBody(), token);
		}
		catch (JSONException | UnirestException e) {
			throw new IOException(e);
		}
	}

	public Token requestAccessToken(String code, String redirectURL)
		throws AuthorizationException, IOException {

		try {
			HttpRequestWithBody httpRequestWithBody = Unirest.post(
				_sharepointRepositoryConfiguration.
					authorizationTokenEndpoint());

			MultipartBody multipartBody = httpRequestWithBody.field(
				"client_id",
				_sharepointRepositoryConfiguration.clientId() + "@" +
					_sharepointRepositoryConfiguration.tenantId());

			multipartBody = multipartBody.field(
				"client_secret",
				_sharepointRepositoryConfiguration.clientSecret());
			multipartBody = multipartBody.field("code", code);
			multipartBody = multipartBody.field(
				"grant_type", "authorization_code");
			multipartBody = multipartBody.field("redirect_uri", redirectURL);
			multipartBody = multipartBody.field(
				"resource", _sharepointRepositoryConfiguration.resource());

			HttpResponse<String> httpResponse = multipartBody.asString();

			return SharepointRepositoryToken.newInstance(
				httpResponse.getBody());
		}
		catch (JSONException | UnirestException e) {
			throw new IOException(e);
		}
	}

	private final SharepointRepositoryConfiguration
		_sharepointRepositoryConfiguration;

}