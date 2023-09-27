/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	static {
		ClassLoader.registerAsParallelCapable();
	}

}