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

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Properties;

import org.junit.runner.Description;

/**
 * @author Matthew Tambara
 */
public class InitializeKernelUtilClassTestRule<Void>
	extends ClassTestRule<Void> {

	public static final InitializeKernelUtilClassTestRule INSTANCE =
		new InitializeKernelUtilClassTestRule();

	public void addProperties(Properties properties) {
	}

	@Override
	protected void afterClass(Description description, Void v) {
	}

	@Override
	protected Void beforeClass(Description description) throws Throwable {
		_setUpPropsUtil();
		_setUpFileUtil();

		return null;
	}

	private void _setUpFileUtil() throws Exception {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.FileImpl");

		FileUtil fileUtil = new FileUtil();

		Field field = ReflectionUtil.getDeclaredField(clazz, "_fileImpl");

		fileUtil.setFile((File)field.get(null));
	}

	private void _setUpPropsUtil() throws Exception {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.PropsImpl");

		PropsUtil.setProps((Props)clazz.newInstance());

		Properties properties = new Properties();

		addProperties(properties);

		if (!properties.isEmpty()) {
			Method method = ReflectionUtil.getDeclaredMethod(
				classLoader.loadClass("com.liferay.portal.util.PropsUtil"),
				"addProperties", Properties.class);

			method.invoke(null, properties);
		}
	}

}