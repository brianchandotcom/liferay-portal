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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.Serializable;

import java.time.format.DateTimeFormatter;

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
public class DDMFormXMLExporter extends BaseDDMFormExporter {

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
		return "xml";
	}

	protected void addFieldElement(
		DDMFormFieldRenderedValue ddmFormFieldRenderedValue, Element element,
		Map.Entry<String, DDMFormField> entry) {

		LocalizedValue label = null;
		String value = null;

		if (ddmFormFieldRenderedValue == null) {
			DDMFormField ddmFormField = entry.getValue();

			label = ddmFormField.getLabel();

			value = StringPool.BLANK;
		}
		else {
			label = ddmFormFieldRenderedValue.getLabel();

			value = ddmFormFieldRenderedValue.getValue();
		}

		addFieldElement(element, label.getString(getLocale()), value);
	}

	protected void addFieldElement(
		Element fieldsElement, String label, Serializable value) {

		Element fieldElement = fieldsElement.addElement("field");

		Element labelElement = fieldElement.addElement("label");

		labelElement.addText(label);

		Element valueElement = fieldElement.addElement("value");

		valueElement.addText(String.valueOf(value));
	}

	@Override
	protected byte[] doExport(
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws Exception {

		Map<String, DDMFormField> ddmFormFields = getDistinctFields(
			formInstanceId);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		List<DDMFormInstanceRecord> formInstanceRecords =
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				formInstanceId, status, start, end, orderByComparator);

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();

		for (DDMFormInstanceRecord formInstanceRecord : formInstanceRecords) {
			Element fieldsElement = rootElement.addElement("fields");

			DDMFormInstanceRecordVersion recordVersion =
				formInstanceRecord.getFormInstanceRecordVersion();

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				recordVersion.getStorageId());

			Map<String, DDMFormFieldRenderedValue> values = getRenderedValues(
				ddmFormFields.values(), ddmFormValues);

			for (Map.Entry<String, DDMFormField> entry :
					ddmFormFields.entrySet()) {

				DDMFormFieldRenderedValue ddmFormFieldRenderedValue =
					values.get(entry.getKey());

				addFieldElement(
					ddmFormFieldRenderedValue, fieldsElement, entry);
			}

			Locale locale = getLocale();

			addFieldElement(
				fieldsElement, LanguageUtil.get(locale, "status"),
				getStatusMessage(recordVersion.getStatus()));

			addFieldElement(
				fieldsElement, LanguageUtil.get(locale, "modified-date"),
				formatDate(recordVersion.getStatusDate(), dateTimeFormatter));

			addFieldElement(
				fieldsElement, LanguageUtil.get(locale, "author"),
				recordVersion.getUserName());
		}

		String xml = document.asXML();

		return xml.getBytes();
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