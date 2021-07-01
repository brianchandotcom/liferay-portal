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

package com.liferay.shielded.container.internal;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ShieldedContainerClassLoader extends URLClassLoader {

	public static final String NAME =
		ShieldedContainerClassLoader.class.getName();

	public ShieldedContainerClassLoader(
		URL[] urls, ClassLoader fallbackClassLoader) {

		super(urls, null);

		_fallbackClassLoader = fallbackClassLoader;
	}

	@Override
	public URL findResource(String name) {
		URL url = super.findResource(name);

		if (url == null) {
			url = _fallbackClassLoader.getResource(name);
		}

		return url;
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {
		List<URL> urls = new ArrayList<>();

		Enumeration<URL> enumeration = super.findResources(name);

		while (enumeration.hasMoreElements()) {
			urls.add(enumeration.nextElement());
		}

		enumeration = _fallbackClassLoader.getResources(name);

		while (enumeration.hasMoreElements()) {
			urls.add(enumeration.nextElement());
		}

		return Collections.enumeration(urls);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return super.findClass(name);
		}
		catch (ClassNotFoundException classNotFoundException) {
			return _fallbackClassLoader.loadClass(name);
		}
	}

	private final ClassLoader _fallbackClassLoader;

}