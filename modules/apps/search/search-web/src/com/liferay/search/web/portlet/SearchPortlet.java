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

package com.liferay.search.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.PortalOpenSearchImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.search.web.constants.SearchPortletKeys;
import com.liferay.search.web.upgrade.SearchWebUpgrade;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-search",
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SearchPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals("getOpenSearchXML")) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

			try {
				ServletResponseUtil.sendFile(
					request, response, null, getXML(request),
					ContentTypes.TEXT_XML_UTF8);
			}
			catch (Exception e) {
				try {
					PortalUtil.sendError(e, request, response);
				}
				catch (ServletException se) {
				}
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	protected byte[] getXML(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl openSearchResourceURL = new PortletURLImpl(
			request, SearchPortletKeys.SEARCH, themeDisplay.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		openSearchResourceURL.setParameter(Constants.CMD, "getOpenSearchXML");

		long groupId = ParamUtil.getLong(request, "groupId");

		PortletURLImpl openSearchDescriptionXMLURL = new PortletURLImpl(
			request, SearchPortletKeys.SEARCH, themeDisplay.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		openSearchDescriptionXMLURL.setParameter(
			"mvcPath", "/open_search_description.jsp");
		openSearchDescriptionXMLURL.setParameter(
			"groupId", String.valueOf(groupId));

		OpenSearch search = new PortalOpenSearchImpl(
			openSearchResourceURL.toString(),
			openSearchDescriptionXMLURL.toString());

		String xml = search.search(
			request,
			openSearchResourceURL.toString() + StringPool.QUESTION +
				request.getQueryString());

		return xml.getBytes();
	}

	@Reference(unbind = "-")
	protected void setSearchWebUpgrade(SearchWebUpgrade searchWebUpgrade) {
	}

}