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

package com.liferay.lcs.oauth;

import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Igor Beslic
 */
public class OAuthUtil {

	public static String buildURL(
		String hostName, int port, String protocol, String uri) {

		StringBundler sb = new StringBundler(7);

		sb.append(protocol);
		sb.append(StringPool.COLON);
		sb.append(StringPool.DOUBLE_SLASH);
		sb.append(hostName);

		if ((protocol.equals(Http.HTTP) && (port != Http.HTTP_PORT)) ||
			(protocol.equals(Http.HTTPS) && (port != Http.HTTPS_PORT))) {

			sb.append(StringPool.COLON);
			sb.append(port);
		}

		sb.append(uri);

		return sb.toString();
	}

	public static Token extractAccessToken(
		Token requestToken, String oAuthVerifier) {

		Verifier verifier = new Verifier(oAuthVerifier);

		OAuthService oAuthService = getOAuthService();

		return oAuthService.getAccessToken(requestToken, verifier);
	}

	public static String getAuthorizeURL(
		String callbackURL, Token requestToken) {

		String authorizeRequestURL = getOAuthService().getAuthorizationUrl(
			requestToken);

		if (Validator.isNotNull(callbackURL)) {
			authorizeRequestURL = HttpUtil.addParameter(
				authorizeRequestURL, "oauth_callback", callbackURL);
		}

		return authorizeRequestURL.replace("{0}", requestToken.getToken());
	}

	public static OAuthService getOAuthService() {
		if (_oAuthService == null) {
			ServiceBuilder oAuthServiceBuilder = new ServiceBuilder();

			oAuthServiceBuilder.apiKey(
				PortletPropsValues.OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY);
			oAuthServiceBuilder.apiSecret(
				PortletPropsValues.OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET);
			oAuthServiceBuilder.provider(OAuthAPIImpl.class);

			_oAuthService = oAuthServiceBuilder.build();
		}

		return _oAuthService;
	}

	public static Token getRequestToken() {
		OAuthService oAuthService = getOAuthService();

		return oAuthService.getRequestToken();
	}

	public static Token getRequestToken(PortletRequest portletRequest) {
		try {
			return getRequestToken();
		}
		catch (OAuthException oae) {
			if (hasOAuthException(oae)) {
				processOAuthException(portletRequest, oae);

				return null;
			}

			SessionErrors.add(portletRequest, "oAuthGeneralError");

			throw oae;
		}
	}

	public static boolean hasOAuthException(Exception e) {
		String message = e.getMessage();

		for (OAuthExceptionType oAuthExceptionType :
				OAuthExceptionType.values()) {

			String oAuthExceptionPattern = StringUtil.toLowerCase(
				oAuthExceptionType.name());

			if (message.contains(oAuthExceptionPattern)) {
				return true;
			}
		}

		return false;
	}

	public static boolean hasOAuthTokenRejectedException(Exception e) {
		String message = e.getMessage();
		String tokenRejectedPattern = StringUtil.toLowerCase(
			OAuthExceptionType.TOKEN_REJECTED.name());

		if (message.contains(tokenRejectedPattern)) {
			return true;
		}

		return false;
	}

	public static void processOAuthException(
		PortletRequest portletRequest, Exception e) {

		processOAuthException(portletRequest, e.getMessage());
	}

	public static void processOAuthException(
		PortletRequest portletRequest, String message) {

		for (OAuthExceptionType oAuthExceptionType :
				OAuthExceptionType.values()) {

			String oAuthExceptionPattern = StringUtil.toLowerCase(
				oAuthExceptionType.name());

			if (message.contains(oAuthExceptionPattern)) {
				SessionErrors.add(portletRequest, oAuthExceptionType.getKey());
			}
		}
	}

	private static OAuthService _oAuthService;

}