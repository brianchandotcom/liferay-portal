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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.oauth.OAuthException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.OutputStream;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface OAuth {

	public String addParameters(String url, String... parameters)
		throws OAuthException;

	public void authorize(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException;

	public void formEncode(
			String token, String tokenSecret, OutputStream outputStream)
		throws OAuthException;

	public void generateAccessToken(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException;

	public void generateRequestToken(OAuthAccessor oAuthAccessor);

	public OAuthAccessor getOAuthAccessor(OAuthMessage oAuthMessage)
		throws PortalException;

	public OAuthConsumer getOAuthConsumer(OAuthMessage oAuthMessage)
		throws PortalException;

	public OAuthMessage getOAuthMessage(HttpServletRequest request);

	public OAuthMessage getOAuthMessage(HttpServletRequest request, String url);

	public OAuthMessage getOAuthMessage(PortletRequest portletRequest);

	public OAuthMessage getOAuthMessage(
		PortletRequest portletRequest, String url);

	public void handleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception exception, boolean sendBody)
		throws OAuthException;

	public String randomizeToken(String token);

	public void validateOAuthMessage(
			OAuthMessage oAuthMessage, OAuthAccessor oAuthAccessor)
		throws OAuthException;

}