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
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
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

		_convertDDMFields(rootElement);

		if (_hasNestedFields(rootElement)) {
			_convertNestedFields(rootElement);
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

	private void _convertDDMFields(Element element) {
		String type = element.attributeValue("type");

		if (Validator.isNotNull(type)) {
			element.addAttribute("type", _convertDDMFieldType(type));
		}

		List<Element> dynamicElements = element.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			_convertDDMFields(dynamicElement);
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

	private void _convertNestedFields(Element element) {
		for (Element dynamicElement : element.elements("dynamic-element")) {
			List<Element> nestedFieldsElements = dynamicElement.elements(
				"dynamic-element");

			if (nestedFieldsElements.isEmpty()) {
				continue;
			}

			_convertNestedFields(dynamicElement);

			Element newDynamicElement = dynamicElement.addElement(
				"dynamic-element");

			for (Attribute attribute : dynamicElement.attributes()) {
				newDynamicElement.addAttribute(
					attribute.getName(), attribute.getValue());

				dynamicElement.remove(attribute);
			}

			for (Element dynamicContent :
					dynamicElement.elements("dynamic-content")) {

				newDynamicElement.add(dynamicContent.createCopy());

				dynamicElement.remove(dynamicContent);
			}

			dynamicElement.addAttribute("index-type", StringPool.BLANK);
			dynamicElement.addAttribute(
				"instance-id", StringUtil.randomString());
			dynamicElement.addAttribute(
				"name", newDynamicElement.attributeValue("name") + "FieldSet");
		}
	}

	private boolean _hasNestedFields(Element element) {
		for (Element dynamicElement : element.elements("dynamic-element")) {
			List<Element> nestedFieldsElements = dynamicElement.elements(
				"dynamic-element");

			if (!nestedFieldsElements.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private static final String _LATEST_CONTENT_VERSION = "1.0";

}