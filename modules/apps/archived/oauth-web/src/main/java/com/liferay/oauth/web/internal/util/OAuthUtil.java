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

package com.liferay.oauth.web.internal.util;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 * @author Igor Beslic
 */
@Component(immediate = true, service = {})
public class OAuthUtil {

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

	public static OAuthAccessor getOAuthAccessor(OAuthMessage oAuthMessage)
		throws PortalException {

		return _oAuth.getOAuthAccessor(oAuthMessage);
	}

	public static OAuthMessage getOAuthMessage(
		HttpServletRequest httpServletRequest, String url) {

		return _oAuth.getOAuthMessage(httpServletRequest, url);
	}

	public static String getRequestTokenURI() {
		if (_requestTokenURI == null) {
			_requestTokenURI = _getOAuthURI("request_token");
		}

		return _requestTokenURI;
	}

	@Reference(unbind = "-")
	protected void setOAuth(OAuth oAuth) {
		_oAuth = oAuth;
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