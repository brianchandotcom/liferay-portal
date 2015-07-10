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

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
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
	public <T> T deepCopyProperties(Object source, Class<T> targetClazz)
		throws Exception {

		T target = targetClazz.newInstance();

		BeanInfo targetBeanInfo = Introspector.getBeanInfo(targetClazz);

		for (PropertyDescriptor targetPropertyDescriptor :
				targetBeanInfo.getPropertyDescriptors()) {

			Class<?> targetPropertyType =
				targetPropertyDescriptor.getPropertyType();

			if ((targetPropertyType == null) ||
				(targetPropertyDescriptor.getWriteMethod() == null)) {

				continue;
			}

			PropertyDescriptor sourcePropertyDescriptor = getPropertyDescriptor(
				source.getClass(), targetPropertyDescriptor.getName());

			if (sourcePropertyDescriptor == null) {
				continue;
			}

			Class<?> sourcePropertyType =
				sourcePropertyDescriptor.getPropertyType();

			if ((sourcePropertyType == null) ||
				(sourcePropertyDescriptor.getReadMethod() == null)) {

				continue;
			}

			deepCopyProperties(
				sourcePropertyType, sourcePropertyDescriptor, source,
				targetPropertyType, targetPropertyDescriptor, target);
		}

		return target;
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

	protected Collection<?> copyCollection(
			Collection<?> source, Class<?> targetClazz)
		throws Exception {

		if (source == null) {
			return null;
		}

		Class<?> sourceClazz = source.getClass();

		Collection<Object> target =
			(Collection<Object>)sourceClazz.newInstance();

		for (Object object : source) {
			if (isSimpleCopy(object.getClass(), targetClazz)) {
				target.add(object);
			}
			else {
				target.add(deepCopyProperties(object, targetClazz));
			}
		}

		return target;
	}

	protected void copyCollection(
			Method sourceReadMethod, Object source, Method targetWriteMethod,
			Object target)
		throws Exception {

		Object value = sourceReadMethod.invoke(source);

		if (value == null) {
			return;
		}

		Class<?> targetClazz = getCollectionType(targetWriteMethod);

		targetWriteMethod.invoke(
			target, copyCollection((Collection<?>)value, targetClazz));
	}

	protected void copyMap(
			Method sourceReadMethod, Object source, Method targetWriteMethod,
			Object target)
		throws Exception {

		Object value = sourceReadMethod.invoke(source);

		if (value == null) {
			return;
		}

		Class<?> keyClazz = getMapKeyClass(targetWriteMethod);

		Class<?> valueClazz = getMapValueClass(targetWriteMethod);

		Map<?, ?> sourceMap = (Map<?, ?>)value;

		Class<?> sourceClazz = sourceMap.getClass();

		Map<Object, Object> targetMap =
			(Map<Object, Object>)sourceClazz.newInstance();

		for (Object key : sourceMap.keySet()) {
			Object targetKey = null;
			Object targetValue = null;

			Object sourceValue = sourceMap.get(key);

			if (isSimpleCopy(keyClazz, key.getClass())) {
				targetKey = key;
			}
			else {
				targetKey = deepCopyProperties(key, keyClazz);
			}

			if (isSimpleCopy(valueClazz, sourceValue.getClass())) {
				targetValue = sourceValue;
			}
			else {
				targetValue = deepCopyProperties(sourceValue, valueClazz);
			}

			targetMap.put(targetKey, targetValue);
		}

		targetWriteMethod.invoke(target, targetMap);
	}

	protected void deepCopyProperties(
			Class<?> sourcePropertyType,
			PropertyDescriptor sourcePropertyDescriptor, Object source,
			Class<?> targetPropertyType,
			PropertyDescriptor targetPropertyDescriptor, Object target)
		throws Exception {

		Method sourceReadMethod = sourcePropertyDescriptor.getReadMethod();

		Method targetWriteMethod = targetPropertyDescriptor.getWriteMethod();

		if (isCollectionCopy(sourcePropertyType, targetPropertyType)) {
			copyCollection(sourceReadMethod, source, targetWriteMethod, target);
		}
		else if (isMapCopy(sourcePropertyType, targetPropertyType)) {
			copyMap(sourceReadMethod, source, targetWriteMethod, target);
		}
		else if (isSimpleCopy(sourcePropertyType, targetPropertyType)) {
			targetWriteMethod.invoke(target, sourceReadMethod.invoke(source));
		}
		else {
			Object value = sourceReadMethod.invoke(source);

			if (value != null) {
				targetWriteMethod.invoke(
					target, deepCopyProperties(value, targetPropertyType));
			}
		}
	}

	protected Class<?> getCollectionType(Method targetWriteMethod) {
		Type[] types = targetWriteMethod.getGenericParameterTypes();

		ParameterizedType parameterizedType = (ParameterizedType)types[0];

		return (Class<?>)parameterizedType.getActualTypeArguments()[0];
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

	protected Class<?> getMapKeyClass(Method targetWriteMethod) {
		Type[] types = targetWriteMethod.getGenericParameterTypes();

		ParameterizedType parameterizedType = (ParameterizedType)types[0];

		return (Class<?>)parameterizedType.getActualTypeArguments()[0];
	}

	protected Class<?> getMapValueClass(Method targetWriteMethod) {
		Type[] types = targetWriteMethod.getGenericParameterTypes();

		ParameterizedType parameterizedType = (ParameterizedType)types[0];

		return (Class<?>)parameterizedType.getActualTypeArguments()[1];
	}

	protected PropertyDescriptor getPropertyDescriptor(
			Class<?> clazz, String propertyName)
		throws Exception {

		if (propertyName.equals("class")) {
			return null;
		}

		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

		for (PropertyDescriptor propertyDescriptor :
				beanInfo.getPropertyDescriptors()) {

			if (propertyName.equals(propertyDescriptor.getName())) {
				return propertyDescriptor;
			}
		}

		return null;
	}

	protected boolean isCollectionCopy(
		Class<?> sourceClazz, Class<?> targetClazz) {

		if ((sourceClazz.equals(List.class) || sourceClazz.equals(Set.class)) &&
			(targetClazz.equals(List.class) || targetClazz.equals(Set.class))) {

			return true;
		}

		return false;
	}

	protected boolean isMapCopy(Class<?> sourceClazz, Class<?> targetClazz) {
		if (sourceClazz.equals(Map.class) && targetClazz.equals(Map.class)) {
			return true;
		}

		return false;
	}

	protected boolean isSimpleCopy(Class<?> sourceClazz, Class<?> targetClazz) {
		if (!targetClazz.isAssignableFrom(sourceClazz)) {
			return false;
		}

		if (sourceClazz.isPrimitive() || sourceClazz.equals(Boolean.class) ||
			sourceClazz.equals(Double.class) ||
			sourceClazz.equals(Float.class) ||
			sourceClazz.equals(Integer.class) ||
			sourceClazz.equals(Locale.class) ||
			sourceClazz.equals(Long.class) ||
			sourceClazz.equals(String.class)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPropertiesImpl.class);

}