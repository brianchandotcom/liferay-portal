/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.exporter;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HtmlParser;
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
@Component(service = DDLExporter.class)
public class DDLXMLExporter extends BaseDDLExporter {

	@Override
	public String getFormat() {
		return "xml";
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

			Map<String, DDMFormFieldRenderedValue> values = getRenderedValues(
				recordSet.getScope(), ddmFormFields.values(),
				_ddmStorageEngineManager.getDDMFormValues(
					recordVersion.getDDMStorageId()),
				_htmlParser);

			for (Map.Entry<String, DDMFormField> entry :
					ddmFormFields.entrySet()) {

				DDMFormFieldRenderedValue ddmFormFieldRenderedValue =
					values.get(entry.getKey());

				_addFieldElement(
					ddmFormFieldRenderedValue, fieldsElement, entry);
			}

			Locale locale = getLocale();

			_addFieldElement(
				fieldsElement, _language.get(locale, "status"),
				getStatusMessage(recordVersion.getStatus()));

			_addFieldElement(
				fieldsElement, _language.get(locale, "modified-date"),
				formatDate(recordVersion.getStatusDate(), dateTimeFormatter));

			_addFieldElement(
				fieldsElement, _language.get(locale, "author"),
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
	protected DDMFormFieldTypeServicesRegistry
		getDDMFormFieldTypeServicesRegistry() {

		return _ddmFormFieldTypeServicesRegistry;
	}

	@Override
	protected DDMFormFieldValueRendererRegistry
		getDDMFormFieldValueRendererRegistry() {

		return _ddmFormFieldValueRendererRegistry;
	}

	private void _addFieldElement(
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

		_addFieldElement(element, label.getString(getLocale()), value);
	}

	private void _addFieldElement(
		Element fieldsElement, String label, Serializable value) {

		Element fieldElement = fieldsElement.addElement("field");

		Element labelElement = fieldElement.addElement("label");

		labelElement.addText(label);

		Element valueElement = fieldElement.addElement("value");

		valueElement.addText(String.valueOf(value));
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetService _ddlRecordSetService;

	@Reference
	private DDLRecordSetVersionService _ddlRecordSetVersionService;

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference
	private DDMFormFieldValueRendererRegistry
		_ddmFormFieldValueRendererRegistry;

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private Language _language;

}