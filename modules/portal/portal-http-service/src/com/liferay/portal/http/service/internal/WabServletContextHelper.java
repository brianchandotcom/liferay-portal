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
package com.liferay.portal.http.service.internal;

import java.io.IOException;

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

		this.bundle = bundle;
		this.string = getClass().getSimpleName() + '[' + bundle.getBundleId() +
			']';
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
		if ((name == null) || (bundle == null)) {
			return null;
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		URL entry = bundle.getEntry(name);

		if (entry == null) {
			entry = bundle.getResource(name);
		}

		return entry;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		if ((path == null) || (bundle == null)) {
			return null;
		}

		Enumeration<URL> enumeration = bundle.findEntries(path, null, false);

		if (enumeration == null) {
			return null;
		}

		Set<String> result = new HashSet<String>();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			result.add(url.getPath());
		}

		return result;
	}

	@Override
	public boolean handleSecurity(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return true;
	}

	@Override
	public String toString() {
		return string;
	}

	private final Bundle bundle;
	private final String string;

}