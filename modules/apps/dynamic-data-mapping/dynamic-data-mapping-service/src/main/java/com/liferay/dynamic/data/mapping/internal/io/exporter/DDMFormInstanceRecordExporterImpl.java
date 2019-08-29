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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.exception.FormInstanceRecordExporterException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
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
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMFormInstanceRecordExporter.class)
public class DDMFormInstanceRecordExporterImpl
	implements DDMFormInstanceRecordExporter {

	@Override
	public DDMFormInstanceRecordExporterResponse export(
			DDMFormInstanceRecordExporterRequest
				ddmFormInstanceRecordExporterRequest)
		throws FormInstanceRecordExporterException {

		long ddmFormInstanceId =
			ddmFormInstanceRecordExporterRequest.getDDMFormInstanceId();
		int status = ddmFormInstanceRecordExporterRequest.getStatus();
		int start = ddmFormInstanceRecordExporterRequest.getStart();
		int end = ddmFormInstanceRecordExporterRequest.getEnd();
		OrderByComparator<DDMFormInstanceRecord> orderByComparator =
			ddmFormInstanceRecordExporterRequest.getOrderByComparator();
		Locale locale = ddmFormInstanceRecordExporterRequest.getLocale();
		String type = ddmFormInstanceRecordExporterRequest.getType();

		DDMFormInstanceRecordExporterResponse.Builder builder =
			DDMFormInstanceRecordExporterResponse.Builder.newBuilder();

		try {
			List<DDMFormInstanceRecord> ddmFormInstanceRecords =
				ddmFormInstanceRecordLocalService.getFormInstanceRecords(
					ddmFormInstanceId, status, start, end, orderByComparator);

			Map<String, DDMFormField> ddmFormFields = getDistinctFields(
				ddmFormInstanceId);

			byte[] content = write(
				type, getDDMFormFieldsLabel(ddmFormFields, locale),
				getDDMFormFieldValues(
					ddmFormFields, ddmFormInstanceRecords, locale));

			builder = builder.withContent(content);
		}
		catch (Exception e) {
			throw new FormInstanceRecordExporterException(e);
		}

		return builder.build();
	}

	protected String formatDate(
		Date date, DateTimeFormatter dateTimeFormatter) {

		LocalDateTime localDateTime = LocalDateTime.ofInstant(
			date.toInstant(), ZoneId.systemDefault());

		return dateTimeFormatter.format(localDateTime);
	}

	protected DateTimeFormatter getDateTimeFormatter(Locale locale) {
		DateTimeFormatter dateTimeFormatter =
			DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

		return dateTimeFormatter.withLocale(locale);
	}

	protected Map<String, String> getDDMFormFieldsLabel(
		Map<String, DDMFormField> ddmFormFieldMap, Locale locale) {

		Map<String, String> ddmFormFieldsLabel = new LinkedHashMap<>();

		Collection<DDMFormField> ddmFormFields = ddmFormFieldMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.forEach(
			field -> {
				LocalizedValue localizedValue = field.getLabel();

				ddmFormFieldsLabel.put(
					field.getName(), localizedValue.getString(locale));
			});

		ddmFormFieldsLabel.put(_STATUS, LanguageUtil.get(locale, _STATUS));
		ddmFormFieldsLabel.put(
			_MODIFIED_DATE, LanguageUtil.get(locale, "modified-date"));
		ddmFormFieldsLabel.put(_AUTHOR, LanguageUtil.get(locale, _AUTHOR));

		return ddmFormFieldsLabel;
	}

	protected String getDDMFormFieldValue(
		DDMFormField ddmFormField,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap,
		Locale locale) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValueMap.get(
			ddmFormField.getName());

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		return HtmlUtil.render(
			ddmFormFieldValueRenderer.render(
				ddmFormFieldValues.get(0), locale));
	}

	protected List<Map<String, String>> getDDMFormFieldValues(
			Map<String, DDMFormField> ddmFormFields,
			List<DDMFormInstanceRecord> ddmFormInstanceRecords, Locale locale)
		throws Exception {

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(locale);

		List<Map<String, String>> ddmFormFieldValues = new ArrayList<>();

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			DDMFormValues ddmFormValues =
				ddmFormInstanceRecord.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			Map<String, String> ddmFormFieldsValue = new LinkedHashMap<>();

			for (Map.Entry<String, DDMFormField> entry :
					ddmFormFields.entrySet()) {

				if (!ddmFormFieldValuesMap.containsKey(entry.getKey())) {
					ddmFormFieldsValue.put(entry.getKey(), StringPool.BLANK);
				}
				else {
					ddmFormFieldsValue.put(
						entry.getKey(),
						getDDMFormFieldValue(
							entry.getValue(), ddmFormFieldValuesMap, locale));
				}
			}

			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				ddmFormInstanceRecord.getFormInstanceRecordVersion();

			ddmFormFieldsValue.put(
				_STATUS,
				getStatusMessage(
					ddmFormInstanceRecordVersion.getStatus(), locale));

			ddmFormFieldsValue.put(
				_MODIFIED_DATE,
				formatDate(
					ddmFormInstanceRecordVersion.getStatusDate(),
					dateTimeFormatter));

			ddmFormFieldsValue.put(
				_AUTHOR, ddmFormInstanceRecordVersion.getUserName());

			ddmFormFieldValues.add(ddmFormFieldsValue);
		}

		return ddmFormFieldValues;
	}

	protected Map<String, DDMFormField> getDistinctFields(
			long ddmFormInstanceId)
		throws Exception {

		List<DDMStructureVersion> ddmStructureVersions = getStructureVersions(
			ddmFormInstanceId);

		Map<String, DDMFormField> ddmFormFields = new LinkedHashMap<>();

		Stream<DDMStructureVersion> stream = ddmStructureVersions.stream();

		stream.map(
			this::getNontransientDDMFormFieldsMap
		).forEach(
			ddmFormFields::putAll
		);

		return ddmFormFields;
	}

	protected Map<String, DDMFormField> getNontransientDDMFormFieldsMap(
		DDMStructureVersion ddmStructureVersion) {

		DDMForm ddmForm = ddmStructureVersion.getDDMForm();

		return ddmForm.getNontransientDDMFormFieldsMap(true);
	}

	protected String getStatusMessage(int status, Locale locale) {
		return LanguageUtil.get(
			locale, WorkflowConstants.getStatusLabel(status));
	}

	protected List<DDMStructureVersion> getStructureVersions(
			long ddmFormInstanceId)
		throws Exception {

		List<DDMFormInstanceVersion> ddmFormInstanceVersions =
			ddmFormInstanceVersionLocalService.getFormInstanceVersions(
				ddmFormInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<DDMStructureVersion> ddmStructureVersions = new ArrayList<>();

		for (DDMFormInstanceVersion ddmFormInstanceVersion :
				ddmFormInstanceVersions) {

			ddmStructureVersions.add(
				ddmFormInstanceVersion.getStructureVersion());
		}

		return ddmStructureVersions;
	}

	protected byte[] write(
			String type, Map<String, String> ddmFormFieldsLabel,
			List<Map<String, String>> ddmFormFieldValues)
		throws Exception {

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			ddmFormInstanceRecordWriterTracker.getDDMFormInstanceRecordWriter(
				type);

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		DDMFormInstanceRecordWriterRequest ddmFormInstanceRecordWriterRequest =
			builder.build();

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordWriter.write(
					ddmFormInstanceRecordWriterRequest);

		return ddmFormInstanceRecordWriterResponse.getContent();
	}

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	@Reference
	protected DDMFormInstanceRecordWriterTracker
		ddmFormInstanceRecordWriterTracker;

	@Reference
	protected DDMFormInstanceVersionLocalService
		ddmFormInstanceVersionLocalService;

	private static final String _AUTHOR = "author";

	private static final String _MODIFIED_DATE = "modifiedDate";

	private static final String _STATUS = "status";

}