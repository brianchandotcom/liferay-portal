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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.URLUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Constructor;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class ServiceLoader {

	public static <S> List<S> load(Class<S> clazz) throws Exception {
		return load(clazz, _serviceLoaderCondition);
	}

	public static <S> List<S> load(
			Class<S> clazz, ServiceLoaderCondition serviceLoaderCondition)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return load(classLoader, clazz, serviceLoaderCondition);
	}

	public static <S> List<S> load(ClassLoader classLoader, Class<S> clazz)
		throws Exception {

		return load(classLoader, clazz, _serviceLoaderCondition);
	}

	public static <S> List<S> load(
			ClassLoader classLoader, Class<S> clazz,
			ServiceLoaderCondition serviceLoaderCondition)
		throws Exception {

		Enumeration<URL> enu = classLoader.getResources(
			"META-INF/services/" + clazz.getName());

		List<S> services = new ArrayList<S>();

		while (enu.hasMoreElements()) {
			URL url = enu.nextElement();

			if (!serviceLoaderCondition.isLoad(url)) {
				continue;
			}

			try {
				_load(services, classLoader, clazz, url);
			}
			catch (Exception e) {
				_log.error(
					"Unable to load " + clazz + " with " + classLoader, e);
			}
		}

		return services;
	}

	private static <S> void _load(
			List<S> services, ClassLoader classLoader, Class<S> clazz, URL url)
		throws Exception {

		if (ServerDetector.isJBoss5()) {
			url = URLUtil.normalizeURL(url);
		}

		try (InputStream inputStream = url.openStream()) {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StringPool.UTF8));

			while (true) {
				String line = bufferedReader.readLine();

				if (line == null) {
					break;
				}

				int comment = line.indexOf(CharPool.POUND);

				if (comment >= 0) {
					line = line.substring(0, comment);
				}

				String name = line.trim();

				if (name.length() == 0) {
					continue;
				}

				Class<?> serviceClass = Class.forName(name, true, classLoader);

				Class<? extends S> serviceImplClass = serviceClass.asSubclass(
					clazz);

				Constructor<? extends S> constructor =
					serviceImplClass.getConstructor();

				S service = constructor.newInstance();

				services.add(service);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ServiceLoader.class);

	private static final ServiceLoaderCondition _serviceLoaderCondition =
		new DefaultServiceLoaderCondition();

}