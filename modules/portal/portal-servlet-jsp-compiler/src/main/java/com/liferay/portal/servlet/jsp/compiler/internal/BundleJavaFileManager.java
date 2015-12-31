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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.IOException;

import java.lang.reflect.Field;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.felix.utils.log.Logger;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class BundleJavaFileManager
	extends ForwardingJavaFileManager<JavaFileManager> {

	public static final String OPT_VERBOSE = "-verbose";

	public BundleJavaFileManager(
		ClassLoader classLoader, Set<String> systemPackageNames,
		JavaFileManager javaFileManager, Logger logger, boolean verbose,
		JavaFileObjectResolver javaFileObjectResolver) {

		super(javaFileManager);

		_classLoader = classLoader;
		_systemPackageNames = systemPackageNames;
		_logger = logger;
		_verbose = verbose;
		_javaFileObjectResolver = javaFileObjectResolver;
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		if (location != StandardLocation.CLASS_PATH) {
			return fileManager.getClassLoader(location);
		}

		return _classLoader;
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if ((location == StandardLocation.CLASS_PATH) &&
			(file instanceof BaseJavaFileObject)) {

			BaseJavaFileObject baseJavaFileObject = (BaseJavaFileObject)file;

			if (_verbose) {
				_logger.log(
					Logger.LOG_INFO,
					"Inferring binary name from " + baseJavaFileObject);
			}

			return baseJavaFileObject.getClassName();
		}

		if (file.getClass() == _zipFileIndexFileObjectClass) {
			try {
				String name = (String)_nameField.get(file);

				return name.substring(0, name.lastIndexOf(CharPool.PERIOD));
			}
			catch (ReflectiveOperationException roe) {
			}
		}

		return fileManager.inferBinaryName(location, file);
	}

	@Override
	public Iterable<JavaFileObject> list(
			Location location, String packageName, Set<Kind> kinds,
			boolean recurse)
		throws IOException {

		if (!kinds.contains(Kind.CLASS)) {
			return Collections.emptyList();
		}

		if ((location == StandardLocation.CLASS_PATH) && _verbose) {
			StringBundler sb = new StringBundler(9);

			sb.append("List for {kinds=");
			sb.append(kinds);
			sb.append(", location=");
			sb.append(location);
			sb.append(", packageName=");
			sb.append(packageName);
			sb.append(", recurse=");
			sb.append(recurse);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			_logger.log(Logger.LOG_INFO, sb.toString());
		}

		String packagePath = packageName.replace(
			CharPool.PERIOD, CharPool.SLASH);

		if (!packageName.startsWith("java.") &&
			(location == StandardLocation.CLASS_PATH)) {

			Collection<JavaFileObject> javaFileObjects =
				_javaFileObjectResolver.resolveClasses(recurse, packagePath);

			if (!javaFileObjects.isEmpty() ||
				!_systemPackageNames.contains(packageName)) {

				return javaFileObjects;
			}
		}

		return fileManager.list(location, packagePath, kinds, recurse);
	}

	private static final Field _nameField;
	private static final Class<?> _zipFileIndexFileObjectClass;

	static {
		Class<?> zipFileIndexFileObjectClass = null;
		Field nameField = null;

		if (GetterUtil.getBoolean(
				SystemProperties.get("sun.javac.hack.enabled"), true)) {

			try {
				ClassLoader systemToolClassLoader =
					ToolProvider.getSystemToolClassLoader();

				zipFileIndexFileObjectClass = systemToolClassLoader.loadClass(
					"com.sun.tools.javac.file.ZipFileIndexArchive$" +
						"ZipFileIndexFileObject");
				nameField = zipFileIndexFileObjectClass.getDeclaredField(
					"name");

				nameField.setAccessible(true);
			}
			catch (ReflectiveOperationException roe) {
				zipFileIndexFileObjectClass = null;
				nameField = null;
			}
		}

		_zipFileIndexFileObjectClass = zipFileIndexFileObjectClass;
		_nameField = nameField;
	}

	private final ClassLoader _classLoader;
	private final JavaFileObjectResolver _javaFileObjectResolver;
	private final Logger _logger;
	private final Set<String> _systemPackageNames;
	private final boolean _verbose;

}