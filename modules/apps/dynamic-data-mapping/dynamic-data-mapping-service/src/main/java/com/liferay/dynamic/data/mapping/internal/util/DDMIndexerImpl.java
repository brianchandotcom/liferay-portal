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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.FieldArray;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

		FieldArray fieldArray = (FieldArray)document.getField(
			DDMIndexer.DDM_FIELDS);

		if (fieldArray == null) {
			fieldArray = new FieldArray(DDMIndexer.DDM_FIELDS);

			document.add(fieldArray);
		}

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		Fields fields = toFields(ddmStructure, ddmFormValues);

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType) || indexType.equals("none")) {
					continue;
				}

				String name = null;
				String type = field.getType();
				Serializable value = null;

				if (GetterUtil.getBoolean(
						ddmStructure.getFieldProperty(
							field.getName(), "localizable"))) {

					for (Locale locale : locales) {
						name = encodeName(
							ddmStructure.getStructureId(), field.getName(),
							locale, indexType);
						value = field.getValue(locale);

						fieldArray.addField(
							createField(indexType, name, type, value, locale));
					}
				}
				else {
					name = encodeName(
						ddmStructure.getStructureId(), field.getName(), null,
						indexType);
					value = field.getValue(ddmFormValues.getDefaultLocale());

					fieldArray.addField(
						createField(indexType, name, type, value, null));
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
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
			ddmStructureFieldValue = _ddm.getIndexedFieldValue(
				ddmStructureFieldValue, structure.getFieldType(fieldName));
		}

		String ddmFieldsFieldValueSuffix =
			StringUtil.upperCaseFirstLetter(ddmStructureFieldNameParts[1]) +
				StringPool.UNDERLINE + LocaleUtil.toLanguageId(locale);

		if (ddmStructureFieldValue instanceof String[]) {
			String[] ddmStructureFieldValueArray =
				(String[])ddmStructureFieldValue;

			for (String ddmStructureFieldValueString :
					ddmStructureFieldValueArray) {

				booleanQuery.addRequiredTerm(
					StringBundler.concat(
						DDMIndexer.DDM_FIELDS, StringPool.PERIOD,
						DDMIndexer.DDM_FIELD_NAME),
					ddmStructureFieldName);
				booleanQuery.addRequiredTerm(
					StringBundler.concat(
						DDMIndexer.DDM_FIELDS, StringPool.PERIOD,
						DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
						ddmFieldsFieldValueSuffix),
					StringPool.QUOTE + ddmStructureFieldValueString +
						StringPool.QUOTE);
			}
		}
		else {
			booleanQuery.addRequiredTerm(
				StringBundler.concat(
					DDMIndexer.DDM_FIELDS, StringPool.PERIOD,
					DDMIndexer.DDM_FIELD_NAME),
				ddmStructureFieldName);
			booleanQuery.addRequiredTerm(
				StringBundler.concat(
					DDMIndexer.DDM_FIELDS, StringPool.PERIOD,
					DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
					ddmFieldsFieldValueSuffix),
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);
		}

		return new QueryFilter(
			new NestedQuery(DDMIndexer.DDM_FIELDS, booleanQuery));
	}

	@Override
	public String encodeName(long ddmStructureId, String fieldName) {
		return encodeName(ddmStructureId, fieldName, null);
	}

	@Override
	public String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		String indexType = StringPool.BLANK;
		boolean localizable = true;

		if (ddmStructureId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure != null) {
				try {
					indexType = ddmStructure.getFieldProperty(
						fieldName, "indexType");
					localizable = GetterUtil.getBoolean(
						ddmStructure.getFieldProperty(
							fieldName, "localizable"));
				}
				catch (PortalException portalException) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Unable to obtain index tpe for field ", fieldName,
							" and DDM structure ID ", ddmStructureId),
						portalException);
				}
			}
		}

		if (localizable) {
			return encodeName(ddmStructureId, fieldName, locale, indexType);
		}

		return encodeName(ddmStructureId, fieldName, null, indexType);
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

				if (Validator.isNull(indexType) || indexType.equals("none")) {
					continue;
				}

				Serializable value = field.getValue(locale);

				if (value instanceof Boolean || value instanceof Number) {
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

					if (type.equals(DDMImpl.TYPE_SELECT)) {
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
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}
		}

		return sb.toString();
	}

	@Override
	public String getValueFieldName(String indexType, Locale locale) {
		String valueFieldName = DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX;

		if (indexType != null) {
			valueFieldName =
				valueFieldName.concat(
					StringUtil.upperCaseFirstLetter(indexType));
		}

		if (locale != null) {
			valueFieldName =
				StringBundler.concat(
					valueFieldName, StringPool.UNDERLINE,
					LocaleUtil.toLanguageId(locale));
		}

		return valueFieldName;
	}

	protected Document createDocument(
			String indexType, String type, Serializable value, Locale locale)
		throws JSONException {

		Document document = new DocumentImpl();

		String name = getValueFieldName(indexType, locale);

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
			document.addDateSortable(name, (Date)value);
		}
		else if (value instanceof Date[]) {
			document.addDateSortable(name, (Date[])value);
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
			String[] valuesString = ArrayUtil.toStringArray((Object[])value);

			if (indexType.equals("keyword")) {
				document.addKeywordSortable(name, valuesString);
			}
			else {
				document.addTextSortable(name, valuesString);
			}
		}
		else {
			String valueString = String.valueOf(value);

			if (type.equals(DDMFormFieldType.GEOLOCATION)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				double latitude = jsonObject.getDouble("latitude", 0);
				double longitude = jsonObject.getDouble("longitude", 0);

				document.addGeoLocation(
					name.concat("_geolocation"), latitude, longitude);
			}
			else if (type.equals(DDMImpl.TYPE_SELECT)) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
					valueString);

				String[] stringArray = ArrayUtil.toStringArray(jsonArray);

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

		return document;
	}

	protected com.liferay.portal.kernel.search.Field createField(
			String indexType, String name, String type, Serializable value,
			Locale locale)
		throws PortalException {

		Document document = createDocument(indexType, type, value, locale);

		com.liferay.portal.kernel.search.Field field =
			new com.liferay.portal.kernel.search.Field("");

		field.addField(
			new com.liferay.portal.kernel.search.Field(
				DDMIndexer.DDM_FIELD_NAME, name));

		Stream.of(
			document.getFields()
		).map(
			Map::entrySet
		).flatMap(
			Set::stream
		).map(
			Map.Entry::getValue
		).forEach(
			entry -> {
				if (entry.getGeoLocationPoint() != null) {
					com.liferay.portal.kernel.search.Field geolocationField =
						new com.liferay.portal.kernel.search.Field(
							entry.getName());

					geolocationField.setGeoLocationPoint(
						entry.getGeoLocationPoint());

					field.addField(geolocationField);
				}
				else {
					field.addField(
						new com.liferay.portal.kernel.search.Field(
							entry.getName(), entry.getValue()));
				}

				Stream<com.liferay.portal.kernel.search.Field> stream =
					field.getFields(
					).stream();

				Optional<com.liferay.portal.kernel.search.Field>
					valueFieldName = stream.filter(
						currentField -> currentField.getName(
						).equals(
							"valueFieldName"
						)
					).findFirst();

				if (!valueFieldName.isPresent() &&
					!entry.getName().contains(
						com.liferay.portal.kernel.search.Field.
							SORTABLE_FIELD_SUFFIX)) {

					field.addField(
						new com.liferay.portal.kernel.search.Field(
							"valueFieldName", entry.getName()));
				}
			}
		);

		return field;
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
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesToFieldsConverter(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	protected Fields toFields(
		DDMStructure ddmStructure, DDMFormValues ddmFormValues) {

		try {
			return _ddmFormValuesToFieldsConverter.convert(
				ddmStructure, ddmFormValues);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to convert DDMFormValues to Fields", portalException);
		}

		return new Fields();
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

	private DDM _ddm;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private DDMStructureLocalService _ddmStructureLocalService;

}