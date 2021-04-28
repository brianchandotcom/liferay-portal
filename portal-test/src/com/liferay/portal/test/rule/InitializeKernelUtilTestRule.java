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

package com.liferay.portal.test.rule;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.runner.Description;

/**
 * @author Matthew Tambara
 */
public class InitializeKernelUtilTestRule
	extends AbstractTestRule<Void, Properties> {

	public static final InitializeKernelUtilTestRule INSTANCE =
		new InitializeKernelUtilTestRule();

	@Override
	protected void afterClass(Description description, Void v)
		throws ReflectiveOperationException {

		_setUpPropsUtil(null);
	}

	@Override
	protected void afterMethod(
			Description description, Properties properties, Object target)
		throws ReflectiveOperationException {

		if ((properties != null) && !properties.isEmpty()) {
			Thread thread = Thread.currentThread();

			ClassLoader classLoader = thread.getContextClassLoader();

			ReflectionTestUtil.invoke(
				classLoader.loadClass("com.liferay.portal.util.PropsUtil"),
				"removeProperties", new Class<?>[] {Properties.class},
				properties);
		}
	}

	@Override
	protected Void beforeClass(Description description)
		throws ReflectiveOperationException {

		Class<?> clazz = description.getTestClass();

		PortalProps portalProps = clazz.getAnnotation(PortalProps.class);

		if (portalProps == null) {
			_setUpPropsUtil(null);
		}
		else {
			_setUpPropsUtil(_processProperties(portalProps.properties()));
		}

		_setUpCalendarFactoryUtil();
		_setUpFileUtil();
		_setUpJSONFactoryUtil();
		_setUpHtmlUtil();
		_setUpHttpUtil();

		return null;
	}

	@Override
	protected Properties beforeMethod(Description description, Object target)
		throws Throwable {

		PortalProps portalProps = description.getAnnotation(PortalProps.class);

		if (portalProps == null) {
			return null;
		}

		return _setUpPropsUtil(_processProperties(portalProps.properties()));
	}

	private Map<String, String> _processProperties(String[] properties) {
		if (properties == null) {
			return null;
		}

		Map<String, String> propertyMap = new HashMap<>();

		for (String variable : properties) {
			String[] parts = StringUtil.split(variable, CharPool.EQUAL);

			if (parts.length != 2) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Property ", variable,
						" needs to be in \"key=value\" format"));
			}

			propertyMap.put(parts[0], parts[1]);
		}

		return propertyMap;
	}

	private void _setUpCalendarFactoryUtil()
		throws ReflectiveOperationException {

		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.CalendarFactoryImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		calendarFactoryUtil.setCalendarFactory(
			(CalendarFactory)constructor.newInstance());
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

	private void _setUpHtmlUtil() throws ReflectiveOperationException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		HtmlUtil htmlUtil = new HtmlUtil();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.HtmlImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		htmlUtil.setHtml((Html)constructor.newInstance());
	}

	private void _setUpHttpUtil() throws ReflectiveOperationException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		HttpUtil httpUtil = new HttpUtil();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.HttpImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		httpUtil.setHttp((Http)constructor.newInstance());
	}

	private void _setUpJSONFactoryUtil() throws ReflectiveOperationException {
		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.json.JSONFactoryImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		jsonFactoryUtil.setJSONFactory((JSONFactory)constructor.newInstance());
	}

	private Properties _setUpPropsUtil(Map<String, String> map)
		throws ReflectiveOperationException {

		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.util.PropsImpl");

		Constructor<?> constructor = clazz.getDeclaredConstructor();

		PropsUtil.setProps((Props)constructor.newInstance());

		if (map == null) {
			return null;
		}

		Properties properties = new Properties();

		properties.putAll(map);

		if (!properties.isEmpty()) {
			ReflectionTestUtil.invoke(
				classLoader.loadClass("com.liferay.portal.util.PropsUtil"),
				"addProperties", new Class<?>[] {Properties.class}, properties);
		}

		return properties;
	}

}