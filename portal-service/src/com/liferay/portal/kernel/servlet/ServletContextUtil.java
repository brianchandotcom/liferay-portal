/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ServletContextUtil {

	public static final String PATH_WEB_XML = "/WEB-INF/web.xml";

	public static final String URI_ATTRIBUTE =
		ServletContextUtil.class.getName().concat(".rootURI");

	public static Set<String> getClassNames(ServletContext servletContext)
		throws IOException {

		Set<String> classNames = new HashSet<String>();

		_getClassNames(servletContext, "/WEB-INF/classes", classNames);
		_getClassNames(servletContext, "/WEB-INF/lib", classNames);

		return classNames;
	}

	public static long getLastModified(ServletContext servletContext) {
		return getLastModified(servletContext, StringPool.SLASH);
	}

	public static long getLastModified(
		ServletContext servletContext, String resourcePath) {

		return getLastModified(servletContext, resourcePath, false);
	}

	public static long getLastModified(
		ServletContext servletContext, String resourcePath, boolean cache) {

		if (cache) {
			Long lastModified = (Long)servletContext.getAttribute(
				ServletContextUtil.class.getName() + StringPool.PERIOD +
					resourcePath);

			if (lastModified != null) {
				return lastModified.longValue();
			}
		}

		long lastModified = 0;

		Set<String> resourcePaths = null;

		if (resourcePath.endsWith(StringPool.SLASH)) {
			resourcePaths = servletContext.getResourcePaths(resourcePath);
		}
		else {
			resourcePaths = new HashSet<String>();

			resourcePaths.add(resourcePath);
		}

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			if (cache) {
				servletContext.setAttribute(
					ServletContextUtil.class.getName() + StringPool.PERIOD +
						resourcePath,
					new Long(lastModified));
			}

			return lastModified;
		}

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				long curLastModified = getLastModified(
					servletContext, curResourcePath);

				if (curLastModified > lastModified) {
					lastModified = curLastModified;
				}
			}
			else {
				try {
					URL resourceURL = servletContext.getResource(
						curResourcePath);

					if (resourceURL == null) {
						_log.error(
							"Resource url for " + curResourcePath + " is null");

						continue;
					}

					URLConnection urlConnection = resourceURL.openConnection();

					if (urlConnection.getLastModified() > lastModified) {
						lastModified = urlConnection.getLastModified();
					}
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		if (cache) {
			servletContext.setAttribute(
				ServletContextUtil.class.getName() + StringPool.PERIOD +
					resourcePath,
				new Long(lastModified));
		}

		return lastModified;
	}

	public static String getRootPath(ServletContext servletContext)
		throws MalformedURLException {

		URI rootURI = getRootURI(servletContext);

		return rootURI.toString();
	}

	public static URI getRootURI(ServletContext servletContext)
		throws MalformedURLException {

		URI rootURI = (URI)servletContext.getAttribute(URI_ATTRIBUTE);

		if (rootURI != null) {
			return rootURI;
		}

		try {
			URL rootURL = servletContext.getResource(PATH_WEB_XML);

			String path = rootURL.getPath();

			int index = path.indexOf(PATH_WEB_XML);

			if (index < 0) {
				throw new MalformedURLException("Invalid URL: " + rootURL);
			}

			if (index == 0) {
				path = StringPool.SLASH;
			}
			else {
				path = path.substring(0, index);
			}

			rootURI = new URI(rootURL.getProtocol(), path, null);

			servletContext.setAttribute(URI_ATTRIBUTE, rootURI);
		}
		catch (URISyntaxException urise) {
			throw new MalformedURLException(urise.getMessage());
		}

		return rootURI;
	}

	private static String _getClassName(String path) {
		return _getClassName(null, path);
	}

	private static String _getClassName(String rootResourcePath, String path) {
		String className = path.substring(
			0, path.length() - _EXT_CLASS.length());

		if (rootResourcePath != null) {
			className = className.substring(rootResourcePath.length() + 1);
		}

		className = StringUtil.replace(
			className, CharPool.SLASH, CharPool.PERIOD);

		return className;
	}

	private static void _getClassNames(
			ServletContext servletContext, String rootResourcePath,
			Set<String> classNames)
		throws IOException {

		_getClassNames(
			servletContext, rootResourcePath,
			servletContext.getResourcePaths(rootResourcePath), classNames);
	}

	private static void _getClassNames(
			ServletContext servletContext, String rootResourcePath,
			Set<String> resourcePaths, Set<String> classNames)
		throws IOException {

		if (resourcePaths == null) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(_EXT_CLASS)) {
				String className = _getClassName(
					rootResourcePath, resourcePath);

				classNames.add(className);
			}
			else if (resourcePath.endsWith(_EXT_JAR)) {
				JarInputStream jarFile = new JarInputStream(
					servletContext.getResourceAsStream(resourcePath));

				while (true) {
					JarEntry jarEntry = jarFile.getNextJarEntry();

					if (jarEntry == null) {
						break;
					}

					String jarEntryName = jarEntry.getName();

					if (jarEntryName.endsWith(_EXT_CLASS)) {
						String className = _getClassName(jarEntryName);

						classNames.add(className);
					}
				}

				jarFile.close();

			}
			else if (resourcePath.endsWith(StringPool.SLASH)) {
				_getClassNames(
					servletContext, rootResourcePath,
					servletContext.getResourcePaths(resourcePath), classNames);
			}
		}
	}

	private static final String _EXT_CLASS = ".class";

	private static final String _EXT_JAR = ".jar";

	private static Log _log = LogFactoryUtil.getLog(ServletContextUtil.class);

}