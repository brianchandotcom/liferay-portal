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

package com.liferay.journal.internal.content.compatibility.converter;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.content.compatibility.converter.JournalContentCompatibilityConverter;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = JournalContentCompatibilityConverter.class
)
public class JournalContentCompatibilityConverterImpl
	implements JournalContentCompatibilityConverter {

	@Override
	public void convert(Document document) {
		Element rootElement = document.getRootElement();

		String version = rootElement.attributeValue("version");

		if (Validator.isNotNull(version) &&
			Objects.equals(version, _LATEST_CONTENT_VERSION)) {

			return;
		}

		rootElement.addAttribute("version", _LATEST_CONTENT_VERSION);

		Locale defaultLocale = null;

		String defaultLanguageId = rootElement.attributeValue("default-locale");

		if (defaultLanguageId == null) {
			defaultLocale = LocaleUtil.getSiteDefault();
		}
		else {
			defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		_convertDDMFields(defaultLocale, rootElement);

		if (_hasNestedFields(rootElement)) {
			_convertNestedFields(rootElement);
		}
	}

	@Override
	public String convert(String content) {
		try {
			Document document = SAXReaderUtil.read(content);

			convert(document);

			return XMLUtil.formatXML(document);
		}
		catch (Exception exception) {
			return content;
		}
	}

	private void _convertDDMFields(Locale defaultLocale, Element element) {
		String type = element.attributeValue("type");

		if (Validator.isNotNull(type)) {
			element.addAttribute("type", _convertDDMFieldType(type));
		}

		_convertDDMFieldValue(element, type, defaultLocale);

		List<Element> dynamicElements = element.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			_convertDDMFields(defaultLocale, dynamicElement);
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

	private void _convertDDMFieldValue(
		Element element, String ddmFieldType, Locale defaultLocale) {

		List<Element> dynamicContentElements = element.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			String text = dynamicContentElement.getText();

			dynamicContentElement.clearContent();

			dynamicContentElement.addCDATA(
				_convertDDMFieldValue(defaultLocale, ddmFieldType, text));
		}
	}

	private String _convertDDMFieldValue(
		Locale defaultLocale, String ddmFieldType, String value) {

		if (Objects.equals(ddmFieldType, "ddm-link-to-page") ||
			Objects.equals(
				ddmFieldType, LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT)) {

			return _convertLinkToLayoutValue(defaultLocale, value);
		}

		return value;
	}

	private String _convertLinkToLayoutValue(
		Locale defaultLocale, String value) {

		if (JSONUtil.isValid(value)) {
			return value;
		}

		String[] values = StringUtil.split(value, CharPool.AT);

		if (ArrayUtil.isEmpty(values) ||
			((values.length <= 2) && (_groupId == 0))) {

			return StringPool.BLANK;
		}

		long groupId = _groupId;

		if (values.length > 2) {
			groupId = GetterUtil.getLong(values[2]);
		}

		boolean privateLayout = !Objects.equals(values[1], "public");
		long layoutId = GetterUtil.getLong(values[0]);

		Layout layout = _layoutLocalService.fetchLayout(
			groupId, privateLayout, layoutId);

		if (layout == null) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = JSONUtil.put(
			"groupId", groupId
		).put(
			"id", layout.getUuid()
		).put(
			"layoutId", layoutId
		).put(
			"name", layout.getName(defaultLocale)
		).put(
			"privateLayout", privateLayout
		).put(
			"value", layout.getFriendlyURL(defaultLocale)
		);

		return jsonObject.toJSONString();
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

	@Reference(unbind = "-")
	private LayoutLocalService _layoutLocalService;

}