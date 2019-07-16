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

package com.liferay.dynamic.data.lists.internal.exporter;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public abstract class BaseDDLExporter implements DDLExporter {

	@Override
	public byte[] export(long recordSetId) throws Exception {
		return doExport(
			recordSetId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long recordSetId, int status) throws Exception {
		return doExport(
			recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long recordSetId, int status, int start, int end)
		throws Exception {

		return doExport(recordSetId, status, start, end, null);
	}

	@Override
	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		return doExport(recordSetId, status, start, end, orderByComparator);
	}

	@Override
	public Locale getLocale() {
		if (_locale == null) {
			_locale = LocaleUtil.getSiteDefault();
		}

		return _locale;
	}

	@Override
	public void setLocale(Locale locale) {
		_locale = locale;
	}

	protected abstract byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception;

	protected String formatDate(
		Date date, DateTimeFormatter dateTimeFormatter) {

		LocalDateTime localDateTime = LocalDateTime.ofInstant(
			date.toInstant(), ZoneId.systemDefault());

		return dateTimeFormatter.format(localDateTime);
	}

	protected DateTimeFormatter getDateTimeFormatter() {
		DateTimeFormatter dateTimeFormatter =
			DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

		return dateTimeFormatter.withLocale(getLocale());
	}

	protected abstract DDLRecordSetVersionService
		getDDLRecordSetVersionService();

	protected DDMFormFieldRenderedValue getDDMFormFieldRenderedValue(
		int scope, DDMFormField ddmFormField,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap) {

		List<DDMFormFieldValue> ddmForFieldValues = ddmFormFieldValueMap.get(
			ddmFormField.getName());

		String valueString = StringPool.BLANK;

		DDMFormFieldValue ddmFormFieldValue = ddmForFieldValues.get(0);

		if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
			DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
				getDDMFormFieldTypeServicesTracker().
					getDDMFormFieldValueRenderer(ddmFormFieldValue.getType());

			valueString = ddmFormFieldValueRenderer.render(
				ddmFormFieldValue, getLocale());
		}
		else {
			DDMFormFieldValueRendererRegistry
				ddmFormFieldValueRendererRegistry =
					getDDMFormFieldValueRendererRegistry();

			com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer
				ddmFormFieldValueRenderer =
					ddmFormFieldValueRendererRegistry.
						getDDMFormFieldValueRenderer(
							ddmFormFieldValue.getType());

			String ddmFormFieldType =
				ddmFormFieldValueRenderer.getSupportedDDMFormFieldType();

			if (Objects.equals(DDMFormFieldType.TEXT_HTML, ddmFormFieldType)) {
				Value value = ddmFormFieldValue.getValue();

				valueString = HtmlUtil.escape(value.getString(getLocale()));
			}
			else {
				valueString = ddmFormFieldValueRenderer.render(
					ddmForFieldValues, getLocale());
			}
		}

		valueString = HtmlUtil.render(valueString);

		return new DDMFormFieldRenderedValue(
			ddmFormField.getName(), ddmFormField.getLabel(), valueString);
	}

	protected abstract DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker();

	protected abstract DDMFormFieldValueRendererRegistry
		getDDMFormFieldValueRendererRegistry();

	protected Map<String, DDMFormField> getDistinctFields(long recordSetId)
		throws Exception {

		List<DDMStructureVersion> ddmStructureVersions = getStructureVersions(
			recordSetId);

		Map<String, DDMFormField> ddmFormFields = new LinkedHashMap<>();

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			DDMForm ddmForm = ddmStructureVersion.getDDMForm();

			ddmFormFields.putAll(ddmForm.getDDMFormFieldsMap(true));
		}

		return ddmFormFields;
	}

	protected Map<String, DDMFormFieldRenderedValue> getRenderedValues(
			int scope, Collection<DDMFormField> ddmFormFields,
			DDMFormValues ddmFormValues)
		throws Exception {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		ddmFormFieldStream = ddmFormFieldStream.filter(
			ddmFormField -> ddmFormFieldValueMap.containsKey(
				ddmFormField.getName()));

		Stream<DDMFormFieldRenderedValue> valueStream = ddmFormFieldStream.map(
			ddmFormField -> getDDMFormFieldRenderedValue(
				scope, ddmFormField, ddmFormFieldValueMap));

		return valueStream.collect(
			Collectors.toMap(
				DDMFormFieldRenderedValue::getFieldName, value -> value));
	}

	protected String getStatusMessage(int status) {
		return LanguageUtil.get(
			_locale, WorkflowConstants.getStatusLabel(status));
	}

	protected List<DDMStructureVersion> getStructureVersions(long recordSetId)
		throws Exception {

		DDLRecordSetVersionService recordSetVersionService =
			getDDLRecordSetVersionService();

		List<DDLRecordSetVersion> recordSetVersions =
			recordSetVersionService.getRecordSetVersions(
				recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<DDMStructureVersion> ddmStructureVersions = new ArrayList<>();

		for (DDLRecordSetVersion recordSetVersion : recordSetVersions) {
			ddmStructureVersions.add(recordSetVersion.getDDMStructureVersion());
		}

		return ddmStructureVersions;
	}

	protected static class DDMFormFieldRenderedValue {

		protected DDMFormFieldRenderedValue(
			String fieldName, LocalizedValue label, String value) {

			_fieldName = fieldName;
			_label = label;
			_value = value;
		}

		protected String getFieldName() {
			return _fieldName;
		}

		protected LocalizedValue getLabel() {
			return _label;
		}

		protected String getValue() {
			return _value;
		}

		private final String _fieldName;
		private final LocalizedValue _label;
		private final String _value;

	}

	private Locale _locale;

}