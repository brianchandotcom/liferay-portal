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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDMFormInstanceFactory {

	public static <T> T create(Class<T> clazz, DDMFormValues ddmFormValues) {
		return create(clazz, ddmFormValues, ddmFormValues.getDefaultLocale());
	}

	public static <T> T create(
		Class<T> clazz, DDMFormValues ddmFormValues, Locale locale) {

		Object proxy = ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			new DDMFormInstanceInvocationHandler<>(
				clazz, ddmFormValues, locale));

		return clazz.cast(proxy);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceFactory.class);

	private static class DDMFormInstanceInvocationHandler<T>
		implements InvocationHandler {

		public DDMFormInstanceInvocationHandler(
			Class<T> clazz, DDMFormValues ddmFormValues, Locale locale) {

			_ddmFormValues = ddmFormValues;
			_locale = locale;

			_ddmFormFactoryHelper = new DDMFormFactoryHelper(clazz);

			_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (!method.isAnnotationPresent(DDMFormField.class)) {
				return null;
			}

			DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
				new DDMFormFieldFactoryHelper(_ddmFormFactoryHelper, method);

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValuesMap.get(
					ddmFormFieldFactoryHelper.getDDMFormFieldName());

			if (ListUtil.isEmpty(ddmFormFieldValues)) {
				LocalizedValue predefinedValue =
					ddmFormFieldFactoryHelper.getDDMFormFieldPredefinedValue();

				return convert(
					method.getReturnType(),
					ddmFormFieldFactoryHelper.getDDMFormFieldType(),
					predefinedValue.getString(_locale));
			}

			return convert(
				method.getReturnType(),
				ddmFormFieldFactoryHelper.getDDMFormFieldType(),
				ddmFormFieldValues);
		}

		protected Object convert(
			Class<?> returnType, String ddmFormFieldType,
			List<DDMFormFieldValue> ddmFormFieldValues) {

			if (returnType.isArray()) {
				return toArray(
					returnType.getComponentType(), ddmFormFieldType,
					ddmFormFieldValues);
			}

			return toPrimitive(
				returnType, ddmFormFieldType, ddmFormFieldValues.get(0));
		}

		protected Object convert(
			Class<?> returnType, String ddmFormFieldType,
			String predefinedValue) {

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

				return toString(ddmFormFieldType, predefinedValue);
			}

			return null;
		}

		protected String joinJSONArrayString(String valueString) {
			if (Validator.isNull(valueString)) {
				return valueString;
			}

			try {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
					valueString);

				StringBundler sb = new StringBundler(jsonArray.length() * 2);

				for (int i = 0; i < jsonArray.length(); i++) {
					sb.append(jsonArray.getString(i));
					sb.append(StringPool.COMMA);
				}

				if (sb.length() > 0) {
					sb.setIndex(sb.index() - 1);
				}

				return sb.toString();
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsonException);
				}

				return valueString;
			}
		}

		protected Object toArray(
			Class<?> returnType, String ddmFormFieldType,
			List<DDMFormFieldValue> ddmFormFieldValues) {

			Object array = Array.newInstance(
				returnType, ddmFormFieldValues.size());

			int i = 0;

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				Array.set(
					array, i++,
					toPrimitive(
						returnType, ddmFormFieldType, ddmFormFieldValue));
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
			Class<?> returnType, String ddmFormFieldType,
			DDMFormFieldValue ddmFormFieldValue) {

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
				return toString(ddmFormFieldType, value.getString(_locale));
			}

			return toDynamicForm(returnType, ddmFormFieldValue);
		}

		protected short toShort(Value value) {
			return Short.valueOf(value.getString(_locale));
		}

		protected String toString(String ddmFormFieldType, String valueString) {
			if (Objects.equals(ddmFormFieldType, DDMFormFieldType.SELECT)) {
				return joinJSONArrayString(valueString);
			}

			return valueString;
		}

		private final DDMFormFactoryHelper _ddmFormFactoryHelper;
		private final Map<String, List<DDMFormFieldValue>>
			_ddmFormFieldValuesMap;
		private final DDMFormValues _ddmFormValues;
		private final Locale _locale;

	}

}