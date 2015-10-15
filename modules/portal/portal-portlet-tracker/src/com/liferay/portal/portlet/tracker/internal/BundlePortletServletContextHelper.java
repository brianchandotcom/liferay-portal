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

package com.liferay.portal.portlet.tracker.internal;

import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class BundlePortletServletContextHelper extends ServletContextHelper {

	public BundlePortletServletContextHelper(Bundle bundle) {
		super(bundle);

		_bundle = bundle;

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle.getBundleId() + ']';
	}

	@Override
	public URL getResource(String name) {
		URL url = _getEntryInBundleOrFragments(name);

		if (url == null) {
			if (name.startsWith("/")) {
				name = name.substring(1);
			}

			if (!name.startsWith("META-INF/resources/")) {
				url = _getEntryInBundleOrFragments(
					"META-INF/resources/" + name);
			}
		}

		return url;
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest request, HttpServletResponse response) {

		String pathInfo = null;

		if (request.getAttribute(
				RequestDispatcher.INCLUDE_REQUEST_URI) != null) {

			pathInfo = (String)request.getAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO);
		}
		else {
			pathInfo = request.getPathInfo();
		}

		if (pathInfo == null) {
			return true;
		}

		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		if (pathInfo.startsWith("META-INF/") ||
			pathInfo.startsWith("OSGI-INF/") ||
			pathInfo.startsWith("OSGI-OPT/") ||
			pathInfo.startsWith("WEB-INF/")) {

			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, pathInfo);
			}
			catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return _string;
	}

	private URL _getEntryInBundleOrFragments(String name) {
		int lastIndexOf = name.lastIndexOf('/');

		String baseDir = "/";
		String fileName = name;

		if (lastIndexOf > 0) {
			baseDir = name.substring(0, lastIndexOf);
			fileName = name.substring(lastIndexOf + 1);
		}

		Enumeration<URL> entries = _bundle.findEntries(
			baseDir, fileName, false);

		if ((entries == null) || !entries.hasMoreElements()) {
			return null;
		}

		return entries.nextElement();
	}

	private final Bundle _bundle;
	private final String _string;

}