/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLConnection;

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
			File coreDir = new File(
				PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR, "osgi");

			_initDir(
				"com/liferay/portal/deploy/dependencies/osgi/core",
				coreDir.getAbsolutePath());
			_initDir(
				"com/liferay/portal/deploy/dependencies/osgi/portal",
				PropsValues.MODULE_FRAMEWORK_PORTAL_DIR);

			File[] files = coreDir.listFiles();

			URL[] urls = new URL[files.length];

			for (int i = 0; i < urls.length; i++) {
				urls[i] = new URL("file", null, files[i].getAbsolutePath());
			}

			_classLoader = new ModuleFrameworkClassLoader(
				urls, ClassLoaderUtil.getPortalClassLoader());

			return _classLoader;
		}
		catch (Exception e) {
			_log.error(
				"Unable to configure the class loader for the module " +
					"framework");

			throw new RuntimeException(e);
		}
	}

	public ModuleFrameworkAdapterHelper(String className) {
		try {
			_adaptedObject = InstanceFactory.newInstance(
				getClassLoader(), className);
		}
		catch (Exception e) {
			_log.error("Unable to load the module framework");

			throw new RuntimeException(e);
		}
	}

	public Object exec(
		String methodName, Class<?>[] parameterTypes, Object...parameters) {

		try {
			Method method = searchMethod(methodName, parameterTypes);

			return method.invoke(_adaptedObject, parameters);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	public Object execute(String methodName, Object...parameters) {
		Class<?>[] parameterTypes = ReflectionUtil.getParameterTypes(
			parameters);

		return exec(methodName, parameterTypes, parameters);
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

	private static void _initDir(String sourcePath, String destinationPath)
		throws Exception {

		if (FileUtil.getFile() == null) {
			FileUtil fileUtil = new FileUtil();

			fileUtil.setFile(DoPrivilegedUtil.wrap(new FileImpl()));
		}

		if (!FileUtil.exists(destinationPath)) {
			FileUtil.mkdirs(destinationPath);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		URL url = classLoader.getResource(sourcePath + "/jars.txt");

		URLConnection urlConnection = url.openConnection();

		String[] jarFileNames = StringUtil.split(
			StringUtil.read(urlConnection.getInputStream()));

		for (String jarFileName : jarFileNames) {
			File distinationFile = new File(destinationPath, jarFileName);

			if (distinationFile.lastModified() <
					urlConnection.getLastModified()) {

				byte[] bytes = FileUtil.getBytes(
					classLoader.getResourceAsStream(
						sourcePath + "/" + jarFileName));

				FileUtil.write(distinationFile, bytes);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkAdapterHelper.class);

	private static ClassLoader _classLoader;
	private static Map<MethodKey, Method> _methods =
		new HashMap<MethodKey, Method>();

	private Object _adaptedObject;

}