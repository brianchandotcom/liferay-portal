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

package com.liferay.portal.kernel.util;

import com.liferay.portlet.PortletSettings;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletSettingsParamUtil {

	public static boolean getBoolean(
		PortletSettings portletSettings, HttpServletRequest request,
		String param) {

		return getBoolean(
			portletSettings, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletSettings portletSettings, HttpServletRequest request,
		String param, boolean defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			settingsValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param) {

		return getBoolean(
			portletSettings, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param, boolean defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			settingsValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		PortletSettings portletSettings, HttpServletRequest request,
		String param) {

		return getDouble(
			portletSettings, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletSettings portletSettings, HttpServletRequest request,
		String param, double defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			settingsValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param) {

		return getDouble(
			portletSettings, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param, double defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			settingsValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		PortletSettings portletSettings, HttpServletRequest request,
		String param) {

		return getInteger(
			portletSettings, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletSettings portletSettings, HttpServletRequest request,
		String param, int defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			settingsValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param) {

		return getInteger(
			portletSettings, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param, int defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			settingsValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		PortletSettings portletSettings, HttpServletRequest request,
		String param) {

		return getLong(
			portletSettings, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletSettings portletSettings, HttpServletRequest request,
		String param, long defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(settingsValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param) {

		return getLong(
			portletSettings, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param, long defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(settingsValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static String getString(
		PortletSettings portletSettings, HttpServletRequest request,
		String param) {

		return getString(
			portletSettings, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletSettings portletSettings, HttpServletRequest request,
		String param, String defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			settingsValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param) {

		return getString(
			portletSettings, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletSettings portletSettings, PortletRequest portletRequest,
		String param, String defaultValue) {

		String settingsValue = portletSettings.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			settingsValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

}