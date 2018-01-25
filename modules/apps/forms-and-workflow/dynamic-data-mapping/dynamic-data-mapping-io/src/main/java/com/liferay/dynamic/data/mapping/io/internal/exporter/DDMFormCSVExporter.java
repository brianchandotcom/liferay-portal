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
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormExporter;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;

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
@Component(immediate = true, service = DDMFormExporter.class)
public class DDMFormCSVExporter extends BaseDDMFormExporter {

	@Override
	public DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker() {

		return _ddmFormFieldTypeServicesTracker;
	}

	@Override
	public DDMFormInstanceVersionLocalService
		getDDMFormInstanceVersionLocalService() {

		return _ddmFormInstanceVersionService;
	}

	@Override
	public String getFormat() {
		return "csv";
	}

	@Override
	protected byte[] doExport(
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws Exception {

		StringBundler sb = new StringBundler();

		Map<String, DDMFormField> ddmFormFields = getDistinctFields(
			formInstanceId);

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

		List<DDMFormInstanceRecord> formInstanceRecords =
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				formInstanceId, status, start, end, orderByComparator);

		Iterator<DDMFormInstanceRecord> iterator =
			formInstanceRecords.iterator();

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();

		while (iterator.hasNext()) {
			DDMFormInstanceRecord record = iterator.next();

			DDMFormInstanceRecordVersion recordVersion =
				record.getFormInstanceRecordVersion();

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				recordVersion.getStorageId());

			Map<String, DDMFormFieldRenderedValue> values = getRenderedValues(
				ddmFormFields.values(), ddmFormValues);

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

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMFormInstanceVersionLocalService _ddmFormInstanceVersionService;

	@Reference
	private StorageEngine _storageEngine;

}