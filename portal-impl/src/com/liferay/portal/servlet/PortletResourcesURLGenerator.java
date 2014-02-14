/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class PortletResourcesURLGenerator {

	public List<String> generate(
		Portlet portlet,
		PortletResourcesAccessor ... portletResourcesAccessors) {

		List<String> urls = new ArrayList<String>();

		for (
			PortletResourcesAccessor portletResourcesAccessor :
				portletResourcesAccessors) {

			String contextPath;

			if (portletResourcesAccessor.isPortalResource()) {
				contextPath = PortalUtil.getPathContext();
			}
			else {
				contextPath = portlet.getContextPath();
			}

			for (String resource : portletResourcesAccessor.get(portlet)) {
				if (!HttpUtil.hasProtocol(resource)) {
					Portlet curRootPortlet = portlet.getRootPortlet();

					resource = PortalUtil.getStaticResourceURL(
						_request, contextPath + resource,
						curRootPortlet.getTimestamp());
				}

				if (!resource.contains(Http.PROTOCOL_DELIMITER)) {
					String cdnBaseURL = _themeDisplay.getCDNBaseURL();

					resource = cdnBaseURL.concat(resource);
				}

				if (!_visited.contains(resource)) {
					urls.add(resource);

					_visited.add(resource);
				}
			}
		}

		return urls;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public void setVisited(Set<String> visited) {
		_visited = visited;
	}

	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;
	private Set<String> _visited;

}