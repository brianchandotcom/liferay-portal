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

package com.liferay.dynamic.data.mapping.util.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(immediate = true, service = DDMIndexer.class)
public class DDMIndexerImpl implements DDMIndexer {

	@Override
	public void addAttributes(
		Document document, DDMStructure ddmStructure,
		DDMFormValues ddmFormValues) {

		long groupId = GetterUtil.getLong(
			document.get(com.liferay.portal.kernel.search.Field.GROUP_ID));

		Set<Locale> locales = LanguageUtil.getAvailableLocales(groupId);

		Fields fields = toFields(ddmStructure, ddmFormValues);

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				for (Locale locale : locales) {
					String name = encodeName(
						ddmStructure.getStructureId(), field.getName(), locale,
						indexType);

					Serializable value = field.getValue(locale);

					if (value instanceof BigDecimal) {
						document.addNumberSortable(name, (BigDecimal)value);
					}
					else if (value instanceof BigDecimal[]) {
						document.addNumberSortable(name, (BigDecimal[])value);
					}
					else if (value instanceof Boolean) {
						document.addKeywordSortable(name, (Boolean)value);
					}
					else if (value instanceof Boolean[]) {
						document.addKeywordSortable(name, (Boolean[])value);
					}
					else if (value instanceof Date) {
						document.addDate(name, (Date)value);
					}
					else if (value instanceof Date[]) {
						document.addDate(name, (Date[])value);
					}
					else if (value instanceof Double) {
						document.addNumberSortable(name, (Double)value);
					}
					else if (value instanceof Double[]) {
						document.addNumberSortable(name, (Double[])value);
					}
					else if (value instanceof Integer) {
						document.addNumberSortable(name, (Integer)value);
					}
					else if (value instanceof Integer[]) {
						document.addNumberSortable(name, (Integer[])value);
					}
					else if (value instanceof Long) {
						document.addNumberSortable(name, (Long)value);
					}
					else if (value instanceof Long[]) {
						document.addNumberSortable(name, (Long[])value);
					}
					else if (value instanceof Float) {
						document.addNumberSortable(name, (Float)value);
					}
					else if (value instanceof Float[]) {
						document.addNumberSortable(name, (Float[])value);
					}
					else if (value instanceof Number[]) {
						Number[] numbers = (Number[])value;

						Double[] doubles = new Double[numbers.length];

						for (int i = 0; i < numbers.length; i++) {
							doubles[i] = numbers[i].doubleValue();
						}

						document.addNumberSortable(name, doubles);
					}
					else if (value instanceof Object[]) {
						String[] valuesString = ArrayUtil.toStringArray(
							(Object[])value);

						if (indexType.equals("keyword")) {
							document.addKeywordSortable(name, valuesString);
						}
						else {
							document.addTextSortable(name, valuesString);
						}
					}
					else {
						String valueString = String.valueOf(value);

						String type = field.getType();

						if (type.equals(DDMFormFieldType.GEOLOCATION)) {
							JSONObject geolocationJSONObject =
								JSONFactoryUtil.createJSONObject(valueString);

							double latitude = geolocationJSONObject.getDouble(
								"latitude");
							double longitude = geolocationJSONObject.getDouble(
								"longitude");

							document.addGeoLocation(latitude, longitude);
						}
						else if (type.equals(DDMImpl.TYPE_RADIO) ||
								 type.equals(DDMImpl.TYPE_SELECT)) {

							JSONArray jsonArray =
								JSONFactoryUtil.createJSONArray(valueString);

							String[] stringArray = ArrayUtil.toStringArray(
								jsonArray);

							document.addKeywordSortable(name, stringArray);
						}
						else {
							if (type.equals(DDMImpl.TYPE_DDM_TEXT_HTML)) {
								valueString = HtmlUtil.extractText(valueString);
							}

							if (indexType.equals("keyword")) {
								document.addKeywordSortable(name, valueString);
							}
							else {
								document.addTextSortable(name, valueString);
							}
						}
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	@Override
	public QueryFilter createFieldValueQueryFilter(
			String ddmStructureFieldName, Serializable ddmStructureFieldValue,
			Locale locale)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		String[] ddmStructureFieldNameParts = StringUtil.split(
			ddmStructureFieldName, DDMIndexer.DDM_FIELD_SEPARATOR);

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			GetterUtil.getLong(ddmStructureFieldNameParts[2]));

		String fieldName = StringUtil.replaceLast(
			ddmStructureFieldNameParts[3],
			StringPool.UNDERLINE.concat(LocaleUtil.toLanguageId(locale)),
			StringPool.BLANK);

		if (structure.hasField(fieldName)) {
			ddmStructureFieldValue = DDMUtil.getIndexedFieldValue(
				ddmStructureFieldValue, structure.getFieldType(fieldName));
		}

		booleanQuery.addRequiredTerm(
			ddmStructureFieldName,
			StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);

		return new QueryFilter(booleanQuery);
	}

	@Override
	public String encodeName(long ddmStructureId, String fieldName) {
		return encodeName(ddmStructureId, fieldName, null);
	}

	@Override
	public String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		String indexType = StringPool.BLANK;

		if (ddmStructureId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure != null) {
				try {
					indexType = ddmStructure.getFieldProperty(
						fieldName, "indexType");
				}
				catch (PortalException e) {
					throw new IllegalArgumentException(
						"Unable to obtain index tpe for field " + fieldName +
							" and DDM structure ID " + ddmStructureId);
				}
			}
		}

		return encodeName(ddmStructureId, fieldName, locale, indexType);
	}

	@Override
	public String extractIndexableAttributes(
		DDMStructure ddmStructure, DDMFormValues ddmFormValues, Locale locale) {

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		StringBundler sb = new StringBundler();

		Fields fields = toFields(ddmStructure, ddmFormValues);

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				Serializable value = field.getValue(locale);

				if ((value instanceof Boolean) || (value instanceof Number)) {
					sb.append(value);
					sb.append(StringPool.SPACE);
				}
				else if (value instanceof Date) {
					sb.append(dateFormat.format(value));
					sb.append(StringPool.SPACE);
				}
				else if (value instanceof Date[]) {
					Date[] dates = (Date[])value;

					for (Date date : dates) {
						sb.append(dateFormat.format(date));
						sb.append(StringPool.SPACE);
					}
				}
				else if (value instanceof Object[]) {
					Object[] values = (Object[])value;

					for (Object object : values) {
						sb.append(object);
						sb.append(StringPool.SPACE);
					}
				}
				else {
					String valueString = String.valueOf(value);

					String type = field.getType();

					if (type.equals(DDMImpl.TYPE_RADIO) ||
						type.equals(DDMImpl.TYPE_SELECT)) {

						JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
							valueString);

						String[] stringArray = ArrayUtil.toStringArray(
							jsonArray);

						sb.append(stringArray);
						sb.append(StringPool.SPACE);
					}
					else {
						if (type.equals(DDMImpl.TYPE_DDM_TEXT_HTML)) {
							valueString = HtmlUtil.extractText(valueString);
						}

						sb.append(valueString);
						sb.append(StringPool.SPACE);
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return sb.toString();
	}

	protected String encodeName(
		long ddmStructureId, String fieldName, Locale locale,
		String indexType) {

		StringBundler sb = new StringBundler(8);

		sb.append(DDM_FIELD_PREFIX);

		if (Validator.isNotNull(indexType)) {
			sb.append(indexType);
			sb.append(DDM_FIELD_SEPARATOR);
		}

		sb.append(ddmStructureId);
		sb.append(DDM_FIELD_SEPARATOR);
		sb.append(fieldName);

		if (locale != null) {
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));
		}

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	protected Fields toFields(
		DDMStructure ddmStructure, DDMFormValues ddmFormValues) {

		try {
			return DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);
		}
		catch (PortalException pe) {
			_log.error("Unable to convert DDMFormValues to Fields", pe);
		}

		return new Fields();
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

	private volatile DDMStructureLocalService _ddmStructureLocalService;

}