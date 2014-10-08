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

package com.liferay.portal.spring.extender.internal.classloader;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Miguel Pastor
 */
public class BundleResolverClassLoader extends ClassLoader {

	public BundleResolverClassLoader(Bundle ... bundles) {
		if (bundles.length == 0) {
			throw new IllegalArgumentException(
				"At least one bundle is required");
		}

		_bundles = bundles;
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
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (Bundle bundle : _bundles) {
			try {
				return bundle.loadClass(name);
			}
			catch (ClassNotFoundException cnfe) {
				continue;
			}
		}

		throw new ClassNotFoundException(name);
	}

	@Override
	protected URL findResource(String name) {
		for (Bundle bundle : _bundles) {
			URL url = bundle.getResource(name);

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	@Override
	protected Enumeration<URL> findResources(String name) {
		for (Bundle bundle : _bundles) {
			try {
				Enumeration<URL> resources = bundle.getResources(name);

				if ((resources != null) && resources.hasMoreElements()) {
					return resources;
				}
			}
			catch (IOException ioe) {
			}
		}

		return Collections.enumeration(Collections.<URL>emptyList());
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> clazz = findClass(name);

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	private final Bundle[] _bundles;

}