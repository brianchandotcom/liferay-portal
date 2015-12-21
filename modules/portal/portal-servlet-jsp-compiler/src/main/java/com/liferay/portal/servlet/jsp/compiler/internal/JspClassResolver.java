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

import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
public class JspClassResolver implements ClassResolver {

	public JspClassResolver(Bundle bundle, Bundle jspBundle, Logger logger) {
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
	public URL getClassURL(BundleWiring bundleWiring, String name) {
		Bundle bundle = bundleWiring.getBundle();

		URL url = bundle.getResource(name);

		if ((url == null) && (bundle.getBundleId() == 0)) {
			ClassLoader classLoader = bundleWiring.getClassLoader();

			return classLoader.getResource(name);
		}

		return url;
	}

	@Override
	public Collection<String> resolveClasses(
		BundleWiring bundleWiring, String path, int options) {

		Collection<String> resources = null;

		Bundle bundle = bundleWiring.getBundle();

		if (bundle.equals(_bundle) || bundle.equals(_jspBundle)) {
			resources = bundleWiring.listResources(path, "*.class", options);
		}
		else if (isExportsPackage(bundleWiring, path.replace('/', '.'))) {
			if (bundle.getBundleId() == 0) {
				resources = handleSystemBundle(bundleWiring, path);
			}
			else {
				resources = bundleWiring.listResources(
					path, "*.class", options);
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

	protected Collection<String> handleSystemBundle(
		BundleWiring bundleWiring, String path) {

		Collection<String> resources = _jspResourceCache.get(path);

		if (resources != null) {
			return resources;
		}

		List<URL> urls = null;

		Map<String, List<URL>> extraPackageMap = _serviceTracker.getService();

		if (extraPackageMap != null) {
			urls = extraPackageMap.get(path.replace('/', '.'));
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
			_jspResourceCache.put(path, Collections.<String>emptyList());

			return null;
		}

		for (URL url : urls) {
			try (FileSystem fileSystem = openFileSystem(url)) {
				FileSystemProvider fileSystemProvider = fileSystem.provider();

				try (DirectoryStream<Path> directoryStream =
						fileSystemProvider.newDirectoryStream(
							fileSystem.getPath(path),
							new Filter<Path>() {

								@Override
								public boolean accept(Path entryPath) {
									Path fileNamePath = entryPath.getFileName();

									String fileName = fileNamePath.toString();

									return fileName.endsWith(".class");
								}

							})) {

					for (Path filePath : directoryStream) {
						String filePathString = filePath.toString();

						if (resources == null) {
							resources = new ArrayList<>();
						}

						resources.add(filePathString.substring(1));
					}
				}
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		if (resources == null) {
			_jspResourceCache.put(path, Collections.<String>emptyList());
		}
		else {
			_jspResourceCache.put(path, resources);
		}

		return resources;
	}

	protected boolean isExportsPackage(
		BundleWiring bundleWiring, String packageName) {

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("osgi.wiring.package");

		for (BundleCapability bundleCapability : bundleCapabilities) {
			Map<String, Object> attributes = bundleCapability.getAttributes();

			if (packageName.equals(attributes.get("osgi.wiring.package"))) {
				return true;
			}
		}

		return false;
	}

	protected FileSystem openFileSystem(URL url) throws IOException {
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

		return FileSystems.newFileSystem(Paths.get(decodePath(fileName)), null);
	}

	private final Bundle _bundle;
	private final Bundle _jspBundle;
	private final Map<String, Collection<String>> _jspResourceCache =
		new ConcurrentHashMap<>();
	private final Logger _logger;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}