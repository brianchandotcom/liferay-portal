/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.item.selector.web.util;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Iván Zaera
 */
public class ItemSelectorCriterionSerializer<T extends ItemSelectorCriterion> {

	public ItemSelectorCriterionSerializer(
		T itemSelectorCriterion, String paramPrefix) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_paramPrefix = paramPrefix;
	}

	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties = new HashMap<>();

		try {
			Map<String, Object> map = PropertyUtils.describe(
				_itemSelectorCriterion);

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();

				if (_isInternalProperty(key)) {
					continue;
				}

				properties.put(
					_paramPrefix + key,
					new String[] {_serialize(entry.getValue())});
			}
		}
		catch (
			IllegalAccessException | InvocationTargetException |
				NoSuchMethodException e) {

			throw new SystemException(e);
		}

		return properties;
	}

	public void setProperties(Map<String, String[]> parameters) {
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith(_paramPrefix)) {
				continue;
			}

			key = key.substring(_paramPrefix.length());

			if (_isInternalProperty(key)) {
				continue;
			}

			String value = entry.getValue()[0];

			try {
				Class<?> clazz = PropertyUtils.getPropertyType(
					_itemSelectorCriterion, key);

				PropertyUtils.setProperty
					(_itemSelectorCriterion, key, _deserialize(clazz, value));
			}
			catch (
				IllegalAccessException | InvocationTargetException |
					NoSuchMethodException e) {

				throw new SystemException(e);
			}
		}
	}

	private Object _deserialize(Class<?> clazz, String value) {
		try {
			if (clazz.isArray()) {
				Class<?> componentClass = clazz.getComponentType();

				String[] stringValues = StringUtil.split(value);

				Object[] values = (Object[])Array.newInstance(
					componentClass, stringValues.length);

				for (int i = 0; i<stringValues.length; i++) {
					values[i] = _deserialize(componentClass, stringValues[i]);
				}

				return values;
			}
			else if ((clazz == byte.class) || (clazz == Byte.class)) {
				return Byte.valueOf(value);
			}
			else if ((clazz == short.class) || (clazz == Short.class)) {
				return Short.valueOf(value);
			}
			else if ((clazz == int.class) || (clazz == Integer.class)) {
				return Integer.valueOf(value);
			}
			else if ((clazz == long.class) || (clazz == Long.class)) {
				return Long.valueOf(value);
			}
			else if ((clazz == float.class) || (clazz == Float.class)) {
				return Float.valueOf(value);
			}
			else if ((clazz == double.class) || (clazz == Double.class)) {
				return Double.valueOf(value);
			}
			else if ((clazz == boolean.class) || (clazz == Boolean.class)) {
				return Boolean.valueOf(value);
			}
			else if ((clazz == char.class) || (clazz == Character.class)) {
				return value.charAt(0);
			}

			Constructor<?> constructor = clazz.getConstructor(String.class);

			return constructor.newInstance(value);
		}
		catch (
			NoSuchMethodException | InvocationTargetException |
				InstantiationException | IllegalAccessException e) {

			throw new SystemException(e);
		}
	}

	private boolean _isInternalProperty(String name) {
		if (name.equals("availableReturnTypes") || name.equals("class") ||
			name.equals("desiredReturnTypes")) {

			return true;
		}

		return false;
	}

	private String _serialize(Object value) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Object[] array = (Object[])value;

			return StringUtil.merge(array);
		}

		return value.toString();
	}

	private final T _itemSelectorCriterion;
	private final String _paramPrefix;

}