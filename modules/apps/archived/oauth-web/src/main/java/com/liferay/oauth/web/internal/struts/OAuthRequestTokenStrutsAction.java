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

package com.liferay.oauth.web.internal.struts;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.util.DefaultOAuthAccessor;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.WebServerUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuthConsumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "path=" + OAuthConstants.PUBLIC_PATH_REQUEST_TOKEN,
	service = StrutsAction.class
)
public class OAuthRequestTokenStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			OAuthMessage oAuthMessage = _oAuth.getOAuthMessage(
				httpServletRequest,
				WebServerUtil.getWebServerURL(
					httpServletRequest.getRequestURL()));

			OAuthAccessor oAuthAccessor = new DefaultOAuthAccessor(
				_oAuth.getOAuthConsumer(oAuthMessage));

			_oAuth.validateOAuthMessage(oAuthMessage, oAuthAccessor);

			String oAuthAccessorSecret = oAuthMessage.getParameter(
				OAuthConsumer.ACCESSOR_SECRET);

			if (oAuthAccessorSecret != null) {
				oAuthAccessor.setProperty(
					OAuthConsumer.ACCESSOR_SECRET, oAuthAccessorSecret);
			}

			_oAuth.generateRequestToken(oAuthAccessor);

			httpServletResponse.setContentType(ContentTypes.TEXT_PLAIN);

			OutputStream outputStream = httpServletResponse.getOutputStream();

			_oAuth.formEncode(
				oAuthAccessor.getRequestToken(), oAuthAccessor.getTokenSecret(),
				outputStream);

			outputStream.close();
		}
		catch (Exception exception) {
			_oAuth.handleException(
				httpServletRequest, httpServletResponse, exception, true);
		}

		return null;
	}

	@Reference
	private OAuth _oAuth;

}