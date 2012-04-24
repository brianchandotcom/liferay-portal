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

package com.liferay.portal.json;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import flexjson.BeanAnalyzer;
import flexjson.BeanProperty;
import flexjson.JSONException;
import flexjson.ObjectBinder;

import flexjson.factories.BeanObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class PortalBeanObjectFactory extends BeanObjectFactory {

	@Override
	public Object instantiate(
		ObjectBinder objectBinder, Object value, Type targetType,
		@SuppressWarnings("rawtypes") Class targetClass) {

		if (_safeMode) {
			Map<Object, Object> target = new HashMap<Object, Object>();

			target.put("class", targetClass.getName());

			Map<?, ?> values = (Map<?, ?>)value;

			return objectBinder.bindIntoMap(values, target, null, null);
		}

		String targetClassName = targetClass.getName();

		if (targetClassName.contains("com.liferay") &&
			targetClassName.contains("Util")) {

			throw new JSONException(
				"Not instantiating " + targetClass.getName() + " at " +
					objectBinder.getCurrentPath());
		}

		try {
			Object target = instantiate(targetClass);

			Map<?, ?> values = (Map<?, ?>)value;

			if (PropsValues.JSON_DESERIALIZER_STRICT_MODE) {
				removeInvalidFields(values, targetClass);
			}

			return objectBinder.bindIntoObject(values, target, targetType);
		}
		catch (Exception e) {
			throw new JSONException(
				"Unable to instantiate " + targetClass.getName() + " at " +
					objectBinder.getCurrentPath(),
				e);
		}
	}

	public void setSafeMode(boolean safeMode) {
		_safeMode = safeMode;
	}

	protected Map<String, Field> getDeclaredFields(
		@SuppressWarnings("rawtypes") Class targetClass) {

		Map<String, Field> declaredFieldsMap = _declaredFields.get(targetClass);

		if (declaredFieldsMap == null) {
			declaredFieldsMap = new ConcurrentHashMap<String, Field>();

			Field[] declaredFields = targetClass.getDeclaredFields();

			for (Field declaredField : declaredFields) {
				String fieldName = declaredField.getName();

				if (fieldName.startsWith(StringPool.UNDERLINE)) {
					fieldName = fieldName.substring(1);
				}

				declaredFieldsMap.put(fieldName, declaredField);
			}

			_declaredFields.put(targetClass, declaredFieldsMap);
		}

		return declaredFieldsMap;
	}

	protected boolean isValidField(
		Map<String, Field> declaredFields, String beanName) {

		Field declaredField = declaredFields.get(beanName);

		if (declaredField == null) {
			return false;
		}

		int modifier = declaredField.getModifiers();

		if (Modifier.isStatic(modifier)) {
			return false;
		}

		return true;
	}

	protected void removeInvalidFields(Map<?, ?> values, Class<?> targetClass) {
		Map<String, Field> declaredFields = getDeclaredFields(targetClass);

		BeanAnalyzer beanAnalyzer = BeanAnalyzer.analyze(targetClass);

		for (BeanProperty beanProperty : beanAnalyzer.getProperties()) {
			String beanName = beanProperty.getName();

			String capitalizedBeanName = null;

			Object beanValue = values.get(beanName);

			if (beanValue == null) {
				capitalizedBeanName = Character.toUpperCase(
					beanName.charAt(0)) + beanName.substring(1);

				beanValue = values.get(capitalizedBeanName);
			}

			if ((beanValue != null) &&
				!isValidField(declaredFields, beanName)) {

				if (capitalizedBeanName != null) {
					beanName = capitalizedBeanName;
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Removing non-JavaBeans field " + beanName);
				}

				values.remove(beanName);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalBeanObjectFactory.class);

	private Map<Class<?>, Map<String, Field>> _declaredFields =
		new ConcurrentHashMap<Class<?>, Map<String, Field>>();

	private boolean _safeMode = false;

}