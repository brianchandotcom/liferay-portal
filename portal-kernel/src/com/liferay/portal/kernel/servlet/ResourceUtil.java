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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.IOException;

import java.net.URL;

import javax.servlet.ServletContext;

/**
 * @author Minhchau Dang
 */
public class ResourceUtil {

	public static ObjectValuePair<ServletContext, URL> getObjectValuePair(
			String requestPath, String requestURI,
			ServletContext defaultServletContext)
		throws IOException {

		ServletContext servletContext = defaultServletContext;

		URL resourceURL = servletContext.getResource(requestURI);

		if (resourceURL != null) {
			return new ObjectValuePair<>(servletContext, resourceURL);
		}

		servletContext = PortalWebResourcesUtil.getPathServletContext(
			requestPath);

		resourceURL = PortalWebResourcesUtil.getResource(
			servletContext, requestPath);

		if (resourceURL != null) {
			return new ObjectValuePair<>(servletContext, resourceURL);
		}

		servletContext = PortletResourcesUtil.getPathServletContext(
			requestPath);

		resourceURL = PortletResourcesUtil.getResource(
			servletContext, requestPath);

		if (resourceURL != null) {
			return new ObjectValuePair<>(servletContext, resourceURL);
		}

		servletContext = DynamicResourceIncludeUtil.getPathServletContext(
			requestPath);

		resourceURL = DynamicResourceIncludeUtil.getResource(
			servletContext, requestPath);

		if (resourceURL != null) {
			return new ObjectValuePair<>(servletContext, resourceURL);
		}

		return null;
	}

	public static ServletContext getPathServletContext(
			String requestPath, String requestURI,
			ServletContext defaultServletContext)
		throws IOException {

		ObjectValuePair<ServletContext, URL> ovp = getObjectValuePair(
			requestPath, requestURI, defaultServletContext);

		if (ovp == null) {
			return null;
		}

		return ovp.getKey();
	}

	public static URL getResource(
			String requestPath, String requestURI,
			ServletContext defaultServletContext)
		throws IOException {

		ObjectValuePair<ServletContext, URL> ovp = getObjectValuePair(
			requestPath, requestURI, defaultServletContext);

		if (ovp == null) {
			return null;
		}

		return ovp.getValue();
	}

}