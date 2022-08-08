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
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
@Component(immediate = true, service = DynamicInclude.class)
public class OAuth2ProviderApplicationUserAgentDynamicInclude
	implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", _portal.getCompanyId(httpServletRequest)));

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"clientProfile", ClientProfile.USER_AGENT_APPLICATION.id()));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (OAuth2Application oAuth2Application : oAuth2Applications) {
			jsonObject.put(
				oAuth2Application.getExternalReferenceCode(),
				JSONFactoryUtil.createJSONObject(
				).put(
					"clientId", oAuth2Application.getClientId()
				).put(
					"homePageURL", oAuth2Application.getHomePageURL()
				).put(
					"redirectURIs",
					JSONFactoryUtil.createJSONArray(
						oAuth2Application.getRedirectURIsList())
				));
		}

		String portalURL = _portal.getPortalURL(httpServletRequest);
		String pathContext = _portal.getPathContext();

		printWriter.write("<script data-senna-track=\"temporary\" ");
		printWriter.write("type=\"");
		printWriter.write(ContentTypes.TEXT_JAVASCRIPT);
		printWriter.write("\">");
		printWriter.write("window.Liferay = Liferay || {};");
		printWriter.write("window.Liferay.OAuth2 = {");
		printWriter.write("getAuthorizeURL: function() {");
		printWriter.write("return '");
		printWriter.write(portalURL);
		printWriter.write(pathContext);
		printWriter.write("/o/oauth2/authorize';");
		printWriter.write("}, ");
		printWriter.write("getBuiltInRedirectURL: function() {");
		printWriter.write("return '");
		printWriter.write(portalURL);
		printWriter.write(pathContext);
		printWriter.write("/o/oauth2/redirect';");
		printWriter.write("}, ");
		printWriter.write("getIntrospectURL: function() {");
		printWriter.write("return '");
		printWriter.write(portalURL);
		printWriter.write(pathContext);
		printWriter.write("/o/oauth2/introspect';");
		printWriter.write("}, ");
		printWriter.write("getTokenURL: function() {");
		printWriter.write("return '");
		printWriter.write(portalURL);
		printWriter.write(pathContext);
		printWriter.write("/o/oauth2/token';");
		printWriter.write("}, ");
		printWriter.write(
			"getUserAgentApplication: function(externalReferenceCode) {");
		printWriter.write("return Liferay.OAuth2._userAgentApplications[");
		printWriter.write("externalReferenceCode];");
		printWriter.write("}, ");
		printWriter.write("_userAgentApplications: ");
		printWriter.write(jsonObject.toString());
		printWriter.write("}</script>");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

}