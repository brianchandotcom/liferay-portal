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

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ParamUtil {

	public static boolean get(
		HttpServletRequest request, String param, boolean defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static Date get(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			request.getParameter(param), dateFormat, defaultValue);
	}

	public static double get(
		HttpServletRequest request, String param, double defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static float get(
		HttpServletRequest request, String param, float defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static int get(
		HttpServletRequest request, String param, int defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static long get(
		HttpServletRequest request, String param, long defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static Number get(
		HttpServletRequest request, String param, Number defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static short get(
		HttpServletRequest request, String param, short defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static String get(
		HttpServletRequest request, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			request.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	public static boolean get(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static Date get(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			portletRequest.getParameter(param), dateFormat, defaultValue);
	}

	public static double get(
		PortletRequest portletRequest, String param, double defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static float get(
		PortletRequest portletRequest, String param, float defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static int get(
		PortletRequest portletRequest, String param, int defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static long get(
		PortletRequest portletRequest, String param, long defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static Number get(
		PortletRequest portletRequest, String param, Number defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static short get(
		PortletRequest portletRequest, String param, short defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static String get(
		PortletRequest portletRequest, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			portletRequest.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	public static boolean get(
		ServiceContext serviceContext, String param, boolean defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static Date get(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			serviceContext.getAttribute(param), dateFormat, defaultValue);
	}

	public static double get(
		ServiceContext serviceContext, String param, double defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static float get(
		ServiceContext serviceContext, String param, float defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static int get(
		ServiceContext serviceContext, String param, int defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static long get(
		ServiceContext serviceContext, String param, long defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static Number get(
		ServiceContext serviceContext, String param, Number defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static short get(
		ServiceContext serviceContext, String param, short defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	public static String get(
		ServiceContext serviceContext, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			serviceContext.getAttribute(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	public static boolean getBoolean(HttpServletRequest request, String param) {
		return GetterUtil.getBoolean(request.getParameter(param));
	}

	public static boolean getBoolean(
		HttpServletRequest request, String param, boolean defaultValue) {

		return get(request, param, defaultValue);
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getBoolean(portletRequest.getParameter(param));
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static boolean getBoolean(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getBoolean(serviceContext.getAttribute(param));
	}

	public static boolean getBoolean(
		ServiceContext serviceContext, String param, boolean defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param) {

		return getBooleanValues(request, param, new boolean[0]);
	}

	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param) {

		return getBooleanValues(portletRequest, param, new boolean[0]);
	}

	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static boolean[] getBooleanValues(
		ServiceContext serviceContext, String param) {

		return getBooleanValues(serviceContext, param, new boolean[0]);
	}

	public static boolean[] getBooleanValues(
		ServiceContext serviceContext, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static Date getDate(
		HttpServletRequest request, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(request.getParameter(param), dateFormat);
	}

	public static Date getDate(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(request, param, dateFormat, defaultValue);
	}

	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(
			portletRequest.getParameter(param), dateFormat);
	}

	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(portletRequest, param, dateFormat, defaultValue);
	}

	public static Date getDate(
		ServiceContext serviceContext, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(
			serviceContext.getAttribute(param), dateFormat);
	}

	public static Date getDate(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(serviceContext, param, dateFormat, defaultValue);
	}

	public static Date[] getDateValues(
		HttpServletRequest request, String param, DateFormat dateFormat) {

		return getDateValues(request, param, dateFormat, new Date[0]);
	}

	public static Date[] getDateValues(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			getParameterValues(request, param, null), dateFormat, defaultValue);
	}

	public static Date[] getDateValues(
		PortletRequest portletRequest, String param, DateFormat dateFormat) {

		return getDateValues(portletRequest, param, dateFormat, new Date[0]);
	}

	public static Date[] getDateValues(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			getParameterValues(portletRequest, param, null), dateFormat,
			defaultValue);
	}

	public static Date[] getDateValues(
		ServiceContext serviceContext, String param, DateFormat dateFormat) {

		return getDateValues(serviceContext, param, dateFormat, new Date[0]);
	}

	public static Date[] getDateValues(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			serviceContext.getAttribute(param), dateFormat, defaultValue);
	}

	public static double getDouble(HttpServletRequest request, String param) {
		return GetterUtil.getDouble(request.getParameter(param));
	}

	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue) {

		return get(request, param, defaultValue);
	}

	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue,
		Locale locale) {

		return GetterUtil.get(
			request.getParameter(param), defaultValue, locale);
	}

	public static double getDouble(
		HttpServletRequest request, String param, Locale locale) {

		return GetterUtil.getDouble(request.getParameter(param), locale);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getDouble(portletRequest.getParameter(param));
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue,
		Locale locale) {

		return GetterUtil.get(
			portletRequest.getParameter(param), defaultValue, locale);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, Locale locale) {

		return GetterUtil.getDouble(portletRequest.getParameter(param), locale);
	}

	public static double getDouble(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getDouble(serviceContext.getAttribute(param));
	}

	public static double getDouble(
		ServiceContext serviceContext, String param, double defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static double[] getDoubleValues(
		HttpServletRequest request, String param) {

		return getDoubleValues(request, param, new double[0]);
	}

	public static double[] getDoubleValues(
		HttpServletRequest request, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param) {

		return getDoubleValues(portletRequest, param, new double[0]);
	}

	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static double[] getDoubleValues(
		ServiceContext serviceContext, String param) {

		return getDoubleValues(serviceContext, param, new double[0]);
	}

	public static double[] getDoubleValues(
		ServiceContext serviceContext, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static float getFloat(HttpServletRequest request, String param) {
		return GetterUtil.getFloat(request.getParameter(param));
	}

	public static float getFloat(
		HttpServletRequest request, String param, float defaultValue) {

		return get(request, param, defaultValue);
	}

	public static float getFloat(PortletRequest portletRequest, String param) {
		return GetterUtil.getFloat(portletRequest.getParameter(param));
	}

	public static float getFloat(
		PortletRequest portletRequest, String param, float defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static float getFloat(ServiceContext serviceContext, String param) {
		return GetterUtil.getFloat(serviceContext.getAttribute(param));
	}

	public static float getFloat(
		ServiceContext serviceContext, String param, float defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static float[] getFloatValues(
		HttpServletRequest request, String param) {

		return getFloatValues(request, param, new float[0]);
	}

	public static float[] getFloatValues(
		HttpServletRequest request, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static float[] getFloatValues(
		PortletRequest portletRequest, String param) {

		return getFloatValues(portletRequest, param, new float[0]);
	}

	public static float[] getFloatValues(
		PortletRequest portletRequest, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static float[] getFloatValues(
		ServiceContext serviceContext, String param) {

		return getFloatValues(serviceContext, param, new float[0]);
	}

	public static float[] getFloatValues(
		ServiceContext serviceContext, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static int getInteger(HttpServletRequest request, String param) {
		return GetterUtil.getInteger(request.getParameter(param));
	}

	public static int getInteger(
		HttpServletRequest request, String param, int defaultValue) {

		return get(request, param, defaultValue);
	}

	public static int getInteger(PortletRequest portletRequest, String param) {
		return GetterUtil.getInteger(portletRequest.getParameter(param));
	}

	public static int getInteger(
		PortletRequest portletRequest, String param, int defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static int getInteger(ServiceContext serviceContext, String param) {
		return GetterUtil.getInteger(serviceContext.getAttribute(param));
	}

	public static int getInteger(
		ServiceContext serviceContext, String param, int defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static int[] getIntegerValues(
		HttpServletRequest request, String param) {

		return getIntegerValues(request, param, new int[0]);
	}

	public static int[] getIntegerValues(
		HttpServletRequest request, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param) {

		return getIntegerValues(portletRequest, param, new int[0]);
	}

	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static int[] getIntegerValues(
		ServiceContext serviceContext, String param) {

		return getIntegerValues(serviceContext, param, new int[0]);
	}

	public static int[] getIntegerValues(
		ServiceContext serviceContext, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static long getLong(HttpServletRequest request, String param) {
		return GetterUtil.getLong(request.getParameter(param));
	}

	public static long getLong(
		HttpServletRequest request, String param, long defaultValue) {

		return get(request, param, defaultValue);
	}

	public static long getLong(PortletRequest portletRequest, String param) {
		return GetterUtil.getLong(portletRequest.getParameter(param));
	}

	public static long getLong(
		PortletRequest portletRequest, String param, long defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static long getLong(ServiceContext serviceContext, String param) {
		return GetterUtil.getLong(serviceContext.getAttribute(param));
	}

	public static long getLong(
		ServiceContext serviceContext, String param, long defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static long[] getLongValues(
		HttpServletRequest request, String param) {

		return getLongValues(request, param, new long[0]);
	}

	public static long[] getLongValues(
		HttpServletRequest request, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static long[] getLongValues(
		PortletRequest portletRequest, String param) {

		return getLongValues(portletRequest, param, new long[0]);
	}

	public static long[] getLongValues(
		PortletRequest portletRequest, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static long[] getLongValues(
		ServiceContext serviceContext, String param) {

		return getLongValues(serviceContext, param, new long[0]);
	}

	public static long[] getLongValues(
		ServiceContext serviceContext, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static Number getNumber(HttpServletRequest request, String param) {
		return GetterUtil.getNumber(request.getParameter(param));
	}

	public static Number getNumber(
		HttpServletRequest request, String param, Number defaultValue) {

		return get(request, param, defaultValue);
	}

	public static Number getNumber(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getNumber(portletRequest.getParameter(param));
	}

	public static Number getNumber(
		PortletRequest portletRequest, String param, Number defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static Number getNumber(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getNumber(serviceContext.getAttribute(param));
	}

	public static Number getNumber(
		ServiceContext serviceContext, String param, Number defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static Number[] getNumberValues(
		HttpServletRequest request, String param) {

		return getNumberValues(request, param, new Number[0]);
	}

	public static Number[] getNumberValues(
		HttpServletRequest request, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static Number[] getNumberValues(
		PortletRequest portletRequest, String param) {

		return getNumberValues(portletRequest, param, new Number[0]);
	}

	public static Number[] getNumberValues(
		PortletRequest portletRequest, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static Number[] getNumberValues(
		ServiceContext serviceContext, String param) {

		return getNumberValues(serviceContext, param, new Number[0]);
	}

	public static Number[] getNumberValues(
		ServiceContext serviceContext, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static String[] getParameterValues(
		HttpServletRequest request, String param) {

		return getParameterValues(request, param, new String[0]);
	}

	public static String[] getParameterValues(
		HttpServletRequest request, String param, String[] defaultValue) {

		return getParameterValues(request, param, defaultValue, true);
	}

	public static String[] getParameterValues(
		HttpServletRequest request, String param, String[] defaultValue,
		boolean split) {

		String[] values = request.getParameterValues(param);

		if (values == null) {
			return defaultValue;
		}

		if (split && (values.length == 1)) {
			return StringUtil.split(values[0]);
		}

		return values;
	}

	public static String[] getParameterValues(
		PortletRequest portletRequest, String param) {

		return getParameterValues(portletRequest, param, new String[0]);
	}

	public static String[] getParameterValues(
		PortletRequest portletRequest, String param, String[] defaultValue) {

		return getParameterValues(portletRequest, param, defaultValue, true);
	}

	public static String[] getParameterValues(
		PortletRequest portletRequest, String param, String[] defaultValue,
		boolean split) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getParameterValues(request, param, defaultValue, split);
	}

	public static short getShort(HttpServletRequest request, String param) {
		return GetterUtil.getShort(request.getParameter(param));
	}

	public static short getShort(
		HttpServletRequest request, String param, short defaultValue) {

		return get(request, param, defaultValue);
	}

	public static short getShort(PortletRequest portletRequest, String param) {
		return GetterUtil.getShort(portletRequest.getParameter(param));
	}

	public static short getShort(
		PortletRequest portletRequest, String param, short defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static short getShort(ServiceContext serviceContext, String param) {
		return GetterUtil.getShort(serviceContext.getAttribute(param));
	}

	public static short getShort(
		ServiceContext serviceContext, String param, short defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static short[] getShortValues(
		HttpServletRequest request, String param) {

		return getShortValues(request, param, new short[0]);
	}

	public static short[] getShortValues(
		HttpServletRequest request, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static short[] getShortValues(
		PortletRequest portletRequest, String param) {

		return getShortValues(portletRequest, param, new short[0]);
	}

	public static short[] getShortValues(
		PortletRequest portletRequest, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static short[] getShortValues(
		ServiceContext serviceContext, String param) {

		return getShortValues(serviceContext, param, new short[0]);
	}

	public static short[] getShortValues(
		ServiceContext serviceContext, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static String getString(HttpServletRequest request, String param) {
		return GetterUtil.getString(request.getParameter(param));
	}

	public static String getString(
		HttpServletRequest request, String param, String defaultValue) {

		return get(request, param, defaultValue);
	}

	public static String getString(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getString(portletRequest.getParameter(param));
	}

	public static String getString(
		PortletRequest portletRequest, String param, String defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static String getString(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getString(serviceContext.getAttribute(param));
	}

	public static String getString(
		ServiceContext serviceContext, String param, String defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	public static String[] getStringValues(
		HttpServletRequest request, String param) {

		return getStringValues(request, param, new String[0]);
	}

	public static String[] getStringValues(
		HttpServletRequest request, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			getParameterValues(request, param, null), defaultValue);
	}

	public static String[] getStringValues(
		PortletRequest portletRequest, String param) {

		return getStringValues(portletRequest, param, new String[0]);
	}

	public static String[] getStringValues(
		PortletRequest portletRequest, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	public static String[] getStringValues(
		ServiceContext serviceContext, String param) {

		return getStringValues(serviceContext, param, new String[0]);
	}

	public static String[] getStringValues(
		ServiceContext serviceContext, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	public static void print(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				System.out.println(name + "[" + i + "] = " + values[i]);
			}
		}
	}

	public static void print(PortletRequest portletRequest) {
		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = portletRequest.getParameterValues(param);

			for (int i = 0; i < values.length; i++) {
				System.out.println(param + "[" + i + "] = " + values[i]);
			}
		}
	}

	public static void print(ServiceContext serviceContext) {
		Map<String, Serializable> attributes = serviceContext.getAttributes();

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			System.out.println(
				entry.getKey() + " = " + String.valueOf(entry.getValue()));
		}
	}

}