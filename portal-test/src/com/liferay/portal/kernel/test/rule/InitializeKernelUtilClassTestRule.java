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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.reflect.Constructor;

import java.util.Properties;

import org.junit.runner.Description;

/**
 * @author Matthew Tambara
 */
public class InitializeKernelUtilClassTestRule extends ClassTestRule<Void> {

	public static final InitializeKernelUtilClassTestRule INSTANCE =
		new InitializeKernelUtilClassTestRule();

	public void addProperties(Properties properties) {
	}

	@Override
	protected void afterClass(Description description, Void v) {
	}

	@Override
	protected Void beforeClass(Description description)
		throws ReflectiveOperationException {

		_setUpPropsUtil();
		_setUpFileUtil();

		return null;
	}

	private void _setUpFileUtil() throws ClassNotFoundException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(
			ReflectionTestUtil.getFieldValue(
				classLoader.loadClass("com.liferay.portal.util.FileImpl"),
				"_fileImpl"));
	}

	private void _setUpPropsUtil() throws ReflectiveOperationException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.PropsImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		PropsUtil.setProps((Props)constructor.newInstance());

		Properties properties = new Properties();

		addProperties(properties);

		if (!properties.isEmpty()) {
			ReflectionTestUtil.invoke(
				classLoader.loadClass("com.liferay.portal.util.PropsUtil"),
				"addProperties", new Class<?>[] {Properties.class}, properties);
		}
	}

}