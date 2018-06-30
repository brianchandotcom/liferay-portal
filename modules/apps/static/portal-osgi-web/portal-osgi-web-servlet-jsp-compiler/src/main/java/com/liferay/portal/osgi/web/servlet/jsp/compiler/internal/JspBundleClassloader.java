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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collections;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Miguel Pastor
 */
public class JspBundleClassloader extends URLClassLoader {

	public JspBundleClassloader(Bundle... bundles) {
		super(new URL[0], PortalException.class.getClassLoader());

		if (bundles.length == 0) {
			throw new IllegalArgumentException(
				"At least one bundle is required");
		}

		_bundles = bundles;
	}

	@Override
	public URL findResource(String name) {
		for (Bundle bundle : _bundles) {
			URL url = bundle.getResource(name);

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	@Override
	public Enumeration<URL> findResources(String name) {
		for (Bundle bundle : _bundles) {
			try {
				Enumeration<URL> enumeration = bundle.getResources(name);

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					return enumeration;
				}
			}
			catch (IOException ioe) {
			}
		}

		return Collections.enumeration(Collections.<URL>emptyList());
	}

	public Bundle[] getBundles() {
		return _bundles;
	}

	@Override
	public URL getResource(String name) {
		return findResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) {
		return findResources(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> clazz = null;

		for (Bundle bundle : _bundles) {
			try {
				clazz = bundle.loadClass(name);

				break;
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		if (clazz == null) {
			return super.loadClass(name, resolve);
		}

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	private final Bundle[] _bundles;

}