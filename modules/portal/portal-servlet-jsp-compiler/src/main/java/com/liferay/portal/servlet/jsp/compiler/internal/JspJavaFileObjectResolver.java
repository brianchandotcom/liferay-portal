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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
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

import javax.tools.JavaFileObject;

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
public class JspJavaFileObjectResolver implements JavaFileObjectResolver {

	public JspJavaFileObjectResolver(
		Bundle bundle, Bundle jspBundle, Logger logger) {

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
	public Collection<JavaFileObject> resolveClasses(
		BundleWiring bundleWiring, String path, int options) {

		Bundle bundle = bundleWiring.getBundle();

		if (bundle.equals(_bundle) || bundle.equals(_jspBundle)) {
			return toJavaFileObjects(
				bundle, bundleWiring.listResources(path, "*.class", options));
		}

		if (!isExportsPackage(bundleWiring, path.replace('/', '.'))) {
			return Collections.emptyList();
		}

		if (bundle.getBundleId() == 0) {
			return handleSystemBundle(bundleWiring, path);
		}

		return toJavaFileObjects(
			bundle, bundleWiring.listResources(path, "*.class", options));
	}

	protected String decodePath(String path) {
		path = StringUtil.replace(
			path, StringPool.SLASH, "_LIFERAY_TEMP_SLASH_");

		path = URLCodec.decodeURL(path, StringPool.UTF8);

		path = StringUtil.replace(
			path, "_LIFERAY_TEMP_SLASH_", StringPool.SLASH);

		return path;
	}

	protected String getClassName(String resourceName) {
		if (resourceName.endsWith(".class")) {
			resourceName = resourceName.substring(0, resourceName.length() - 6);
		}

		return resourceName.replace(CharPool.SLASH, CharPool.PERIOD);
	}

	protected JavaFileObject getJavaFileObject(
		URL resourceURL, String resourceName) {

		String protocol = resourceURL.getProtocol();

		String className = getClassName(resourceName);

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			return new BundleJavaFileObject(className, resourceURL);
		}
		else if (protocol.equals("jar")) {
			try {
				return new JarJavaFileObject(
					className, resourceURL, resourceName);
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}
		else if (protocol.equals("vfs")) {
			try {
				return new VfsJavaFileObject(
					className, resourceURL, resourceName);
			}
			catch (MalformedURLException murie) {
				_logger.log(Logger.LOG_ERROR, murie.getMessage(), murie);
			}
		}

		return null;
	}

	protected Collection<JavaFileObject> handleSystemBundle(
		BundleWiring bundleWiring, String path) {

		Collection<JavaFileObject> javaFileObjects = _javaFileObjectCache.get(
			path);

		if (javaFileObjects != null) {
			return javaFileObjects;
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
			_javaFileObjectCache.put(
				path, Collections.<JavaFileObject>emptyList());

			return Collections.emptyList();
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
						if (javaFileObjects == null) {
							javaFileObjects = new ArrayList<>();
						}

						URI uri = filePath.toUri();

						String filePathString = filePath.toString();

						javaFileObjects.add(
							getJavaFileObject(
								uri.toURL(), filePathString.substring(1)));
					}
				}
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		if (javaFileObjects == null) {
			javaFileObjects = Collections.<JavaFileObject>emptyList();
		}

		_javaFileObjectCache.put(path, javaFileObjects);

		return javaFileObjects;
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

	protected Collection<JavaFileObject> toJavaFileObjects(
		Bundle bundle, Collection<String> resources) {

		if ((resources == null) || resources.isEmpty()) {
			return Collections.emptyList();
		}

		List<JavaFileObject> javaFileObjects = new ArrayList<>(
			resources.size());

		for (String resource : resources) {
			javaFileObjects.add(
				getJavaFileObject(bundle.getResource(resource), resource));
		}

		return javaFileObjects;
	}

	private final Bundle _bundle;
	private final Map<String, Collection<JavaFileObject>> _javaFileObjectCache =
		new ConcurrentHashMap<>();
	private final Bundle _jspBundle;
	private final Logger _logger;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}