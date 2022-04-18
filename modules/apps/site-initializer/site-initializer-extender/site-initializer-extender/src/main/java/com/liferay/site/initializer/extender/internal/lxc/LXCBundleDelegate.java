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

package com.liferay.site.initializer.extender.internal.lxc;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Shuyang Zhou
 */
public class LXCBundleDelegate {

	public LXCBundleDelegate(
			BundleContext bundleContext, String symbolicName,
			File siteInitializerFolder)
		throws MalformedURLException {

		_bundleContext = bundleContext;
		_symbolicName = symbolicName;
		_siteInitializerFolder = siteInitializerFolder;

		URI uri = siteInitializerFolder.toURI();

		_classLoader = new URLClassLoader(new URL[] {uri.toURL()}, null) {

			@Override
			public InputStream getResourceAsStream(String name) {
				return super.getResourceAsStream(PathUtil.removePrefix(name));
			}

			@Override
			public Enumeration<URL> getResources(String name)
				throws IOException {

				return super.getResources(PathUtil.removePrefix(name));
			}

		};
	}

	public <T extends Object> T adapt(Class<T> type) {
		if (type != BundleWiring.class) {
			throw new IllegalArgumentException("Unsupported type " + type);
		}

		return ProxyUtil.newDelegateProxyInstance(
			type.getClassLoader(), type,
			new Object() {

				public ClassLoader getClassLoader() {
					return _classLoader;
				}

			},
			null);
	}

	public Enumeration<URL> findEntries(
			String path, String filePattern, boolean recurse)
		throws IOException {

		Path rootPath = _siteInitializerFolder.toPath();

		Path searchPath = rootPath.resolve(PathUtil.removePrefix(path));

		if (Files.notExists(searchPath)) {
			return Collections.emptyEnumeration();
		}

		List<URL> urls = new ArrayList<>();

		if (recurse) {
			Files.walkFileTree(
				searchPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path dirPath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						_collect(urls, dirPath, filePattern);

						return FileVisitResult.CONTINUE;
					}

				});
		}
		else {
			_collect(urls, rootPath, filePattern);
		}

		return Collections.enumeration(urls);
	}

	public BundleContext getBundleContext() {
		return _bundleContext;
	}

	public URL getEntry(String path) {
		return null;
	}

	public Dictionary<String, String> getHeaders(String locale) {
		return MapUtil.singletonDictionary(
			"Liferay-Site-Initializer-Name", _siteInitializerFolder.getName());
	}

	public String getSymbolicName() {
		return _symbolicName;
	}

	private void _collect(List<URL> urls, Path dirPath, String glob)
		throws IOException {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			for (Path path : directoryStream) {
				URI uri = path.toUri();

				urls.add(uri.toURL());
			}
		}
	}

	private final BundleContext _bundleContext;
	private final ClassLoader _classLoader;
	private final File _siteInitializerFolder;
	private final String _symbolicName;

}