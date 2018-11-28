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

package com.liferay.jenkins.results.parser.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class TestPropertiesUtil {

	public static String get(String key) {
		return _instance._get(key);
	}

	public static Properties getProperties() {
		return _instance._properties;
	}

	public static void printProperties() {
		_instance._printProperties(true);
	}

	public static void set(String key, String value) {
		_instance._set(key, value);
	}

	private TestPropertiesUtil() {
		try (InputStream is = TestPropertiesUtil.class.getResourceAsStream(
				"/test-jenkins-results-parser-util.properties")) {

			_properties.load(is);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		try (InputStream is = TestPropertiesUtil.class.getResourceAsStream(
				"/test-jenkins-results-parser-util-ext.properties")) {

			if (is != null) {
				_properties.load(is);
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		String repositoryDir = System.getProperty("repository.dir");

		if ((repositoryDir != null) && !repositoryDir.isEmpty()) {
			_properties.setProperty("repository.dir", repositoryDir);
		}

		_printProperties(false);
	}

	private String _get(String key) {
		return _properties.getProperty(key);
	}

	private void _printProperties(boolean update) {
		List<String> keys = Collections.list(
			(Enumeration<String>)_properties.propertyNames());

		if (update) {
			System.out.println("-- updated properties --");
		}
		else {
			System.out.println("-- listing properties --");
		}

		for (String key : keys) {
			System.out.println(key + "=" + _properties.getProperty(key));
		}

		System.out.println("");
	}

	private void _set(String key, String value) {
		_properties.setProperty(key, value);
	}

	private static final TestPropertiesUtil _instance =
		new TestPropertiesUtil();

	private final Properties _properties = new Properties();

}