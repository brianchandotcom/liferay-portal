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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
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

	public JspResourceResolver(Bundle bundle, Bundle jspBundle, Logger logger) {
		_bundle = bundle;
		_jspBundle = jspBundle;
		_logger = logger;

		BundleContext bundleContext = _bundle.getBundleContext();

		try {
			_serviceTracker = ServiceTrackerFactory.open(
				bundleContext,
				"(&(jsp.compiler.resource.map=*)(objectClass=" +
					Map.class.getName() + "))");
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public URL getResource(BundleWiring bundleWiring, String name) {
		Bundle bundle = bundleWiring.getBundle();

		URL url = bundle.getResource(name);

		if ((url == null) && (bundle.getBundleId() == 0)) {
			ClassLoader classLoader = bundleWiring.getClassLoader();

			return classLoader.getResource(name);
		}

		return url;
	}

	@Override
	public Collection<String> resolveResources(
		BundleWiring bundleWiring, String path, String filePattern,
		int options) {

		Collection<String> resources = null;

		Bundle bundle = bundleWiring.getBundle();

		if (bundle.equals(_bundle) || bundle.equals(_jspBundle)) {
			resources = bundleWiring.listResources(path, filePattern, options);
		}
		else if (isExportsPackage(bundleWiring, path.replace('/', '.'))) {
			if (bundle.getBundleId() == 0) {
				resources = handleSystemBundle(
					bundleWiring, path, filePattern, options);
			}
			else {
				resources = bundleWiring.listResources(
					path, filePattern, options);
			}
		}

		return resources;
	}

	protected String decodePath(String path) {
		path = StringUtil.replace(
			path, StringPool.SLASH, "_LIFERAY_TEMP_SLASH_");

		path = URLCodec.decodeURL(path, StringPool.UTF8);

		path = StringUtil.replace(
			path, "_LIFERAY_TEMP_SLASH_", StringPool.SLASH);

		return path;
	}

	protected JarFile getJarFile(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();

		String fileName = url.getFile();

		if (urlConnection instanceof JarURLConnection) {
			JarURLConnection jarURLConnection = (JarURLConnection)urlConnection;

			URL jarFileURL = jarURLConnection.getJarFileURL();

			fileName = jarFileURL.getFile();
		}
		else if (Validator.equals(url.getProtocol(), "vfs")) {

			// JBoss uses a custom vfs protocol to represent JAR files

			fileName = url.getFile();

			int index = fileName.indexOf(".jar");

			if (index > 0) {
				fileName = fileName.substring(0, index + 4);
			}
		}
		else if (Validator.equals(url.getProtocol(), "wsjar")) {

			// WebSphere uses a custom wsjar protocol to represent JAR files

			fileName = url.getFile();

			String protocol = "file:/";

			int index = fileName.indexOf(protocol);

			if (index > -1) {
				fileName = fileName.substring(protocol.length());
			}

			index = fileName.indexOf('!');

			if (index > -1) {
				fileName = fileName.substring(0, index);
			}
		}
		else if (Validator.equals(url.getProtocol(), "zip")) {

			// Weblogic uses a custom zip protocol to represent JAR files

			fileName = url.getFile();

			int index = fileName.indexOf('!');

			if (index > 0) {
				fileName = fileName.substring(0, index);
			}
		}

		fileName = decodePath(fileName);

		return new JarFile(fileName);
	}

	protected Collection<String> handleSystemBundle(
		BundleWiring bundleWiring, String path, String fileRegex, int options) {

		String key = path + '/' + fileRegex;

		Collection<String> resources = _jspResourceCache.get(key);

		if (resources != null) {
			return resources;
		}

		resources = new ArrayList<>();

		String packageName = path.replace('/', '.');

		List<URL> urls = null;

		Map<String, List<URL>> extraPackageMap = _serviceTracker.getService();

		if (extraPackageMap != null) {
			urls = extraPackageMap.get(packageName);
		}

		if ((urls == null) || urls.isEmpty()) {
			ClassLoader classLoader = bundleWiring.getClassLoader();

			try {
				Enumeration<URL> enumeration = classLoader.getResources(path);

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					urls = Collections.list(enumeration);
				}
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}

		if ((urls == null) || urls.isEmpty()) {
			_jspResourceCache.put(key, resources);

			return resources;
		}

		String matcherRegex = replace(fileRegex, '*', "[^/]*");

		matcherRegex = replace(matcherRegex, '.', "\\.");

		matcherRegex = path + "/" + matcherRegex;

		Pattern pattern = Pattern.compile(matcherRegex);

		for (URL url : urls) {
			try {
				JarFile jarFile = getJarFile(url);

				Enumeration<? extends ZipEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					ZipEntry zipEntry = enumeration.nextElement();

					String name = zipEntry.getName();

					Matcher matcher = pattern.matcher(name);

					if (matcher.matches()) {
						resources.add(name);
					}
				}
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_jspResourceCache.put(key, resources);

		return resources;
	}

	protected boolean isExportsPackage(
		BundleWiring bundleWiring, String packageName) {

		List<BundleWire> bundleWires = bundleWiring.getProvidedWires(
			"osgi.wiring.package");

		for (BundleWire bundleWire : bundleWires) {
			BundleCapability bundleCapability = bundleWire.getCapability();

			Map<String, Object> attributes = bundleCapability.getAttributes();

			if (packageName.equals(attributes.get("osgi.wiring.package"))) {
				return true;
			}
		}

		return false;
	}

	protected String replace(String s, char oldSub, String newSub) {
		int y = s.indexOf(oldSub);

		if (y < 0) {
			return s;
		}

		StringBuilder sb = new StringBuilder();

		int x = 0;

		while (x <= y) {
			sb.append(s.substring(x, y));
			sb.append(newSub);

			x = y + 1;
			y = s.indexOf(oldSub, x);
		}

		sb.append(s.substring(x));

		return sb.toString();
	}

	private final Bundle _bundle;
	private final Bundle _jspBundle;
	private final Map<String, Collection<String>> _jspResourceCache =
		new ConcurrentHashMap<>();
	private final Logger _logger;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}