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

/**
 * @author Brian Wing Shun Chan
 */
public class ParamUtil_IW {
	public static ParamUtil_IW getInstance() {
		return _instance;
	}

	public boolean get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public java.util.Date get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date defaultValue) {
		return ParamUtil.get(request, param, dateFormat, defaultValue);
	}

	public double get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, double defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public float get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, float defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public int get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, int defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public long get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, long defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public java.lang.Number get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, java.lang.Number defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public short get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, short defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public java.lang.String get(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, java.lang.String defaultValue) {
		return ParamUtil.get(request, param, defaultValue);
	}

	public boolean get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public java.util.Date get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date defaultValue) {
		return ParamUtil.get(portletRequest, param, dateFormat, defaultValue);
	}

	public double get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, double defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public float get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, float defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public int get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, int defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public long get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, long defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public java.lang.Number get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.lang.Number defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public short get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, short defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public java.lang.String get(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.lang.String defaultValue) {
		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public boolean get(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public java.util.Date get(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date defaultValue) {
		return ParamUtil.get(serviceContext, param, dateFormat, defaultValue);
	}

	public double get(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, double defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public float get(com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, float defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public int get(com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, int defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public long get(com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, long defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public java.lang.Number get(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.Number defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public short get(com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, short defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public java.lang.String get(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.String defaultValue) {
		return ParamUtil.get(serviceContext, param, defaultValue);
	}

	public boolean getBoolean(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getBoolean(request, param);
	}

	public boolean getBoolean(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.getBoolean(request, param, defaultValue);
	}

	public boolean getBoolean(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getBoolean(portletRequest, param);
	}

	public boolean getBoolean(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.getBoolean(portletRequest, param, defaultValue);
	}

	public boolean getBoolean(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getBoolean(serviceContext, param);
	}

	public boolean getBoolean(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, boolean defaultValue) {
		return ParamUtil.getBoolean(serviceContext, param, defaultValue);
	}

	public boolean[] getBooleanValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getBooleanValues(request, param);
	}

	public boolean[] getBooleanValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		boolean[] defaultValue) {
		return ParamUtil.getBooleanValues(request, param, defaultValue);
	}

	public boolean[] getBooleanValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getBooleanValues(portletRequest, param);
	}

	public boolean[] getBooleanValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		boolean[] defaultValue) {
		return ParamUtil.getBooleanValues(portletRequest, param, defaultValue);
	}

	public boolean[] getBooleanValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getBooleanValues(serviceContext, param);
	}

	public boolean[] getBooleanValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, boolean[] defaultValue) {
		return ParamUtil.getBooleanValues(serviceContext, param, defaultValue);
	}

	public java.util.Date getDate(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.text.DateFormat dateFormat) {
		return ParamUtil.getDate(request, param, dateFormat);
	}

	public java.util.Date getDate(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.text.DateFormat dateFormat, java.util.Date defaultValue) {
		return ParamUtil.getDate(request, param, dateFormat, defaultValue);
	}

	public java.util.Date getDate(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.text.DateFormat dateFormat) {
		return ParamUtil.getDate(portletRequest, param, dateFormat);
	}

	public java.util.Date getDate(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date defaultValue) {
		return ParamUtil.getDate(portletRequest, param, dateFormat, defaultValue);
	}

	public java.util.Date getDate(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.text.DateFormat dateFormat) {
		return ParamUtil.getDate(serviceContext, param, dateFormat);
	}

	public java.util.Date getDate(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date defaultValue) {
		return ParamUtil.getDate(serviceContext, param, dateFormat, defaultValue);
	}

	public java.util.Date[] getDateValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.text.DateFormat dateFormat) {
		return ParamUtil.getDateValues(request, param, dateFormat);
	}

	public java.util.Date[] getDateValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.text.DateFormat dateFormat, java.util.Date[] defaultValue) {
		return ParamUtil.getDateValues(request, param, dateFormat, defaultValue);
	}

	public java.util.Date[] getDateValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.text.DateFormat dateFormat) {
		return ParamUtil.getDateValues(portletRequest, param, dateFormat);
	}

	public java.util.Date[] getDateValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.text.DateFormat dateFormat, java.util.Date[] defaultValue) {
		return ParamUtil.getDateValues(portletRequest, param, dateFormat,
			defaultValue);
	}

	public java.util.Date[] getDateValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.text.DateFormat dateFormat) {
		return ParamUtil.getDateValues(serviceContext, param, dateFormat);
	}

	public java.util.Date[] getDateValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.text.DateFormat dateFormat,
		java.util.Date[] defaultValue) {
		return ParamUtil.getDateValues(serviceContext, param, dateFormat,
			defaultValue);
	}

	public double getDouble(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getDouble(request, param);
	}

	public double getDouble(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, double defaultValue) {
		return ParamUtil.getDouble(request, param, defaultValue);
	}

	public double getDouble(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, double defaultValue, java.util.Locale locale) {
		return ParamUtil.getDouble(request, param, defaultValue, locale);
	}

	public double getDouble(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, java.util.Locale locale) {
		return ParamUtil.getDouble(request, param, locale);
	}

	public double getDouble(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getDouble(portletRequest, param);
	}

	public double getDouble(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, double defaultValue) {
		return ParamUtil.getDouble(portletRequest, param, defaultValue);
	}

	public double getDouble(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, double defaultValue, java.util.Locale locale) {
		return ParamUtil.getDouble(portletRequest, param, defaultValue, locale);
	}

	public double getDouble(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, java.util.Locale locale) {
		return ParamUtil.getDouble(portletRequest, param, locale);
	}

	public double getDouble(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getDouble(serviceContext, param);
	}

	public double getDouble(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, double defaultValue) {
		return ParamUtil.getDouble(serviceContext, param, defaultValue);
	}

	public double[] getDoubleValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getDoubleValues(request, param);
	}

	public double[] getDoubleValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		double[] defaultValue) {
		return ParamUtil.getDoubleValues(request, param, defaultValue);
	}

	public double[] getDoubleValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getDoubleValues(portletRequest, param);
	}

	public double[] getDoubleValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		double[] defaultValue) {
		return ParamUtil.getDoubleValues(portletRequest, param, defaultValue);
	}

	public double[] getDoubleValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getDoubleValues(serviceContext, param);
	}

	public double[] getDoubleValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, double[] defaultValue) {
		return ParamUtil.getDoubleValues(serviceContext, param, defaultValue);
	}

	public float getFloat(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getFloat(request, param);
	}

	public float getFloat(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, float defaultValue) {
		return ParamUtil.getFloat(request, param, defaultValue);
	}

	public float getFloat(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getFloat(portletRequest, param);
	}

	public float getFloat(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, float defaultValue) {
		return ParamUtil.getFloat(portletRequest, param, defaultValue);
	}

	public float getFloat(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getFloat(serviceContext, param);
	}

	public float getFloat(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, float defaultValue) {
		return ParamUtil.getFloat(serviceContext, param, defaultValue);
	}

	public float[] getFloatValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getFloatValues(request, param);
	}

	public float[] getFloatValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		float[] defaultValue) {
		return ParamUtil.getFloatValues(request, param, defaultValue);
	}

	public float[] getFloatValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getFloatValues(portletRequest, param);
	}

	public float[] getFloatValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, float[] defaultValue) {
		return ParamUtil.getFloatValues(portletRequest, param, defaultValue);
	}

	public float[] getFloatValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getFloatValues(serviceContext, param);
	}

	public float[] getFloatValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, float[] defaultValue) {
		return ParamUtil.getFloatValues(serviceContext, param, defaultValue);
	}

	public int getInteger(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getInteger(request, param);
	}

	public int getInteger(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, int defaultValue) {
		return ParamUtil.getInteger(request, param, defaultValue);
	}

	public int getInteger(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getInteger(portletRequest, param);
	}

	public int getInteger(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, int defaultValue) {
		return ParamUtil.getInteger(portletRequest, param, defaultValue);
	}

	public int getInteger(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getInteger(serviceContext, param);
	}

	public int getInteger(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, int defaultValue) {
		return ParamUtil.getInteger(serviceContext, param, defaultValue);
	}

	public int[] getIntegerValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getIntegerValues(request, param);
	}

	public int[] getIntegerValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		int[] defaultValue) {
		return ParamUtil.getIntegerValues(request, param, defaultValue);
	}

	public int[] getIntegerValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getIntegerValues(portletRequest, param);
	}

	public int[] getIntegerValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, int[] defaultValue) {
		return ParamUtil.getIntegerValues(portletRequest, param, defaultValue);
	}

	public int[] getIntegerValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getIntegerValues(serviceContext, param);
	}

	public int[] getIntegerValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, int[] defaultValue) {
		return ParamUtil.getIntegerValues(serviceContext, param, defaultValue);
	}

	public long getLong(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getLong(request, param);
	}

	public long getLong(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, long defaultValue) {
		return ParamUtil.getLong(request, param, defaultValue);
	}

	public long getLong(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getLong(portletRequest, param);
	}

	public long getLong(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, long defaultValue) {
		return ParamUtil.getLong(portletRequest, param, defaultValue);
	}

	public long getLong(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getLong(serviceContext, param);
	}

	public long getLong(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, long defaultValue) {
		return ParamUtil.getLong(serviceContext, param, defaultValue);
	}

	public long[] getLongValues(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getLongValues(request, param);
	}

	public long[] getLongValues(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, long[] defaultValue) {
		return ParamUtil.getLongValues(request, param, defaultValue);
	}

	public long[] getLongValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getLongValues(portletRequest, param);
	}

	public long[] getLongValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, long[] defaultValue) {
		return ParamUtil.getLongValues(portletRequest, param, defaultValue);
	}

	public long[] getLongValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getLongValues(serviceContext, param);
	}

	public long[] getLongValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, long[] defaultValue) {
		return ParamUtil.getLongValues(serviceContext, param, defaultValue);
	}

	public java.lang.Number getNumber(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getNumber(request, param);
	}

	public java.lang.Number getNumber(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.Number defaultValue) {
		return ParamUtil.getNumber(request, param, defaultValue);
	}

	public java.lang.Number getNumber(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getNumber(portletRequest, param);
	}

	public java.lang.Number getNumber(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.Number defaultValue) {
		return ParamUtil.getNumber(portletRequest, param, defaultValue);
	}

	public java.lang.Number getNumber(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getNumber(serviceContext, param);
	}

	public java.lang.Number getNumber(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.Number defaultValue) {
		return ParamUtil.getNumber(serviceContext, param, defaultValue);
	}

	public java.lang.Number[] getNumberValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getNumberValues(request, param);
	}

	public java.lang.Number[] getNumberValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.Number[] defaultValue) {
		return ParamUtil.getNumberValues(request, param, defaultValue);
	}

	public java.lang.Number[] getNumberValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getNumberValues(portletRequest, param);
	}

	public java.lang.Number[] getNumberValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.Number[] defaultValue) {
		return ParamUtil.getNumberValues(portletRequest, param, defaultValue);
	}

	public java.lang.Number[] getNumberValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getNumberValues(serviceContext, param);
	}

	public java.lang.Number[] getNumberValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.Number[] defaultValue) {
		return ParamUtil.getNumberValues(serviceContext, param, defaultValue);
	}

	public java.lang.String[] getParameterValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getParameterValues(request, param);
	}

	public java.lang.String[] getParameterValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.String[] defaultValue) {
		return ParamUtil.getParameterValues(request, param, defaultValue);
	}

	public java.lang.String[] getParameterValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.String[] defaultValue, boolean split) {
		return ParamUtil.getParameterValues(request, param, defaultValue, split);
	}

	public java.lang.String[] getParameterValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getParameterValues(portletRequest, param);
	}

	public java.lang.String[] getParameterValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.String[] defaultValue) {
		return ParamUtil.getParameterValues(portletRequest, param, defaultValue);
	}

	public java.lang.String[] getParameterValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.String[] defaultValue, boolean split) {
		return ParamUtil.getParameterValues(portletRequest, param,
			defaultValue, split);
	}

	public short getShort(javax.servlet.http.HttpServletRequest request,
		java.lang.String param) {
		return ParamUtil.getShort(request, param);
	}

	public short getShort(javax.servlet.http.HttpServletRequest request,
		java.lang.String param, short defaultValue) {
		return ParamUtil.getShort(request, param, defaultValue);
	}

	public short getShort(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getShort(portletRequest, param);
	}

	public short getShort(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, short defaultValue) {
		return ParamUtil.getShort(portletRequest, param, defaultValue);
	}

	public short getShort(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getShort(serviceContext, param);
	}

	public short getShort(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, short defaultValue) {
		return ParamUtil.getShort(serviceContext, param, defaultValue);
	}

	public short[] getShortValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getShortValues(request, param);
	}

	public short[] getShortValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		short[] defaultValue) {
		return ParamUtil.getShortValues(request, param, defaultValue);
	}

	public short[] getShortValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param) {
		return ParamUtil.getShortValues(portletRequest, param);
	}

	public short[] getShortValues(javax.portlet.PortletRequest portletRequest,
		java.lang.String param, short[] defaultValue) {
		return ParamUtil.getShortValues(portletRequest, param, defaultValue);
	}

	public short[] getShortValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getShortValues(serviceContext, param);
	}

	public short[] getShortValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, short[] defaultValue) {
		return ParamUtil.getShortValues(serviceContext, param, defaultValue);
	}

	public java.lang.String getString(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getString(request, param);
	}

	public java.lang.String getString(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.String defaultValue) {
		return ParamUtil.getString(request, param, defaultValue);
	}

	public java.lang.String getString(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getString(portletRequest, param);
	}

	public java.lang.String getString(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.String defaultValue) {
		return ParamUtil.getString(portletRequest, param, defaultValue);
	}

	public java.lang.String getString(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getString(serviceContext, param);
	}

	public java.lang.String getString(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.String defaultValue) {
		return ParamUtil.getString(serviceContext, param, defaultValue);
	}

	public java.lang.String[] getStringValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param) {
		return ParamUtil.getStringValues(request, param);
	}

	public java.lang.String[] getStringValues(
		javax.servlet.http.HttpServletRequest request, java.lang.String param,
		java.lang.String[] defaultValue) {
		return ParamUtil.getStringValues(request, param, defaultValue);
	}

	public java.lang.String[] getStringValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param) {
		return ParamUtil.getStringValues(portletRequest, param);
	}

	public java.lang.String[] getStringValues(
		javax.portlet.PortletRequest portletRequest, java.lang.String param,
		java.lang.String[] defaultValue) {
		return ParamUtil.getStringValues(portletRequest, param, defaultValue);
	}

	public java.lang.String[] getStringValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param) {
		return ParamUtil.getStringValues(serviceContext, param);
	}

	public java.lang.String[] getStringValues(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.lang.String param, java.lang.String[] defaultValue) {
		return ParamUtil.getStringValues(serviceContext, param, defaultValue);
	}

	public void print(javax.servlet.http.HttpServletRequest request) {
		ParamUtil.print(request);
	}

	public void print(javax.portlet.PortletRequest portletRequest) {
		ParamUtil.print(portletRequest);
	}

	public void print(com.liferay.portal.service.ServiceContext serviceContext) {
		ParamUtil.print(serviceContext);
	}

	private ParamUtil_IW() {
	}

	private static ParamUtil_IW _instance = new ParamUtil_IW();
}