/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static boolean getBoolean(long companyId, String name)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getBooleanMethodKey1, companyId, name);

		if (returnObj != null) {
			return (Boolean)returnObj;
		}
		else {
			return false;
		}
	}

	public static boolean getBoolean(
			long companyId, String name, boolean defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getBooleanMethodKey2, companyId, name, defaultValue);

		if (returnObj != null) {
			return (Boolean)returnObj;
		}
		else {
			return false;
		}
	}

	public static boolean getBoolean(String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getBooleanMethodKey3, name);

		if (returnObj != null) {
			return (Boolean)returnObj;
		}
		else {
			return false;
		}
	}

	public static int getInteger(long companyId, String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getIntegerMethodKey1, companyId, name);

		if (returnObj != null) {
			return (Integer)returnObj;
		}
		else {
			return 0;
		}
	}

	public static int getInteger(long companyId, String name, int defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getIntegerMethodKey2, companyId, name, defaultValue);

		if (returnObj != null) {
			return (Integer)returnObj;
		}
		else {
			return 0;
		}
	}

	public static int getInteger(String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getIntegerMethodKey3, name);

		if (returnObj != null) {
			return (Integer)returnObj;
		}
		else {
			return 0;
		}
	}

	public static long getLong(long companyId, String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getLongMethodKey1, companyId, name);

		if (returnObj != null) {
			return (Long)returnObj;
		}
		else {
			return 0;
		}
	}

	public static long getLong(long companyId, String name, long defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getLongMethodKey2, companyId, name, defaultValue);

		if (returnObj != null) {
			return (Long)returnObj;
		}
		else {
			return 0;
		}
	}

	public static long getLong(String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getLongMethodKey3, name);

		if (returnObj != null) {
			return (Long)returnObj;
		}
		else {
			return 0;
		}
	}

	public static String getString(long companyId, String name)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringMethodKey1, companyId, name);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getString(
			long companyId, String name, String defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringMethodKey2, companyId, name, defaultValue);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getString(String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringMethodKey3, name);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey1, companyId, name, delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter,
			String[] defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey2, companyId, name, delimiter,
			defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			PortletPreferences preferences, long companyId, String name,
			String delimiter)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey3, preferences, companyId, name,
			delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			PortletPreferences preferences, long companyId, String name,
			String delimiter, String[] defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey4, preferences, companyId, name,
			delimiter, defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(String name, String delimiter)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey5, name, delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			String name, String delimiter, String[] defaultValue)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _getStringArrayMethodKey6, name, delimiter, defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.util.PrefsPropsUtil";

	private static MethodKey _getBooleanMethodKey1 = new MethodKey(
		_CLASS_NAME, "getBoolean", long.class, String.class);
	private static MethodKey _getBooleanMethodKey2 = new MethodKey(
		_CLASS_NAME, "getBoolean", long.class, String.class, boolean.class);
	private static MethodKey _getBooleanMethodKey3 = new MethodKey(
		_CLASS_NAME, "getBoolean", String.class);
	private static MethodKey _getIntegerMethodKey1 = new MethodKey(
		_CLASS_NAME, "getInteger", long.class, String.class);
	private static MethodKey _getIntegerMethodKey2 = new MethodKey(
		_CLASS_NAME, "getInteger", long.class, String.class, int.class);
	private static MethodKey _getIntegerMethodKey3 = new MethodKey(
		_CLASS_NAME, "getInteger", String.class);
	private static MethodKey _getLongMethodKey1 = new MethodKey(
		_CLASS_NAME, "getLong", long.class, String.class);
	private static MethodKey _getLongMethodKey2 = new MethodKey(
		_CLASS_NAME, "getLong", long.class, String.class, long.class);
	private static MethodKey _getLongMethodKey3 = new MethodKey(
		_CLASS_NAME, "getLong", String.class);
	private static MethodKey _getStringArrayMethodKey1 = new MethodKey(
		_CLASS_NAME, "getStringArray", long.class, String.class, String.class);
	private static MethodKey _getStringArrayMethodKey2 = new MethodKey(
		_CLASS_NAME, "getStringArray", long.class, String.class, String.class,
		String[].class);
	private static MethodKey _getStringArrayMethodKey3 = new MethodKey(
		_CLASS_NAME, "getStringArray", PortletPreferences.class, long.class,
		String.class, String.class);
	private static MethodKey _getStringArrayMethodKey4 = new MethodKey(
		_CLASS_NAME, "getStringArray", PortletPreferences.class, long.class,
		String.class, String.class, String[].class);
	private static MethodKey _getStringArrayMethodKey5 = new MethodKey(
		_CLASS_NAME, "getStringArray", String.class, String.class);
	private static MethodKey _getStringArrayMethodKey6 = new MethodKey(
		_CLASS_NAME, "getStringArray", String.class, String.class,
		String[].class);
	private static MethodKey _getStringMethodKey1 = new MethodKey(
		_CLASS_NAME, "getString", long.class, String.class);
	private static MethodKey _getStringMethodKey2 = new MethodKey(
		_CLASS_NAME, "getString", long.class, String.class, String.class);
	private static MethodKey _getStringMethodKey3 = new MethodKey(
		_CLASS_NAME, "getString", String.class);

}