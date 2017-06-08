/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.http;

import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class DefaultHttpContext implements HttpContext {

	public DefaultHttpContext(Bundle bundle) {
		_bundle = bundle;
	}

	public String getMimeType(String name) {
		return MimeTypesUtil.getContentType(name);
	}

	public URL getResource(String path) {
		if (!path.startsWith(StringPool.SLASH)) {
			path = StringPool.SLASH.concat(path);
		}

		URL resourceURL = null;

		if (!path.startsWith(_EXT_PREFIX)) {
			resourceURL = getResource(_EXT_PREFIX.concat(path));

			if (resourceURL != null) {
				return resourceURL;
			}
		}

		resourceURL = _bundle.getResource(path);

		if (resourceURL != null) {
			return resourceURL;
		}

		String filePattern = path;

		int pos = path.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			filePattern = path.substring(pos + 1);
			path = path.substring(0, pos);
		}

		Enumeration<URL> findEntries = _bundle.findEntries(
			path, filePattern, false);

		if ((findEntries != null) && findEntries.hasMoreElements()) {
			return findEntries.nextElement();
		}

		return null;
	}

	public boolean handleSecurity(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return true;
	}

	private static final String _EXT_PREFIX = "/ext";
	private Bundle _bundle;

}