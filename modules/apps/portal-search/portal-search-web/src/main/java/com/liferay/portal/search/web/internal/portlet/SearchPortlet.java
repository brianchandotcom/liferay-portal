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

package com.liferay.portal.search.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchPortletKeys;
import com.liferay.portal.search.web.internal.display.context.SearchDisplayContext;
import com.liferay.portal.search.web.internal.display.context.SearchDisplayContextFactory;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

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
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SearchPortletKeys.SEARCH,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class SearchPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		SearchDisplayContext searchDisplayContext =
			searchDisplayContextFactory.create(
				renderRequest, renderResponse, renderRequest.getPreferences());

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getOpenSearchXML")) {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			try {
				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse, null,
					getXML(resourceRequest, resourceResponse),
					ContentTypes.TEXT_XML_UTF8);
			}
			catch (Exception exception) {
				try {
					_portal.sendError(
						exception, httpServletRequest, httpServletResponse);
				}
				catch (ServletException servletException) {
				}
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	protected byte[] getXML(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ResourceURL openSearchResourceURL =
			resourceResponse.createResourceURL();

		openSearchResourceURL.setResourceID("getOpenSearchXML");

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		ResourceURL openSearchDescriptionXMLURL =
			resourceResponse.createResourceURL();

		openSearchDescriptionXMLURL.setParameter(
			"mvcPath", "/open_search_description.jsp");
		openSearchDescriptionXMLURL.setParameter(
			"groupId", String.valueOf(groupId));

		OpenSearch openSearch = new PortalOpenSearchImpl(
			openSearchResourceURL.toString(),
			openSearchDescriptionXMLURL.toString());

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		String xml = openSearch.search(
			httpServletRequest,
			openSearchResourceURL.toString() + StringPool.QUESTION +
				httpServletRequest.getQueryString());

		return xml.getBytes();
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portal.search.web)(&(release.schema.version>=2.0.0)(!(release.schema.version>=3.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference
	protected SearchDisplayContextFactory searchDisplayContextFactory;

	@Reference
	private Portal _portal;

}