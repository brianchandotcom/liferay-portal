/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.configuration.Filter;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsUtil {

	public static boolean contains(String key) {
		return _props.contains(key);
	}

	public static String get(String key) {
		return _props.get(key);
	}

	public static String get(String key, Filter filter) {
		return _props.get(key, filter);
	}

	public static String[] getArray(String key) {
		return _props.getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return _props.getArray(key, filter);
	}

	public static Properties getProperties() {
		return _props.getProperties();
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		return _props.getProperties(prefix, removePrefix);
	}

	public static Props getProps() {
		return _props;
	}

	public static void setProps(Props props) {
		_props = props;
	}

	private static Props _props;

}