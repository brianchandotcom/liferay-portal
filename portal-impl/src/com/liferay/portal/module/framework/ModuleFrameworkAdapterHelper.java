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

package com.liferay.portal.module.framework;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class ModuleFrameworkAdapterHelper {

	public static ClassLoader getClassLoader() {
		if (_classLoader != null) {
			return _classLoader;
		}

		try {
			if (FileUtil.getFile() == null) {
				FileUtil fileUtil = new FileUtil();

				fileUtil.setFile(new FileImpl());
			}

			File coreDir = new File(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR, "core");

			File[] files = coreDir.listFiles();

			if (files == null) {
				throw new IllegalStateException(
					"Missing " + coreDir.getCanonicalPath());
			}

			URL[] urls = new URL[files.length];
			String[] packageNames = new String[files.length + 4];

			for (int i = 0; i < urls.length; i++) {
				File file = files[i];

				URI uri = file.toURI();

				urls[i] = uri.toURL();

				String name = file.getName();

				if (name.endsWith(".jar")) {
					name = name.substring(0, name.length() - 3);
				}

				if (name.endsWith(".api.")) {
					name = name.substring(0, name.length() - 4);
				}

				if (name.endsWith(".impl.")) {
					name = name.substring(0, name.length() - 5);

					name = name.concat("internal.");
				}

				packageNames[i] = name;
			}

			packageNames[files.length] = "org.apache.felix.resolver.";
			packageNames[files.length + 1] = "org.eclipse.core.";
			packageNames[files.length + 2] = "org.eclipse.equinox.";
			packageNames[files.length + 3] = "org.osgi.";

			Arrays.sort(packageNames);

			_classLoader = new ModuleFrameworkClassLoader(
				urls, PortalClassLoaderUtil.getClassLoader(), packageNames);

			return _classLoader;
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to configure the class loader for the module framework",
				ioException);

			return ReflectionUtil.throwException(ioException);
		}
	}

	public ModuleFrameworkAdapterHelper(String className) {
		try {
			_adaptedObject = InstanceFactory.newInstance(
				getClassLoader(), className);
		}
		catch (Exception exception) {
			_log.error("Unable to load the module framework", exception);

			throw new RuntimeException(exception);
		}
	}

	public Object exec(
		String methodName, Class<?>[] parameterTypes, Object... parameters) {

		try {
			Method method = searchMethod(methodName, parameterTypes);

			return method.invoke(_adaptedObject, parameters);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RuntimeException(exception);
		}
	}

	protected Method searchMethod(String methodName, Class<?>[] parameterTypes)
		throws Exception {

		MethodKey methodKey = new MethodKey(
			_adaptedObject.getClass(), methodName, parameterTypes);

		if (_methods.containsKey(methodKey)) {
			return _methods.get(methodKey);
		}

		Method method = ReflectionUtil.getDeclaredMethod(
			_adaptedObject.getClass(), methodName, parameterTypes);

		_methods.put(methodKey, method);

		return method;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkAdapterHelper.class);

	private static ClassLoader _classLoader;
	private static final Map<MethodKey, Method> _methods = new HashMap<>();

	private final Object _adaptedObject;

}