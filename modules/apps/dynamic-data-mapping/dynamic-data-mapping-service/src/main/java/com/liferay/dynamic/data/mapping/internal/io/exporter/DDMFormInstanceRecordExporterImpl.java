/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.exception.FormInstanceRecordExporterException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRegistry;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
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
import com.liferay.dynamic.data.mapping.util.comparator.FormInstanceVersionVersionComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = DDMFormInstanceRecordExporter.class)
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
		catch (Exception exception) {
			throw new FormInstanceRecordExporterException(exception);
		}

		return builder.build();
	}

	protected Map<String, String> getDDMFormFieldsLabel(
		Map<String, DDMFormField> ddmFormFieldMap, Locale locale) {

		Map<String, String> ddmFormFieldsLabel = new LinkedHashMap<>();

		for (DDMFormField ddmFormField : ddmFormFieldMap.values()) {
			LocalizedValue localizedValue = ddmFormField.getLabel();

			ddmFormFieldsLabel.put(
				ddmFormField.getFieldReference(),
				localizedValue.getString(locale));
		}

		ddmFormFieldsLabel.put(_KEY_AUTHOR, _language.get(locale, _KEY_AUTHOR));
		ddmFormFieldsLabel.put(
			_KEY_LANGUAGE_ID, _language.get(locale, "default-language"));
		ddmFormFieldsLabel.put(
			_KEY_MODIFIED_DATE, _language.get(locale, "modified-date"));
		ddmFormFieldsLabel.put(_KEY_STATUS, _language.get(locale, _KEY_STATUS));

		return ddmFormFieldsLabel;
	}

	protected String getDDMFormFieldValue(
		DDMFormField ddmFormField,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap,
		Locale locale) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValueMap.get(
			ddmFormField.getFieldReference());

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		StringBundler sb = new StringBundler(2 * ddmFormFieldValues.size());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			String value = ddmFormFieldValueRenderer.render(
				ddmFormFieldValue, locale);

			if (value != null) {
				sb.append(value);
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		sb.setIndex(sb.index() - 1);

		return _html.unescape(sb.toString());
	}

	protected List<Map<String, String>> getDDMFormFieldValues(
			Map<String, DDMFormField> ddmFormFields,
			List<DDMFormInstanceRecord> ddmFormInstanceRecords, Locale locale)
		throws Exception {

		List<Map<String, String>> ddmFormFieldValues = new ArrayList<>();

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(locale);

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			DDMFormValues ddmFormValues =
				ddmFormInstanceRecord.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
				ddmFormValues.getDDMFormFieldValuesReferencesMap(true);

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
							entry.getValue(), ddmFormFieldValuesMap,
							ddmFormValues.getDefaultLocale()));
				}
			}

			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				ddmFormInstanceRecord.getFormInstanceRecordVersion();

			ddmFormFieldsValue.put(
				_KEY_AUTHOR, ddmFormInstanceRecordVersion.getUserName());

			ddmFormFieldsValue.put(
				_KEY_LANGUAGE_ID,
				LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()));
			ddmFormFieldsValue.put(
				_KEY_MODIFIED_DATE,
				dateTimeFormat.format(
					ddmFormInstanceRecordVersion.getStatusDate()));
			ddmFormFieldsValue.put(
				_KEY_STATUS,
				getStatusMessage(
					ddmFormInstanceRecordVersion.getStatus(), locale));

			ddmFormFieldValues.add(ddmFormFieldsValue);
		}

		return ddmFormFieldValues;
	}

	protected Map<String, DDMFormField> getDistinctFields(
			long ddmFormInstanceId)
		throws Exception {

		Map<String, DDMFormField> ddmFormFields = new LinkedHashMap<>();

		for (DDMStructureVersion ddmStructureVersion :
				getStructureVersions(ddmFormInstanceId)) {

			Map<String, DDMFormField> map =
				getNontransientDDMFormFieldsReferencesMap(ddmStructureVersion);

			for (Map.Entry<String, DDMFormField> entry : map.entrySet()) {
				ddmFormFields.putIfAbsent(entry.getKey(), entry.getValue());
			}
		}

		return ddmFormFields;
	}

	protected Map<String, DDMFormField>
		getNontransientDDMFormFieldsReferencesMap(
			DDMStructureVersion ddmStructureVersion) {

		DDMForm ddmForm = ddmStructureVersion.getDDMForm();

		return ddmForm.getNontransientDDMFormFieldsReferencesMap(true);
	}

	protected String getStatusMessage(int status, Locale locale) {
		return _language.get(locale, WorkflowConstants.getStatusLabel(status));
	}

	protected List<DDMStructureVersion> getStructureVersions(
			long ddmFormInstanceId)
		throws Exception {

		List<DDMFormInstanceVersion> ddmFormInstanceVersions =
			ddmFormInstanceVersionLocalService.getFormInstanceVersions(
				ddmFormInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		ddmFormInstanceVersions = ListUtil.sort(
			ddmFormInstanceVersions,
			new FormInstanceVersionVersionComparator());

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
			ddmFormInstanceRecordWriterRegistry.getDDMFormInstanceRecordWriter(
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
	protected DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry;

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	@Reference
	protected DDMFormInstanceRecordWriterRegistry
		ddmFormInstanceRecordWriterRegistry;

	@Reference
	protected DDMFormInstanceVersionLocalService
		ddmFormInstanceVersionLocalService;

	private static final String _KEY_AUTHOR = "author";

	private static final String _KEY_LANGUAGE_ID = "languageId";

	private static final String _KEY_MODIFIED_DATE = "modifiedDate";

	private static final String _KEY_STATUS = "status";

	@Reference
	private Html _html;

	@Reference
	private Language _language;

}