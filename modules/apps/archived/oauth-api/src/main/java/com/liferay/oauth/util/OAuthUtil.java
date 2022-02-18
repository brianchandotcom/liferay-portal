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

package com.liferay.oauth.util;

import com.liferay.oauth.configuration.OAuthConfigurationValues;
import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.exception.OAuthException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.OutputStream;

import java.lang.reflect.Constructor;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author     Ivica Cardic
 * @author     Raymond Augé
 * @author     Igor Beslic
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class OAuthUtil {

	public static String addParameters(String url, String... parameters)
		throws OAuthException {

		return getOAuth().addParameters(url, parameters);
	}

	public static void authorize(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		getOAuth().authorize(oAuthAccessor, userId, serviceContext);
	}

	public static void formEncode(
			String token, String tokenSecret, OutputStream outputStream)
		throws OAuthException {

		getOAuth().formEncode(token, tokenSecret, outputStream);
	}

	public static void generateAccessToken(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		getOAuth().generateAccessToken(oAuthAccessor, userId, serviceContext);
	}

	public static void generateRequestToken(OAuthAccessor oAuthAccessor) {
		getOAuth().generateRequestToken(oAuthAccessor);
	}

	public static String getAccessTokenURI() {
		if (_accessTokenURI == null) {
			_accessTokenURI = _getOAuthURI("access_token");
		}

		return _accessTokenURI;
	}

	public static String getAuthorizeURI() {
		if (_authorizeURI == null) {
			_authorizeURI = _getOAuthURI("authorize");
		}

		return _authorizeURI;
	}

	public static OAuth getOAuth() {
		if (_oAuth == null) {
			try {
				Class<?> clazz = Class.forName(
					OAuthConfigurationValues.OAUTH_CLASS_NAME);

				Constructor<?> oauthConstructor = clazz.getConstructor(
					OAuthValidator.class);

				_oAuth = (OAuth)oauthConstructor.newInstance(
					new DefaultOAuthValidator());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return _oAuth;
	}

	public static OAuthAccessor getOAuthAccessor(OAuthMessage oAuthMessage)
		throws PortalException {

		return getOAuth().getOAuthAccessor(oAuthMessage);
	}

	public static OAuthConsumer getOAuthConsumer(OAuthMessage oAuthMessage)
		throws PortalException {

		return getOAuth().getOAuthConsumer(oAuthMessage);
	}

	public static OAuthMessage getOAuthMessage(
		HttpServletRequest httpServletRequest) {

		return getOAuth().getOAuthMessage(httpServletRequest);
	}

	public static OAuthMessage getOAuthMessage(
		HttpServletRequest httpServletRequest, String url) {

		return getOAuth().getOAuthMessage(httpServletRequest, url);
	}

	public static OAuthMessage getOAuthMessage(PortletRequest portletRequest) {
		return getOAuth().getOAuthMessage(portletRequest);
	}

	public static OAuthMessage getOAuthMessage(
		PortletRequest portletRequest, String url) {

		return getOAuth().getOAuthMessage(portletRequest, url);
	}

	public static String getRequestTokenURI() {
		if (_requestTokenURI == null) {
			_requestTokenURI = _getOAuthURI("request_token");
		}

		return _requestTokenURI;
	}

	public static void handleException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception,
			boolean sendBody)
		throws OAuthException {

		getOAuth().handleException(
			httpServletRequest, httpServletResponse, exception, sendBody);
	}

	public static String randomizeToken(String token) {
		return getOAuth().randomizeToken(token);
	}

	public static void validateOAuthMessage(
			OAuthMessage message, OAuthAccessor oAuthAccessor)
		throws OAuthException {

		getOAuth().validateOAuthMessage(message, oAuthAccessor);
	}

	private static String _getOAuthURI(String uriSuffix) {
		String oauthPublicPath = null;

		for (String publicPath : OAuthConstants.PUBLIC_PATHS) {
			if (publicPath.endsWith(uriSuffix)) {
				oauthPublicPath = publicPath;

				break;
			}
		}

		if (oauthPublicPath != null) {
			oauthPublicPath = "/c" + oauthPublicPath;
		}
		else {
			oauthPublicPath = StringPool.BLANK;
		}

		return oauthPublicPath;
	}

	private static String _accessTokenURI;
	private static String _authorizeURI;
	private static OAuth _oAuth;
	private static String _requestTokenURI;

}