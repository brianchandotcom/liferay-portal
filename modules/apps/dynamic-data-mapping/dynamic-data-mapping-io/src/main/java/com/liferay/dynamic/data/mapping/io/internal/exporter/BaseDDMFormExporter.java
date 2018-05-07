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

package com.liferay.dynamic.data.mapping.io.internal.exporter;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormExporter;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public abstract class BaseDDMFormExporter implements DDMFormExporter {

	@Override
	public byte[] export(long formInstanceId) throws Exception {
		return doExport(
			formInstanceId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long formInstanceId, int status) throws Exception {
		return doExport(
			formInstanceId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long formInstanceId, int status, int start, int end)
		throws Exception {

		return doExport(formInstanceId, status, start, end, null);
	}

	@Override
	public byte[] export(
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws Exception {

		return doExport(formInstanceId, status, start, end, orderByComparator);
	}

	public abstract DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker();

	public abstract DDMFormInstanceVersionLocalService
		getDDMFormInstanceVersionLocalService();

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
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
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

	protected DDMFormFieldRenderedValue getDDMFormFieldRenderedValue(
		DDMFormField ddmFormField,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap) {

		List<DDMFormFieldValue> ddmForFieldValues = ddmFormFieldValueMap.get(
			ddmFormField.getName());

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		String valueString = HtmlUtil.render(
			ddmFormFieldValueRenderer.render(
				ddmForFieldValues.get(0), getLocale()));

		return new DDMFormFieldRenderedValue(
			ddmFormField.getName(), ddmFormField.getLabel(), valueString);
	}

	protected Map<String, DDMFormField> getDistinctFields(long formInstanceId)
		throws Exception {

		List<DDMStructureVersion> ddmStructureVersions = getStructureVersions(
			formInstanceId);

		Map<String, DDMFormField> ddmFormFields = new LinkedHashMap<>();

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			DDMForm ddmForm = ddmStructureVersion.getDDMForm();

			ddmFormFields.putAll(ddmForm.getNontransientDDMFormFieldsMap(true));
		}

		return ddmFormFields;
	}

	protected Map<String, DDMFormFieldRenderedValue> getRenderedValues(
			Collection<DDMFormField> ddmFormFields, DDMFormValues ddmFormValues)
		throws Exception {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		ddmFormFieldStream = ddmFormFieldStream.filter(
			ddmFormField -> ddmFormFieldValueMap.containsKey(
				ddmFormField.getName()));

		Stream<DDMFormFieldRenderedValue> valueStream = ddmFormFieldStream.map(
			ddmFormField -> getDDMFormFieldRenderedValue(
				ddmFormField, ddmFormFieldValueMap));

		return valueStream.collect(
			Collectors.toMap(
				DDMFormFieldRenderedValue::getFieldName, value -> value));
	}

	protected String getStatusMessage(int status) {
		String statusLabel = WorkflowConstants.getStatusLabel(status);

		return LanguageUtil.get(_locale, statusLabel);
	}

	protected List<DDMStructureVersion> getStructureVersions(
			long formInstanceId)
		throws Exception {

		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService =
			getDDMFormInstanceVersionLocalService();

		List<DDMFormInstanceVersion> formInstanceVersions =
			ddmFormInstanceVersionLocalService.getFormInstanceVersions(
				formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<DDMStructureVersion> ddmStructureVersions = new ArrayList<>();

		for (DDMFormInstanceVersion formInstanceVersion :
				formInstanceVersions) {

			ddmStructureVersions.add(formInstanceVersion.getStructureVersion());
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