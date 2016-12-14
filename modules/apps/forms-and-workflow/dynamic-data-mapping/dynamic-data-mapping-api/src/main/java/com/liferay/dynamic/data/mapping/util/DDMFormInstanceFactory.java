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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormInstanceFactory {

	public static <T> T create(Class<T> clazz, DDMFormValues ddmFormValues) {
		return create(clazz, ddmFormValues, ddmFormValues.getDefaultLocale());
	}

	public static <T> T create(
		Class<T> clazz, DDMFormValues ddmFormValues, Locale locale) {

		DDMForm ddmForm = DDMFormFactory.create(clazz);

		if (!ddmForm.equals(ddmFormValues.getDDMForm())) {
			throw new IllegalArgumentException(
				"DDM form values does not match with the given class " + clazz);
		}

		Object proxy = ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			new DDMFormInstanceInvocationHandler<>(
				clazz, ddmFormValues, locale));

		return clazz.cast(proxy);
	}

	private static class DDMFormInstanceInvocationHandler<T>
		implements InvocationHandler {

		public DDMFormInstanceInvocationHandler(
			T clazz, DDMFormValues ddmFormValues, Locale locale) {

			_clazz = clazz;
			_ddmFormValues = ddmFormValues;
			_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();
			_locale = locale;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (!method.isAnnotationPresent(DDMFormField.class)) {
				return null;
			}

			DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
				new DDMFormFieldFactoryHelper(method);

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValuesMap.get(
					ddmFormFieldFactoryHelper.getDDMFormFieldName());

			if (ListUtil.isEmpty(ddmFormFieldValues)) {
				LocalizedValue predefinedValue =
					ddmFormFieldFactoryHelper.getDDMFormFieldPredefinedValue();

				return convert(
					method.getReturnType(), predefinedValue.getString(_locale));
			}
			else {
				return convert(method.getReturnType(), ddmFormFieldValues);
			}
		}

		protected Object convert(
			Class<?> returnType, List<DDMFormFieldValue> ddmFormFieldValues) {

			if (returnType.isArray()) {
				return toArray(
					returnType.getComponentType(), ddmFormFieldValues);
			}

			return toPrimitive(returnType, ddmFormFieldValues.get(0));
		}

		protected Object convert(Class<?> returnType, String predefinedValue) {
			if (returnType.isArray()) {
				return Array.newInstance(returnType.getComponentType(), 0);
			}

			if ((returnType == boolean.class) ||
				(returnType == Boolean.class)) {

				return GetterUtil.getBoolean(predefinedValue);
			}

			if ((returnType == double.class) || (returnType == Double.class)) {
				return GetterUtil.getDouble(predefinedValue);
			}

			if ((returnType == float.class) || (returnType == Float.class)) {
				return GetterUtil.getFloat(predefinedValue);
			}

			if ((returnType == int.class) || (returnType == Integer.class)) {
				return GetterUtil.getInteger(predefinedValue);
			}

			if ((returnType == long.class) || (returnType == Long.class)) {
				return GetterUtil.getLong(predefinedValue);
			}

			if ((returnType == short.class) || (returnType == Short.class)) {
				return GetterUtil.getShort(predefinedValue);
			}

			if ((returnType == String.class) &&
				Validator.isNotNull(predefinedValue)) {

				return predefinedValue;
			}

			return null;
		}

		protected Object toArray(
			Class<?> returnType, List<DDMFormFieldValue> ddmFormFieldValues) {

			Object array = Array.newInstance(
				returnType, ddmFormFieldValues.size());

			int i = 0;

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				Array.set(
					array, i++, toPrimitive(returnType, ddmFormFieldValue));
			}

			return array;
		}

		protected boolean toBoolean(Value value) {
			return Boolean.valueOf(value.getString(_locale));
		}

		protected double toDouble(Value value) {
			return Double.valueOf(value.getString(_locale));
		}

		protected Object toDynamicForm(
			Class<?> clazz, DDMFormFieldValue ddmFormFieldValue) {

			DDMFormValues ddmFormValues = new DDMFormValues(
				DDMFormFactory.create(clazz));

			ddmFormValues.setAvailableLocales(
				_ddmFormValues.getAvailableLocales());
			ddmFormValues.setDefaultLocale(_ddmFormValues.getDefaultLocale());

			ddmFormValues.setDDMFormFieldValues(
				ddmFormFieldValue.getNestedDDMFormFieldValues());

			Object proxy = ProxyUtil.newProxyInstance(
				clazz.getClassLoader(), new Class<?>[] {clazz},
				new DDMFormInstanceInvocationHandler<>(
					clazz, ddmFormValues, _locale));

			return clazz.cast(proxy);
		}

		protected float toFloat(Value value) {
			return Float.valueOf(value.getString(_locale));
		}

		protected int toInteger(Value value) {
			return Integer.valueOf(value.getString(_locale));
		}

		protected long toLong(Value value) {
			return Long.valueOf(value.getString(_locale));
		}

		protected Object toPrimitive(
			Class<?> returnType, DDMFormFieldValue ddmFormFieldValue) {

			Value value = ddmFormFieldValue.getValue();

			if ((returnType == boolean.class) ||
				(returnType == Boolean.class)) {

				return toBoolean(value);
			}

			if ((returnType == double.class) || (returnType == Double.class)) {
				return toDouble(value);
			}

			if ((returnType == float.class) || (returnType == Float.class)) {
				return toFloat(value);
			}

			if ((returnType == int.class) || (returnType == Integer.class)) {
				return toInteger(value);
			}

			if ((returnType == long.class) || (returnType == Long.class)) {
				return toLong(value);
			}

			if ((returnType == short.class) || (returnType == Short.class)) {
				return toShort(value);
			}

			if (returnType == String.class) {
				return toString(value);
			}

			return toDynamicForm(returnType, ddmFormFieldValue);
		}

		protected short toShort(Value value) {
			return Short.valueOf(value.getString(_locale));
		}

		protected String toString(Value value) {
			return value.getString(_locale);
		}

		private final T _clazz;
		private final Map<String, List<DDMFormFieldValue>>
			_ddmFormFieldValuesMap;
		private final DDMFormValues _ddmFormValues;
		private final Locale _locale;

	}

}