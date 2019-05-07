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

package com.liferay.portal.kernel.bean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class BeanPropertiesUtil {

	public static void copyProperties(Object source, Object target) {
		getBeanProperties().copyProperties(source, target);
	}

	public static void copyProperties(
		Object source, Object target, Class<?> editable) {

		getBeanProperties().copyProperties(source, target, editable);
	}

	public static void copyProperties(
		Object source, Object target, String[] ignoreProperties) {

		getBeanProperties().copyProperties(source, target, ignoreProperties);
	}

	public static <T> T deepCopyProperties(Object source) throws Exception {
		return getBeanProperties().deepCopyProperties(source);
	}

	public static BeanProperties getBeanProperties() {
		return _beanProperties;
	}

	public static boolean getBoolean(Object bean, String param) {
		return getBeanProperties().getBoolean(bean, param);
	}

	public static boolean getBoolean(
		Object bean, String param, boolean defaultValue) {

		return getBeanProperties().getBoolean(bean, param, defaultValue);
	}

	public static boolean getBooleanSilent(Object bean, String param) {
		return getBeanProperties().getBooleanSilent(bean, param);
	}

	public static boolean getBooleanSilent(
		Object bean, String param, boolean defaultValue) {

		return getBeanProperties().getBooleanSilent(bean, param, defaultValue);
	}

	public static byte getByte(Object bean, String param) {
		return getBeanProperties().getByte(bean, param);
	}

	public static byte getByte(Object bean, String param, byte defaultValue) {
		return getBeanProperties().getByte(bean, param, defaultValue);
	}

	public static byte getByteSilent(Object bean, String param) {
		return getBeanProperties().getByteSilent(bean, param);
	}

	public static byte getByteSilent(
		Object bean, String param, byte defaultValue) {

		return getBeanProperties().getByteSilent(bean, param, defaultValue);
	}

	public static double getDouble(Object bean, String param) {
		return getBeanProperties().getDouble(bean, param);
	}

	public static double getDouble(
		Object bean, String param, double defaultValue) {

		return getBeanProperties().getDouble(bean, param, defaultValue);
	}

	public static double getDoubleSilent(Object bean, String param) {
		return getBeanProperties().getDoubleSilent(bean, param);
	}

	public static double getDoubleSilent(
		Object bean, String param, double defaultValue) {

		return getBeanProperties().getDoubleSilent(bean, param, defaultValue);
	}

	public static float getFloat(Object bean, String param) {
		return getBeanProperties().getFloat(bean, param);
	}

	public static float getFloat(
		Object bean, String param, float defaultValue) {

		return getBeanProperties().getFloat(bean, param, defaultValue);
	}

	public static float getFloatSilent(Object bean, String param) {
		return getBeanProperties().getFloatSilent(bean, param);
	}

	public static float getFloatSilent(
		Object bean, String param, float defaultValue) {

		return getBeanProperties().getFloatSilent(bean, param, defaultValue);
	}

	public static int getInteger(Object bean, String param) {
		return getBeanProperties().getInteger(bean, param);
	}

	public static int getInteger(Object bean, String param, int defaultValue) {
		return getBeanProperties().getInteger(bean, param, defaultValue);
	}

	public static int getIntegerSilent(Object bean, String param) {
		return getBeanProperties().getIntegerSilent(bean, param);
	}

	public static int getIntegerSilent(
		Object bean, String param, int defaultValue) {

		return getBeanProperties().getIntegerSilent(bean, param, defaultValue);
	}

	public static long getLong(Object bean, String param) {
		return getBeanProperties().getLong(bean, param);
	}

	public static long getLong(Object bean, String param, long defaultValue) {
		return getBeanProperties().getLong(bean, param, defaultValue);
	}

	public static long getLongSilent(Object bean, String param) {
		return getBeanProperties().getLongSilent(bean, param);
	}

	public static long getLongSilent(
		Object bean, String param, long defaultValue) {

		return getBeanProperties().getLongSilent(bean, param, defaultValue);
	}

	public static Object getObject(Object bean, String param) {
		return getBeanProperties().getObject(bean, param);
	}

	public static Object getObject(
		Object bean, String param, Object defaultValue) {

		return getBeanProperties().getObject(bean, param, defaultValue);
	}

	public static Object getObjectSilent(Object bean, String param) {
		return getBeanProperties().getObjectSilent(bean, param);
	}

	public static Object getObjectSilent(
		Object bean, String param, Object defaultValue) {

		return getBeanProperties().getObjectSilent(bean, param, defaultValue);
	}

	public static Class<?> getObjectType(Object bean, String param) {
		return getBeanProperties().getObjectType(bean, param);
	}

	public static Class<?> getObjectType(
		Object bean, String param, Class<?> defaultValue) {

		return getBeanProperties().getObjectType(bean, param, defaultValue);
	}

	public static Class<?> getObjectTypeSilent(Object bean, String param) {
		return getBeanProperties().getObjectType(bean, param);
	}

	public static Class<?> getObjectTypeSilent(
		Object bean, String param, Class<?> defaultValue) {

		return getBeanProperties().getObjectType(bean, param, defaultValue);
	}

	public static short getShort(Object bean, String param) {
		return getBeanProperties().getShort(bean, param);
	}

	public static short getShort(
		Object bean, String param, short defaultValue) {

		return getBeanProperties().getShort(bean, param, defaultValue);
	}

	public static short getShortSilent(Object bean, String param) {
		return getBeanProperties().getShortSilent(bean, param);
	}

	public static short getShortSilent(
		Object bean, String param, short defaultValue) {

		return getBeanProperties().getShortSilent(bean, param, defaultValue);
	}

	public static String getString(Object bean, String param) {
		return getBeanProperties().getString(bean, param);
	}

	public static String getString(
		Object bean, String param, String defaultValue) {

		return getBeanProperties().getString(bean, param, defaultValue);
	}

	public static String getStringSilent(Object bean, String param) {
		return getBeanProperties().getStringSilent(bean, param);
	}

	public static String getStringSilent(
		Object bean, String param, String defaultValue) {

		return getBeanProperties().getStringSilent(bean, param, defaultValue);
	}

	public static void setProperties(
		Object bean, HttpServletRequest httpServletRequest) {

		getBeanProperties().setProperties(bean, httpServletRequest);
	}

	public static void setProperties(
		Object bean, HttpServletRequest httpServletRequest,
		String[] ignoreProperties) {

		getBeanProperties().setProperties(
			bean, httpServletRequest, ignoreProperties);
	}

	public static void setProperty(Object bean, String param, Object value) {
		getBeanProperties().setProperty(bean, param, value);
	}

	public static void setPropertySilent(
		Object bean, String param, Object value) {

		getBeanProperties().setPropertySilent(bean, param, value);
	}

	public void setBeanProperties(BeanProperties beanProperties) {
		_beanProperties = beanProperties;
	}

	private static BeanProperties _beanProperties;

}