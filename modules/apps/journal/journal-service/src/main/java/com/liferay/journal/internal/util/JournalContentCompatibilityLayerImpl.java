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

package com.liferay.journal.internal.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.util.JournalContentCompatibilityLayer;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = JournalContentCompatibilityLayer.class)
public class JournalContentCompatibilityLayerImpl
	implements JournalContentCompatibilityLayer {

	@Override
	public void convertDocumentContent(Document document) {
		Element rootElement = document.getRootElement();

		String version = rootElement.attributeValue("version");

		if (Validator.isNotNull(version) &&
			Objects.equals(version, _LATEST_CONTENT_VERSION)) {

			return;
		}

		rootElement.addAttribute("version", _LATEST_CONTENT_VERSION);

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			_convertDDMFields(dynamicElementElement);
		}
	}

	@Override
	public String convertDocumentContent(String content) {
		try {
			Document document = SAXReaderUtil.read(content);

			convertDocumentContent(document);

			return XMLUtil.formatXML(document.asXML());
		}
		catch (Exception exception) {
			return content;
		}
	}

	private void _convertDDMFields(Element dynamicElementElement) {
		String newType = _convertDDMFieldType(
			dynamicElementElement.attributeValue("type"));

		dynamicElementElement.addAttribute("type", newType);

		List<Element> childrenDynamicElementElements =
			dynamicElementElement.elements("dynamic-element");

		for (Element childrenDynamicElementElement :
				childrenDynamicElementElements) {

			_convertDDMFields(childrenDynamicElementElement);
		}
	}

	private String _convertDDMFieldType(String ddmFieldType) {
		if (Objects.equals(ddmFieldType, "boolean")) {
			return DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE;
		}

		if (Objects.equals(ddmFieldType, "ddm-color")) {
			return DDMFormFieldTypeConstants.COLOR;
		}

		if (Objects.equals(ddmFieldType, "ddm-date")) {
			return DDMFormFieldTypeConstants.DATE;
		}

		if (Objects.equals(ddmFieldType, "ddm-decimal")) {
			return DDMFormFieldTypeConstants.NUMERIC;
		}

		if (Objects.equals(ddmFieldType, "ddm-geolocation")) {
			return DDMFormFieldTypeConstants.GEOLOCATION;
		}

		if (Objects.equals(ddmFieldType, "ddm-journal-article")) {
			return JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE;
		}

		if (Objects.equals(ddmFieldType, "ddm-image")) {
			return DDMFormFieldTypeConstants.IMAGE;
		}

		if (Objects.equals(ddmFieldType, "ddm-integer")) {
			return DDMFormFieldTypeConstants.NUMERIC;
		}

		if (Objects.equals(ddmFieldType, "ddm-link-to-page")) {
			return LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT;
		}

		if (Objects.equals(ddmFieldType, "ddm-number")) {
			return DDMFormFieldTypeConstants.NUMERIC;
		}

		if (Objects.equals(ddmFieldType, "document_library")) {
			return DDMFormFieldTypeConstants.DOCUMENT_LIBRARY;
		}

		if (Objects.equals(ddmFieldType, "text_area")) {
			return DDMFormFieldTypeConstants.RICH_TEXT;
		}

		if (Objects.equals(ddmFieldType, "text_box")) {
			return DDMFormFieldTypeConstants.TEXT;
		}

		if (Objects.equals(ddmFieldType, "list")) {
			return DDMFormFieldTypeConstants.SELECT;
		}

		if (Objects.equals(ddmFieldType, "selection_break")) {
			return DDMFormFieldTypeConstants.SEPARATOR;
		}

		if (Objects.equals(ddmFieldType, "text")) {
			return DDMFormFieldTypeConstants.TEXT;
		}

		return ddmFieldType;
	}

	private static final String _LATEST_CONTENT_VERSION = "1.0";

}