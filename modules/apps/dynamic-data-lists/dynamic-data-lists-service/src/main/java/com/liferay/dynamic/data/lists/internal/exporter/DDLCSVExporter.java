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
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.time.format.DateTimeFormatter;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = DDLExporter.class)
public class DDLCSVExporter extends BaseDDLExporter {

	@Override
	public String getFormat() {
		return "csv";
	}

	@Override
	protected byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		StringBundler sb = new StringBundler();

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		Map<String, DDMFormField> ddmFormFields = getDistinctFields(
			recordSetId);

		Locale locale = getLocale();

		for (DDMFormField ddmFormField : ddmFormFields.values()) {
			LocalizedValue label = ddmFormField.getLabel();

			sb.append(CSVUtil.encode(label.getString(locale)));

			sb.append(CharPool.COMMA);
		}

		sb.append(LanguageUtil.get(locale, "status"));
		sb.append(CharPool.COMMA);
		sb.append(LanguageUtil.get(locale, "modified-date"));
		sb.append(CharPool.COMMA);
		sb.append(LanguageUtil.get(locale, "author"));
		sb.append(StringPool.NEW_LINE);

		List<DDLRecord> records = _ddlRecordLocalService.getRecords(
			recordSetId, status, start, end, orderByComparator);

		Iterator<DDLRecord> iterator = records.iterator();

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();

		while (iterator.hasNext()) {
			DDLRecord record = iterator.next();

			DDLRecordVersion recordVersion = record.getRecordVersion();

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				recordVersion.getDDMStorageId());

			Map<String, DDMFormFieldRenderedValue> values = getRenderedValues(
				recordSet.getScope(), ddmFormFields.values(), ddmFormValues);

			for (Map.Entry<String, DDMFormField> entry :
					ddmFormFields.entrySet()) {

				if (values.containsKey(entry.getKey())) {
					DDMFormFieldRenderedValue ddmFormFieldRenderedValue =
						values.get(entry.getKey());

					sb.append(
						CSVUtil.encode(ddmFormFieldRenderedValue.getValue()));
				}
				else {
					sb.append(StringPool.BLANK);
				}

				sb.append(CharPool.COMMA);
			}

			sb.append(getStatusMessage(recordVersion.getStatus()));

			sb.append(CharPool.COMMA);

			sb.append(
				formatDate(recordVersion.getStatusDate(), dateTimeFormatter));

			sb.append(CharPool.COMMA);

			sb.append(CSVUtil.encode(recordVersion.getUserName()));

			if (iterator.hasNext()) {
				sb.append(StringPool.NEW_LINE);
			}
		}

		String csv = sb.toString();

		return csv.getBytes();
	}

	@Override
	protected DDLRecordSetVersionService getDDLRecordSetVersionService() {
		return _ddlRecordSetVersionService;
	}

	@Override
	protected DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker() {

		return _ddmFormFieldTypeServicesTracker;
	}

	@Override
	protected DDMFormFieldValueRendererRegistry
		getDDMFormFieldValueRendererRegistry() {

		return _ddmFormFieldValueRendererRegistry;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetVersionService(
		DDLRecordSetVersionService ddlRecordSetVersionService) {

		_ddlRecordSetVersionService = ddlRecordSetVersionService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldValueRendererRegistry(
		DDMFormFieldValueRendererRegistry ddmFormFieldValueRendererRegistry) {

		_ddmFormFieldValueRendererRegistry = ddmFormFieldValueRendererRegistry;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordSetService _ddlRecordSetService;
	private DDLRecordSetVersionService _ddlRecordSetVersionService;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private DDMFormFieldValueRendererRegistry
		_ddmFormFieldValueRendererRegistry;
	private StorageEngine _storageEngine;

}