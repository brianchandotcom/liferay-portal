/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal.file.backed.osgi;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.site.initializer.extender.internal.file.backed.util.PathUtil;

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
public class FileBackedBundleDelegate {

	public FileBackedBundleDelegate(
			BundleContext bundleContext, File file, JSONFactory jsonFactory,
			String symbolicName)
		throws Exception {

		_bundleContext = bundleContext;
		_file = file;
		_jsonFactory = jsonFactory;
		_symbolicName = symbolicName;

		URI uri = file.toURI();

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

		File jsonFile = new File(file, "site-initializer.json");

		if (jsonFile.exists()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				FileUtil.read(jsonFile));

			_siteInitializerName = jsonObject.getString(
				"name", _file.getName());
		}
		else {
			_siteInitializerName = _file.getName();
		}
	}

	public <T extends Object> T adapt(Class<T> clazz) {
		if (clazz != BundleWiring.class) {
			throw new IllegalArgumentException("Unsupported clazz " + clazz);
		}

		return ProxyUtil.newDelegateProxyInstance(
			clazz.getClassLoader(), clazz,
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

		Path rootPathObject = _file.toPath();

		Path searchPathObject = rootPathObject.resolve(
			PathUtil.removePrefix(path));

		if (Files.notExists(searchPathObject)) {
			return Collections.emptyEnumeration();
		}

		List<URL> urls = new ArrayList<>();

		if (recurse) {
			Files.walkFileTree(
				searchPathObject,
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
			_collect(urls, rootPathObject, filePattern);
		}

		return Collections.enumeration(urls);
	}

	public BundleContext getBundleContext() {
		return _bundleContext;
	}

	public URL getEntry(String path) {
		File file = new File(path);

		if (file.exists()) {
			URI uri = file.toURI();

			try {
				return uri.toURL();
			}
			catch (MalformedURLException malformedURLException) {
				if (_log.isDebugEnabled()) {
					_log.debug(malformedURLException);
				}
			}
		}

		return null;
	}

	public Dictionary<String, String> getHeaders(String locale) {
		return MapUtil.singletonDictionary(
			"Liferay-Site-Initializer-Name", _siteInitializerName);
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

	private static final Log _log = LogFactoryUtil.getLog(
		FileBackedBundleDelegate.class);

	private final BundleContext _bundleContext;
	private final ClassLoader _classLoader;
	private final File _file;
	private final JSONFactory _jsonFactory;
	private final String _siteInitializerName;
	private final String _symbolicName;

}