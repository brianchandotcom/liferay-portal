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

import com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortBuilderFactory;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration",
	immediate = true, service = DDMIndexer.class
)
public class DDMIndexerImpl implements DDMIndexer {

	@Override
	public void addAttributes(
		Document document, DDMStructure ddmStructure,
		DDMFormValues ddmFormValues) {

		boolean legacyDDMIndexFieldsEnabled = isLegacyDDMIndexFieldsEnabled();

		FieldArray fieldArray = (FieldArray)document.getField(DDM_FIELD_ARRAY);

		if ((fieldArray == null) && !legacyDDMIndexFieldsEnabled) {
			fieldArray = new FieldArray(DDM_FIELD_ARRAY);

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

				DDMFormField ddmFormField = ddmStructure.getDDMFormField(
					field.getName());
				String name = null;
				Serializable value = null;

				if (GetterUtil.getBoolean(
						ddmStructure.getFieldProperty(
							field.getName(), "localizable"))) {

					for (Locale locale : locales) {
						name = encodeName(
							ddmStructure.getStructureId(),
							ddmFormField.getFieldReference(), locale,
							indexType);
						value = field.getValue(locale);

						if (legacyDDMIndexFieldsEnabled) {
							addToDocument(
								document, field, indexType, name, value);
						}
						else {
							fieldArray.addField(
								createField(
									ddmFormField, field, indexType, locale,
									name, value));
						}
					}
				}
				else {
					name = encodeName(
						ddmStructure.getStructureId(),
						ddmFormField.getFieldReference(), null, indexType);
					value = field.getValue(ddmFormValues.getDefaultLocale());

					if (legacyDDMIndexFieldsEnabled) {
						addToDocument(document, field, indexType, name, value);
					}
					else {
						fieldArray.addField(
							createField(
								ddmFormField, field, indexType, null, name,
								value));
					}
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
	public Sort createDDMStructureFieldSort(
			DDMStructure ddmStructure, String fieldReference, Locale locale,
			SortOrder sortOrder)
		throws PortalException {

		DDMFormField ddmFormField =
			ddmStructure.getDDMFormFieldByFieldReference(fieldReference);

		if (GetterUtil.getBoolean(ddmFormField.getProperty("localizable"))) {
			if (locale == null) {
				throw new IllegalArgumentException(
					"Locale cannot be null if the dynamic data mapping form " +
						"field is localizable");
			}
		}
		else {
			locale = null;
		}

		StringBundler sb = new StringBundler(5);

		if (isLegacyDDMIndexFieldsEnabled()) {
			sb.append(
				encodeName(
					ddmStructure.getStructureId(), fieldReference, locale));
		}
		else {
			sb.append(DDMIndexer.DDM_FIELD_ARRAY);
			sb.append(StringPool.PERIOD);

			String indexType = ddmStructure.getFieldPropertyByFieldReference(
				fieldReference, "indexType");

			sb.append(getValueFieldName(indexType, locale));
		}

		sb.append(StringPool.UNDERLINE);

		String ddmFormFieldType = ddmFormField.getType();

		if (Objects.equals(ddmFormFieldType, DDMFormFieldType.DECIMAL) ||
			Objects.equals(ddmFormFieldType, DDMFormFieldType.INTEGER) ||
			Objects.equals(ddmFormFieldType, DDMFormFieldType.NUMBER) ||
			Objects.equals(ddmFormFieldType, DDMFormFieldType.NUMERIC)) {

			sb.append("Number");
		}
		else {
			sb.append("String");
		}

		FieldSort fieldSort = sorts.field(
			com.liferay.portal.kernel.search.Field.getSortableFieldName(
				sb.toString()),
			sortOrder);

		if (isLegacyDDMIndexFieldsEnabled()) {
			return fieldSort;
		}

		NestedSort nestedSort = sorts.nested(DDMIndexer.DDM_FIELD_ARRAY);

		nestedSort.setFilterQuery(
			queries.term(
				StringBundler.concat(
					DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
					DDMIndexer.DDM_FIELD_NAME),
				encodeName(
					ddmStructure.getStructureId(), fieldReference, locale)));

		fieldSort.setNestedSort(nestedSort);

		return fieldSort;
	}

	@Override
	public Sort createDDMStructureFieldSort(
			String ddmStructureFieldName, Locale locale, SortOrder sortOrder)
		throws PortalException {

		String[] ddmStructureFieldNameParts = StringUtil.split(
			ddmStructureFieldName, DDM_FIELD_SEPARATOR);

		long ddmStructureId = GetterUtil.getLong(ddmStructureFieldNameParts[2]);

		String fieldReference = StringUtil.replaceLast(
			ddmStructureFieldNameParts[3],
			StringPool.UNDERLINE.concat(LocaleUtil.toLanguageId(locale)),
			StringPool.BLANK);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		return createDDMStructureFieldSort(
			ddmStructure, fieldReference, locale, sortOrder);
	}

	public QueryFilter createFieldValueQueryFilter(
			DDMStructure ddmStructure, String fieldReference, Locale locale,
			Serializable value)
		throws Exception {

		String indexType = ddmStructure.getFieldPropertyByFieldReference(
			fieldReference, "indexType");

		return createFieldValueQueryFilter(
			ddmStructure,
			encodeName(ddmStructure.getStructureId(), fieldReference, locale),
			value, fieldReference, indexType, locale);
	}

	@Override
	public QueryFilter createFieldValueQueryFilter(
			String ddmStructureFieldName, Serializable ddmStructureFieldValue,
			Locale locale)
		throws Exception {

		String[] ddmStructureFieldNameParts = StringUtil.split(
			ddmStructureFieldName, DDM_FIELD_SEPARATOR);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			GetterUtil.getLong(ddmStructureFieldNameParts[2]));

		String fieldReference = StringUtil.replaceLast(
			ddmStructureFieldNameParts[3],
			StringPool.UNDERLINE.concat(LocaleUtil.toLanguageId(locale)),
			StringPool.BLANK);

		return createFieldValueQueryFilter(
			ddmStructure, ddmStructureFieldName, ddmStructureFieldValue,
			fieldReference, ddmStructureFieldNameParts[1], locale);
	}

	@Override
	public String encodeName(long ddmStructureId, String fieldReference) {
		return encodeName(ddmStructureId, fieldReference, null);
	}

	@Override
	public String encodeName(
		long ddmStructureId, String fieldReference, Locale locale) {

		String indexType = StringPool.BLANK;
		boolean localizable = true;

		if (ddmStructureId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure != null) {
				try {
					indexType = ddmStructure.getFieldPropertyByFieldReference(
						fieldReference, "indexType");
					localizable = GetterUtil.getBoolean(
						ddmStructure.getFieldPropertyByFieldReference(
							fieldReference, "localizable"));
				}
				catch (PortalException portalException) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Unable to obtain index tpe for field ",
							fieldReference, " and DDM structure ID ",
							ddmStructureId),
						portalException);
				}
			}
		}

		if (localizable) {
			return encodeName(
				ddmStructureId, fieldReference, locale, indexType);
		}

		return encodeName(ddmStructureId, fieldReference, null, indexType);
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
				}
				else if (value instanceof Date) {
					sb.append(dateFormat.format(value));
				}
				else if (value instanceof Date[]) {
					Date[] dates = (Date[])value;

					for (int i = 0; i < dates.length; i++) {
						sb.append(dateFormat.format(dates[i]));

						if (i < (dates.length - 1)) {
							sb.append(StringPool.SPACE);
						}
					}
				}
				else if (value instanceof Object[]) {
					Object[] values = (Object[])value;

					for (int i = 0; i < values.length; i++) {
						sb.append(values[i]);

						if (i < (values.length - 1)) {
							sb.append(StringPool.SPACE);
						}
					}
				}
				else {
					String valueString = _getSortableValue(
						ddmStructure.getDDMFormField(field.getName()), locale,
						value);

					String type = field.getType();

					if (type.equals(DDMFormFieldTypeConstants.SELECT)) {
						JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
							valueString);

						String[] stringArray = ArrayUtil.toStringArray(
							jsonArray);

						sb.append(stringArray);
					}
					else {
						if (type.equals(DDMFormFieldTypeConstants.RICH_TEXT)) {
							valueString = HtmlUtil.extractText(valueString);
						}

						sb.append(valueString);
					}
				}

				sb.append(StringPool.SPACE);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@Override
	public String getValueFieldName(String indexType) {
		return getValueFieldName(indexType, null);
	}

	@Override
	public String getValueFieldName(String indexType, Locale locale) {
		String valueFieldName = DDM_VALUE_FIELD_NAME_PREFIX;

		if (indexType != null) {
			valueFieldName = valueFieldName.concat(
				StringUtil.upperCaseFirstLetter(indexType));
		}

		if (locale != null) {
			valueFieldName = StringBundler.concat(
				valueFieldName, StringPool.UNDERLINE,
				LocaleUtil.toLanguageId(locale));
		}

		return valueFieldName;
	}

	@Override
	public boolean isLegacyDDMIndexFieldsEnabled() {
		return _ddmIndexerConfiguration.enableLegacyDDMIndexFields();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmIndexerConfiguration = ConfigurableUtil.createConfigurable(
			DDMIndexerConfiguration.class, properties);
	}

	protected void addFieldValueRequiredTerm(
		BooleanQuery booleanQuery, String ddmStructureFieldName,
		String ddmStructureFieldValue, String indexType, Locale locale) {

		if (isLegacyDDMIndexFieldsEnabled()) {
			booleanQuery.addRequiredTerm(
				ddmStructureFieldName,
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);

			return;
		}

		booleanQuery.addRequiredTerm(
			StringBundler.concat(
				DDM_FIELD_ARRAY, StringPool.PERIOD, DDM_FIELD_NAME),
			ddmStructureFieldName);
		booleanQuery.addRequiredTerm(
			StringBundler.concat(
				DDM_FIELD_ARRAY, StringPool.PERIOD,
				getValueFieldName(indexType, locale)),
			StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);
	}

	protected void addToDocument(
			Document document, Field field, String indexType, String name,
			Serializable value)
		throws PortalException {

		addToDocument(document, field, indexType, name, value, value);
	}

	protected void addToDocument(
			Document document, Field field, String indexType, String name,
			Serializable sortableValue, Serializable value)
		throws PortalException {

		if (value == null) {
		}
		else if (value instanceof BigDecimal) {
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
			String sortableValueString = StringUtil.toLowerCase(
				String.valueOf(sortableValue));
			String valueString = String.valueOf(value);

			String type = field.getType();

			if (type.equals(DDMFormFieldTypeConstants.GEOLOCATION)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				double latitude = jsonObject.getDouble("lat", 0);
				double longitude = jsonObject.getDouble("lng", 0);

				document.addGeoLocation(
					name.concat("_geolocation"), latitude, longitude);
			}
			else if (type.equals(DDMFormFieldTypeConstants.SELECT)) {
				document.addKeyword(
					_getSortableFieldName(name),
					ArrayUtil.toStringArray(
						JSONFactoryUtil.createJSONArray(sortableValueString)));
				document.addKeyword(
					name,
					ArrayUtil.toStringArray(
						JSONFactoryUtil.createJSONArray(valueString)));
			}
			else {
				if (type.equals(DDMFormFieldTypeConstants.RICH_TEXT)) {
					valueString = HtmlUtil.extractText(valueString);
					sortableValueString = HtmlUtil.extractText(
						sortableValueString);
				}

				_createSortableTextField(document, name, sortableValueString);

				if (indexType.equals("keyword")) {
					document.addKeyword(name, valueString);
				}
				else {
					document.addText(name, valueString);
				}
			}
		}
	}

	protected com.liferay.portal.kernel.search.Field createField(
			DDMFormField ddmFormField, Field ddmStructureField,
			String indexType, Locale locale, String name, Serializable value)
		throws PortalException {

		com.liferay.portal.kernel.search.Field ddmField =
			new com.liferay.portal.kernel.search.Field(StringPool.BLANK);

		List<com.liferay.portal.kernel.search.Field> sortedFields =
			new SortedArrayList<>(
				Comparator.comparing(
					com.liferay.portal.kernel.search.Field::getName));

		Document document = new DocumentImpl();

		String valueFieldName = getValueFieldName(indexType, locale);

		addToDocument(
			document, ddmStructureField, indexType, valueFieldName,
			_getSortableValue(ddmFormField, locale, value), value);

		Map<String, com.liferay.portal.kernel.search.Field> documentFields =
			document.getFields();

		sortedFields.addAll(documentFields.values());

		sortedFields.add(
			new com.liferay.portal.kernel.search.Field(DDM_FIELD_NAME, name));

		sortedFields.add(
			new com.liferay.portal.kernel.search.Field(
				DDM_VALUE_FIELD_NAME, valueFieldName));

		sortedFields.forEach(ddmField::addField);

		return ddmField;
	}

	protected QueryFilter createFieldValueQueryFilter(
			DDMStructure ddmStructure, String ddmStructureFieldName,
			Serializable ddmStructureFieldValue, String fieldReference,
			String indexType, Locale locale)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		if (ddmStructure.hasFieldByFieldReference(fieldReference)) {
			ddmStructureFieldValue = _ddm.getIndexedFieldValue(
				ddmStructureFieldValue,
				ddmStructure.getFieldPropertyByFieldReference(
					fieldReference, "type"));
		}

		if (ddmStructureFieldValue instanceof String[]) {
			String[] ddmStructureFieldValueArray =
				(String[])ddmStructureFieldValue;

			for (String ddmStructureFieldValueString :
					ddmStructureFieldValueArray) {

				addFieldValueRequiredTerm(
					booleanQuery, ddmStructureFieldName,
					ddmStructureFieldValueString, indexType, locale);
			}
		}
		else {
			addFieldValueRequiredTerm(
				booleanQuery, ddmStructureFieldName,
				String.valueOf(ddmStructureFieldValue), indexType, locale);
		}

		if (isLegacyDDMIndexFieldsEnabled()) {
			return new QueryFilter(booleanQuery);
		}

		return new QueryFilter(new NestedQuery(DDM_FIELD_ARRAY, booleanQuery));
	}

	protected String encodeName(
		long ddmStructureId, String fieldReference, Locale locale,
		String indexType) {

		StringBundler sb = new StringBundler(8);

		sb.append(DDM_FIELD_PREFIX);

		if (Validator.isNotNull(indexType)) {
			sb.append(indexType);
			sb.append(DDM_FIELD_SEPARATOR);
		}

		sb.append(ddmStructureId);
		sb.append(DDM_FIELD_SEPARATOR);
		sb.append(fieldReference);

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

	@Reference
	protected Queries queries;

	@Reference
	protected SortBuilderFactory sortBuilderFactory;

	@Reference
	protected Sorts sorts;

	private void _createSortableTextField(
		Document document, String name, String sortableValueString) {

		if (Validator.isNull(sortableValueString)) {
			return;
		}

		if (sortableValueString.length() >
				_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH) {

			sortableValueString = sortableValueString.substring(
				0, _SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH);
		}

		document.addKeyword(_getSortableFieldName(name), sortableValueString);
	}

	private String _getSortableFieldName(String name) {
		return com.liferay.portal.kernel.search.Field.getSortableFieldName(
			name + "_String");
	}

	private String _getSortableValue(
		DDMFormField ddmFormField, Locale locale, Serializable value) {

		if (Validator.isNull(value)) {
			return null;
		}

		String sortableValue = String.valueOf(value);

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)ddmFormField.getProperty("options");

		Map<String, LocalizedValue> map = ddmFormFieldOptions.getOptions();

		for (Map.Entry<String, LocalizedValue> entry : map.entrySet()) {
			LocalizedValue localizedValue = entry.getValue();

			sortableValue = StringUtil.replace(
				sortableValue, entry.getKey(),
				localizedValue.getString(locale));
		}

		return sortableValue;
	}

	private static final int _SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.INDEX_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH));

	private static final Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

	private DDM _ddm;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private volatile DDMIndexerConfiguration _ddmIndexerConfiguration;
	private DDMStructureLocalService _ddmStructureLocalService;

}