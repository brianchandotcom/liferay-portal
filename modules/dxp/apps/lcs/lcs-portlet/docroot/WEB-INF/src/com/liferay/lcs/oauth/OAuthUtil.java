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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletRequest;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

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

	public static OAuthConsumer getOAuthConsumer(
		String accessToken, String accessSecret) {

		OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(
			PortletPropsValues.OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY,
			PortletPropsValues.OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET);

		oAuthConsumer.setTokenWithSecret(accessToken, accessSecret);

		return oAuthConsumer;
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

}