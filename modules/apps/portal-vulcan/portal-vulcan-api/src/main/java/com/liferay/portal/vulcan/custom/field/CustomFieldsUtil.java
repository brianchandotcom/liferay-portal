/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.custom.field;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * @author Carlos Correa
 */
public class CustomFieldsUtil {

	public static CustomField[] toCustomFields(
		boolean acceptAllLanguages, String className, long classPK,
		long companyId, Locale locale) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className, classPK);

		return toCustomFields(
			acceptAllLanguages, className, classPK, companyId,
			expandoBridge.getAttributes(), locale);
	}

	public static CustomField[] toCustomFields(
		boolean acceptAllLanguages, String className, long classPK,
		long companyId, Map<String, Serializable> expandoBridgeAttributes,
		Locale locale) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className, classPK);

		return TransformUtil.transformToArray(
			expandoBridgeAttributes.entrySet(),
			entry -> {
				UnicodeProperties unicodeProperties =
					expandoBridge.getAttributeProperties(entry.getKey());

				if (GetterUtil.getBoolean(
						unicodeProperties.getProperty(
							ExpandoColumnConstants.PROPERTY_HIDDEN))) {

					return null;
				}

				return _toCustomField(
					acceptAllLanguages,
					unicodeProperties.getProperty(
						ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE),
					entry, expandoBridge, locale);
			},
			CustomField.class);
	}

	public static Map<String, Serializable> toMap(
		String className, long companyId, CustomField[] customFields,
		Locale locale) {

		if (customFields == null) {
			return null;
		}

		Map<String, Serializable> map = new HashMap<>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className);

		for (CustomField customField : customFields) {
			CustomValue customValue = customField.getCustomValue();

			Object data = customValue.getData();

			if ((data == null) && (customValue.getData_i18n() == null) &&
				(customValue.getGeo() == null)) {

				continue;
			}

			String name = customField.getName();

			int attributeType = expandoBridge.getAttributeType(name);

			if (ExpandoColumnConstants.isArray(attributeType) &&
				(attributeType !=
					ExpandoColumnConstants.STRING_ARRAY_LOCALIZED)) {

				_validateArray(customField, data);
			}

			if (ExpandoColumnConstants.BOOLEAN == attributeType) {
				_validateCustomField(
					customField, data, Boolean.class, String.class);

				map.put(name, GetterUtil.getBoolean(data));
			}
			else if (ExpandoColumnConstants.BOOLEAN_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Boolean.class, String.class);

				map.put(
					name,
					_toArray(boolean.class, data, GetterUtil::getBoolean));
			}
			else if (ExpandoColumnConstants.DATE == attributeType) {
				_validateCustomField(
					customField, data, Date.class, String.class);

				map.put(name, _toDate(data));
			}
			else if (ExpandoColumnConstants.DATE_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Date.class, String.class);

				map.put(
					name,
					_toArray(Date.class, data, CustomFieldsUtil::_toDate));
			}
			else if (ExpandoColumnConstants.DOUBLE == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getDouble(data));
			}
			else if (ExpandoColumnConstants.DOUBLE_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(
					name, _toArray(double.class, data, GetterUtil::getDouble));
			}
			else if (ExpandoColumnConstants.FLOAT == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getFloat(data));
			}
			else if (ExpandoColumnConstants.FLOAT_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(
					name, _toArray(float.class, data, GetterUtil::getFloat));
			}
			else if (ExpandoColumnConstants.GEOLOCATION == attributeType) {
				_validateCustomField(customField, data, Geo.class);

				Geo geo = customValue.getGeo();

				map.put(
					name,
					JSONUtil.put(
						"latitude", geo.getLatitude()
					).put(
						"longitude", geo.getLongitude()
					).toString());
			}
			else if (ExpandoColumnConstants.INTEGER == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getInteger(data));
			}
			else if (ExpandoColumnConstants.INTEGER_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(
					name, _toArray(int.class, data, GetterUtil::getInteger));
			}
			else if (ExpandoColumnConstants.LONG == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getLong(data));
			}
			else if (ExpandoColumnConstants.LONG_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(name, _toArray(long.class, data, GetterUtil::getLong));
			}
			else if (ExpandoColumnConstants.NUMBER == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getNumber(data));
			}
			else if (ExpandoColumnConstants.NUMBER_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(
					name, _toArray(Number.class, data, GetterUtil::getNumber));
			}
			else if (ExpandoColumnConstants.SHORT == attributeType) {
				_validateCustomField(
					customField, data, Number.class, String.class);

				map.put(name, GetterUtil.getShort(data));
			}
			else if (ExpandoColumnConstants.SHORT_ARRAY == attributeType) {
				_validateArrayCustomField(
					customField, data, Number.class, String.class);

				map.put(
					name, _toArray(short.class, data, GetterUtil::getShort));
			}
			else if (ExpandoColumnConstants.STRING == attributeType) {
				_validateCustomField(customField, data, String.class);

				map.put(name, GetterUtil.getString(data));
			}
			else if (ExpandoColumnConstants.STRING_ARRAY == attributeType) {
				_validateArrayCustomField(customField, data, String.class);

				map.put(
					name, _toArray(String.class, data, GetterUtil::getString));
			}
			else if (ExpandoColumnConstants.STRING_LOCALIZED == attributeType) {
				_validateCustomField(customField, data, String.class);
				_validateCustomField(
					customField, customValue.getData_i18n(), Map.class);

				map.put(
					name,
					(Serializable)LocalizedMapUtil.getLocalizedMap(
						locale, (String)data, customValue.getData_i18n()));
			}
			else {
				map.put(name, (Serializable)data);
			}
		}

		return map;
	}

	private static Map<String, String> _getLocalizedValues(
		boolean acceptAllLanguages, int attributeType, Object value) {

		if (ExpandoColumnConstants.STRING_LOCALIZED != attributeType) {
			return null;
		}

		return LocalizedMapUtil.getI18nMap(
			acceptAllLanguages, (Map<Locale, String>)value);
	}

	private static Object _getValue(
		int attributeType, Locale locale, Object value) {

		if (ExpandoColumnConstants.STRING_LOCALIZED == attributeType) {
			Map<Locale, String> map = (Map<Locale, String>)value;

			return map.get(locale);
		}
		else if (ExpandoColumnConstants.DATE == attributeType) {
			return DateUtil.getDate(
				(Date)value, "yyyy-MM-dd'T'HH:mm:ss'Z'", locale,
				TimeZone.getTimeZone("UTC"));
		}

		return value;
	}

	private static Object _getValue(
		int attributeType, String displayType,
		Map.Entry<String, Serializable> entry, ExpandoBridge expandoBridge,
		String key) {

		Object value = entry.getValue();

		if (!_isEmpty(value)) {
			return value;
		}

		if (!ExpandoColumnConstants.isArray(attributeType)) {
			return expandoBridge.getAttributeDefault(key);
		}

		boolean selectionList = StringUtil.equals(
			displayType,
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);

		if (ExpandoColumnConstants.DOUBLE_ARRAY == attributeType) {
			if (selectionList) {
				return ArrayUtil.subset(
					GetterUtil.getDoubleValues(
						expandoBridge.getAttributeDefault(key)),
					0, 1);
			}

			return new double[] {GetterUtil.DEFAULT_DOUBLE};
		}
		else if (ExpandoColumnConstants.LONG_ARRAY == attributeType) {
			if (selectionList) {
				return ArrayUtil.subset(
					GetterUtil.getLongValues(
						expandoBridge.getAttributeDefault(key)),
					0, 1);
			}

			return new long[] {GetterUtil.DEFAULT_INTEGER};
		}
		else if (ExpandoColumnConstants.STRING_ARRAY == attributeType) {
			if (selectionList) {
				return ArrayUtil.subset(
					GetterUtil.getStringValues(
						expandoBridge.getAttributeDefault(key)),
					0, 1);
			}

			return new String[] {String.valueOf(GetterUtil.DEFAULT_BOOLEAN)};
		}

		return value;
	}

	private static boolean _isEmpty(Object value) {
		if (value == null) {
			return true;
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray() && (Array.getLength(value) == 0)) {
			return true;
		}

		if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>)value;

			if (map.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private static boolean _isValidCustomField(
		Object value, Class<?>... classes) {

		if (value == null) {
			return true;
		}

		for (Class<?> clazz : classes) {
			if (clazz.isInstance(value)) {
				return true;
			}
		}

		return false;
	}

	private static <T, U> Serializable _toArray(
		Class<? extends U> clazz, Object data, Function<Object, U> function) {

		Serializable newArray = null;

		Class<?> dataClass = data.getClass();

		if (dataClass.isArray()) {
			if (clazz.equals(dataClass.getComponentType())) {
				return (Serializable)data;
			}

			int length = Array.getLength(data);

			newArray = (Serializable)Array.newInstance(clazz, length);

			for (int i = 0; i < length; i++) {
				Array.set(newArray, i, function.apply(Array.get(data, i)));
			}
		}
		else if (data instanceof Collection) {
			Collection<?> collection = (Collection<?>)data;

			newArray = (Serializable)Array.newInstance(
				clazz, collection.size());

			int i = 0;

			Iterator<T> iterator = (Iterator<T>)collection.iterator();

			while (iterator.hasNext()) {
				Array.set(newArray, i++, function.apply(iterator.next()));
			}
		}

		return newArray;
	}

	private static CustomField _toCustomField(
		boolean acceptAllLanguages, String displayType,
		Map.Entry<String, Serializable> entry, ExpandoBridge expandoBridge,
		Locale locale) {

		String key = entry.getKey();

		int attributeType = expandoBridge.getAttributeType(key);

		if (ExpandoColumnConstants.GEOLOCATION == attributeType) {
			return new CustomField() {
				{
					setCustomValue(
						() -> {
							JSONObject jsonObject =
								JSONFactoryUtil.createJSONObject(
									String.valueOf(entry.getValue()));

							return new CustomValue() {
								{
									setGeo(
										() -> new Geo() {
											{
												setLatitude(
													() -> jsonObject.getDouble(
														"latitude"));
												setLongitude(
													() -> jsonObject.getDouble(
														"longitude"));
											}
										});
								}
							};
						});
					setDataType(() -> "Geolocation");
					setName(entry::getKey);
				}
			};
		}

		return new CustomField() {
			{
				setCustomValue(
					() -> new CustomValue() {
						{
							setData(
								() -> _getValue(
									attributeType, locale,
									_getValue(
										attributeType, displayType, entry,
										expandoBridge, key)));
							setData_i18n(
								() -> _getLocalizedValues(
									acceptAllLanguages, attributeType,
									_getValue(
										attributeType, displayType, entry,
										expandoBridge, key)));
						}
					});
				setDataType(
					() -> ExpandoColumnConstants.getDataType(attributeType));
				setName(entry::getKey);
			}
		};
	}

	private static Date _toDate(Object data) {
		if (data instanceof Date) {
			return (Date)data;
		}

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		try {
			return dateFormat.parse((String)data);
		}
		catch (ParseException parseException) {
			throw new IllegalArgumentException(
				"Unable to parse date from " + data, parseException);
		}
	}

	private static void _validateArray(CustomField customField, Object value) {
		if ((value instanceof Collection<?>) ||
			value.getClass(
			).isArray()) {

			return;
		}

		throw new IllegalArgumentException(
			"Unable to parse custom field \"" + customField.getName() + "\"");
	}

	private static void _validateArrayCustomField(
		CustomField customField, Object value, Class<?>... classes) {

		if (value == null) {
			return;
		}

		for (Class<?> clazz : classes) {
			boolean valid = true;

			if (Collection.class.isInstance(value)) {
				Collection<?> collection = (Collection<?>)value;

				Iterator<?> iterator = collection.iterator();

				if (iterator.hasNext()) {
					valid = _isValidCustomField(iterator.next(), clazz);
				}
			}
			else if (value.getClass(
					).isArray()) {

				if (Array.getLength(value) > 0) {
					valid = _isValidCustomField(Array.get(value, 0), clazz);
				}
			}
			else {
				valid = false;
			}

			if (valid) {
				return;
			}
		}

		throw new IllegalArgumentException(
			"Unable to parse custom field \"" + customField.getName() + "\"");
	}

	private static void _validateCustomField(
		CustomField customField, Object value, Class<?>... classes) {

		if (_isValidCustomField(value, classes)) {
			return;
		}

		throw new IllegalArgumentException(
			"Unable to parse custom field \"" + customField.getName() + "\"");
	}

}