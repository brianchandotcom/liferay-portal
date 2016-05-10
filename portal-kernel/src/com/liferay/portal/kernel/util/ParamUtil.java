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

import java.text.DateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods for reading request parameters.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ParamUtil {

	/**
	 * Returns the boolean parameter from the request. If the parameter is
	 * missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the request
	 */
	public static boolean get(
		HttpServletRequest request, String param, boolean defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the date parameter from the request. If the parameter is missing
	 * or not convertible to Date, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse date
	 * @param  defaultValue the default value
	 * @return the date parameter from the request
	 */
	public static Date get(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			request.getParameter(param), dateFormat, defaultValue);
	}

	/**
	 * Returns the double parameter from the request. If the parameter is
	 * missing or not convertible to double, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the request
	 */
	public static double get(
		HttpServletRequest request, String param, double defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the float parameter from the request. If the parameter is missing
	 * or not convertible to float, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the request
	 */
	public static float get(
		HttpServletRequest request, String param, float defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the int parameter from the request. If the parameter is missing
	 * or not convertible to int, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the request
	 */
	public static int get(
		HttpServletRequest request, String param, int defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the long parameter from the request. If the parameter is missing
	 * or not convertible to long, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the request
	 */
	public static long get(
		HttpServletRequest request, String param, long defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the number parameter from the request. If the parameter is
	 * missing or not convertible to Number, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the request
	 */
	public static Number get(
		HttpServletRequest request, String param, Number defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the short parameter from the request. If the parameter is missing
	 * or not convertible to short, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the request
	 */
	public static short get(
		HttpServletRequest request, String param, short defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	/**
	 * Returns the string parameter from the request. If the parameter is
	 * missing or not convertible to String, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the request
	 */
	public static String get(
		HttpServletRequest request, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			request.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	/**
	 * Returns the boolean parameter from the portlet request. If the parameter
	 * is missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the portlet request
	 */
	public static boolean get(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the date parameter from the portlet request. If the parameter is
	 * missing or not convertible to Date, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse date
	 * @param  defaultValue the default value
	 * @return the date parameter from the portlet request
	 */
	public static Date get(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			portletRequest.getParameter(param), dateFormat, defaultValue);
	}

	/**
	 * Returns the double parameter from the portlet request. If the parameter
	 * is missing or not convertible to double, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the portlet request
	 */
	public static double get(
		PortletRequest portletRequest, String param, double defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the float parameter from the portlet request. If the parameter is
	 * missing or not convertible to float, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the portlet request
	 */
	public static float get(
		PortletRequest portletRequest, String param, float defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the int parameter from the portlet request. If the parameter is
	 * missing or not convertible to int, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the portlet request
	 */
	public static int get(
		PortletRequest portletRequest, String param, int defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the long parameter from the portlet request. If the parameter is
	 * missing or not convertible to long, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the portlet request
	 */
	public static long get(
		PortletRequest portletRequest, String param, long defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the number parameter from the portlet request. If the parameter
	 * is missing or not convertible to Number, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the portlet request
	 */
	public static Number get(
		PortletRequest portletRequest, String param, Number defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the short parameter from the portlet request. If the parameter is
	 * missing or not convertible to short, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the portlet request
	 */
	public static short get(
		PortletRequest portletRequest, String param, short defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	/**
	 * Returns the string parameter from the portlet request. If the parameter
	 * is missing or not convertible to String, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the portlet request
	 */
	public static String get(
		PortletRequest portletRequest, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			portletRequest.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	/**
	 * Returns the boolean parameter from the service context. If the parameter
	 * is missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the service context
	 */
	public static boolean get(
		ServiceContext serviceContext, String param, boolean defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the date parameter from the service context. If the parameter is
	 * missing or not convertible to Date, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse date
	 * @param  defaultValue the default value
	 * @return the date parameter from the service context
	 */
	public static Date get(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date defaultValue) {

		return GetterUtil.get(
			serviceContext.getAttribute(param), dateFormat, defaultValue);
	}

	/**
	 * Returns the double parameter from the service context. If the parameter
	 * is missing or not convertible to double, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the service context
	 */
	public static double get(
		ServiceContext serviceContext, String param, double defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the float parameter from the service context. If the parameter is
	 * missing or not convertible to float, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the service context
	 */
	public static float get(
		ServiceContext serviceContext, String param, float defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the int parameter from the service context. If the parameter is
	 * missing or not convertible to int, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the service context
	 */
	public static int get(
		ServiceContext serviceContext, String param, int defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the long parameter from the service context. If the parameter is
	 * missing or not convertible to long, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the service context
	 */
	public static long get(
		ServiceContext serviceContext, String param, long defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the number parameter from the service context. If the parameter
	 * is missing or not convertible to Number, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the service context
	 */
	public static Number get(
		ServiceContext serviceContext, String param, Number defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the short parameter from the service context. If the parameter is
	 * missing or not convertible to short, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the service context
	 */
	public static short get(
		ServiceContext serviceContext, String param, short defaultValue) {

		return GetterUtil.get(serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the string parameter from the service context. If the parameter
	 * is missing or not convertible to String, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the service context
	 */
	public static String get(
		ServiceContext serviceContext, String param, String defaultValue) {

		String returnValue = GetterUtil.get(
			serviceContext.getAttribute(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	/**
	 * Returns the boolean parameter from the request. If the parameter is
	 * missing or not convertible to boolean, <code>false</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean parameter from the request
	 */
	public static boolean getBoolean(HttpServletRequest request, String param) {
		return GetterUtil.getBoolean(request.getParameter(param));
	}

	/**
	 * Returns the boolean parameter from the request. If the parameter is
	 * missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the request
	 */
	public static boolean getBoolean(
		HttpServletRequest request, String param, boolean defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the boolean parameter from the portlet request. If the parameter
	 * is missing or not convertible to boolean, <code>false</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean parameter from the portlet request
	 */
	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getBoolean(portletRequest.getParameter(param));
	}

	/**
	 * Returns the boolean parameter from the portlet request. If the parameter
	 * is missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the portlet request
	 */
	public static boolean getBoolean(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the boolean parameter from the service context. If the parameter
	 * is missing or not convertible to boolean, <code>false</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean parameter from the service context
	 */
	public static boolean getBoolean(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getBoolean(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the boolean parameter from the service context. If the parameter
	 * is missing, the default value is returned.
	 *
	 * <p>
	 * If the value is not convertible to boolean, <code>false</code> is
	 * returned.
	 * </p>
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean parameter from the service context
	 */
	public static boolean getBoolean(
		ServiceContext serviceContext, String param, boolean defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the boolean array parameter from the request. In the returned
	 * array, each parameter value not convertible to boolean is replaced by
	 * <code>false</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean array parameter from the request
	 */
	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param) {

		return getBooleanValues(request, param, new boolean[0]);
	}

	/**
	 * Returns the boolean array parameter from the request. In the returned
	 * array, each parameter value not convertible to boolean is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean array parameter from the request
	 */
	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the boolean array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to boolean is
	 * replaced by <code>false</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean array parameter from the portlet request
	 */
	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param) {

		return getBooleanValues(portletRequest, param, new boolean[0]);
	}

	/**
	 * Returns the boolean array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to boolean is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean array parameter from the portlet request
	 */
	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the boolean array parameter from the service context. In the
	 * returned array, each parameter value not convertible to boolean is
	 * replaced by <code>false</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the boolean array parameter from the service context
	 */
	public static boolean[] getBooleanValues(
		ServiceContext serviceContext, String param) {

		return getBooleanValues(serviceContext, param, new boolean[0]);
	}

	/**
	 * Returns the boolean array parameter from the service context. In the
	 * returned array, each parameter value not convertible to boolean is
	 * replaced by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the boolean array parameter from the service context
	 */
	public static boolean[] getBooleanValues(
		ServiceContext serviceContext, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the date parameter from the request. If the parameter is missing
	 * or not convertible to Date, the current date is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date parameter from the request
	 */
	public static Date getDate(
		HttpServletRequest request, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(request.getParameter(param), dateFormat);
	}

	/**
	 * Returns the date parameter from the request. If the parameter is missing
	 * or not convertible to Date, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date parameter from the request
	 */
	public static Date getDate(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(request, param, dateFormat, defaultValue);
	}

	/**
	 * Returns the date parameter from the portlet request. If the parameter is
	 * missing or not convertible to Date, the current date is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date parameter from the portlet request
	 */
	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(
			portletRequest.getParameter(param), dateFormat);
	}

	/**
	 * Returns the date parameter from the portlet request. If the parameter is
	 * missing or not convertible to Date, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date parameter from the portlet request
	 */
	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(portletRequest, param, dateFormat, defaultValue);
	}

	/**
	 * Returns the date parameter from the service context. If the parameter is
	 * missing or not convertible to Date, the current date is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date parameter from the service context
	 */
	public static Date getDate(
		ServiceContext serviceContext, String param, DateFormat dateFormat) {

		return GetterUtil.getDate(
			serviceContext.getAttribute(param), dateFormat);
	}

	/**
	 * Returns the date parameter from the service context. If the parameter is
	 * missing or not convertible to Date, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date parameter from the service context
	 */
	public static Date getDate(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date defaultValue) {

		return get(serviceContext, param, dateFormat, defaultValue);
	}

	/**
	 * Returns the date array parameter from the request. In the returned array,
	 * each parameter value not convertible to Date is replaced by the current
	 * date.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date array parameter from the request
	 */
	public static Date[] getDateValues(
		HttpServletRequest request, String param, DateFormat dateFormat) {

		return getDateValues(request, param, dateFormat, new Date[0]);
	}

	/**
	 * Returns the date array parameter from the request. In the returned array,
	 * each parameter value not convertible to Date is replaced by the default
	 * value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date array parameter from the request
	 */
	public static Date[] getDateValues(
		HttpServletRequest request, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			getParameterValues(request, param, null), dateFormat, defaultValue);
	}

	/**
	 * Returns the date array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to Date is replaced
	 * by the current date.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date array parameter from the portlet request
	 */
	public static Date[] getDateValues(
		PortletRequest portletRequest, String param, DateFormat dateFormat) {

		return getDateValues(portletRequest, param, dateFormat, new Date[0]);
	}

	/**
	 * Returns the date array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to Date is replaced
	 * by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date array parameter from the portlet request
	 */
	public static Date[] getDateValues(
		PortletRequest portletRequest, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			getParameterValues(portletRequest, param, null), dateFormat,
			defaultValue);
	}

	/**
	 * Returns the date array parameter from the service context. In the
	 * returned array, each parameter value not convertible to Date is replaced
	 * by the current date.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @return the date array parameter from the service context
	 */
	public static Date[] getDateValues(
		ServiceContext serviceContext, String param, DateFormat dateFormat) {

		return getDateValues(serviceContext, param, dateFormat, new Date[0]);
	}

	/**
	 * Returns the date array parameter from the service context. In the
	 * returned array, each parameter value not convertible to Date is replaced
	 * by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue the default value
	 * @return the date array parameter from the service context
	 */
	public static Date[] getDateValues(
		ServiceContext serviceContext, String param, DateFormat dateFormat,
		Date[] defaultValue) {

		return GetterUtil.getDateValues(
			serviceContext.getAttribute(param), dateFormat, defaultValue);
	}

	/**
	 * Returns the double parameter from the request. If the parameter is
	 * missing or not convertible to double, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the double parameter from the request
	 */
	public static double getDouble(HttpServletRequest request, String param) {
		return GetterUtil.getDouble(request.getParameter(param));
	}

	/**
	 * Returns the double parameter from the request. If the parameter is
	 * missing or not convertible to double, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the request
	 */
	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the double parameter from the request. If the parameter is
	 * missing or not convertible to double, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @param  locale the locale used to parse the double value
	 * @return the double parameter from the request
	 */
	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue,
		Locale locale) {

		return GetterUtil.get(
			request.getParameter(param), defaultValue, locale);
	}

	/**
	 * Returns the double parameter from the request. If the parameter is
	 * missing or not convertible to double, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  locale the locale used to parse the double value
	 * @return the double parameter from the request
	 */
	public static double getDouble(
		HttpServletRequest request, String param, Locale locale) {

		return GetterUtil.getDouble(request.getParameter(param), locale);
	}

	/**
	 * Returns the double parameter from the portlet request. If the parameter
	 * is missing or not convertible to double, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the double parameter from the portlet request
	 */
	public static double getDouble(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getDouble(portletRequest.getParameter(param));
	}

	/**
	 * Returns the double parameter from the portlet request. If the parameter
	 * is missing or not convertible to double, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the portlet request
	 */
	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the double parameter from the portlet request. If the parameter
	 * is missing or not convertible to double, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @param  locale the locale used to parse the double value
	 * @return the double parameter from the portlet request
	 */
	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue,
		Locale locale) {

		return GetterUtil.get(
			portletRequest.getParameter(param), defaultValue, locale);
	}

	/**
	 * Returns the double parameter from the portlet request. If the parameter
	 * is missing or not convertible to double, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  locale the locale used to parse the double value
	 * @return the double parameter from the portlet request
	 */
	public static double getDouble(
		PortletRequest portletRequest, String param, Locale locale) {

		return GetterUtil.getDouble(portletRequest.getParameter(param), locale);
	}

	/**
	 * Returns the double parameter from the service context. If the parameter
	 * is missing or not convertible to double, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the double parameter from the service context
	 */
	public static double getDouble(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getDouble(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the double parameter from the service context. If the parameter
	 * is missing or not convertible to double, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double parameter from the service context
	 */
	public static double getDouble(
		ServiceContext serviceContext, String param, double defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the double array parameter from the request. In the returned
	 * array, each parameter value not convertible to double is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the double array parameter from the request
	 */
	public static double[] getDoubleValues(
		HttpServletRequest request, String param) {

		return getDoubleValues(request, param, new double[0]);
	}

	/**
	 * Returns the double array parameter from the request. In the returned
	 * array, each parameter value not convertible to double is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double array parameter from the request
	 */
	public static double[] getDoubleValues(
		HttpServletRequest request, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the double array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to double is
	 * replaced by <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the double array parameter from the portlet request
	 */
	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param) {

		return getDoubleValues(portletRequest, param, new double[0]);
	}

	/**
	 * Returns the double array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to double is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double array parameter from the portlet request
	 */
	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the double array parameter from the service context. In the
	 * returned array, each parameter value not convertible to double is
	 * replaced by <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the double array parameter from the service context
	 */
	public static double[] getDoubleValues(
		ServiceContext serviceContext, String param) {

		return getDoubleValues(serviceContext, param, new double[0]);
	}

	/**
	 * Returns the double array parameter from the service context. In the
	 * returned array, each parameter value not convertible to double is
	 * replaced by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the double array parameter from the service context
	 */
	public static double[] getDoubleValues(
		ServiceContext serviceContext, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the float parameter from the request. If the parameter is missing
	 * or not convertible to float, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the float parameter from the request
	 */
	public static float getFloat(HttpServletRequest request, String param) {

		return GetterUtil.getFloat(request.getParameter(param));
	}

	/**
	 * Returns the float parameter from the request. If the parameter is missing
	 * or not convertible to float, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the request
	 */
	public static float getFloat(
		HttpServletRequest request, String param, float defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the float parameter from the portlet request. If the parameter is
	 * missing or not convertible to float, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the float parameter from the portlet request
	 */
	public static float getFloat(PortletRequest portletRequest, String param) {
		return GetterUtil.getFloat(portletRequest.getParameter(param));
	}

	/**
	 * Returns the float parameter from the portlet request. If the parameter is
	 * missing or not convertible to float, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the portlet request
	 */
	public static float getFloat(
		PortletRequest portletRequest, String param, float defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the float parameter from the service context. If the parameter is
	 * missing or not convertible to float, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the float parameter from the service context
	 */
	public static float getFloat(ServiceContext serviceContext, String param) {
		return GetterUtil.getFloat(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the float parameter from the service context. If the parameter is
	 * missing or not convertible to float, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float parameter from the service context
	 */
	public static float getFloat(
		ServiceContext serviceContext, String param, float defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the float array parameter from the request. In the returned
	 * array, each parameter value not convertible to float is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the float array parameter from the request
	 */
	public static float[] getFloatValues(
		HttpServletRequest request, String param) {

		return getFloatValues(request, param, new float[0]);
	}

	/**
	 * Returns the float array parameter from the request. In the returned
	 * array, each parameter value not convertible to float is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float array parameter from the request
	 */
	public static float[] getFloatValues(
		HttpServletRequest request, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the float array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to float is replaced
	 * by <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the float array parameter from the portlet request
	 */
	public static float[] getFloatValues(
		PortletRequest portletRequest, String param) {

		return getFloatValues(portletRequest, param, new float[0]);
	}

	/**
	 * Returns the float array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to float is replaced
	 * by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float array parameter from the portlet request
	 */
	public static float[] getFloatValues(
		PortletRequest portletRequest, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the float array parameter from the service context. In the
	 * returned array, each parameter value not convertible to float is replaced
	 * by <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the float array parameter from the service context
	 */
	public static float[] getFloatValues(
		ServiceContext serviceContext, String param) {

		return getFloatValues(serviceContext, param, new float[0]);
	}

	/**
	 * Returns the float array parameter from the service context. In the
	 * returned array, each parameter value not convertible to float is replaced
	 * by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the float array parameter from the service context
	 */
	public static float[] getFloatValues(
		ServiceContext serviceContext, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the int parameter from the request. If the parameter is missing
	 * or not convertible to int, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the int parameter from the request
	 */
	public static int getInteger(HttpServletRequest request, String param) {
		return GetterUtil.getInteger(request.getParameter(param));
	}

	/**
	 * Returns the int parameter from the request. If the parameter is missing
	 * or not convertible to int, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the request
	 */
	public static int getInteger(
		HttpServletRequest request, String param, int defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the int parameter from the portlet request. If the parameter is
	 * missing or not convertible to int, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the int parameter from the portlet request
	 */
	public static int getInteger(PortletRequest portletRequest, String param) {
		return GetterUtil.getInteger(portletRequest.getParameter(param));
	}

	/**
	 * Returns the int parameter from the portlet request. If the parameter is
	 * missing or not convertible to int, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the portlet request
	 */
	public static int getInteger(
		PortletRequest portletRequest, String param, int defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the int parameter from the service context. If the parameter is
	 * missing or not convertible to int, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the int parameter from the service context
	 */
	public static int getInteger(ServiceContext serviceContext, String param) {
		return GetterUtil.getInteger(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the int parameter from the service context. If the parameter is
	 * missing or not convertible to int, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int parameter from the service context
	 */
	public static int getInteger(
		ServiceContext serviceContext, String param, int defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the int array parameter from the request. In the returned array,
	 * each parameter value not convertible to int is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the int array parameter from the request
	 */
	public static int[] getIntegerValues(
		HttpServletRequest request, String param) {

		return getIntegerValues(request, param, new int[0]);
	}

	/**
	 * Returns the int array parameter from the request. In the returned array,
	 * each parameter value not convertible to int is replaced by the default
	 * value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int array parameter from the request
	 */
	public static int[] getIntegerValues(
		HttpServletRequest request, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the int array parameter from the portlet request. In the returned
	 * array, each parameter value not convertible to int is replaced by
	 * <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the int array parameter from the portlet request
	 */
	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param) {

		return getIntegerValues(portletRequest, param, new int[0]);
	}

	/**
	 * Returns the int array parameter from the portlet request. In the returned
	 * array, each parameter value not convertible to int is replaced by the
	 * default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int array parameter from the portlet request
	 */
	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the int array parameter from the service context. In the returned
	 * array, each parameter value not convertible to int is replaced by
	 * <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the int array parameter from the service context
	 */
	public static int[] getIntegerValues(
		ServiceContext serviceContext, String param) {

		return getIntegerValues(serviceContext, param, new int[0]);
	}

	/**
	 * Returns the int array parameter from the service context. In the returned
	 * array, each parameter value not convertible to int is replaced by the
	 * default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the int array parameter from the service context
	 */
	public static int[] getIntegerValues(
		ServiceContext serviceContext, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the long parameter from the request. If the parameter is missing
	 * or not convertible to long, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the long parameter from the request
	 */
	public static long getLong(HttpServletRequest request, String param) {
		return GetterUtil.getLong(request.getParameter(param));
	}

	/**
	 * Returns the long parameter from the request. If the parameter is missing
	 * or not convertible to long, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the request
	 */
	public static long getLong(
		HttpServletRequest request, String param, long defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the long parameter from the portlet request. If the parameter is
	 * missing or not convertible to long, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the long parameter from the portlet request
	 */
	public static long getLong(PortletRequest portletRequest, String param) {
		return GetterUtil.getLong(portletRequest.getParameter(param));
	}

	/**
	 * Returns the long parameter from the portlet request. If the parameter is
	 * missing or not convertible to long, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the portlet request
	 */
	public static long getLong(
		PortletRequest portletRequest, String param, long defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the long parameter from the service context. If the parameter is
	 * missing or not convertible to long, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the long parameter from the service context
	 */
	public static long getLong(ServiceContext serviceContext, String param) {
		return GetterUtil.getLong(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the long parameter from the service context. If the parameter is
	 * missing or not convertible to long, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long parameter from the service context
	 */
	public static long getLong(
		ServiceContext serviceContext, String param, long defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the long array parameter from the request. In the returned array,
	 * each parameter value not convertible to long is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the long array parameter from the request
	 */
	public static long[] getLongValues(
		HttpServletRequest request, String param) {

		return getLongValues(request, param, new long[0]);
	}

	/**
	 * Returns the long array parameter from the request. In the returned array,
	 * each parameter value not convertible to long is replaced by the default
	 * value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long array parameter from the request
	 */
	public static long[] getLongValues(
		HttpServletRequest request, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the long array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to long is replaced
	 * by <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the long array parameter from the portlet request
	 */
	public static long[] getLongValues(
		PortletRequest portletRequest, String param) {

		return getLongValues(portletRequest, param, new long[0]);
	}

	/**
	 * Returns the long array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to long is replaced
	 * by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long array parameter from the portlet request
	 */
	public static long[] getLongValues(
		PortletRequest portletRequest, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the long array parameter from the service context. In the
	 * returned array, each parameter value not convertible to long is replaced
	 * by <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the long array parameter from the service context
	 */
	public static long[] getLongValues(
		ServiceContext serviceContext, String param) {

		return getLongValues(serviceContext, param, new long[0]);
	}

	/**
	 * Returns the long array parameter from the service context. In the
	 * returned array, each parameter value not convertible to long is replaced
	 * by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the long array parameter from the service context
	 */
	public static long[] getLongValues(
		ServiceContext serviceContext, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the number parameter from the request. If the parameter is
	 * missing or not convertible to Number, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the number parameter from the request
	 */
	public static Number getNumber(HttpServletRequest request, String param) {
		return GetterUtil.getNumber(request.getParameter(param));
	}

	/**
	 * Returns the number parameter from the request. If the parameter is
	 * missing or not convertible to Number, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the request
	 */
	public static Number getNumber(
		HttpServletRequest request, String param, Number defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the number parameter from the portlet request. If the parameter
	 * is missing or not convertible to Number, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the number parameter from the portlet request
	 */
	public static Number getNumber(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getNumber(portletRequest.getParameter(param));
	}

	/**
	 * Returns the number parameter from the portlet request. If the parameter
	 * is missing or not convertible to Number, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the portlet request
	 */
	public static Number getNumber(
		PortletRequest portletRequest, String param, Number defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the number parameter from the service context. If the parameter
	 * is missing or not convertible to Number, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the number parameter from the service context
	 */
	public static Number getNumber(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getNumber(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the number parameter from the service context. If the parameter
	 * is missing or not convertible to Number, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number parameter from the service context
	 */
	public static Number getNumber(
		ServiceContext serviceContext, String param, Number defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the number array parameter from the request. In the returned
	 * array, each parameter value not convertible to Number is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the number array parameter from the request
	 */
	public static Number[] getNumberValues(
		HttpServletRequest request, String param) {

		return getNumberValues(request, param, new Number[0]);
	}

	/**
	 * Returns the number array parameter from the request. In the returned
	 * array, each parameter value not convertible to Number is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number array parameter from the request
	 */
	public static Number[] getNumberValues(
		HttpServletRequest request, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the number array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to Number is
	 * replaced by <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the number array parameter from the portlet request
	 */
	public static Number[] getNumberValues(
		PortletRequest portletRequest, String param) {

		return getNumberValues(portletRequest, param, new Number[0]);
	}

	/**
	 * Returns the number array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to Number is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number array parameter from the portlet request
	 */
	public static Number[] getNumberValues(
		PortletRequest portletRequest, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the number array parameter from the service context. In the
	 * returned array, each parameter value not convertible to Number is
	 * replaced by <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the number array parameter from the service context
	 */
	public static Number[] getNumberValues(
		ServiceContext serviceContext, String param) {

		return getNumberValues(serviceContext, param, new Number[0]);
	}

	/**
	 * Returns the number array parameter from the service context. In the
	 * returned array, each parameter value not convertible to Number is
	 * replaced by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the number array parameter from the service context
	 */
	public static Number[] getNumberValues(
		ServiceContext serviceContext, String param, Number[] defaultValue) {

		return GetterUtil.getNumberValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the string array parameter from the request. In the returned
	 * array, each parameter value not convertible to String is replaced by a
	 * blank string.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the string array parameter from the request
	 */
	public static String[] getParameterValues(
		HttpServletRequest request, String param) {

		return getParameterValues(request, param, new String[0]);
	}

	/**
	 * Returns the string array parameter from the request. In the returned
	 * array, each parameter value not convertible to String is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string array parameter from the request
	 */
	public static String[] getParameterValues(
		HttpServletRequest request, String param, String[] defaultValue) {

		return getParameterValues(request, param, defaultValue, true);
	}

	/**
	 * Returns the string array parameter from the request. In the returned
	 * array, each parameter value not convertible to String is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @param  split whether to split the single parameter value using comma
	 *         separators to get multiple values
	 * @return the string array parameter from the request
	 */
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

	/**
	 * Returns the string array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by a blank string.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the string array parameter from the portlet request
	 */
	public static String[] getParameterValues(
		PortletRequest portletRequest, String param) {

		return getParameterValues(portletRequest, param, new String[0]);
	}

	/**
	 * Returns the string array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string array parameter from the portlet request
	 */
	public static String[] getParameterValues(
		PortletRequest portletRequest, String param, String[] defaultValue) {

		return getParameterValues(portletRequest, param, defaultValue, true);
	}

	/**
	 * Returns the string array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @param  split whether to split the single parameter value using comma
	 *         separators to get multiple values
	 * @return the string array parameter from the portlet request
	 */
	public static String[] getParameterValues(
		PortletRequest portletRequest, String param, String[] defaultValue,
		boolean split) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getParameterValues(request, param, defaultValue, split);
	}

	/**
	 * Returns the short parameter from the request. If the parameter is missing
	 * or not convertible to short, <code>0</code> is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the short parameter from the request
	 */
	public static short getShort(HttpServletRequest request, String param) {
		return GetterUtil.getShort(request.getParameter(param));
	}

	/**
	 * Returns the short parameter from the request. If the parameter is missing
	 * or not convertible to short, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the request
	 */
	public static short getShort(
		HttpServletRequest request, String param, short defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the short parameter from the portlet request. If the parameter is
	 * missing or not convertible to short, <code>0</code> is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the short parameter from the portlet request
	 */
	public static short getShort(PortletRequest portletRequest, String param) {
		return GetterUtil.getShort(portletRequest.getParameter(param));
	}

	/**
	 * Returns the short parameter from the portlet request. If the parameter is
	 * missing or not convertible to short, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the portlet request
	 */
	public static short getShort(
		PortletRequest portletRequest, String param, short defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the short parameter from the service context. If the parameter is
	 * missing or not convertible to short, <code>0</code> is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the short parameter from the service context
	 */
	public static short getShort(ServiceContext serviceContext, String param) {
		return GetterUtil.getShort(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the short parameter from the service context. If the parameter is
	 * missing or not convertible to short, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short parameter from the service context
	 */
	public static short getShort(
		ServiceContext serviceContext, String param, short defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the short array parameter from the request. In the returned
	 * array, each parameter value not convertible to short is replaced by
	 * <code>0</code>.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the short array parameter from the request
	 */
	public static short[] getShortValues(
		HttpServletRequest request, String param) {

		return getShortValues(request, param, new short[0]);
	}

	/**
	 * Returns the short array parameter from the request. In the returned
	 * array, each parameter value not convertible to short is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short array parameter from the request
	 */
	public static short[] getShortValues(
		HttpServletRequest request, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the short array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to short is replaced
	 * by <code>0</code>.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the short array parameter from the portlet request
	 */
	public static short[] getShortValues(
		PortletRequest portletRequest, String param) {

		return getShortValues(portletRequest, param, new short[0]);
	}

	/**
	 * Returns the short array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to short is replaced
	 * by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short array parameter from the portlet request
	 */
	public static short[] getShortValues(
		PortletRequest portletRequest, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the short array parameter from the service context. In the
	 * returned array, each parameter value not convertible to short is replaced
	 * by <code>0</code>.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the short array parameter from the service context
	 */
	public static short[] getShortValues(
		ServiceContext serviceContext, String param) {

		return getShortValues(serviceContext, param, new short[0]);
	}

	/**
	 * Returns the short array parameter from the service context. In the
	 * returned array, each parameter value not convertible to short is replaced
	 * by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the short array parameter from the service context
	 */
	public static short[] getShortValues(
		ServiceContext serviceContext, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Returns the string parameter from the request. If the parameter is
	 * missing or not convertible to String, a blank string is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the string parameter from the request
	 */
	public static String getString(HttpServletRequest request, String param) {

		return GetterUtil.getString(request.getParameter(param));
	}

	/**
	 * Returns the string parameter from the request. If the parameter is
	 * missing or not convertible to String, the default value is returned.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the request
	 */
	public static String getString(
		HttpServletRequest request, String param, String defaultValue) {

		return get(request, param, defaultValue);
	}

	/**
	 * Returns the string parameter from the portlet request. If the parameter
	 * is missing or not convertible to String, a blank string is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the string parameter from the portlet request
	 */
	public static String getString(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getString(portletRequest.getParameter(param));
	}

	/**
	 * Returns the string parameter from the portlet request. If the parameter
	 * is missing or not convertible to String, the default value is returned.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the portlet request
	 */
	public static String getString(
		PortletRequest portletRequest, String param, String defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	/**
	 * Returns the string parameter from the service context. If the parameter
	 * is missing or not convertible to String, a blank string is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the string parameter from the service context
	 */
	public static String getString(
		ServiceContext serviceContext, String param) {

		return GetterUtil.getString(serviceContext.getAttribute(param));
	}

	/**
	 * Returns the string parameter from the service context. If the parameter
	 * is missing or not convertible to String, the default value is returned.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string parameter from the service context
	 */
	public static String getString(
		ServiceContext serviceContext, String param, String defaultValue) {

		return get(serviceContext, param, defaultValue);
	}

	/**
	 * Returns the string array parameter from the request. In the returned
	 * array, each parameter value not convertible to String is replaced by a
	 * blank string.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @return the string array parameter from the request
	 */
	public static String[] getStringValues(
		HttpServletRequest request, String param) {

		return getStringValues(request, param, new String[0]);
	}

	/**
	 * Returns the string array parameter from the request. In the returned
	 * array, each parameter value not convertible to String is replaced by the
	 * default value.
	 *
	 * @param  request the servlet request from which to read the parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string array parameter from the request
	 */
	public static String[] getStringValues(
		HttpServletRequest request, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			getParameterValues(request, param, null), defaultValue);
	}

	/**
	 * Returns the string array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by a blank string.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the string array parameter from the portlet request
	 */
	public static String[] getStringValues(
		PortletRequest portletRequest, String param) {

		return getStringValues(portletRequest, param, new String[0]);
	}

	/**
	 * Returns the string array parameter from the portlet request. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by the default value.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string array parameter from the portlet request
	 */
	public static String[] getStringValues(
		PortletRequest portletRequest, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			getParameterValues(portletRequest, param, null), defaultValue);
	}

	/**
	 * Returns the string array parameter from the service context. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by a blank string.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @return the string array parameter from the service context
	 */
	public static String[] getStringValues(
		ServiceContext serviceContext, String param) {

		return getStringValues(serviceContext, param, new String[0]);
	}

	/**
	 * Returns the string array parameter from the service context. In the
	 * returned array, each parameter value not convertible to String is
	 * replaced by the default value.
	 *
	 * @param  serviceContext the service context from which to read the
	 *         parameter
	 * @param  param the name of the parameter to return
	 * @param  defaultValue the default value
	 * @return the string array parameter from the service context
	 */
	public static String[] getStringValues(
		ServiceContext serviceContext, String param, String[] defaultValue) {

		return GetterUtil.getStringValues(
			serviceContext.getAttribute(param), defaultValue);
	}

	/**
	 * Prints all request parameters as standard output.
	 *
	 * @param request the servlet request from which to read the parameters
	 */
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

	/**
	 * Prints all portlet request parameters as standard output.
	 *
	 * @param portletRequest the portlet request from which to read the
	 *        parameters
	 */
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

	/**
	 * Prints all service context parameters as standard output.
	 *
	 * @param serviceContext the service context from which to read the
	 *        parameters
	 */
	public static void print(ServiceContext serviceContext) {
		Map<String, Serializable> attributes = serviceContext.getAttributes();

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			System.out.println(
				entry.getKey() + " = " + String.valueOf(entry.getValue()));
		}
	}

}