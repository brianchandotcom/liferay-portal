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

import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsParamUtil {

	public static boolean getBoolean(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param) {

		return getBoolean(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, boolean defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			preferencesValue, defaultValue);

		return ParamUtil.get(httpServletRequest, param, getterUtilValue);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getBoolean(
			preferences, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, boolean defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param) {

		return getDouble(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, double defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.get(httpServletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, double defaultValue, Locale locale) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.getDouble(
			httpServletRequest, param, getterUtilValue, locale);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, Locale locale) {

		return getDouble(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_DOUBLE,
			locale);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getDouble(
			preferences, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, double defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, double defaultValue, Locale locale) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.getDouble(
			portletRequest, param, getterUtilValue, locale);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, Locale locale) {

		return getDouble(
			preferences, portletRequest, param, GetterUtil.DEFAULT_DOUBLE,
			locale);
	}

	public static int getInteger(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param) {

		return getInteger(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, int defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			preferencesValue, defaultValue);

		return ParamUtil.get(httpServletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getInteger(
			preferences, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, int defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param) {

		return getLong(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, long defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(
			preferencesValue, defaultValue);

		return ParamUtil.get(httpServletRequest, param, getterUtilValue);
	}

	public static long getLong(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getLong(
			preferences, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, long defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static String getString(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param) {

		return getString(
			preferences, httpServletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletPreferences preferences, HttpServletRequest httpServletRequest,
		String param, String defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			preferencesValue, defaultValue);

		return ParamUtil.get(httpServletRequest, param, getterUtilValue);
	}

	public static String getString(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getString(
			preferences, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, String defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

}