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

package com.liferay.oauth.web.internal.portlet;

import com.liferay.oauth.exception.OAuthException;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthConsumer;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.OAuthWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=oauth-portlet oauth-portlet-authorize",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=OAuth Authorize",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/authorize/",
		"javax.portlet.init-param.view-template=/authorize/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class AuthorizePortlet extends MVCPortlet {

	public void authorize(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String oAuthCallbackURL = ParamUtil.getString(
			actionRequest, net.oauth.OAuth.OAUTH_CALLBACK);

		if (_OAUTH_CALLBACK_OOB.equals(oAuthCallbackURL)) {
			oAuthCallbackURL = null;
		}

		OAuthMessage oAuthMessage = _oAuth.getOAuthMessage(actionRequest, null);

		OAuthAccessor oAuthAccessor = null;

		try {
			oAuthAccessor = _oAuth.getOAuthAccessor(oAuthMessage);
		}
		catch (OAuthException oAuthException) {
			if (_log.isWarnEnabled()) {
				_log.warn("OAuth authorisation failed", oAuthException);
			}

			if (Validator.isNotNull(oAuthCallbackURL)) {
				oAuthCallbackURL = _oAuth.addParameters(
					oAuthCallbackURL, "oauth_problem",
					oAuthException.getMessage());

				actionResponse.sendRedirect(oAuthCallbackURL);

				return;
			}

			SessionErrors.add(
				actionRequest, OAuthException.class, oAuthException);

			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_oAuth.authorize(
			oAuthAccessor, serviceContext.getUserId(), serviceContext);

		OAuthConsumer oAuthConsumer = oAuthAccessor.getOAuthConsumer();

		String callbackURL = oAuthConsumer.getCallbackURL();

		if (Validator.isNull(oAuthCallbackURL) &&
			Validator.isNotNull(callbackURL) &&
			!callbackURL.equals(_OAUTH_CALLBACK_OOB)) {

			oAuthCallbackURL = callbackURL;
		}

		String requestToken = oAuthAccessor.getRequestToken();

		String oAuthVerifier = DigesterUtil.digestHex(
			Digester.MD5, oAuthCallbackURL + System.nanoTime() + requestToken);

		if (Validator.isNull(oAuthCallbackURL)) {
			actionRequest.setAttribute(
				OAuthWebKeys.OAUTH_ACCESSOR, oAuthAccessor);
			actionRequest.setAttribute(
				OAuthWebKeys.OAUTH_VERIFIER, oAuthVerifier);
		}
		else {
			if (requestToken != null) {
				oAuthCallbackURL = _oAuth.addParameters(
					oAuthCallbackURL, net.oauth.OAuth.OAUTH_TOKEN, requestToken,
					net.oauth.OAuth.OAUTH_VERIFIER, oAuthVerifier);
			}

			actionResponse.sendRedirect(oAuthCallbackURL);
		}
	}

	private static final String _OAUTH_CALLBACK_OOB = "oob";

	private static final Log _log = LogFactoryUtil.getLog(
		AuthorizePortlet.class);

	@Reference
	private OAuth _oAuth;

}