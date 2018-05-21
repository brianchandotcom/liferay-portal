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

package com.liferay.oauth.util;

import com.liferay.oauth.configuration.OAuthConfigurationValues;
import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.oauth.OAuthException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringPool;

import java.io.OutputStream;

import java.lang.reflect.Constructor;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 * @author Igor Beslic
 */
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

				Constructor oauthConstructor = clazz.getConstructor(
					OAuthValidator.class);

				_oAuth = (OAuth)oauthConstructor.newInstance(
					new DefaultOAuthValidator());
			}
			catch (Exception e) {
				throw new RuntimeException(e);
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

	public static OAuthMessage getOAuthMessage(HttpServletRequest request) {
		return getOAuth().getOAuthMessage(request);
	}

	public static OAuthMessage getOAuthMessage(
		HttpServletRequest request, String url) {

		return getOAuth().getOAuthMessage(request, url);
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
			HttpServletRequest request, HttpServletResponse response,
			Exception exception, boolean sendBody)
		throws OAuthException {

		getOAuth().handleException(request, response, exception, sendBody);
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