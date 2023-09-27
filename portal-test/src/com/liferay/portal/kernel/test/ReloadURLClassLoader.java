/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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