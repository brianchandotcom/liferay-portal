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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.util.ClassPathUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.DirectoryStream;
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
import java.util.Set;

import javax.tools.JavaFileObject;

import org.osgi.framework.Bundle;
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
		ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
			serviceTracker) {

		_bundleWiring = bundleWiring;
		_jspBundleWiring = jspBundleWiring;
		_bundleWiringPackageNames = bundleWiringPackageNames;
		_serviceTracker = serviceTracker;
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

		String packageName = StringUtil.replace(
			packagePath, CharPool.SLASH, CharPool.PERIOD);

		for (Map.Entry<BundleWiring, Set<String>> entry :
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

		return StringUtil.replace(
			classResourceName, CharPool.SLASH, CharPool.PERIOD);
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
					className, ClassPathUtil.getFile(resourceURL),
					resourceName);
			}
			catch (IOException ioException) {
				_log.error(ioException.getMessage(), ioException);
			}
		}
		else if (protocol.equals("vfs")) {
			try {
				return new VfsJavaFileObject(
					className, resourceURL, resourceName);
			}
			catch (MalformedURLException malformedURLException) {
				_log.error(
					malformedURLException.getMessage(), malformedURLException);
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
			urls = extraPackageMap.get(StringUtil.replace(path, '/', '.'));
		}

		if ((urls == null) || urls.isEmpty()) {
			ClassLoader classLoader = bundleWiring.getClassLoader();

			try {
				Enumeration<URL> enumeration = classLoader.getResources(path);

				if ((enumeration == null) ||
					((enumeration != null) && !enumeration.hasMoreElements())) {

					// This is a fallback so that WebSphere can find resources
					// during the JSP compilation process

					enumeration = classLoader.getResources(
						path + StringPool.SLASH);
				}

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					urls = Collections.list(enumeration);
				}
			}
			catch (IOException ioException) {
				_log.error(ioException.getMessage(), ioException);
			}
		}

		if ((urls == null) || urls.isEmpty()) {
			_javaFileObjects.put(path, Collections.<JavaFileObject>emptyList());

			return Collections.emptyList();
		}

		for (URL url : urls) {
			try {
				File file = ClassPathUtil.getFile(url);

				if (file == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Ignoring " + url +
								" while handling system bundle");
					}

					continue;
				}

				try (FileSystem fileSystem = FileSystems.newFileSystem(
						file.toPath(), null)) {

					FileSystemProvider fileSystemProvider =
						fileSystem.provider();

					try (DirectoryStream<Path> directoryStream =
							fileSystemProvider.newDirectoryStream(
								fileSystem.getPath(path),
								new DirectoryStream.Filter<Path>() {

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

							if (entryPathString.charAt(0) == CharPool.SLASH) {
								entryPathString = entryPathString.substring(1);
							}

							javaFileObjects.add(
								new JarJavaFileObject(
									getClassName(entryPathString), file,
									entryPathString));
						}
					}
				}
			}
			catch (IOException ioException) {
				_log.error(ioException.getMessage(), ioException);
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
			JavaFileObject javaFileObject = getJavaFileObject(
				bundle.getResource(resource), resource);

			if (javaFileObject != null) {
				javaFileObjects.add(javaFileObject);
			}
		}

		return javaFileObjects;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JspJavaFileObjectResolver.class);

	private final BundleWiring _bundleWiring;
	private final Map<BundleWiring, Set<String>> _bundleWiringPackageNames;
	private final Map<String, Collection<JavaFileObject>> _javaFileObjects =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.SOFT_REFERENCE_FACTORY);
	private final BundleWiring _jspBundleWiring;
	private final ServiceTracker<Map<String, List<URL>>, Map<String, List<URL>>>
		_serviceTracker;

}