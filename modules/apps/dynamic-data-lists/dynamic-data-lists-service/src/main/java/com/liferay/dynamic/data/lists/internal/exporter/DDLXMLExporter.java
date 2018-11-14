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
@Component(immediate = true, service = DDLExporter.class)
public class DDLXMLExporter extends BaseDDLExporter {

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
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		Map<String, DDMFormField> ddmFormFields = getDistinctFields(
			recordSetId);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		List<DDLRecord> records = _ddlRecordLocalService.getRecords(
			recordSetId, status, start, end, orderByComparator);

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();

		for (DDLRecord record : records) {
			Element fieldsElement = rootElement.addElement("fields");

			DDLRecordVersion recordVersion = record.getRecordVersion();

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				recordVersion.getDDMStorageId());

			Map<String, DDMFormFieldRenderedValue> values = getRenderedValues(
				recordSet.getScope(), ddmFormFields.values(), ddmFormValues);

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