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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ServletContextUtil {

	public static final String LOG_INFO_LAST_MODIFIED =
		ServletContextUtil.LOG_INFO_PREFIX + "retrieval of the most recent " +
			"last modified date of a WAR for best performance";

	public static final String LOG_INFO_PREFIX =
		"Please configure Tomcat to unpack WARs to enable ";

	public static final String LOG_INFO_SPRITES =
		ServletContextUtil.LOG_INFO_PREFIX + "enable sprites for best " +
			"performance";

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

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if (resourcePaths != null) {
			for (String curResourcePath : resourcePaths) {
				if (curResourcePath.endsWith(StringPool.SLASH)) {
					long curLastModified = getLastModified(
						servletContext, curResourcePath);

					if (curLastModified > lastModified) {
						lastModified = curLastModified;
					}
				}
				else {
					String realPath = getRealPath(
						servletContext, curResourcePath);

					if (realPath == null) {
						if (ServerDetector.isTomcat()) {
							if (_log.isInfoEnabled()) {
								_log.info(LOG_INFO_LAST_MODIFIED);
							}
						}
						else {
							_log.error(
								"Real path for " + curResourcePath +
									" is null");
						}

						continue;
					}

					File file = new File(realPath);

					if (file.lastModified() > lastModified) {
						lastModified = file.lastModified();
					}
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

	public static String getRealPath(
		ServletContext servletContext, String path) {

		String realPath = servletContext.getRealPath(path);

		if ((realPath == null) && ServerDetector.isWebLogic()) {
			String rootDir = getRootDir(servletContext);

			if (path.startsWith(StringPool.SLASH)) {
				realPath = rootDir + path.substring(1);
			}
			else {
				realPath = rootDir + path;
			}

			if (!FileUtil.exists(realPath)) {
				realPath = null;
			}
		}

		return realPath;
	}

	protected static String getRootDir(ServletContext servletContext) {
		String key = ServletContextUtil.class.getName() + ".rootDir";

		String rootDir = (String)servletContext.getAttribute(key);

		if (rootDir == null) {
			ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

			if (classLoader == null) {
				classLoader = PortalClassLoaderUtil.getClassLoader();
			}

			rootDir = WebDirDetector.getRootDir(classLoader);

			servletContext.setAttribute(key, rootDir);
		}

		return rootDir;
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