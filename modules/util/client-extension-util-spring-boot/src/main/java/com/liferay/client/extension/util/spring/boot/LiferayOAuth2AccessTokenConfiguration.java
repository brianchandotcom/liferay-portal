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

package com.liferay.client.extension.util.spring.boot;

import java.time.Instant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class LiferayOAuth2AccessTokenConfiguration {

	public String getAuthorization() {
		OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken();

		if (oAuth2AccessToken == null) {
			return null;
		}

		return getTokenType() + " " + getTokenValue();
	}

	public OAuth2AccessToken getOAuth2AccessToken() {
		synchronized (_log) {
			if (_oAuth2AccessToken == null) {
				_oAuth2AccessToken = _getOAuth2AccessToken();

				return _oAuth2AccessToken;
			}

			Instant instant = Instant.now();

			Instant expiresAtInstant = _oAuth2AccessToken.getExpiresAt();

			if ((expiresAtInstant == null) ||
				expiresAtInstant.isBefore(instant.minusSeconds(300))) {

				_oAuth2AccessToken = _getOAuth2AccessToken();
			}

			return _oAuth2AccessToken;
		}
	}

	public String getTokenType() {
		OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken();

		if (oAuth2AccessToken == null) {
			return null;
		}

		OAuth2AccessToken.TokenType tokenType =
			oAuth2AccessToken.getTokenType();

		if (tokenType == null) {
			return null;
		}

		return tokenType.getValue();
	}

	public String getTokenValue() {
		OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken();

		if (oAuth2AccessToken == null) {
			return null;
		}

		return oAuth2AccessToken.getTokenValue();
	}

	public void refresh() {
		synchronized (_log) {
			_oAuth2AccessToken = null;

			_oAuth2AccessToken = getOAuth2AccessToken();
		}
	}

	private OAuth2AccessToken _getOAuth2AccessToken() {
		String liferayOauthApplicationExternalReferenceCodes =
			_environment.getProperty(
				"liferay.oauth.application.external.reference.codes");

		if (liferayOauthApplicationExternalReferenceCodes == null) {
			throw new IllegalArgumentException(
				"Property " +
					"\"liferay.oauth.application.external.reference.codes\" " +
						"is not defined");
		}

		OAuth2AuthorizeRequest.Builder oAuth2AuthorizeRequestBuilder =
			OAuth2AuthorizeRequest.withClientRegistrationId(
				liferayOauthApplicationExternalReferenceCodes
			).principal(
				liferayOauthApplicationExternalReferenceCodes
			);

		OAuth2AuthorizedClient oAuth2AuthorizedClient =
			_authorizedClientServiceOAuth2AuthorizedClientManager.authorize(
				oAuth2AuthorizeRequestBuilder.build());

		if (oAuth2AuthorizedClient == null) {
			_log.error("Unable to get OAuth 2 authorized client");

			return null;
		}

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AuthorizedClient.getAccessToken();

		if (oAuth2AccessToken == null) {
			_log.error("Unable to get OAuth 2 access token");

			return null;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updated OAuth2 access token at " +
					oAuth2AccessToken.getIssuedAt());
		}

		return oAuth2AccessToken;
	}

	private static final Log _log = LogFactory.getLog(
		LiferayOAuth2AccessTokenConfiguration.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager
		_authorizedClientServiceOAuth2AuthorizedClientManager;

	@Autowired
	private Environment _environment;

	private OAuth2AccessToken _oAuth2AccessToken;

}