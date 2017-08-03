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

package com.liferay.oauth.web.internal.action;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthAccessorConstants;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.OAuthUtil;
import com.liferay.oauth.util.WebServerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthProblemException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = "path=" + OAuthConstants.PUBLIC_PATH_ACCESS_TOKEN,
	service = StrutsAction.class
)
public class OAuthAccessTokenAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			OAuthMessage oAuthMessage = OAuthUtil.getOAuthMessage(
				request,
				WebServerUtil.getWebServerURL(request.getRequestURL()));

			OAuthAccessor oAuthAccessor = OAuthUtil.getOAuthAccessor(
				oAuthMessage);

			OAuthUtil.validateOAuthMessage(oAuthMessage, oAuthAccessor);

			boolean authorized = GetterUtil.getBoolean(
				oAuthAccessor.getProperty(OAuthAccessorConstants.AUTHORIZED));

			if (!authorized) {
				throw new OAuthProblemException(
					OAuth.Problems.ADDITIONAL_AUTHORIZATION_REQUIRED);
			}

			long userId = (Long)oAuthAccessor.getProperty(
				OAuthAccessorConstants.USER_ID);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				request);

			OAuthUtil.generateAccessToken(
				oAuthAccessor, userId, serviceContext);

			response.setContentType(ContentTypes.TEXT_PLAIN);

			OutputStream outputStream = response.getOutputStream();

			OAuthUtil.formEncode(
				oAuthAccessor.getAccessToken(), oAuthAccessor.getTokenSecret(),
				outputStream);

			outputStream.close();
		}
		catch (Exception e) {
			OAuthUtil.handleException(request, response, e, false);
		}

		return null;
	}

}