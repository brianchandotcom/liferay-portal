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

package com.liferay.portal.test.runners;

import com.liferay.portal.deploy.hot.IndexerPostProcessorRegistry;
import com.liferay.portal.deploy.hot.SchedulerEntryRegistry;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceTestUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;

import org.junit.runners.model.InitializationError;

/**
 * @author Raymond Augé
 */
public class PACLIntegrationJUnitTestRunner
	extends LiferayIntegrationJUnitTestRunner {

	public static final String RESOURCE_PATH =
		"com/liferay/portal/security/pacl/test/dependencies";

	public PACLIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(_wrapTestClass(clazz));
	}

	@Override
	public void initApplicationContext() {
		if (_initialized) {
			return;
		}

		Class<?> clazz = getClass();

		URL resource = clazz.getResource("pacl-test.properties");

		if (resource != null) {
			System.setProperty("external-properties", resource.getPath());
		}

		System.setProperty(
			Context.INITIAL_CONTEXT_FACTORY,
			"org.apache.naming.java.javaURLContextFactory");

		super.initApplicationContext();

		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		new IndexerPostProcessorRegistry();
		new SchedulerEntryRegistry();
		new ServiceWrapperRegistry();

		_initialized = true;
	}

	private static Class<?> _wrapTestClass(Class<?> clazz)
		throws InitializationError {

		try {
			ProtectionDomain protectionDomain = clazz.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			ClassLoader classLoader = new PACLClassLoader(
				new URL[] {codeSource.getLocation()}, clazz.getClassLoader());

			return Class.forName(clazz.getName(), true, classLoader);
		}
		catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	private static final String _PACKAGE_PATH =
		"com.liferay.portal.security.pacl.test.";

	private static boolean _initialized;

	private static class PACLClassLoader extends URLClassLoader {

		public PACLClassLoader(URL[] urls, ClassLoader parentClassLoader) {
			super(urls, parentClassLoader);
		}

		@Override
		public URL findResource(String name) {
			if (_urls.containsKey(name)) {
				return _urls.get(name);
			}

			URL resource = null;

			if (!name.contains(RESOURCE_PATH)) {
				String newName = name;

				if (!newName.startsWith(StringPool.SLASH)) {
					newName = StringPool.SLASH.concat(newName);
				}

				newName = RESOURCE_PATH.concat(newName);

				resource = super.findResource(newName);
			}

			if ((resource == null) && !name.contains(RESOURCE_PATH)) {
				String newName = name;

				if (!newName.startsWith(StringPool.SLASH)) {
					newName = StringPool.SLASH.concat(newName);
				}

				newName = RESOURCE_PATH.concat("/WEB-INF/classes").concat(
					newName);

				resource = super.findResource(newName);
			}

			if (resource == null) {
				resource = super.findResource(name);
			}

			if (resource != null) {
				_urls.put(name, resource);
			}

			return resource;
		}

		@Override
		public URL getResource(String name) {
			if (name.equals(
					"com/liferay/util/bean/PortletBeanLocatorUtil.class")) {

				URL url = findResource("/");

				String path = url.getPath();

				path = path.substring(
					0, path.length() - RESOURCE_PATH.length() - 1);

				path = path.concat(name);

				try {
					return new URL("file", null, path);
				}
				catch (MalformedURLException murle) {
				}
			}

			URL url = findResource(name);

			if (url != null) {
				return url;
			}

			return super.getResource(name);
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (name.startsWith(_PACKAGE_PATH)) {
				if (_classes.containsKey(name)) {
					return _classes.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classes.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name);
		}

		@Override
		protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

			if (name.startsWith(_PACKAGE_PATH)) {
				if (_classes.containsKey(name)) {
					return _classes.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classes.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name, resolve);
		}

		private final Map<String, Class<?>> _classes =
			new ConcurrentHashMap<String, Class<?>>();
		private final Map<String, URL> _urls =
			new ConcurrentHashMap<String, URL>();

	}

}