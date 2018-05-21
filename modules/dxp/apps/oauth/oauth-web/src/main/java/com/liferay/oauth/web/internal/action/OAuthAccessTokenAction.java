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