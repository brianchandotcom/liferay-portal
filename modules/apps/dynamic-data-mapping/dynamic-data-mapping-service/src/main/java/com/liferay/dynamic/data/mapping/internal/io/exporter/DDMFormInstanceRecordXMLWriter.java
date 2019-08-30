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

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.instance.record.writer.type=xml",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordXMLWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldsLabel();

		List<Map<String, String>> ddmFormFieldsValueList =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues();

		for (Map<String, String> ddmFormFieldsValue : ddmFormFieldsValueList) {
			addFieldElements(
				rootElement.addElement("fields"), ddmFormFieldsLabel,
				ddmFormFieldsValue);
		}

		String xml = document.asXML();

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				xml.getBytes());

		return builder.build();
	}

	protected void addFieldElement(
		Element element, String label, String value) {

		Element fieldElement = element.addElement("field");

		Element labelElement = fieldElement.addElement("label");

		labelElement.addText(label);

		Element valueElement = fieldElement.addElement("value");

		valueElement.addText(value);
	}

	protected void addFieldElements(
		Element element, Map<String, String> ddmFormFieldsLabel,
		Map<String, String> ddmFormFieldsValue) {

		for (Map.Entry<String, String> entry : ddmFormFieldsValue.entrySet()) {
			addFieldElement(
				element, ddmFormFieldsLabel.get(entry.getKey()),
				entry.getValue());
		}
	}

}