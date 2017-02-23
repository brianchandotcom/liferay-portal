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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.IOException;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class BundleJavaFileManager
	extends ForwardingJavaFileManager<JavaFileManager> {

	public static final String OPT_VERBOSE = "-verbose";

	public BundleJavaFileManager(
		ClassLoader classLoader, JavaFileManager javaFileManager,
		List<JavaFileObjectResolver> javaFileObjectResolvers) {

		super(javaFileManager);

		_classLoader = classLoader;
		_javaFileObjectResolvers = javaFileObjectResolvers;
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

			if (_log.isInfoEnabled()) {
				_log.info("Inferring binary name from " + baseJavaFileObject);
			}

			return baseJavaFileObject.getClassName();
		}

		Field nameField = _getZipFileIndexFileObjectNameField();

		if ((nameField != null) &&
			(file.getClass() == nameField.getDeclaringClass())) {

			try {
				String name = (String)nameField.get(file);

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

		if ((location == StandardLocation.CLASS_PATH) && _log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(9);

			sb.append("List for {kinds=");
			sb.append(_kinds);
			sb.append(", location=");
			sb.append(location);
			sb.append(", packageName=");
			sb.append(packageName);
			sb.append(", recurse=");
			sb.append(recurse);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			_log.info(sb.toString());
		}

		String packagePath = packageName.replace(
			CharPool.PERIOD, CharPool.SLASH);

		if (!packageName.startsWith("java.") &&
			(location == StandardLocation.CLASS_PATH)) {

			for (JavaFileObjectResolver javaFileObjectResolver :
					_javaFileObjectResolvers) {

				Collection<JavaFileObject> javaFileObjects =
					javaFileObjectResolver.resolveClasses(recurse, packagePath);

				if (!javaFileObjects.isEmpty()) {
					return javaFileObjects;
				}
			}
		}

		return fileManager.list(location, packagePath, _kinds, recurse);
	}

	private static Field _doGetZipFileIndexFileObjectNameField() {
		if ((JavaDetector.isOpenJDK() || JavaDetector.isOracle()) &&
			GetterUtil.getBoolean(
				SystemProperties.get(
					"portal.servlet.jsp.compiler.sun.javac.hack.enabled"),
				true)) {

			try {
				ClassLoader systemToolClassLoader =
					ToolProvider.getSystemToolClassLoader();

				Class<?> zipFileIndexFileObjectClass =
					systemToolClassLoader.loadClass(
						"com.sun.tools.javac.file.ZipFileIndexArchive$" +
							"ZipFileIndexFileObject");

				Field nameField = zipFileIndexFileObjectClass.getDeclaredField(
					"name");

				nameField.setAccessible(true);

				return nameField;
			}
			catch (ReflectiveOperationException roe) {
			}
		}

		return null;
	}

	private static Field _getZipFileIndexFileObjectNameField() {
		if (_nameFieldReference == null) {
			return null;
		}

		Field nameField = _nameFieldReference.get();

		if (nameField != null) {
			return nameField;
		}

		nameField = _doGetZipFileIndexFileObjectNameField();

		_nameFieldReference = new SoftReference<>(nameField);

		return nameField;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleJavaFileManager.class);

	private static final Set<Kind> _kinds = EnumSet.of(Kind.CLASS);
	private static SoftReference<Field> _nameFieldReference;

	static {
		Field nameField = _doGetZipFileIndexFileObjectNameField();

		if (nameField != null) {
			_nameFieldReference = new SoftReference<>(nameField);
		}
	}

	private final ClassLoader _classLoader;
	private final List<JavaFileObjectResolver> _javaFileObjectResolvers;

}