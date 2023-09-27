/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.services.PlaintextSignatureService;
import com.github.scribejava.core.services.SignatureService;

import com.liferay.osb.faro.engine.client.model.Credentials;
import com.liferay.osb.faro.engine.client.model.credentials.OAuth1Credentials;
import com.liferay.osb.faro.engine.client.model.credentials.OAuth2Credentials;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;

import java.io.IOException;

import java.util.concurrent.ExecutionException;

/**
 * @author Shinn Lok
 */
public class OAuthUtil {

	public static Credentials getLiferayOAuthCredentials(
		String baseURL, String oAuthConsumerKey, String oAuthConsumerSecret,
		String oAuthCallbackURL) {

		OAuth1Credentials oAuth1Credentials = new OAuth1Credentials();

		ServiceBuilder serviceBuilder = new ServiceBuilder(oAuthConsumerKey);

		serviceBuilder.apiSecret(oAuthConsumerSecret);
		serviceBuilder.callback(oAuthCallbackURL);
		serviceBuilder.userAgent("LiferayAnalyticsCloud");

		try {
			OAuth10aService oAuth10aService = serviceBuilder.build(
				new LiferayApi10a(baseURL, oAuthCallbackURL));

			OAuth1RequestToken oAuth1RequestToken =
				oAuth10aService.getRequestToken();

			oAuth1Credentials.setOAuthAuthorizationURL(
				oAuth10aService.getAuthorizationUrl(oAuth1RequestToken));

			oAuth1Credentials.setOAuthConsumerSecret(oAuthConsumerSecret);
			oAuth1Credentials.setOAuthConsumerKey(oAuthConsumerKey);
			oAuth1Credentials.setOAuthToken(oAuth1RequestToken.getToken());
			oAuth1Credentials.setOAuthTokenSecret(
				oAuth1RequestToken.getTokenSecret());

			return oAuth1Credentials;
		}
		catch (ExecutionException | InterruptedException | IOException
					exception) {

			_log.error(exception);

			return getOAuth2Credentials(
				serviceBuilder, new LiferayApi20(baseURL), oAuthConsumerKey,
				oAuthConsumerSecret);
		}
	}

	public static Credentials getOAuth10aCredentials(
			String baseURL, String oAuthConsumerKey, String oAuthConsumerSecret,
			String oAuthToken, String oAuthTokenSecret, String oAuthVerifier)
		throws Exception {

		OAuth1Credentials oAuth1Credentials = new OAuth1Credentials();

		ServiceBuilder serviceBuilder = new ServiceBuilder(oAuthConsumerKey);

		serviceBuilder.apiSecret(oAuthConsumerSecret);
		serviceBuilder.userAgent("LiferayAnalyticsCloud");

		OAuth10aService oAuth10aService = serviceBuilder.build(
			new LiferayApi10a(baseURL));

		OAuth1AccessToken oAuth1AccessToken = oAuth10aService.getAccessToken(
			new OAuth1RequestToken(oAuthToken, oAuthTokenSecret),
			oAuthVerifier);

		oAuth1Credentials.setOAuthConsumerSecret(oAuthConsumerSecret);
		oAuth1Credentials.setOAuthConsumerKey(oAuthConsumerKey);
		oAuth1Credentials.setOAuthAccessToken(oAuth1AccessToken.getToken());
		oAuth1Credentials.setOAuthAccessSecret(
			oAuth1AccessToken.getTokenSecret());

		return oAuth1Credentials;
	}

	public static Credentials getOAuth20Credentials(
			String baseURL, String providerType, String oAuthClientId,
			String oAuthClientSecret, String code, String oAuthCallbackURL)
		throws Exception {

		OAuth2Credentials oAuth2Credentials = new OAuth2Credentials();

		ServiceBuilder serviceBuilder = new ServiceBuilder(oAuthClientId);

		serviceBuilder.apiSecret(oAuthClientSecret);
		serviceBuilder.callback(oAuthCallbackURL);
		serviceBuilder.userAgent("LiferayAnalyticsCloud");

		OAuth20Service oAuth20Service = null;

		if (providerType.equals(LiferayProvider.TYPE)) {
			oAuth20Service = serviceBuilder.build(new LiferayApi20(baseURL));
		}
		else {
			oAuth20Service = serviceBuilder.build(new SalesforceApi(baseURL));
		}

		OAuth2AccessToken oAuth2AccessToken = oAuth20Service.getAccessToken(
			code);

		oAuth2Credentials.setOAuthClientId(oAuthClientId);
		oAuth2Credentials.setOAuthClientSecret(oAuthClientSecret);
		oAuth2Credentials.setOAuthRefreshToken(
			oAuth2AccessToken.getRefreshToken());

		return oAuth2Credentials;
	}

	public static Credentials getSalesforceOAuthCredentials(
		String baseURL, String oAuthConsumerKey, String oAuthConsumerSecret,
		String oAuthCallbackURL) {

		ServiceBuilder serviceBuilder = new ServiceBuilder(oAuthConsumerKey);

		serviceBuilder.apiSecret(oAuthConsumerSecret);
		serviceBuilder.callback(oAuthCallbackURL);
		serviceBuilder.userAgent("LiferayAnalyticsCloud");

		return getOAuth2Credentials(
			serviceBuilder, new SalesforceApi(baseURL), oAuthConsumerKey,
			oAuthConsumerSecret);
	}

	protected static Credentials getOAuth2Credentials(
		ServiceBuilder serviceBuilder, DefaultApi20 defaultApi20,
		String oAuthConsumerKey, String oAuthConsumerSecret) {

		OAuth2Credentials oAuth2Credentials = new OAuth2Credentials();

		OAuth20Service oAuth20Service = serviceBuilder.build(defaultApi20);

		oAuth2Credentials.setOAuthAuthorizationURL(
			oAuth20Service.getAuthorizationUrl());

		oAuth2Credentials.setOAuthClientId(oAuthConsumerKey);
		oAuth2Credentials.setOAuthClientSecret(oAuthConsumerSecret);

		return oAuth2Credentials;
	}

	private static final Log _log = LogFactoryUtil.getLog(OAuthUtil.class);

	private static class LiferayApi10a extends DefaultApi10a {

		public LiferayApi10a(String baseURL) {
			_baseURL = baseURL;

			_callbackURL = null;
		}

		public LiferayApi10a(String baseURL, String callbackURL) {
			_baseURL = baseURL;
			_callbackURL = callbackURL;
		}

		@Override
		public String getAccessTokenEndpoint() {
			return _baseURL.concat(_ACCESS_TOKEN_URL_PATH);
		}

		@Override
		public String getAuthorizationUrl(
			OAuth1RequestToken oAuth1RequestToken) {

			String authorizationURL = HttpComponentsUtil.addParameter(
				_baseURL.concat(_AUTHORIZATION_URL_PATH), OAuthConstants.TOKEN,
				oAuth1RequestToken.getToken());

			return HttpComponentsUtil.addParameter(
				authorizationURL, OAuthConstants.CALLBACK, _callbackURL);
		}

		@Override
		public String getRequestTokenEndpoint() {
			return _baseURL.concat(_REQUEST_TOKEN_URL_PATH);
		}

		@Override
		public SignatureService getSignatureService() {
			return _plainTextSignatureService;
		}

		@Override
		protected String getAuthorizationBaseUrl() {
			return _baseURL.concat(_AUTHORIZATION_URL_PATH);
		}

		private static final String _ACCESS_TOKEN_URL_PATH =
			"/c/portal/oauth/access_token";

		private static final String _AUTHORIZATION_URL_PATH =
			"/c/portal/oauth/authorize";

		private static final String _REQUEST_TOKEN_URL_PATH =
			"/c/portal/oauth/request_token";

		private static final SignatureService _plainTextSignatureService =
			new PlaintextSignatureService();

		private final String _baseURL;
		private final String _callbackURL;

	}

	private static class LiferayApi20 extends DefaultApi20 {

		public LiferayApi20(String baseURL) {
			_baseURL = baseURL;
		}

		@Override
		public String getAccessTokenEndpoint() {
			return _baseURL.concat(_ACCESS_TOKEN_URL_PATH);
		}

		@Override
		protected String getAuthorizationBaseUrl() {
			return _baseURL.concat(_AUTHORIZATION_URL_PATH);
		}

		private static final String _ACCESS_TOKEN_URL_PATH = "/o/oauth2/token";

		private static final String _AUTHORIZATION_URL_PATH =
			"/o/oauth2/authorize";

		private final String _baseURL;

	}

	private static class SalesforceApi extends DefaultApi20 {

		public SalesforceApi(String baseURL) {
			_baseURL = baseURL;
		}

		@Override
		public String getAccessTokenEndpoint() {
			return _baseURL.concat(_ACCESS_TOKEN_URL_PATH);
		}

		@Override
		protected String getAuthorizationBaseUrl() {
			return _baseURL.concat(_AUTHORIZATION_URL_PATH);
		}

		private static final String _ACCESS_TOKEN_URL_PATH =
			"/services/oauth2/token";

		private static final String _AUTHORIZATION_URL_PATH =
			"/services/oauth2/authorize";

		private final String _baseURL;

	}

}