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

package com.liferay.portal.security.wedeploy.auth.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthTokenConstants;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchAppException;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchTokenException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;
import com.liferay.portal.security.wedeploy.auth.service.WeDeployAuthTokenLocalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Supritha Sundaram
 */
@Component(
	immediate = true, property = {"path=/portal/wedeploy/access_token"},
	service = StrutsAction.class
)
public class WeDeployAccessTokenAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			WeDeployTokenActionUtil.sendLoginRedirect(
				request, response, themeDisplay.getPlid());

			return null;
		}

		String clientId = ParamUtil.getString(request, "client_id");
		String clientSecret = ParamUtil.getString(request, "client_secret");
		String authorizationToken = ParamUtil.getString(request, "code");
		String redirectURI = ParamUtil.getString(request, "redirect_uri");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WeDeployAuthToken.class.getName(), request);

		try {
			WeDeployAuthToken weDeployAuthAccessToken =
				_weDeployAuthTokenLocalService.addAccessWeDeployAuthToken(
					themeDisplay.getUserId(), clientId, clientSecret,
					authorizationToken,
					WeDeployAuthTokenConstants.TYPE_AUTHORIZATION,
					serviceContext);

			redirectURI = HttpUtil.addParameter(
				redirectURI, "token", weDeployAuthAccessToken.getToken());

			JSONObject elementJSONObject = JSONFactoryUtil.createJSONObject();

			elementJSONObject.put(
				"email", themeDisplay.getUser().getEmailAddress());
			elementJSONObject.put("name", themeDisplay.getUser().getFullName());

			jsonObject.put("info", elementJSONObject);
		}
		catch (NoSuchAppException nsae) {
			_log.error(nsae);

			jsonObject.put(
				"error_message",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"client-id-and-client-secret-do-not-match"));
		}
		catch (NoSuchTokenException nste) {
			_log.error(nste);

			jsonObject.put(
				"error_message",
				LanguageUtil.get(
					themeDisplay.getLocale(), "request-token-does-not-match"));
		}
		catch (Exception e) {
			_log.error(e);

			jsonObject.put(
				"error_message",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"an-error-occurred-while-processing-the-requested-" +
						"resource"));
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);

		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		ServletResponseUtil.write(response, jsonObject.toString());

		response.sendRedirect(redirectURI);

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WeDeployAccessTokenAction.class);

	@Reference
	private WeDeployAuthTokenLocalService _weDeployAuthTokenLocalService;

}