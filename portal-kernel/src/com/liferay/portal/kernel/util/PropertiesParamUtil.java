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

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class PropertiesParamUtil {

	public static boolean getBoolean(
		Properties properties, HttpServletRequest request, String param) {

		return getBoolean(
			properties, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Properties properties, HttpServletRequest request, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		Properties properties, PortletRequest portletRequest, String param) {

		return getBoolean(
			properties, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Properties properties, PortletRequest portletRequest, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getBoolean(
			properties, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, HttpServletRequest request, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getBoolean(
			properties, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param) {

		return getDouble(properties, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param,
		double defaultValue, Locale locale) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.getDouble(request, param, getterUtilValue, locale);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param,
		Locale locale) {

		return getDouble(
			properties, request, param, GetterUtil.DEFAULT_DOUBLE, locale);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param,
		double defaultValue, Locale locale) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.getDouble(
			portletRequest, param, getterUtilValue, locale);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param,
		Locale locale) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE,
			locale);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getDouble(properties, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request, String param,
		double defaultValue, Locale locale) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.getDouble(request, param, getterUtilValue, locale);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request, String param,
		Locale locale) {

		return getDouble(
			properties, request, param, GetterUtil.DEFAULT_DOUBLE, locale);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, double defaultValue, Locale locale) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.getDouble(
			portletRequest, param, getterUtilValue, locale);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, Locale locale) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE,
			locale);
	}

	public static int getInteger(
		Properties properties, HttpServletRequest request, String param) {

		return getInteger(
			properties, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Properties properties, HttpServletRequest request, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		Properties properties, PortletRequest portletRequest, String param) {

		return getInteger(
			properties, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Properties properties, PortletRequest portletRequest, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getInteger(
			properties, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		UnicodeProperties properties, HttpServletRequest request, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getInteger(
			properties, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		Properties properties, HttpServletRequest request, String param) {

		return getLong(properties, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Properties properties, HttpServletRequest request, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		Properties properties, PortletRequest portletRequest, String param) {

		return getLong(
			properties, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Properties properties, PortletRequest portletRequest, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getLong(properties, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		UnicodeProperties properties, HttpServletRequest request, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getLong(
			properties, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static UnicodeProperties getProperties(
		HttpServletRequest request, String prefix) {

		UnicodeProperties properties = new UnicodeProperties(true);

		Map<String, String[]> parameterMap = request.getParameterMap();

		for (String param : parameterMap.keySet()) {
			if (param.startsWith(prefix)) {
				String key = param.substring(
					prefix.length(), param.length() - 2);

				String value = request.getParameter(param);

				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static UnicodeProperties getProperties(
		PortletRequest portletRequest, String prefix) {

		UnicodeProperties properties = new UnicodeProperties(true);

		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		for (String param : parameterMap.keySet()) {
			if (param.startsWith(prefix)) {
				String key = param.substring(
					prefix.length(), param.length() - 2);

				String[] values = portletRequest.getParameterValues(param);

				String value = StringUtil.merge(values);

				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static UnicodeProperties getProperties(
		ServiceContext serviceContext, String prefix) {

		UnicodeProperties properties = new UnicodeProperties(true);

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		for (String param : attributes.keySet()) {
			if (param.startsWith(prefix)) {
				String key = param.substring(
					prefix.length(), param.length() - 2);

				String value = ParamUtil.getString(serviceContext, param);

				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static String getString(
		Properties properties, HttpServletRequest request, String param) {

		return getString(properties, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Properties properties, HttpServletRequest request, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		Properties properties, PortletRequest portletRequest, String param) {

		return getString(
			properties, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Properties properties, PortletRequest portletRequest, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static String getString(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getString(properties, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		UnicodeProperties properties, HttpServletRequest request, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getString(
			properties, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

}