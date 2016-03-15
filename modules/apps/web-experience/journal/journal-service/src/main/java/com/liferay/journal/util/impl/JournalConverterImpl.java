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

package com.liferay.journal.util.impl;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.util.impl.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.util.xml.XMLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
@Component(immediate = true)
public class JournalConverterImpl implements JournalConverter {

	public JournalConverterImpl() {
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

		return XMLUtil.formatXML(document.asXML());
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, Document document)
		throws PortalException {

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringPool.BLANK);

		Fields ddmFields = new Fields();

		ddmFields.put(fieldsDisplayField);

		Element rootElement = document.getRootElement();

		String[] availableLanguageIds = StringUtil.split(
			rootElement.attributeValue("available-locales"));
		String defaultLanguageId = rootElement.attributeValue("default-locale");

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			addDDMFields(
				dynamicElementElement, ddmStructure, ddmFields,
				availableLanguageIds, defaultLanguageId);
		}

		return ddmFields;
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws PortalException {

		try {
			return getDDMFields(ddmStructure, SAXReaderUtil.read(content));
		}
		catch (DocumentException de) {
			throw new PortalException(de);
		}
	}

	public DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, Fields fields)
		throws PortalException {

		return _fieldsToDDMFormValuesConverter.convert(ddmStructure, fields);
	}

	@Reference(unbind = "-")
	public void setFieldsToDDMFormValuesConverter(
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter) {

		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
	}

	protected void addDDMFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String[] availableLanguageIds,
			String defaultLanguageId)
		throws PortalException {

		String name = dynamicElementElement.attributeValue("name");
		String instanceId = dynamicElementElement.attributeValue("instance-id");

		if (!ddmStructure.hasField(name)) {
			return;
		}

		if (!ddmStructure.isFieldTransient(name)) {
			Field ddmField = getField(
				dynamicElementElement, ddmStructure, availableLanguageIds,
				defaultLanguageId);

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
				availableLanguageIds, defaultLanguageId);
		}
	}

	protected void addMissingFieldValues(
		Field ddmField, String defaultLanguageId,
		Set<String> missingLanguageIds) {

		if (missingLanguageIds.isEmpty()) {
			return;
		}

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		Serializable fieldValue = ddmField.getValue(defaultLocale);

		for (String missingLanguageId : missingLanguageIds) {
			Locale missingLocale = LocaleUtil.fromLanguageId(missingLanguageId);

			ddmField.setValue(missingLocale, fieldValue);
		}
	}

	protected int countFieldRepetition(
			Fields ddmFields, String fieldName, String parentFieldName,
			int parentOffset)
		throws Exception {

		Field fieldsDisplayField = ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = getDDMFieldsDisplayValues(
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

	protected String getAvailableLocales(Fields ddmFields) {
		Set<Locale> availableLocales = ddmFields.getAvailableLocales();

		Locale[] availableLocalesArray = new Locale[availableLocales.size()];

		availableLocalesArray = availableLocales.toArray(availableLocalesArray);

		String[] languageIds = LocaleUtil.toLanguageIds(availableLocalesArray);

		return StringUtil.merge(languageIds);
	}

	protected String[] getDDMFieldsDisplayValues(Field ddmFieldsDisplayField)
		throws PortalException {

		try {
			DDMStructure ddmStructure = ddmFieldsDisplayField.getDDMStructure();

			List<String> fieldsDisplayValues = new ArrayList<>();

			String[] values = splitFieldsDisplayValue(ddmFieldsDisplayField);

			for (String value : values) {
				String fieldName = StringUtil.extractFirst(
					value, DDMImpl.INSTANCE_SEPARATOR);

				if (ddmStructure.hasField(fieldName)) {
					fieldsDisplayValues.add(fieldName);
				}
			}

			return fieldsDisplayValues.toArray(
				new String[fieldsDisplayValues.size()]);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected Serializable getDocumentLibraryValue(String url) {
		try {
			FileEntry fileEntry = null;

			if (url.contains("/c/document_library/get_file?") ||
				url.contains("/image/image_gallery?")) {

				fileEntry = getFileEntryByOldDocumentLibraryURL(url);
			}
			else if (url.contains("/documents/")) {
				fileEntry = getFileEntryByDocumentLibraryURL(url);
			}

			if (fileEntry == null) {
				return StringPool.BLANK;
			}

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
			String[] availableLanguageIds, String defaultLanguageId)
		throws PortalException {

		Field ddmField = new Field();

		ddmField.setDDMStructureId(ddmStructure.getStructureId());
		ddmField.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLanguageId));

		String name = dynamicElementElement.attributeValue("name");

		ddmField.setName(name);

		String dataType = ddmStructure.getFieldDataType(name);
		String type = ddmStructure.getFieldType(name);

		Set<String> missingLanguageIds = SetUtil.fromArray(
			availableLanguageIds);

		missingLanguageIds.remove(defaultLanguageId);

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			Locale locale = LocaleUtil.fromLanguageId(defaultLanguageId);

			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			if (Validator.isNotNull(languageId)) {
				locale = LocaleUtil.fromLanguageId(languageId);

				missingLanguageIds.remove(languageId);
			}

			Serializable serializable = getFieldValue(
				dataType, type, dynamicContentElement);

			ddmField.addValue(locale, serializable);
		}

		addMissingFieldValues(ddmField, defaultLanguageId, missingLanguageIds);

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
		String dataType, String type, Element dynamicContentElement) {

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
			jsonObject.put(
				"type", dynamicContentElement.attributeValue("type"));

			serializable = jsonObject.toString();
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(type)) {
			String[] values = StringUtil.split(
				dynamicContentElement.getText(), CharPool.AT);

			if (ArrayUtil.isEmpty(values)) {
				return StringPool.BLANK;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (values.length > 2) {
				jsonObject.put("groupId", GetterUtil.getLong(values[2]));
			}

			jsonObject.put("layoutId", GetterUtil.getLong(values[0]));

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

	protected FileEntry getFileEntryByDocumentLibraryURL(String url)
		throws PortalException {

		int x = url.indexOf("/documents/");

		int y = url.indexOf(StringPool.QUESTION);

		if (y == -1) {
			y = url.length();
		}

		url = url.substring(x, y);

		String[] parts = StringUtil.split(url, CharPool.SLASH);

		long groupId = GetterUtil.getLong(parts[2]);

		return _dlAppLocalService.getFileEntryByUuidAndGroupId(
			parts[5], groupId);
	}

	protected FileEntry getFileEntryByOldDocumentLibraryURL(String url)
		throws PortalException {

		Matcher matcher = _oldDocumentLibraryURLPattern.matcher(url);

		if (!matcher.find()) {
			return null;
		}

		long groupId = GetterUtil.getLong(matcher.group(2));

		return _dlAppLocalService.getFileEntryByUuidAndGroupId(
			matcher.group(1), groupId);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	protected String[] splitFieldsDisplayValue(Field fieldsDisplayField) {
		String value = (String)fieldsDisplayField.getValue();

		return StringUtil.split(value);
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

				String valueString = String.valueOf(fieldValue);

				updateDynamicContentValue(
					dynamicContentElement, fieldType, multiple,
					valueString.trim());
			}
		}

		ddmFieldsCounter.incrementKey(fieldName);
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
			dynamicContentElement.addAttribute(
				"type", jsonObject.getString("type"));
			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMImpl.TYPE_DDM_LINK_TO_PAGE.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fieldValue);

			long groupId = jsonObject.getLong("groupId");

			long layoutId = jsonObject.getLong("layoutId");

			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			StringBundler sb = new StringBundler((groupId > 0) ? 5 : 3);

			sb.append(layoutId);
			sb.append(StringPool.AT);

			if (privateLayout) {
				Group group = _groupLocalService.fetchGroup(groupId);

				if (group == null) {
					sb.append("private");
				}
				else if (group.isUser()) {
					sb.append("private-user");
				}
				else {
					sb.append("private-group");
				}
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

			JSONArray jsonArray = null;

			try {
				jsonArray = JSONFactoryUtil.createJSONArray(fieldValue);
			}
			catch (JSONException jsone) {
				return;
			}

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

	private static final Log _log = LogFactoryUtil.getLog(
		JournalConverterImpl.class);

	private final Map<String, String> _ddmTypesToJournalTypes;
	private DLAppLocalService _dlAppLocalService;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private GroupLocalService _groupLocalService;
	private final Pattern _oldDocumentLibraryURLPattern = Pattern.compile(
		"uuid=([^&]+)&groupId=([^&]+)");

}