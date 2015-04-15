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
import com.liferay.portal.kernel.util.HttpUtil;
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
		_ddmDataTypes = new HashMap<>();

		_ddmDataTypes.put("boolean", "boolean");
		_ddmDataTypes.put("document_library", "document-library");
		_ddmDataTypes.put("image", "image");
		_ddmDataTypes.put("link_to_layout", "link-to-page");
		_ddmDataTypes.put("list", "string");
		_ddmDataTypes.put("multi-list", "string");
		_ddmDataTypes.put("text", "string");
		_ddmDataTypes.put("text_area", "html");
		_ddmDataTypes.put("text_box", "string");

		_ddmMetadataAttributes = new HashMap<>();

		_ddmMetadataAttributes.put("instructions", "tip");
		_ddmMetadataAttributes.put("label", "label");
		_ddmMetadataAttributes.put("predefinedValue", "predefinedValue");

		_ddmTypesToJournalTypes = new HashMap<>();

		_ddmTypesToJournalTypes.put("checkbox", "boolean");
		_ddmTypesToJournalTypes.put("ddm-documentlibrary", "document_library");
		_ddmTypesToJournalTypes.put("ddm-image", "image");
		_ddmTypesToJournalTypes.put("ddm-link-to-page", "link_to_layout");
		_ddmTypesToJournalTypes.put("ddm-separator", "selection_break");
		_ddmTypesToJournalTypes.put("ddm-text-html", "text_area");
		_ddmTypesToJournalTypes.put("select", "list");
		_ddmTypesToJournalTypes.put("text", "text");
		_ddmTypesToJournalTypes.put("textarea", "text_box");

		_journalTypesToDDMTypes = new HashMap<>();

		_journalTypesToDDMTypes.put("boolean", "checkbox");
		_journalTypesToDDMTypes.put("document_library", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("image", "ddm-image");
		_journalTypesToDDMTypes.put("image_gallery", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("link_to_layout", "ddm-link-to-page");
		_journalTypesToDDMTypes.put("list", "select");
		_journalTypesToDDMTypes.put("multi-list", "select");
		_journalTypesToDDMTypes.put("selection_break", "ddm-separator");
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

				updateContentDynamicElement(
					dynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		return DDMXMLUtil.formatXML(document.asXML());
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, Document document)
		throws Exception {

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringPool.BLANK);

		Fields ddmFields = new Fields();

		ddmFields.put(fieldsDisplayField);

		Element rootElement = document.getRootElement();

		String defaultLanguageId = rootElement.attributeValue("default-locale");

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			addDDMFields(
				dynamicElementElement, ddmStructure, ddmFields,
				defaultLanguageId);
		}

		return ddmFields;
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws Exception {

		return getDDMFields(ddmStructure, SAXReaderUtil.read(content));
	}

	@Override
	public String getDDMXSD(String journalXSD) throws Exception {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return getDDMXSD(journalXSD, defaultLocale);
	}

	@Override
	public String getDDMXSD(String journalXSD, Locale defaultLocale)
		throws Exception {

		Document document = SAXReaderUtil.read(journalXSD);

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("available-locales", defaultLocale.toString());
		rootElement.addAttribute("default-locale", defaultLocale.toString());

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(
				dynamicElementElement, defaultLocale.toString());
		}

		return DDMXMLUtil.formatXML(document);
	}

	@Override
	public String getJournalXSD(String ddmXSD) throws Exception {
		Document document = SAXReaderUtil.read(ddmXSD);

		Element rootElement = document.getRootElement();

		String defaultLanguageId = rootElement.attributeValue("default-locale");

		removeAttribute(rootElement, "available-locales");
		removeAttribute(rootElement, "default-locale");

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateDDMXSDDynamicElement(
				dynamicElementElement, defaultLanguageId);
		}

		return DDMXMLUtil.formatXML(document);
	}

	protected void addDDMFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String defaultLanguageId)
		throws Exception {

		String name = dynamicElementElement.attributeValue("name");
		String instanceId = dynamicElementElement.attributeValue("instance-id");

		if (!ddmStructure.hasField(name)) {
			return;
		}

		if (!ddmStructure.isFieldTransient(name)) {
			Field ddmField = getField(
				dynamicElementElement, ddmStructure, defaultLanguageId);

			String fieldName = ddmField.getName();

			Field existingDDMField = ddmFields.get(fieldName);

			if (existingDDMField != null) {
				for (Locale locale : ddmField.getAvailableLocales()) {
					existingDDMField.addValues(
						locale, ddmField.getValues(locale));
				}
			}
			else {
				ddmFields.put(ddmField);
			}
		}

		updateFieldsDisplay(ddmFields, name, instanceId);

		List<Element> childrenDynamicElementElements =
			dynamicElementElement.elements("dynamic-element");

		for (Element childrenDynamicElementElement :
				childrenDynamicElementElements) {

			addDDMFields(
				childrenDynamicElementElement, ddmStructure, ddmFields,
				defaultLanguageId);
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
				return StringPool.BLANK;
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
			jsonObject.put("title", fileEntry.getTitle());
			jsonObject.put("uuid", fileEntry.getUuid());
			jsonObject.put("version", fileEntry.getVersion());

			return jsonObject.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error retrieving file entry", e);
			}
		}

		return StringPool.BLANK;
	}

	protected Field getField(
			Element dynamicElementElement, DDMStructure ddmStructure,
			String defaultLanguageId)
		throws Exception {

		Field ddmField = new Field();

		ddmField.setDDMStructureId(ddmStructure.getStructureId());
		ddmField.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLanguageId));

		String name = dynamicElementElement.attributeValue("name");

		ddmField.setName(name);

		String dataType = ddmStructure.getFieldDataType(name);
		String type = ddmStructure.getFieldType(name);

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			Locale locale = LocaleUtil.fromLanguageId(defaultLanguageId);

			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			if (Validator.isNotNull(languageId)) {
				locale = LocaleUtil.fromLanguageId(languageId);
			}

			Serializable serializable = getFieldValue(
				dataType, type, dynamicContentElement);

			ddmField.addValue(locale, serializable);
		}

		return ddmField;
	}

	protected String getFieldInstanceId(
		Fields ddmFields, String fieldName, int index) {

		Field fieldsDisplayField = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String prefix = fieldName.concat(DDMImpl.INSTANCE_SEPARATOR);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		for (String fieldsDisplayValue : fieldsDisplayValues) {
			if (fieldsDisplayValue.startsWith(prefix)) {
				index--;

				if (index < 0) {
					return StringUtil.extractLast(
						fieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
				}
			}
		}

		return null;
	}

	protected Serializable getFieldValue(
			String dataType, String type, Element dynamicContentElement)
		throws Exception {

		Serializable serializable = null;

		if (DDMImpl.TYPE_DDM_DOCUMENTLIBRARY.equals(type)) {
			serializable = getDocumentLibraryValue(
				dynamicContentElement.getText());
		}
		else if (DDMImpl.TYPE_DDM_IMAGE.equals(type)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("alt", dynamicContentElement.attributeValue("alt"));
			jsonObject.put("data", dynamicContentElement.getText());
			jsonObject.put(
				"name", dynamicContentElement.attributeValue("name"));
			jsonObject.put(
				"title", dynamicContentElement.attributeValue("title"));

			serializable = jsonObject.toString();
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(type)) {
			String[] values = StringUtil.split(
				dynamicContentElement.getText(), CharPool.AT);

			if (ArrayUtil.isEmpty(values)) {
				return StringPool.BLANK;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (values.length > 1) {
				jsonObject.put("groupId", values[2]);
			}

			jsonObject.put("layoutId", values[0]);

			serializable = jsonObject.toString();
		}
		else if (DDMImpl.TYPE_SELECT.equals(type)) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			List<Element> optionElements = dynamicContentElement.elements(
				"option");

			if (!optionElements.isEmpty()) {
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

				String instanceId = getFieldInstanceId(
					ddmFields, fieldName, (count + i));

				childDynamicElementElement.addAttribute(
					"instance-id", instanceId);

				updateContentDynamicElement(
					childDynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		updateContentDynamicElement(
			dynamicElementElement, ddmStructure, ddmFields, fieldName,
			ddmFieldsCounter);
	}

	protected void updateContentDynamicElement(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String fieldName,
			DDMFieldsCounter ddmFieldsCounter)
		throws Exception {

		String fieldType = ddmStructure.getFieldType(fieldName);
		String indexType = ddmStructure.getFieldProperty(
			fieldName, "indexType");
		boolean multiple = GetterUtil.getBoolean(
			ddmStructure.getFieldProperty(fieldName, "multiple"));

		String type = _ddmTypesToJournalTypes.get(fieldType);

		if (type == null) {
			type = fieldType;
		}

		dynamicElementElement.addAttribute("type", type);

		dynamicElementElement.addAttribute("index-type", indexType);

		int count = ddmFieldsCounter.get(fieldName);

		dynamicElementElement.addAttribute("index", String.valueOf(count));

		String instanceId = getFieldInstanceId(ddmFields, fieldName, count);

		dynamicElementElement.addAttribute("instance-id", instanceId);

		Field ddmField = ddmFields.get(fieldName);

		if (!ddmStructure.isFieldTransient(fieldName) && (ddmField != null)) {
			for (Locale locale : ddmField.getAvailableLocales()) {
				Element dynamicContentElement =
					dynamicElementElement.addElement("dynamic-content");

				dynamicContentElement.addAttribute(
					"language-id", LocaleUtil.toLanguageId(locale));

				Serializable fieldValue = ddmField.getValue(locale, count);

				if (fieldValue instanceof Date) {
					Date valueDate = (Date)fieldValue;

					fieldValue = valueDate.getTime();
				}

				String valueString = String.valueOf(fieldValue);

				updateDynamicContentValue(
					dynamicContentElement, fieldType, multiple,
					valueString.trim());
			}
		}

		ddmFieldsCounter.incrementKey(fieldName);
	}

	protected void updateDDMXSDDynamicElement(
		Element dynamicElementElement, String defaultLanguageId) {

		// Metadata

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String languageId = metadataElement.attributeValue("locale");

			if (languageId.equals(defaultLanguageId)) {
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

			Element metadataElement = dynamicElementElement.element(
				"meta-data");

			Element labelElement = fetchMetadataEntry(
				metadataElement, "name", "label");

			dynamicElementElement.addAttribute("name", labelElement.getText());

			String repeatable = parentElement.attributeValue("repeatable");

			dynamicElementElement.addAttribute("repeatable", repeatable);

			String value = dynamicElementElement.attributeValue("value");

			dynamicElementElement.addAttribute("type", value);

			removeAttribute(dynamicElementElement, "value");

			dynamicElementElement.remove(metadataElement);

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
				childrenDynamicElementElement, defaultLanguageId);
		}
	}

	protected void updateDynamicContentValue(
			Element dynamicContentElement, String fieldType, boolean multiple,
			String fieldValue)
		throws Exception {

		if (DDMImpl.TYPE_CHECKBOX.equals(fieldType)) {
			if (fieldValue.equals(Boolean.FALSE.toString())) {
				fieldValue = StringPool.BLANK;
			}

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_IMAGE.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			dynamicContentElement.addAttribute(
				"alt", jsonObject.getString("alt"));
			dynamicContentElement.addAttribute(
				"name", jsonObject.getString("name"));
			dynamicContentElement.addAttribute(
				"title", jsonObject.getString("title"));
			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			long groupId = jsonObject.getLong("groupId");

			String layoutId = jsonObject.getString("layoutId");

			StringBundler sb = new StringBundler((groupId > 0) ? 4 : 2);

			sb.append(layoutId);
			sb.append(StringPool.AT);

			if (groupId > 0) {
				sb.append(StringPool.AT);
				sb.append(groupId);
			}

			dynamicContentElement.addCDATA(sb.toString());
		}
		else if (DDMImpl.TYPE_SELECT.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(fieldValue);

			if (multiple) {
				for (int i = 0; i < jsonArray.length(); i++) {
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

	protected void updateFieldsDisplay(
		Fields ddmFields, String fieldName, String instanceId) {

		if (Validator.isNull(instanceId)) {
			instanceId = StringUtil.randomString();
		}

		String fieldsDisplayValue = fieldName.concat(
			DDMImpl.INSTANCE_SEPARATOR).concat(instanceId);

		Field fieldsDisplayField = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldsDisplayValue);

		fieldsDisplayField.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	protected void updateJournalXSDDynamicElement(Element element) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		updateJournalXSDDynamicElement(
			element, LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void updateJournalXSDDynamicElement(
		Element element, String defaultLanguageId) {

		String name = element.attributeValue("name");
		String type = element.attributeValue("type");

		Element metadataElement = element.element("meta-data");

		if (metadataElement == null) {
			metadataElement = element.addElement("meta-data");
		}

		if (type.equals("multi-list")) {
			element.addAttribute("multiple", "true");
		}
		else {
			Element parentElement = element.getParent();

			String parentType = parentElement.attributeValue("type");

			if ((parentType != null) && parentType.equals("select")) {
				metadataElement.addAttribute("locale", defaultLanguageId);

				addMetadataEntry(
					metadataElement, "label", HttpUtil.decodeURL(name));

				removeAttribute(element, "index-type");

				element.addAttribute("name", "option" + StringUtil.randomId());
				element.addAttribute("type", "option");
				element.addAttribute("value", HttpUtil.decodeURL(type));

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

		if (!type.equals("selection_break")) {
			String dataType = _ddmDataTypes.get(type);

			if (dataType == null) {
				dataType = "string";
			}

			element.addAttribute("dataType", dataType);
		}

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

		metadataElement.addAttribute("locale", defaultLanguageId);

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
		else if (newType.equals("ddm-image")) {
			element.addAttribute("fieldNamespace", "ddm");
			element.addAttribute("readOnly", "false");
		}

		element.add(metadataElement.detach());

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(
				dynamicElementElement, defaultLanguageId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalConverterImpl.class);

	private final Map<String, String> _ddmDataTypes;
	private final Map<String, String> _ddmMetadataAttributes;
	private final Map<String, String> _ddmTypesToJournalTypes;
	private final Map<String, String> _journalTypesToDDMTypes;

}