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

package com.liferay.oauth.web.internal.struts;

import com.liferay.oauth.constants.OAuthAccessorConstants;
import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.WebServerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuthProblemException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = "path=" + OAuthConstants.PUBLIC_PATH_ACCESS_TOKEN,
	service = StrutsAction.class
)
public class OAuthAccessTokenStrutsAction implements StrutsAction {

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

			OAuthAccessor oAuthAccessor = _oAuth.getOAuthAccessor(oAuthMessage);

			_oAuth.validateOAuthMessage(oAuthMessage, oAuthAccessor);

			boolean authorized = GetterUtil.getBoolean(
				oAuthAccessor.getProperty(OAuthAccessorConstants.AUTHORIZED));

			if (!authorized) {
				throw new OAuthProblemException(
					net.oauth.OAuth.Problems.ADDITIONAL_AUTHORIZATION_REQUIRED);
			}

			long userId = (Long)oAuthAccessor.getProperty(
				OAuthAccessorConstants.USER_ID);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			_oAuth.generateAccessToken(oAuthAccessor, userId, serviceContext);

			httpServletResponse.setContentType(ContentTypes.TEXT_PLAIN);

			OutputStream outputStream = httpServletResponse.getOutputStream();

			_oAuth.formEncode(
				oAuthAccessor.getAccessToken(), oAuthAccessor.getTokenSecret(),
				outputStream);

			outputStream.close();
		}
		catch (Exception exception) {
			_oAuth.handleException(
				httpServletRequest, httpServletResponse, exception, false);
		}

		return null;
	}

	@Reference
	private OAuth _oAuth;

}