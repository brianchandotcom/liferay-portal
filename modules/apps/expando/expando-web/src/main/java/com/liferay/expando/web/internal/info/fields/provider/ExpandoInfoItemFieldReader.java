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

package com.liferay.expando.web.internal.info.fields.provider;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoConverterUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.field.reader.LocalizedInfoItemFieldReader;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 * @author Jorge Ferrer
 */
public class ExpandoInfoItemFieldReader
	implements LocalizedInfoItemFieldReader {

	public ExpandoInfoItemFieldReader(
		String attributeName, ExpandoBridge expandoBridge) {

		_attributeName = attributeName;
		_expandoBridge = expandoBridge;
	}

	@Override
	public InfoField getField() {
		InfoFieldType fieldType = TextInfoFieldType.INSTANCE;

		InfoLocalizedValue labelInfoLocalizedValue = InfoLocalizedValue.builder(
		).addValue(
			LocaleUtil.getDefault(), _attributeName
		).defaultLocale(
			LocaleUtil.getDefault()
		).build();

		return new InfoField(fieldType, labelInfoLocalizedValue, getName());
	}

	public String getName() {
		return _CUSTOM_FIELD_PREFIX +
			_attributeName.replaceAll("\\W", StringPool.UNDERLINE);
	}

	@Override
	public Object getValue(Object model) {
		return getValue(model, LocaleUtil.getDefault());
	}

	@Override
	public Object getValue(Object model, Locale locale) {
		if (!(model instanceof ClassedModel)) {
			return _expandoBridge.getAttributeDefault(_attributeName);
		}

		ClassedModel classedModel = (ClassedModel)model;

		_expandoBridge.setClassPK(
			GetterUtil.getLong(classedModel.getPrimaryKeyObj()));

		Serializable attributeValue = _expandoBridge.getAttribute(
			_attributeName, false);

		if (Validator.isNull(attributeValue)) {
			return _expandoBridge.getAttributeDefault(_attributeName);
		}

		int attributeType = _expandoBridge.getAttributeType(_attributeName);

		if (attributeType == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((boolean[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.DATE) {
			DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.FULL, DateFormat.FULL, locale);

			return dateFormat.format((Date)attributeValue);
		}
		else if (attributeType == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((double[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.FLOAT_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((float[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.GEOLOCATION) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					attributeValue.toString());

				StringBundler sb = new StringBundler(3);

				sb.append(jsonObject.get("latitude"));
				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(jsonObject.get("longitude"));

				attributeValue = sb.toString();
			}
			catch (JSONException jsonException) {
				_log.error("Unable to parse geolocation JSON", jsonException);
			}
		}
		else if (attributeType == ExpandoColumnConstants.INTEGER_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((int[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.LONG_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((long[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.NUMBER_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((double[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.SHORT_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((short[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType == ExpandoColumnConstants.STRING_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((String[])attributeValue),
				StringPool.COMMA_AND_SPACE);
		}
		else if (attributeType ==
					ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {

			Map<Locale, String[]> values =
				(Map<Locale, String[]>)attributeValue;

			Map<Locale, String[]> defaultValues =
				(Map<Locale, String[]>)_expandoBridge.getAttributeDefault(
					_attributeName);

			attributeValue = values.getOrDefault(
				locale, defaultValues.get(locale));
		}
		else if (attributeType == ExpandoColumnConstants.STRING_LOCALIZED) {
			Map<Locale, String> values = (Map<Locale, String>)attributeValue;

			Map<Locale, String> defaultValues =
				(Map<Locale, String>)_expandoBridge.getAttributeDefault(
					_attributeName);

			attributeValue = values.getOrDefault(
				locale, defaultValues.get(locale));

			if (attributeValue == null) {
				return StringPool.BLANK;
			}

			return attributeValue;
		}

		return ExpandoConverterUtil.getStringFromAttribute(
			attributeType, attributeValue);
	}

	private static final String _CUSTOM_FIELD_PREFIX = "_CUSTOM_FIELD_";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoInfoItemFieldReader.class);

	private final String _attributeName;
	private final ExpandoBridge _expandoBridge;

}