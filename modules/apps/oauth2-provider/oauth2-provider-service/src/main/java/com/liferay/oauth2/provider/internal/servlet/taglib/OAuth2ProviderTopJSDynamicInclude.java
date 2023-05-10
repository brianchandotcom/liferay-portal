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

package com.liferay.oauth2.provider.internal.servlet.taglib;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.redirect.OAuth2RedirectURIInterpolator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(service = DynamicInclude.class)
public class OAuth2ProviderTopJSDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		String url =
			_portal.getPortalURL(httpServletRequest) + _portal.getPathContext();

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.getOAuth2Applications(
				_portal.getCompanyId(httpServletRequest),
				ClientProfile.USER_AGENT_APPLICATION.id());

		for (OAuth2Application oAuth2Application : oAuth2Applications) {
			jsonObject.put(
				oAuth2Application.getExternalReferenceCode(),
				_jsonFactory.createJSONObject(
				).put(
					"clientId", oAuth2Application.getClientId()
				).put(
					"homePageURL", oAuth2Application.getHomePageURL()
				).put(
					"redirectURIs",
					_jsonFactory.createJSONArray(
						OAuth2RedirectURIInterpolator.
							interpolateRedirectURIsList(
								httpServletRequest,
								oAuth2Application.getRedirectURIsList(),
								_portal))
				));
		}

		String string = StringBundler.concat(
			"<script data-senna-track=\"temporary\" type=\"",
			ContentTypes.TEXT_JAVASCRIPT,
			"\">window.Liferay = Liferay || {}; window.Liferay.OAuth2 = ",
			"{getAuthorizeURL: function() {return '", url,
			"/o/oauth2/authorize';}, getBuiltInRedirectURL: function() ",
			"{return '", url,
			"/o/oauth2/redirect';}, getIntrospectURL: function() { return '",
			url, "/o/oauth2/introspect';}, getTokenURL: function() {return '",
			url, "/o/oauth2/token';}, getUserAgentApplication: ",
			"function(externalReferenceCode) {return ",
			"Liferay.OAuth2._userAgentApplications[externalReferenceCode];}, ",
			"_userAgentApplications: ", jsonObject, "}</script>");

		printWriter.write(string);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

}