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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class PortalBeanObjectFactory extends BeanObjectFactory {

	@Override
	public Object instantiate(
		ObjectBinder context, Object value, Type targetType,
		Class targetClass) {

		String className = targetClass.getName();

		if (className.contains("com.liferay") && className.contains("Util")) {
			throw new JSONException(
				context.getCurrentPath() +
					": Unable to instantiate class for security reasons: " +
					className);
		}

		try {
			Object target = instantiate(targetClass);

			Map values = (Map) value;

			if (PropsValues.JSON_DESERIALIZER_STRICT_MODE) {
				validateBeanValues(values, targetClass);
			}

			return context.bindIntoObject(values, target, targetType);
		}
		catch (Exception e) {
			throw new JSONException(
				context.getCurrentPath() +
					": Unable to instantiate an instance of " +
					targetClass.getName(),
				e);
		}
	}

	protected Map<String, Field> getDeclaredFields(Class targetClass) {
		Map<String, Field> declaredFieldsMap = _declaredFieldsCache.get(
			targetClass);

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

			_declaredFieldsCache.put(targetClass, declaredFieldsMap);
		}

		return declaredFieldsMap;
	}

	protected void validateBeanValues(Map values, Class targetClass) {
		Map<String, Field> declaredFieldsMap = getDeclaredFields(targetClass);

		BeanAnalyzer beanAnalyzer = BeanAnalyzer.analyze(targetClass);

		for (BeanProperty beanProperty : beanAnalyzer.getProperties()) {
			String beanName = beanProperty.getName();
			String capitalBeanName = null;

			Object beanValue = values.get(beanName);

			if (beanValue == null) {
				capitalBeanName = Character.toUpperCase(
					beanName.charAt(0)) + beanName.substring(1);

				beanValue = values.get(capitalBeanName);
			}

			if ((beanValue != null) &&
				!validateDeclaredField(declaredFieldsMap, beanName)) {

				if (capitalBeanName != null) {
					beanName = capitalBeanName;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Removing field: " + beanName +
							" for security reasons.");
				}
				values.remove(beanName);
			}
		}
	}

	protected boolean validateDeclaredField(
		Map<String, Field> declaredFieldsMap, String beanName) {

		Field declaredField = declaredFieldsMap.get(beanName);

		//if bean is not part of set of declared fields, do not allow
		if (declaredField == null) {
			return false;
		}

		int modifier = declaredField.getModifiers();

		//if bean is a static field , do not allow
		if (Modifier.isStatic(modifier)) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalBeanObjectFactory.class);

	private Map<Class, Map<String, Field>> _declaredFieldsCache =
		new ConcurrentHashMap<Class, Map<String, Field>>();

}