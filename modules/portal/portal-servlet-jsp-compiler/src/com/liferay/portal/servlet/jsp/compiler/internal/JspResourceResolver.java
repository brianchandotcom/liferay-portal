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

package com.liferay.portal.servlet.jsp.compiler.internal;

import java.io.IOException;

import java.net.JarURLConnection;
import java.net.URL;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTracker;

import org.phidias.compile.ResourceResolver;

/**
 * @author Raymond Augé
 */
public class JspResourceResolver implements ResourceResolver {

	public JspResourceResolver(JspResourceCache jspResourceCache) {
		_jspResourceCache = jspResourceCache;

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		_logger = new Logger(bundleContext);

		Filter filter = null;

		try {
			filter = bundleContext.createFilter(
				"(&(jsp.compiler.resource.map=*)(objectClass=" +
					Map.class.getName() + "))");
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

		_serviceTracker = new ServiceTracker<>(bundleContext, filter, null);

		_serviceTracker.open();
	}

	@Override
	public URL getResource(BundleWiring bundleWiring, String name) {
		Bundle bundle = bundleWiring.getBundle();

		URL url = bundle.getResource(name);

		if ((url == null) && (bundle.getBundleId() == 0)) {
			return _frameworkClassLoader.getResource(name);
		}

		return bundle.getResource(name);
	}

	@Override
	public Collection<String> resolveResources(
		BundleWiring bundleWiring, String path, String filePattern,
		int options) {

		Collection<String> resources = bundleWiring.listResources(
			path, filePattern, options);

		Bundle bundle = bundleWiring.getBundle();

		if (((resources == null) || resources.isEmpty()) &&
			(bundle.getBundleId() == 0)) {

			return handleSystemBundle(bundleWiring, path, filePattern, options);
		}

		return resources;
	}

	protected Collection<String> handleSystemBundle(
		BundleWiring bundleWiring, final String path, final String fileRegex,
		int options) {

		String key = path + '/' + fileRegex;

		Collection<String> resources = _jspResourceCache.getResources(
			bundleWiring, key);

		if (resources != null) {
			return resources;
		}

		resources = new ArrayList<>();

		Map<String, List<URL>> extraPackageMap = _serviceTracker.getService();

		if (extraPackageMap == null) {
			_jspResourceCache.putResources(bundleWiring, key, resources);

			return resources;
		}

		String packageName = path.replace('/', '.');

		if (!exportsPackage(bundleWiring, packageName)) {
			_jspResourceCache.putResources(bundleWiring, key, resources);

			return resources;
		}

		List<URL> urls = extraPackageMap.get(packageName);

		if ((urls == null) || urls.isEmpty()) {
			ClassLoader classLoader = bundleWiring.getClassLoader();

			try {
				Enumeration<URL> resources2 = classLoader.getResources(path);

				if ((resources2 != null) && resources2.hasMoreElements()) {
					urls = Collections.list(resources2);
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		if ((urls == null) || urls.isEmpty()) {
			_jspResourceCache.putResources(bundleWiring, key, resources);

			return resources;
		}

		String matcherRegex = fileRegex.replace("*", "[^/]*");

		matcherRegex = matcherRegex.replace(".", "\\.");

		matcherRegex = path + "/" + matcherRegex;

		for (URL url : urls) {
			try {
				JarURLConnection jarUrlConnection =
					(JarURLConnection)url.openConnection();

				JarFile jarFile = jarUrlConnection.getJarFile();

				Enumeration<? extends ZipEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					ZipEntry zipEntry = enumeration.nextElement();

					String name = zipEntry.getName();

					if (name.matches(matcherRegex)) {
						resources.add(name);
					}
				}
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_jspResourceCache.putResources(bundleWiring, key, resources);

		return resources;
	}

	private boolean exportsPackage(
		BundleWiring bundleWiring, String packageName) {

		List<BundleWire> providedWires = bundleWiring.getProvidedWires(
			"osgi.wiring.package");

		for (BundleWire bundleWire : providedWires) {
			BundleCapability capability = bundleWire.getCapability();

			Map<String, Object> attributes = capability.getAttributes();

			if (attributes.get("osgi.wiring.package").equals(packageName)) {
				return true;
			}
		}

		return false;
	}

	private static final ClassLoader _frameworkClassLoader;

	static {
		if (System.getSecurityManager() != null) {
			_frameworkClassLoader = AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return Bundle.class.getClassLoader();
					}

				});
		}
		else {
			_frameworkClassLoader = Bundle.class.getClassLoader();
		}
	}

	private final JspResourceCache _jspResourceCache;
	private final Logger _logger;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}