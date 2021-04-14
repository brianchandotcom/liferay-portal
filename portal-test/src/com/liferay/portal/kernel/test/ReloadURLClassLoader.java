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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class ReloadURLClassLoader extends URLClassLoader {

	public ReloadURLClassLoader(Class<?>... reloadClasses) {
		super(
			_toJarURLs(reloadClasses), _buildParentClassLoader(reloadClasses));
	}

	private static ClassLoader _buildParentClassLoader(
		Class<?>... reloadClasses) {

		ClassLoader[] classloaders = new ClassLoader[reloadClasses.length];

		Set<String> reloadClassNames = new HashSet<>();

		for (int i = 0; i < reloadClasses.length; i++) {
			classloaders[i] = reloadClasses[i].getClassLoader();

			reloadClassNames.add(reloadClasses[i].getName());
		}

		return new ClassLoader(
			AggregateClassLoader.getAggregateClassLoader(classloaders)) {

			@Override
			protected Class<?> loadClass(String name, boolean resolve)
				throws ClassNotFoundException {

				if (reloadClassNames.contains(name)) {
					throw new ClassNotFoundException();
				}

				return super.loadClass(name, resolve);
			}

		};
	}

	private static URL[] _toJarURLs(Class<?>... reloadClasses) {
		if (reloadClasses.length == 0) {
			throw new IllegalArgumentException("Reload classes is empty");
		}

		List<URL> urls = new ArrayList<>();

		for (Class<?> reloadClass : reloadClasses) {
			ProtectionDomain protectionDomain =
				reloadClass.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			URL url = codeSource.getLocation();

			urls.add(url);
		}

		return urls.toArray(new URL[0]);
	}

}