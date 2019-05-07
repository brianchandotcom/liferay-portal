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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class PortletResourceStaticURLGenerator {

	public List<String> generate(
		Portlet portlet, PortletResourceAccessor... portletResourceAccessors) {

		List<String> urls = new ArrayList<>();

		for (PortletResourceAccessor portletResourceAccessor :
				portletResourceAccessors) {

			String contextPath = null;

			if (portletResourceAccessor.isPortalResource()) {
				contextPath = PortalUtil.getPathContext();
			}
			else {
				contextPath =
					PortalUtil.getPathProxy() + portlet.getContextPath();
			}

			List<String> portletResources = portletResourceAccessor.get(
				portlet);

			for (String portletResource : portletResources) {
				if (!HttpUtil.hasProtocol(portletResource)) {
					Portlet rootPortlet = portlet.getRootPortlet();

					portletResource = PortalUtil.getStaticResourceURL(
						_httpServletRequest, contextPath + portletResource,
						rootPortlet.getTimestamp());
				}

				if (!portletResource.contains(Http.PROTOCOL_DELIMITER)) {
					String cdnBaseURL = _themeDisplay.getCDNBaseURL();

					portletResource = cdnBaseURL.concat(portletResource);
				}

				if (!_visitedURLs.contains(portletResource)) {
					urls.add(portletResource);

					_visitedURLs.add(portletResource);
				}
			}
		}

		return urls;
	}

	public void setRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public void setVisitedURLs(Set<String> visitedURLs) {
		_visitedURLs = visitedURLs;
	}

	private HttpServletRequest _httpServletRequest;
	private ThemeDisplay _themeDisplay;
	private Set<String> _visitedURLs;

}