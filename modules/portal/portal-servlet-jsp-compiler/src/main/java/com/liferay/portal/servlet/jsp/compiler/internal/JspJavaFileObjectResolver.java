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
import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		BundleWiring bundleWiring, BundleWiring jspBundleWiring,
		Map<BundleWiring, Set<String>> bundleWiringPackageNames,
		Logger logger) {

		_bundleWiring = bundleWiring;
		_jspBundleWiring = jspBundleWiring;
		_bundleWiringPackageNames = bundleWiringPackageNames;
		_logger = logger;

		Bundle bundle = _bundleWiring.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

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
		boolean recurse, String packagePath) {

		List<JavaFileObject> javaFileObjects = new ArrayList<>();

		int options = 0;

		if (recurse) {
			options = BundleWiring.LISTRESOURCES_RECURSE;
		}

		javaFileObjects.addAll(
			toJavaFileObjects(
				_jspBundleWiring.getBundle(),
				_jspBundleWiring.listResources(
					packagePath, "*.class", options)));

		String packageName = packagePath.replace(
			CharPool.SLASH, CharPool.PERIOD);

		for (Entry<BundleWiring, Set<String>> entry :
				_bundleWiringPackageNames.entrySet()) {

			Set<String> packageNames = entry.getValue();

			if (packageNames.contains(packageName)) {
				javaFileObjects.addAll(
					doResolveClasses(entry.getKey(), packagePath, options));
			}
		}

		if (javaFileObjects.isEmpty()) {
			return toJavaFileObjects(
				_bundleWiring.getBundle(),
				_bundleWiring.listResources(packagePath, "*.class", options));
		}

		return javaFileObjects;
	}

	protected Collection<JavaFileObject> doResolveClasses(
		BundleWiring bundleWiring, String path, int options) {

		Bundle bundle = bundleWiring.getBundle();

		if (bundle.getBundleId() == 0) {
			return handleSystemBundle(bundleWiring, path);
		}

		return toJavaFileObjects(
			bundle, bundleWiring.listResources(path, "*.class", options));
	}

	protected String getClassName(String classResourceName) {
		classResourceName = classResourceName.substring(
			0, classResourceName.length() - 6);

		return classResourceName.replace(CharPool.SLASH, CharPool.PERIOD);
	}

	protected File getFile(URL url) throws IOException {
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

		return new File(URLCodec.decodeURL(fileName, StringPool.UTF8));
	}

	protected JavaFileObject getJavaFileObject(
		URL resourceURL, String resourceName) {

		String protocol = resourceURL.getProtocol();

		String className = getClassName(resourceName);

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			return new BundleJavaFileObject(className, resourceURL);
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

		Collection<JavaFileObject> javaFileObjects = _javaFileObjects.get(path);

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
			_javaFileObjects.put(path, Collections.<JavaFileObject>emptyList());

			return Collections.emptyList();
		}

		for (URL url : urls) {
			try {
				File file = getFile(url);

				try (FileSystem fileSystem = FileSystems.newFileSystem(
						file.toPath(), null)) {

					FileSystemProvider fileSystemProvider =
						fileSystem.provider();

					try (DirectoryStream<Path> directoryStream =
							fileSystemProvider.newDirectoryStream(
								fileSystem.getPath(path),
								new Filter<Path>() {

									@Override
									public boolean accept(Path entryPath) {
										String entryPathString =
											entryPath.toString();

										return entryPathString.endsWith(
											".class");
									}

								})) {

						for (Path entryPath : directoryStream) {
							if (javaFileObjects == null) {
								javaFileObjects = new ArrayList<>();
							}

							String entryPathString = entryPath.toString();

							entryPathString = entryPathString.substring(1);

							javaFileObjects.add(
								new JarJavaFileObject(
									getClassName(entryPathString), file,
									entryPathString));
						}
					}
				}
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}

		if (javaFileObjects == null) {
			javaFileObjects = Collections.<JavaFileObject>emptyList();
		}

		_javaFileObjects.put(path, javaFileObjects);

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

	private final BundleWiring _bundleWiring;
	private final Map<BundleWiring, Set<String>> _bundleWiringPackageNames;
	private final Map<String, Collection<JavaFileObject>> _javaFileObjects =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.SOFT_REFERENCE_FACTORY);
	private final BundleWiring _jspBundleWiring;
	private final Logger _logger;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}