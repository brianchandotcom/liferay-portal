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

package com.liferay.portal.wab.extender.internal;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class WabServletContextHelper extends ServletContextHelper {

	public WabServletContextHelper(Bundle bundle) {
		super(bundle);

		_bundle = bundle;

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle.getBundleId() + ']';
	}

	@Override
	public String getMimeType(String name) {
		return super.getMimeType(name);
	}

	@Override
	public String getRealPath(String path) {
		URL url = getResource(path);

		if (url == null) {
			return null;
		}

		return url.toExternalForm();
	}

	@Override
	public URL getResource(String name) {
		if ((name == null) || (_bundle == null)) {
			return null;
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		URL url = _bundle.getEntry(name);

		if (url == null) {
			url = _bundle.getResource(name);
		}

		return url;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		if ((path == null) || (_bundle == null)) {
			return null;
		}

		Enumeration<URL> enumeration = _bundle.findEntries(path, null, false);

		if (enumeration == null) {
			return null;
		}

		Set<String> paths = new HashSet<String>();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			paths.add(url.getPath());
		}

		return paths;
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest request, HttpServletResponse response) {

		return true;
	}

	@Override
	public String toString() {
		return _string;
	}

	private Bundle _bundle;
	private final String _string;

}