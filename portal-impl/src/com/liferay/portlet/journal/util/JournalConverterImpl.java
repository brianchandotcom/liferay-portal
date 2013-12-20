/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldsCounter;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
public class JournalConverterImpl implements JournalConverter {

	public JournalConverterImpl() {
		_ddmDataTypes = new HashMap<String, String>();

		_ddmDataTypes.put("boolean", "boolean");
		_ddmDataTypes.put("document_library", "document-library");
		_ddmDataTypes.put("image", "image");
		_ddmDataTypes.put("link_to_layout", "link-to-page");
		_ddmDataTypes.put("list", "string");
		_ddmDataTypes.put("multi-list", "string");
		_ddmDataTypes.put("text", "string");
		_ddmDataTypes.put("text_area", "html");
		_ddmDataTypes.put("text_box", "string");

		_ddmMetadataAttributes = new HashMap<String, String>();

		_ddmMetadataAttributes.put("instructions", "tip");
		_ddmMetadataAttributes.put("label", "label");
		_ddmMetadataAttributes.put("predefinedValue", "predefinedValue");

		_ddmTypesToJournalTypes = new HashMap<String, String>();

		_ddmTypesToJournalTypes.put("checkbox", "boolean");
		_ddmTypesToJournalTypes.put("ddm-documentlibrary", "document_library");
		_ddmTypesToJournalTypes.put("ddm-link-to-page", "link_to_layout");
		_ddmTypesToJournalTypes.put("ddm-text-html", "text_area");
		_ddmTypesToJournalTypes.put("select", "list");
		_ddmTypesToJournalTypes.put("text", "text");
		_ddmTypesToJournalTypes.put("textarea", "text_box");
		_ddmTypesToJournalTypes.put("wcm-image", "image");

		_journalTypesToDDMTypes = new HashMap<String, String>();

		_journalTypesToDDMTypes.put("boolean", "checkbox");
		_journalTypesToDDMTypes.put("document_library", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("image", "wcm-image");
		_journalTypesToDDMTypes.put("image_gallery", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("link_to_layout", "ddm-link-to-page");
		_journalTypesToDDMTypes.put("list", "select");
		_journalTypesToDDMTypes.put("multi-list", "select");
		_journalTypesToDDMTypes.put("text", "text");
		_journalTypesToDDMTypes.put("text_area", "ddm-text-html");
		_journalTypesToDDMTypes.put("text_box", "textarea");
	}

	@Override
	public String getContent(DDMStructure ddmStructure, Fields ddmFields)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		String availableLocales = getAvailableLocales(ddmFields);

		rootElement.addAttribute("available-locales", availableLocales);

		Locale defaultLocale = ddmFields.getDefaultLocale();

		rootElement.addAttribute(
			"default-locale", LocaleUtil.toLanguageId(defaultLocale));

		DDMFieldsCounter ddmFieldsCounter = new DDMFieldsCounter();

		for (String fieldName : ddmStructure.getRootFieldNames()) {
			int repetitions = countFieldRepetition(
				ddmFields, fieldName, null, -1);

			for (int i = 0; i < repetitions; i++) {
				Element dynamicElementElement = rootElement.addElement(
					"dynamic-element");

				dynamicElementElement.addAttribute("name", fieldName);
				dynamicElementElement.addAttribute("index", String.valueOf(i));

				updateContentDynamicElement(
					dynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		return DDMXMLUtil.formatXML(document.asXML());
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws Exception {

		Document document = SAXReaderUtil.read(content);

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringPool.BLANK);

		Fields ddmFields = new Fields();

		ddmFields.put(fieldsDisplayField);

		Element rootElement = document.getRootElement();

		String defaultLocale = rootElement.attributeValue("default-locale");

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			addDDMFields(
				dynamicElementElement, ddmStructure, ddmFields, defaultLocale);
		}

		return ddmFields;
	}

	@Override
	public String getDDMXSD(String journalXSD) throws Exception {
		Document document = SAXReaderUtil.read(journalXSD);

		Element rootElement = document.getRootElement();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		rootElement.addAttribute("available-locales", defaultLocale.toString());
		rootElement.addAttribute("default-locale", defaultLocale.toString());

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(dynamicElementElement);
		}

		return DDMXMLUtil.formatXML(document);
	}

	@Override
	public String getJournalXSD(String ddmXSD) throws Exception {
		Document document = SAXReaderUtil.read(ddmXSD);

		Element rootElement = document.getRootElement();

		String defaultLocale = rootElement.attributeValue("default-locale");

		removeAttribute(rootElement, "available-locales");
		removeAttribute(rootElement, "default-locale");

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateDDMXSDDynamicElement(dynamicElementElement, defaultLocale);
		}

		return DDMXMLUtil.formatXML(document);
	}

	protected void addDDMFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String defaultLocale)
		throws Exception {

		String name = dynamicElementElement.attributeValue("name");

		if (!ddmStructure.hasField(name)) {
			return;
		}

		Field ddmField = getField(
			dynamicElementElement, ddmStructure, defaultLocale);

		String fieldName = ddmField.getName();

		Field existingDDMField = ddmFields.get(fieldName);

		if (existingDDMField != null) {
			for (Locale locale : ddmField.getAvailableLocales()) {
				existingDDMField.addValues(locale, ddmField.getValues(locale));
			}
		}
		else {
			ddmFields.put(ddmField);
		}

		updateFieldsDisplay(ddmFields, fieldName);

		List<Element> childrenDynamicElementElements =
			dynamicElementElement.elements("dynamic-element");

		for (Element childrenDynamicElementElement :
				childrenDynamicElementElements) {

			addDDMFields(
				childrenDynamicElementElement, ddmStructure, ddmFields,
				defaultLocale);
		}
	}

	protected void addMetadataEntry(
		Element metadataElement, String name, String value) {

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", name);
		entryElement.addCDATA(value);
	}

	protected int countFieldRepetition(
			Fields ddmFields, String fieldName, String parentFieldName,
			int parentOffset)
		throws Exception {

		if (!ddmFields.contains(fieldName)) {
			return 0;
		}

		Field fieldsDisplayField = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = DDMUtil.getFieldsDisplayValues(
			fieldsDisplayField);

		int offset = -1;

		int repetitions = 0;

		for (int i = 0; i < fieldsDisplayValues.length; i++) {
			String fieldDisplayName = fieldsDisplayValues[i];

			if (offset > parentOffset) {
				break;
			}

			if (fieldDisplayName.equals(parentFieldName)) {
				offset++;
			}

			if (fieldDisplayName.equals(fieldName) &&
				(offset == parentOffset)) {

				repetitions++;
			}
		}

		return repetitions;
	}

	protected Element fetchMetadataEntry(
		Element parentElement, String attributeName, String attributeValue) {

		StringBundler sb = new StringBundler(5);

		sb.append("entry[@");
		sb.append(attributeName);
		sb.append(StringPool.EQUAL);
		sb.append(HtmlUtil.escapeXPathAttribute(attributeValue));
		sb.append(StringPool.CLOSE_BRACKET);

		XPath xPathSelector = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPathSelector.selectSingleNode(parentElement);
	}

	protected String getAvailableLocales(Fields ddmFields) {
		Set<Locale> availableLocales = ddmFields.getAvailableLocales();

		Locale[] availableLocalesArray = new Locale[availableLocales.size()];

		availableLocalesArray = availableLocales.toArray(availableLocalesArray);

		String[] languageIds = LocaleUtil.toLanguageIds(availableLocalesArray);

		return StringUtil.merge(languageIds);
	}

	protected Serializable getDocumentLibraryValue(String url) {
		try {
			int x = url.indexOf("/documents/");

			if (x == -1) {
				return null;
			}

			int y = url.indexOf(StringPool.QUESTION);

			if (y == -1) {
				y = url.length();
			}

			url = url.substring(x, y);

			String[] parts = StringUtil.split(url, CharPool.SLASH);

			long groupId = GetterUtil.getLong(parts[2]);

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					parts[5], groupId);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("uuid", fileEntry.getUuid());
			jsonObject.put("version", fileEntry.getVersion());

			return jsonObject.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error retrieving file entry", e);
			}
		}

		return null;
	}

	protected Field getField(
			Element dynamicElementElement, DDMStructure ddmStructure,
			String defaultLocale)
		throws Exception {

		Field ddmField = new Field();

		ddmField.setDDMStructureId(ddmStructure.getStructureId());
		ddmField.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLocale));

		String name = dynamicElementElement.attributeValue("name");

		ddmField.setName(name);

		String dataType = ddmStructure.getFieldDataType(name);
		String type = ddmStructure.getFieldType(name);

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			Locale locale = LocaleUtil.fromLanguageId(
				dynamicContentElement.attributeValue("language-id"));

			Serializable serializable = getFieldValue(
				dataType, type, dynamicContentElement);

			ddmField.addValue(locale, serializable);
		}

		return ddmField;
	}

	protected Serializable getFieldValue(
			String dataType, String type, Element dynamicContentElement)
		throws Exception {

		Serializable serializable = null;

		if (DDMImpl.TYPE_DDM_DOCUMENTLIBRARY.equals(type)) {
			serializable = getDocumentLibraryValue(
				dynamicContentElement.getText());
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(type)) {
			String[] values = StringUtil.split(
				dynamicContentElement.getText(), CharPool.AT);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (values.length > 2) {
				jsonObject.put("groupId", values[2]);
			}

			jsonObject.put("layoutId", values[0]);

			if (values[1].equals("public")) {
				jsonObject.put("privateLayout", false);
			}
			else {
				jsonObject.put("privateLayout", true);
			}

			serializable = jsonObject.toString();
		}
		else if (DDMImpl.TYPE_SELECT.equals(type)) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			List<Element> optionElements = dynamicContentElement.elements(
				"option");

			if (optionElements.size() > 0) {
				for (Element optionElement : optionElements) {
					jsonArray.put(optionElement.getText());
				}
			}
			else {
				jsonArray.put(dynamicContentElement.getText());
			}

			serializable = jsonArray.toString();
		}
		else {
			serializable = FieldConstants.getSerializable(
				dataType, dynamicContentElement.getText());
		}

		return serializable;
	}

	protected void getJournalMetadataElement(Element metadataElement) {
		removeAttribute(metadataElement, "locale");

		Element dynamicElementElement = metadataElement.getParent();

		// Required

		boolean required = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("required"));

		addMetadataEntry(metadataElement, "required", String.valueOf(required));

		// Tooltip

		Element tipElement = fetchMetadataEntry(metadataElement, "name", "tip");

		if (tipElement != null) {
			tipElement.addAttribute("name", "instructions");

			addMetadataEntry(metadataElement, "displayAsTooltip", "true");
		}
	}

	protected void removeAttribute(Element element, String attributeName) {
		Attribute attribute = element.attribute(attributeName);

		if (attribute == null) {
			return;
		}

		element.remove(attribute);
	}

	protected void updateContentDynamicElement(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Field ddmField, DDMFieldsCounter ddmFieldsCounter)
		throws Exception {

		String fieldName = ddmField.getName();

		String fieldType = ddmStructure.getFieldType(fieldName);
		String indexType = ddmStructure.getFieldProperty(
			fieldName, "indexType");

		String type = _ddmTypesToJournalTypes.get(fieldType);

		if (type == null) {
			type = fieldType;
		}

		dynamicElementElement.addAttribute("type", type);

		dynamicElementElement.addAttribute("index-type", indexType);

		for (Locale locale : ddmField.getAvailableLocales()) {
			Element dynamicContentElement = dynamicElementElement.addElement(
				"dynamic-content");

			dynamicContentElement.addAttribute(
				"language-id", LocaleUtil.toLanguageId(locale));

			int count = ddmFieldsCounter.get(fieldName);

			Serializable fieldValue = ddmField.getValue(locale, count);

			if (fieldValue instanceof Date) {
				Date valueDate = (Date)fieldValue;

				fieldValue = valueDate.getTime();
			}

			String valueString = String.valueOf(fieldValue);

			updateDynamicContentValue(
				dynamicContentElement, fieldType, valueString.trim());
		}

		ddmFieldsCounter.incrementKey(fieldName);
	}

	protected void updateContentDynamicElement(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, DDMFieldsCounter ddmFieldsCounter)
		throws Exception {

		String fieldName = dynamicElementElement.attributeValue("name");

		for (String childFieldName :
				ddmStructure.getChildrenFieldNames(fieldName)) {

			int count = ddmFieldsCounter.get(fieldName);

			int repetitions = countFieldRepetition(
				ddmFields, childFieldName, fieldName, count);

			for (int i = 0; i < repetitions; i++) {
				Element childDynamicElementElement =
					dynamicElementElement.addElement("dynamic-element");

				childDynamicElementElement.addAttribute("name", childFieldName);
				childDynamicElementElement.addAttribute(
					"index", String.valueOf(i));

				updateContentDynamicElement(
					childDynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		updateContentDynamicElement(
			dynamicElementElement, ddmStructure, ddmFields.get(fieldName),
			ddmFieldsCounter);
	}

	protected void updateDDMXSDDynamicElement(
		Element dynamicElementElement, String defaultLocale) {

		// Metadata

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String locale = metadataElement.attributeValue("locale");

			if (locale.equals(defaultLocale)) {
				getJournalMetadataElement(metadataElement);
			}
			else {
				dynamicElementElement.remove(metadataElement);
			}
		}

		Element parentElement = dynamicElementElement.getParent();

		String parentType = parentElement.attributeValue("type");

		if (Validator.equals(parentType, "list") ||
			Validator.equals(parentType, "multi-list")) {

			String repeatable = parentElement.attributeValue("repeatable");

			String value = dynamicElementElement.attributeValue("value");

			dynamicElementElement.addAttribute("name", value);
			dynamicElementElement.addAttribute("repeatable", repeatable);
			dynamicElementElement.addAttribute("type", "value");

			removeAttribute(dynamicElementElement, "value");

			for (Element metadataElement :
					dynamicElementElement.elements("meta-data")) {

				dynamicElementElement.remove(metadataElement);
			}

			return;
		}

		// Index type

		String indexType = GetterUtil.getString(
			dynamicElementElement.attributeValue("indexType"));

		removeAttribute(dynamicElementElement, "indexType");

		dynamicElementElement.addAttribute("index-type", indexType);

		// Type

		String type = dynamicElementElement.attributeValue("type");

		boolean multiple = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("multiple"));

		String newType = _ddmTypesToJournalTypes.get(type);

		if (newType.equals("list") && multiple) {
			newType = "multi-list";
		}

		dynamicElementElement.addAttribute("type", newType);

		// Removable attributes

		String[] removableAttributeNames = new String[] {
			"dataType", "fieldNamespace", "multiple", "readOnly", "required",
			"showLabel", "width"
		};

		for (String removableAttributeName : removableAttributeNames) {
			removeAttribute(dynamicElementElement, removableAttributeName);
		}

		List<Element> childrenDynamicElementElements =
			dynamicElementElement.elements("dynamic-element");

		for (Element childrenDynamicElementElement :
				childrenDynamicElementElements) {

			updateDDMXSDDynamicElement(
				childrenDynamicElementElement, defaultLocale);
		}
	}

	protected void updateDynamicContentValue(
			Element dynamicContentElement, String fieldType, String fieldValue)
		throws Exception {

		if (DDMImpl.TYPE_CHECKBOX.equals(fieldType)) {
			if (fieldValue.equals(Boolean.FALSE.toString())) {
				fieldValue = StringPool.BLANK;
			}

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_DOCUMENTLIBRARY.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

			fieldValue = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, true);

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			long groupId = jsonObject.getLong("groupId");

			String layoutId = jsonObject.getString("layoutId");

			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			StringBundler sb = new StringBundler((groupId > 0) ? 5 : 3);

			sb.append(layoutId);
			sb.append(StringPool.AT);

			if (privateLayout) {
				sb.append("private");
			}
			else {
				sb.append("public");
			}

			if (groupId > 0) {
				sb.append(StringPool.AT);
				sb.append(groupId);
			}

			dynamicContentElement.addCDATA(sb.toString());
		}
		else if (DDMImpl.TYPE_SELECT.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(fieldValue);

			if (jsonArray.length() > 1) {
				for (int i = 0; i <jsonArray.length(); i++) {
					Element optionElement = dynamicContentElement.addElement(
						"option");

					optionElement.addCDATA(jsonArray.getString(i));
				}
			}
			else {
				dynamicContentElement.addCDATA(jsonArray.getString(0));
			}
		}
		else {
			dynamicContentElement.addCDATA(fieldValue);
		}
	}

	protected void updateFieldsDisplay(Fields ddmFields, String fieldName) {
		String fieldsDisplayValue =
			fieldName.concat(DDMImpl.INSTANCE_SEPARATOR).concat(
				StringUtil.randomString());

		Field fieldsDisplayField = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldsDisplayValue);

		fieldsDisplayField.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	protected void updateJournalXSDDynamicElement(Element element) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String name = element.attributeValue("name");
		String type = element.attributeValue("type");

		Element metadataElement = element.element("meta-data");

		if (metadataElement == null) {
			metadataElement = element.addElement("meta-data");
		}

		if (type.equals("multi-list")) {
			element.addAttribute("multiple", "true");
		}
		else if (type.equals("selection_break")) {
			Element parentElement = element.getParent();

			parentElement.remove(element);

			return;
		}
		else {
			Element parentElement = element.getParent();

			String parentType = parentElement.attributeValue("type");

			if ((parentType != null) && parentType.equals("select")) {
				metadataElement.addAttribute(
					"locale", defaultLocale.toString());

				addMetadataEntry(metadataElement, "label", name);

				element.addAttribute("name", "option" + StringUtil.randomId());
				element.addAttribute("type", "option");
				element.addAttribute("value", name);

				return;
			}
		}

		String indexType = StringPool.BLANK;

		Attribute indexTypeAttribute = element.attribute("index-type");

		if (indexTypeAttribute != null) {
			indexType = indexTypeAttribute.getValue();

			element.remove(indexTypeAttribute);
		}

		element.remove(element.attribute("type"));

		String dataType = _ddmDataTypes.get(type);

		if (dataType == null) {
			dataType = "string";
		}

		element.addAttribute("dataType", dataType);
		element.addAttribute("indexType", indexType);

		String required = "false";

		Element requiredElement = fetchMetadataEntry(
			metadataElement, "name", "required");

		if (requiredElement != null) {
			required = requiredElement.getText();
		}

		element.addAttribute("required", required);

		element.addAttribute("showLabel", "true");

		String newType = _journalTypesToDDMTypes.get(type);

		if (newType == null) {
			newType = type;
		}

		element.addAttribute("type", newType);

		if (newType.startsWith("ddm")) {
			element.addAttribute("fieldNamespace", "ddm");
		}

		metadataElement.addAttribute("locale", defaultLocale.toString());

		List<Element> entryElements = metadataElement.elements();

		if (entryElements.isEmpty()) {
			addMetadataEntry(metadataElement, "label", name);
		}
		else {
			for (Element entryElement : entryElements) {
				String oldEntryName = entryElement.attributeValue("name");

				String newEntryName = _ddmMetadataAttributes.get(oldEntryName);

				if (newEntryName == null) {
					metadataElement.remove(entryElement);
				}
				else {
					entryElement.addAttribute("name", newEntryName);
				}
			}
		}

		if (newType.equals("ddm-date") || newType.equals("ddm-decimal") ||
			newType.equals("ddm-integer") ||
			newType.equals("ddm-link-to-page") ||
			newType.equals("ddm-number") || newType.equals("ddm-text-html") ||
			newType.equals("text") || newType.equals("textarea")) {

			element.addAttribute("width", "25");
		}
		else if (newType.equals("wcm-image")) {
			element.addAttribute("fieldNamespace", "wcm");
			element.addAttribute("readOnly", "false");
		}

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(dynamicElementElement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalConverterImpl.class);

	private Map<String, String> _ddmDataTypes;
	private Map<String, String> _ddmMetadataAttributes;
	private Map<String, String> _ddmTypesToJournalTypes;
	private Map<String, String> _journalTypesToDDMTypes;

}