/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldsCounter;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.util.PwdGenerator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class JournalConverterUtil {

	public static Fields getDDMFields(DDMStructure ddmStructure, String xml)
		throws Exception {

		Document document = SAXReaderUtil.read(xml);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, StringPool.BLANK);

		Fields ddmFields = new Fields();

		ddmFields.put(fieldsDisplay);

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

	public static String getXML(DDMStructure ddmStructure, Fields ddmFields)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		String availableLocales = getAvailableLocales(ddmFields);
		Locale defaultLocale = ddmFields.getDefaultLocale();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
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

				updateDynamicElement(
					dynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		return DDMXMLUtil.formatXML(document.asXML());
	}

	protected static void addDDMFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String defaultLocale)
		throws Exception {

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

	protected static int countFieldRepetition(
			Fields ddmFields, String fieldName, String parentFieldName,
			int parentOffset)
		throws Exception {

		Field fieldsDisplay = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String value = (String)fieldsDisplay.getValue();

		String[] fieldsDisplayValues = StringUtil.split(value);

		int offset = -1;

		int repetitions = 0;

		for (int i = 0; i < fieldsDisplayValues.length; i++) {
			String fieldDisplayName = StringUtil.extractFirst(
				fieldsDisplayValues[i], DDMImpl.INSTANCE_SEPARATOR);

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

	protected static String getAvailableLocales(Fields ddmFields) {
		Set<Locale> availableLocales = ddmFields.getAvailableLocales();

		Locale[] availableLocalesArray = new Locale[availableLocales.size()];

		availableLocalesArray = availableLocales.toArray(availableLocalesArray);

		String[] languageIds = LocaleUtil.toLanguageIds(availableLocalesArray);

		return StringUtil.merge(languageIds);
	}

	protected static Field getField(
			Element dynamicElementElement, DDMStructure ddmStructure,
			String defaultLocale)
		throws Exception {

		Field ddmField = new Field();

		String name = dynamicElementElement.attributeValue("name");

		ddmField.setDDMStructureId(ddmStructure.getStructureId());
		ddmField.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLocale));
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

	protected static Serializable getFieldValue(
			String dataType, String type, Element dynamicContentElement)
		throws Exception {

		Serializable serializable = null;

		if (DDMImpl.TYPE_DDM_DOCUMENTLIBRARY.equals(type)) {
			String[] pathArray = StringUtil.split(
				dynamicContentElement.getText(), CharPool.SLASH);

			long groupId = GetterUtil.getLong(pathArray[2]);

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					pathArray[5], groupId);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("uuid", fileEntry.getUuid());
			jsonObject.put("version", fileEntry.getVersion());

			serializable = jsonObject.toString();
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(type)) {
			String[] values = StringUtil.split(
				dynamicContentElement.getText(), CharPool.AT);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

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

	protected static void updateDynamicContentValue(
			Element dynamicContentElement, String fieldType, String fieldValue)
		throws Exception {

		if (DDMImpl.TYPE_CHECKBOX.equals(fieldType)) {
			if (fieldValue.equals(Boolean.FALSE.toString())) {
				fieldValue = StringPool.BLANK;
			}

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_DOCUMENTLIBRARY.equals(fieldType)) {
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
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(fieldType)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			String layoutId = jsonObject.getString("layoutId");

			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			if (privateLayout) {
				fieldValue = layoutId.concat(StringPool.AT).concat("private");
			}
			else {
				fieldValue = layoutId.concat(StringPool.AT).concat("public");
			}

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_SELECT.equals(fieldType)) {
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

	protected static void updateDynamicElement(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Field ddmField, DDMFieldsCounter ddmFieldsCounter)
		throws Exception {

		String fieldName = ddmField.getName();

		String fieldType = ddmStructure.getFieldType(fieldName);
		String indexType = ddmStructure.getFieldProperty(
			fieldName, "indexType");

		dynamicElementElement.addAttribute("type", _ddmTypesMap.get(fieldType));
		dynamicElementElement.addAttribute("index-type", indexType);

		for (Locale locale : ddmField.getAvailableLocales()) {
			Element dynamicContentElement = dynamicElementElement.addElement(
				"dynamic-content");

			dynamicContentElement.addAttribute(
				"language-id", LocaleUtil.toLanguageId(locale));

			int index = ddmFieldsCounter.get(fieldName);

			Serializable fieldValue = ddmField.getValue(locale, index);

			updateDynamicContentValue(
				dynamicContentElement, fieldType, String.valueOf(fieldValue));
		}

		ddmFieldsCounter.incrementKey(fieldName);
	}

	protected static void updateDynamicElement(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, DDMFieldsCounter ddmFieldsCounter)
		throws Exception {

		String fieldName = dynamicElementElement.attributeValue("name");

		for (String childFieldName :
				ddmStructure.getChildrenFieldNames(fieldName)) {

			int offset = ddmFieldsCounter.get(fieldName);

			int repetitions = countFieldRepetition(
				ddmFields, childFieldName, fieldName, offset);

			for (int i = 0; i < repetitions; i++) {
				Element childDynamicElementElement =
					dynamicElementElement.addElement("dynamic-element");

				childDynamicElementElement.addAttribute("name", childFieldName);

				updateDynamicElement(
					childDynamicElementElement, ddmStructure, ddmFields,
					ddmFieldsCounter);
			}
		}

		updateDynamicElement(
			dynamicElementElement, ddmStructure, ddmFields.get(fieldName),
			ddmFieldsCounter);
	}

	protected static void updateFieldsDisplay(
		Fields ddmFields, String fieldName) {

		String fieldsDisplayValue =
			fieldName.concat(DDMImpl.INSTANCE_SEPARATOR).concat(
				PwdGenerator.getPassword());

		Field fieldsDisplay = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplay.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldsDisplayValue);

		fieldsDisplay.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	private static Map<String, String> _ddmTypesMap =
		new HashMap<String, String>();

	static {
		_ddmTypesMap.put("text", "text");
		_ddmTypesMap.put("textarea", "text_box");
		_ddmTypesMap.put("ddm-text-html", "text_area");
		_ddmTypesMap.put("ddm-fileupload", "image");
		_ddmTypesMap.put("ddm-documentlibrary", "document_library");
		_ddmTypesMap.put("checkbox", "boolean");
		_ddmTypesMap.put("select", "list");
		_ddmTypesMap.put("ddm-link-to-page", "link_to_layout");
	}

}