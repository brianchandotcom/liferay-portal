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

import com.liferay.oauth.exception.OAuthException;
import com.liferay.portal.kernel.exception.PortalException;
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

	public OAuthMessage getOAuthMessage(HttpServletRequest httpServletRequest);

	public OAuthMessage getOAuthMessage(
		HttpServletRequest httpServletRequest, String url);

	public OAuthMessage getOAuthMessage(PortletRequest portletRequest);

	public OAuthMessage getOAuthMessage(
		PortletRequest portletRequest, String url);

	public void handleException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception,
			boolean sendBody)
		throws OAuthException;

	public String randomizeToken(String token);

	public void validateOAuthMessage(
			OAuthMessage oAuthMessage, OAuthAccessor oAuthAccessor)
		throws OAuthException;

}