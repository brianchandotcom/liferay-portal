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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.bean.BeanCopy;
import jodd.bean.BeanUtil;

import jodd.typeconverter.Convert;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class BeanPropertiesImpl implements BeanProperties {

	@Override
	public void copyProperties(Object source, Object target) {
		try {
			BeanCopy beanCopy = BeanCopy.beans(source, target);

			beanCopy.copy();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void copyProperties(
		Object source, Object target, Class<?> editable) {

		try {
			BeanCopy beanCopy = BeanCopy.beans(source, target);

			beanCopy.includeAs(editable);

			beanCopy.copy();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void copyProperties(
		Object source, Object target, String[] ignoreProperties) {

		try {
			BeanCopy beanCopy = BeanCopy.beans(source, target);

			beanCopy.exclude(ignoreProperties);

			beanCopy.copy();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public <T> T deepCopyProperties(Class<T> toClazz, Object from)
		throws Exception {

		T to = toClazz.newInstance();

		BeanInfo fromBean = Introspector.getBeanInfo(from.getClass());
		BeanInfo toBean = Introspector.getBeanInfo(toClazz);

		PropertyDescriptor[] fromProperties = fromBean.getPropertyDescriptors();
		PropertyDescriptor[] toProperties = toBean.getPropertyDescriptors();

		for (PropertyDescriptor toProperty : toProperties) {
			Class<?> toPropertyType = toProperty.getPropertyType();

			for (PropertyDescriptor fromProperty : fromProperties) {
				if (!toProperty.getName().equals("class") &&
					toProperty.getName().equals(fromProperty.getName())) {

					Class<?> fromPropertyType = fromProperty.getPropertyType();

					if ((toPropertyType != null) &&
						(fromPropertyType != null) &&
						(toProperty.getWriteMethod() != null) &&
						(fromProperty.getReadMethod() != null)) {

						if (isCollectionType(
								toPropertyType, fromPropertyType)) {

							copyCollection(fromProperty, toProperty, from, to);
						}
						else if (isMapType(toPropertyType, fromPropertyType)) {
							copyMap(fromProperty, toProperty, from, to);
						}
						else if (isSimpleCopy(
									toPropertyType, fromPropertyType)) {

							toProperty.getWriteMethod().invoke(
								to, fromProperty.getReadMethod().invoke(from));
						}
						else {
							Object value =
								fromProperty.getReadMethod().invoke(from);

							Object newObject = deepCopyProperties(
								toPropertyType, value);

							toProperty.getWriteMethod().invoke(to, newObject);
						}
					}

					break;
				}
			}
		}

		return to;
	}

	@Override
	public boolean getBoolean(Object bean, String param) {
		return getBoolean(bean, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	@Override
	public boolean getBoolean(Object bean, String param, boolean defaultValue) {
		boolean beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toBooleanValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public boolean getBooleanSilent(Object bean, String param) {
		return getBooleanSilent(bean, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	@Override
	public boolean getBooleanSilent(
		Object bean, String param, boolean defaultValue) {

		boolean beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toBooleanValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public byte getByte(Object bean, String param) {
		return getByte(bean, param, GetterUtil.DEFAULT_BYTE);
	}

	@Override
	public byte getByte(Object bean, String param, byte defaultValue) {
		byte beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toByteValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public byte getByteSilent(Object bean, String param) {
		return getByteSilent(bean, param, GetterUtil.DEFAULT_BYTE);
	}

	@Override
	public byte getByteSilent(Object bean, String param, byte defaultValue) {
		byte beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toByteValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public double getDouble(Object bean, String param) {
		return getDouble(bean, param, GetterUtil.DEFAULT_DOUBLE);
	}

	@Override
	public double getDouble(Object bean, String param, double defaultValue) {
		double beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toDoubleValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public double getDoubleSilent(Object bean, String param) {
		return getDoubleSilent(bean, param, GetterUtil.DEFAULT_DOUBLE);
	}

	@Override
	public double getDoubleSilent(
		Object bean, String param, double defaultValue) {

		double beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toDoubleValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public float getFloat(Object bean, String param) {
		return getFloat(bean, param, GetterUtil.DEFAULT_FLOAT);
	}

	@Override
	public float getFloat(Object bean, String param, float defaultValue) {
		float beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toFloatValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public float getFloatSilent(Object bean, String param) {
		return getFloatSilent(bean, param, GetterUtil.DEFAULT_FLOAT);
	}

	@Override
	public float getFloatSilent(Object bean, String param, float defaultValue) {
		float beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toFloatValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public int getInteger(Object bean, String param) {
		return getInteger(bean, param, GetterUtil.DEFAULT_INTEGER);
	}

	@Override
	public int getInteger(Object bean, String param, int defaultValue) {
		int beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toIntValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public int getIntegerSilent(Object bean, String param) {
		return getIntegerSilent(bean, param, GetterUtil.DEFAULT_INTEGER);
	}

	@Override
	public int getIntegerSilent(Object bean, String param, int defaultValue) {
		int beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toIntValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public long getLong(Object bean, String param) {
		return getLong(bean, param, GetterUtil.DEFAULT_LONG);
	}

	@Override
	public long getLong(Object bean, String param, long defaultValue) {
		long beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toLongValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public long getLongSilent(Object bean, String param) {
		return getLongSilent(bean, param, GetterUtil.DEFAULT_LONG);
	}

	@Override
	public long getLongSilent(Object bean, String param, long defaultValue) {
		long beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toLongValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public Object getObject(Object bean, String param) {
		return getObject(bean, param, null);
	}

	@Override
	public Object getObject(Object bean, String param, Object defaultValue) {
		Object beanValue = null;

		if (bean != null) {
			try {
				beanValue = BeanUtil.getProperty(bean, param);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (beanValue == null) {
			return defaultValue;
		}
		else {
			return beanValue;
		}
	}

	@Override
	public Object getObjectSilent(Object bean, String param) {
		return getObjectSilent(bean, param, null);
	}

	@Override
	public Object getObjectSilent(
		Object bean, String param, Object defaultValue) {

		Object beanValue = null;

		if (bean != null) {
			try {
				beanValue = BeanUtil.getProperty(bean, param);
			}
			catch (Exception e) {
			}
		}

		if (beanValue == null) {
			return defaultValue;
		}
		else {
			return beanValue;
		}
	}

	@Override
	public Class<?> getObjectType(Object bean, String param) {
		return getObjectType(bean, param, null);
	}

	@Override
	public Class<?> getObjectType(
		Object bean, String param, Class<?> defaultValue) {

		Class<?> beanType = null;

		if (bean != null) {
			try {
				beanType = BeanUtil.getPropertyType(bean, param);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (beanType == null) {
			return defaultValue;
		}
		else {
			return beanType;
		}
	}

	@Override
	public Class<?> getObjectTypeSilent(Object bean, String param) {
		return getObjectTypeSilent(bean, param, null);
	}

	@Override
	public Class<?> getObjectTypeSilent(
		Object bean, String param, Class<?> defaultValue) {

		Class<?> beanType = null;

		if (bean != null) {
			try {
				beanType = BeanUtil.getPropertyType(bean, param);
			}
			catch (Exception e) {
			}
		}

		if (beanType == null) {
			return defaultValue;
		}
		else {
			return beanType;
		}
	}

	@Override
	public short getShort(Object bean, String param) {
		return getShort(bean, param, GetterUtil.DEFAULT_SHORT);
	}

	@Override
	public short getShort(Object bean, String param, short defaultValue) {
		short beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toShortValue(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public short getShortSilent(Object bean, String param) {
		return getShortSilent(bean, param, GetterUtil.DEFAULT_SHORT);
	}

	@Override
	public short getShortSilent(Object bean, String param, short defaultValue) {
		short beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toShortValue(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public String getString(Object bean, String param) {
		return getString(bean, param, GetterUtil.DEFAULT_STRING);
	}

	@Override
	public String getString(Object bean, String param, String defaultValue) {
		String beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toString(value, defaultValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return beanValue;
	}

	@Override
	public String getStringSilent(Object bean, String param) {
		return getStringSilent(bean, param, GetterUtil.DEFAULT_STRING);
	}

	@Override
	public String getStringSilent(
		Object bean, String param, String defaultValue) {

		String beanValue = defaultValue;

		if (bean != null) {
			try {
				Object value = BeanUtil.getProperty(bean, param);

				beanValue = Convert.toString(value, defaultValue);
			}
			catch (Exception e) {
			}
		}

		return beanValue;
	}

	@Override
	public void setProperties(Object bean, HttpServletRequest request) {
		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = request.getParameter(name);

			BeanUtil.setPropertyForcedSilent(bean, name, value);

			if (name.endsWith("Month")) {
				String dateParam = name.substring(0, name.lastIndexOf("Month"));

				if (request.getParameter(dateParam) != null) {
					continue;
				}

				Class<?> propertyTypeClass = BeanUtil.getPropertyType(
					bean, dateParam);

				if ((propertyTypeClass == null) ||
					!propertyTypeClass.equals(Date.class)) {

					continue;
				}

				Date date = getDate(dateParam, request);

				if (date != null) {
					BeanUtil.setPropertyForcedSilent(bean, dateParam, date);
				}
			}
		}
	}

	@Override
	public void setProperty(Object bean, String param, Object value) {
		try {
			BeanUtil.setProperty(bean, param, value);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setPropertySilent(Object bean, String param, Object value) {
		BeanUtil.setPropertyForcedSilent(bean, param, value);
	}

	protected Date getDate(String param, HttpServletRequest request) {
		int month = ParamUtil.getInteger(request, param + "Month");
		int day = ParamUtil.getInteger(request, param + "Day");
		int year = ParamUtil.getInteger(request, param + "Year");
		int hour = ParamUtil.getInteger(request, param + "Hour", -1);
		int minute = ParamUtil.getInteger(request, param + "Minute");

		int amPm = ParamUtil.getInteger(request, param + "AmPm");

		if (amPm == Calendar.PM) {
			hour += 12;
		}

		if (hour == -1) {
			return PortalUtil.getDate(month, day, year);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		try {
			return PortalUtil.getDate(
				month, day, year, hour, minute, user.getTimeZone(), null);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	private Collection<Object> copyCollection(
			Class<?> toClazz, Collection<?> from)
		throws Exception {

		Collection<Object> to = null;

		if (from == null) {
			return null;
		}

		if (from instanceof Set<?>) {
			to = new LinkedHashSet<>();
		}
		else if (from instanceof List<?>) {
			to = new ArrayList<>();
		}

		if (!from.isEmpty()) {
			for (Object obj : from) {
				if (isSimpleCopy(toClazz, obj.getClass())) {
					to.add(obj);
				}
				else {
					to.add(deepCopyProperties(toClazz, obj));
				}
			}
		}

		return to;
	}

	private void copyCollection(
			PropertyDescriptor fromProperty, PropertyDescriptor toProperty,
			Object from, Object to)
		throws Exception {

		Object value = fromProperty.getReadMethod().invoke(from);

		if (value != null) {
			Method toMethod = toProperty.getWriteMethod();

			Type[] types = toMethod.getGenericParameterTypes();

			ParameterizedType pType = (ParameterizedType)types[0];

			Class<?> clazz = (Class<?>)pType.getActualTypeArguments()[0];

			toProperty.getWriteMethod().invoke(
				to, copyCollection(clazz, (Collection<?>)value));
		}
	}

	private void copyMap(
			PropertyDescriptor fromProperty, PropertyDescriptor toProperty,
			Object from, Object to)
		throws Exception {

		Object value = fromProperty.getReadMethod().invoke(from);

		if (value != null) {
			Method toMethod = toProperty.getWriteMethod();

			Type[] types = toMethod.getGenericParameterTypes();

			ParameterizedType pType = (ParameterizedType)types[0];

			Class<?> keyClazz = (Class<?>)pType.getActualTypeArguments()[0];
			Class<?> valueClazz = (Class<?>)pType.getActualTypeArguments()[1];

			Map<?, ?> fromMap = (Map<?, ?>)value;
			Map<Object, Object> toMap = new HashMap<>();

			for (Object key : fromMap.keySet()) {
				Object toKey = null;
				Object toValue = null;

				Object mapValue = fromMap.get(key);

				if (isSimpleCopy(keyClazz, key.getClass())) {
					toKey = key;
				}
				else {
					toKey = deepCopyProperties(keyClazz, key);
				}

				if (isSimpleCopy(valueClazz, mapValue.getClass())) {
					toValue = mapValue;
				}
				else {
					toValue = deepCopyProperties(valueClazz, mapValue);
				}

				toMap.put(toKey, toValue);
			}

			toProperty.getWriteMethod().invoke(to, toMap);
		}
	}

	private boolean isCollectionType(Class<?> toClazz, Class<?> fromClazz) {
		return ((toClazz.equals(Set.class) || toClazz.equals(List.class)) &&
			(fromClazz.equals(Set.class) || fromClazz.equals(List.class)));
	}

	private boolean isMapType(Class<?> toClazz, Class<?> fromClazz) {
		return toClazz.equals(Map.class) && fromClazz.equals(Map.class);
	}

	private boolean isSimpleCopy(Class<?> toClazz, Class<?> fromClazz) {
		return toClazz.isAssignableFrom(fromClazz) &&
			(fromClazz.isPrimitive() || fromClazz.equals(String.class) ||
			fromClazz.equals(Long.class) || fromClazz.equals(Integer.class) ||
			fromClazz.equals(Double.class) || fromClazz.equals(Float.class) ||
			fromClazz.equals(Boolean.class) || fromClazz.equals(Locale.class));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPropertiesImpl.class);

}