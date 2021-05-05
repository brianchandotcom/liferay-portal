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

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.headless.delivery.dto.v1_0.CustomField;
import com.liferay.headless.delivery.dto.v1_0.CustomValue;
import com.liferay.headless.delivery.dto.v1_0.Geo;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier Gamarra
 */
public class CustomFieldsUtil {

	public static CustomField[] toCustomFields(
		boolean acceptAllLanguages, String className, long classPK,
		long companyId, Locale locale) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className, classPK);

		Map<String, Serializable> attributes = expandoBridge.getAttributes();

		Set<Map.Entry<String, Serializable>> entries = attributes.entrySet();

		Stream<Map.Entry<String, Serializable>> entriesStream =
			entries.stream();

		return entriesStream.filter(
			entry -> {
				UnicodeProperties unicodeProperties =
					expandoBridge.getAttributeProperties(entry.getKey());

				return !GetterUtil.getBoolean(
					unicodeProperties.getProperty(
						ExpandoColumnConstants.PROPERTY_HIDDEN));
			}
		).map(
			entry -> _toCustomField(
				acceptAllLanguages, entry, expandoBridge, locale)
		).toArray(
			CustomField[]::new
		);
	}

	public static Map<String, Serializable> toMap(
		String className, long companyId, CustomField[] customFields,
		Locale locale) {

		if (customFields == null) {
			return null;
		}

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className);

		return Stream.of(
			customFields
		).collect(
			Collectors.toMap(
				CustomField::getName,
				customField -> {
					int attributeType = expandoBridge.getAttributeType(
						customField.getName());

					CustomValue customValue = customField.getCustomValue();

					Object data = customValue.getData();

					if (ExpandoColumnConstants.DATE == attributeType) {
						return _parseDate(String.valueOf(data));
					}
					else if (ExpandoColumnConstants.DOUBLE_ARRAY ==
								attributeType) {

						return ArrayUtil.toDoubleArray((List<Number>)data);
					}
					else if (ExpandoColumnConstants.FLOAT_ARRAY ==
								attributeType) {

						return ArrayUtil.toFloatArray((List<Number>)data);
					}
					else if (ExpandoColumnConstants.GEOLOCATION ==
								attributeType) {

						Geo geo = customValue.getGeo();

						return JSONUtil.put(
							"latitude", geo.getLatitude()
						).put(
							"longitude", geo.getLongitude()
						).toString();
					}
					else if (ExpandoColumnConstants.INTEGER_ARRAY ==
								attributeType) {

						return ArrayUtil.toIntArray((List<Number>)data);
					}
					else if (ExpandoColumnConstants.LONG_ARRAY ==
								attributeType) {

						return ArrayUtil.toLongArray((List<Number>)data);
					}
					else if (ExpandoColumnConstants.STRING_ARRAY ==
								attributeType) {

						List<?> list = (List<?>)data;

						return list.toArray(new String[0]);
					}
					else if (ExpandoColumnConstants.STRING_LOCALIZED ==
								attributeType) {

						return (Serializable)LocalizedMapUtil.getLocalizedMap(
							locale, (String)data, customValue.getData_i18n());
					}

					return (Serializable)data;
				})
		);
	}

	private static Map<String, String> _getLocalizedValues(
		boolean acceptAllLanguages, int attributeType, Object value) {

		if (ExpandoColumnConstants.STRING_LOCALIZED == attributeType) {
			Map<Locale, String> map = (Map<Locale, String>)value;

			return LocalizedMapUtil.getI18nMap(acceptAllLanguages, map);
		}

		return null;
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

	private static Serializable _parseDate(String data) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		try {
			return dateFormat.parse(data);
		}
		catch (ParseException parseException) {
			throw new IllegalArgumentException(
				"Unable to parse date from " + data, parseException);
		}
	}

	private static CustomField _toCustomField(
		boolean acceptAllLanguages, Map.Entry<String, Serializable> entry,
		ExpandoBridge expandoBridge, Locale locale) {

		String key = entry.getKey();

		int attributeType = expandoBridge.getAttributeType(key);

		if (ExpandoColumnConstants.GEOLOCATION == attributeType) {
			return new CustomField() {
				{
					dataType = "Geolocation";
					name = entry.getKey();

					setCustomValue(
						() -> {
							JSONObject jsonObject =
								JSONFactoryUtil.createJSONObject(
									String.valueOf(entry.getValue()));

							return new CustomValue() {
								{
									geo = new Geo() {
										{
											latitude = jsonObject.getDouble(
												"latitude");
											longitude = jsonObject.getDouble(
												"longitude");
										}
									};
								}
							};
						});
				}
			};
		}

		return new CustomField() {
			{
				customValue = new CustomValue() {
					{
						Object value = entry.getValue();

						if (_isEmpty(entry.getValue())) {
							value = expandoBridge.getAttributeDefault(key);
						}

						data = _getValue(attributeType, locale, value);
						data_i18n = _getLocalizedValues(
							acceptAllLanguages, attributeType, value);
					}
				};
				dataType = ExpandoColumnConstants.getDataType(attributeType);
				name = entry.getKey();
			}
		};
	}

}