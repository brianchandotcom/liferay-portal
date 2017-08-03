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
import com.liferay.oauth.util.DefaultOAuthAccessor;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthConsumer;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.OAuthUtil;
import com.liferay.oauth.util.WebServerUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "path=" + OAuthConstants.PUBLIC_PATH_REQUEST_TOKEN,
	service = StrutsAction.class
)
public class OAuthRequestTokenAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			OAuthMessage oAuthMessage = OAuthUtil.getOAuthMessage(
				request,
				WebServerUtil.getWebServerURL(request.getRequestURL()));

			OAuthConsumer oAuthConsumer = OAuthUtil.getOAuthConsumer(
				oAuthMessage);

			OAuthAccessor oAuthAccessor = new DefaultOAuthAccessor(
				oAuthConsumer);

			OAuthUtil.validateOAuthMessage(oAuthMessage, oAuthAccessor);

			String oAuthAccessorSecret = oAuthMessage.getParameter(
				net.oauth.OAuthConsumer.ACCESSOR_SECRET);

			if (oAuthAccessorSecret != null) {
				oAuthAccessor.setProperty(
					net.oauth.OAuthConsumer.ACCESSOR_SECRET,
					oAuthAccessorSecret);
			}

			OAuthUtil.generateRequestToken(oAuthAccessor);

			response.setContentType(ContentTypes.TEXT_PLAIN);

			OutputStream outputStream = response.getOutputStream();

			OAuthUtil.formEncode(
				oAuthAccessor.getRequestToken(), oAuthAccessor.getTokenSecret(),
				outputStream);

			outputStream.close();
		}
		catch (Exception e) {
			OAuthUtil.handleException(request, response, e, true);
		}

		return null;
	}

}