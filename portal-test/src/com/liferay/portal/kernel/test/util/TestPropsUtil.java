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

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsUtil {

	public static String get(String key) {
		return _testPropsUtil._get(key);
	}

	public static Properties getProperties() {
		return _testPropsUtil._props;
	}

	public static void printProperties() {
		_testPropsUtil._printProperties(true);
	}

	public static void set(String key, String value) {
		_testPropsUtil._set(key, value);
	}

	private TestPropsUtil() {
		try (InputStream is = TestPropsUtil.class.getResourceAsStream(
				"/test-portal-impl.properties")) {

			_props.load(is);
		}
		catch (IOException ioe) {
			ReflectionUtil.throwException(ioe);
		}

		try (InputStream is = TestPropsUtil.class.getResourceAsStream(
				"/test-portal-impl-ext.properties")) {

			if (is != null) {
				_props.load(is);
			}
		}
		catch (IOException ioe) {
			ReflectionUtil.throwException(ioe);
		}

		_printProperties(false);
	}

	private String _get(String key) {
		return _props.getProperty(key);
	}

	private void _printProperties(boolean update) {
		List<String> keys = Collections.list(
			(Enumeration<String>)_props.propertyNames());

		keys = ListUtil.sort(keys);

		if (update) {
			System.out.println("-- updated properties --");
		}
		else {
			System.out.println("-- listing properties --");
		}

		for (String key : keys) {
			System.out.println(key + "=" + _props.getProperty(key));
		}

		System.out.println("");
	}

	private void _set(String key, String value) {
		_props.setProperty(key, value);
	}

	private static final TestPropsUtil _testPropsUtil = new TestPropsUtil();

	private final Properties _props = new Properties();

}