/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.deploy.hot.HookHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceTestUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;

import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * @author Raymond Augé
 */
public class PACLIntegrationJUnitTestRunner
	extends LiferayIntegrationJUnitTestRunner {

	public PACLIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(wrapTestClass(clazz));
	}

	@Override
	public void initApplicationContext() {
		TestClass testClass = getTestClass();

		Class<?> clazz = testClass.getJavaClass();

		if (_initialized) {
			return;
		}

		URL resource = clazz.getResource("dependencies/pacl-test.properties");

		if (resource != null) {
			System.setProperty("external-properties", resource.getPath());
		}

		System.setProperty("catalina.base", ".");

		System.setProperty(
			Context.INITIAL_CONTEXT_FACTORY,
			"org.apache.naming.java.javaURLContextFactory");

		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		HotDeployUtil.registerListener(new HookHotDeployListener());

		HotDeployUtil.setCapturePrematureEvents(false);

		PortalLifecycleUtil.flushInits();

		_initialized = true;
	}

	private static Class<?> wrapTestClass(Class<?> clazz)
		throws InitializationError {

		try {
			URL location =
				clazz.getProtectionDomain().getCodeSource().getLocation();

			ClassLoader classLoader = new PACLClassLoader(
				new URL[] {location}, clazz.getClassLoader());

			return Class.forName(clazz.getName(), true, classLoader);
		}
		catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	private static final String _packageBasePath =
		"com.liferay.portal.security.pacl.test.";
	protected static final String _resourceBasePath =
		"com/liferay/portal/security/pacl/test/dependencies";

	private static boolean _initialized = false;

	private static class PACLClassLoader extends URLClassLoader {

		public PACLClassLoader(URL[] urls, ClassLoader parentClassLoader) {
			super(urls, parentClassLoader);
		}

		@Override
		public URL getResource(String name) {
			if (name.equals(
					"com/liferay/util/bean/PortletBeanLocatorUtil.class")) {

				URL url = findResource("/");

				String path = url.getPath();

				path = path.substring(
					0, path.length() - _resourceBasePath.length() - 1);

				path = path.concat(name);

				try {
					return new URL("file", null, path);
				}
				catch (MalformedURLException e) {
				}
			}

			// child first

			URL resource = findResource(name);

			if (resource != null) {
				return resource;
			}

			return super.getResource(name);
		}

		@Override
		public URL findResource(String name) {
			if (_resourceCache.containsKey(name)) {
				return _resourceCache.get(name);
			}

			URL resource = null;

			if (!name.contains(_resourceBasePath)) {
				String newName = name;

				if (!newName.startsWith(StringPool.SLASH)) {
					newName = StringPool.SLASH.concat(newName);
				}

				newName = _resourceBasePath.concat(newName);

				resource = super.findResource(newName);
			}

			if (resource == null) {
				resource = super.findResource(name);
			}

			if (resource != null) {
				_resourceCache.put(name, resource);
			}

			return resource;
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (name.startsWith(_packageBasePath)) {
				if (_classCache.containsKey(name)) {
					return _classCache.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classCache.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name);
		}

		@Override
		protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

			if (name.startsWith(_packageBasePath)) {
				if (_classCache.containsKey(name)) {
					return _classCache.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classCache.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name, resolve);
		}

		private Map<String, Class<?>> _classCache =
			new ConcurrentHashMap<String, Class<?>>();
		private Map<String, URL> _resourceCache =
			new ConcurrentHashMap<String, URL>();

	}

}