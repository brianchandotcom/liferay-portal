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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.journal.exception.ArticleContentException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
@Component(immediate = true, service = JournalConverter.class)
public class JournalConverterImpl implements JournalConverter {

	public JournalConverterImpl() {
		_ddmDataTypes = HashMapBuilder.put(
			"boolean", "boolean"
		).put(
			"document_library", "document-library"
		).put(
			"image", "image"
		).put(
			"link_to_layout", "link-to-page"
		).put(
			"list", "string"
		).put(
			"multi-list", "string"
		).put(
			"text", "string"
		).put(
			"text_area", "html"
		).put(
			"text_box", "string"
		).build();

		_ddmMetadataAttributes = HashMapBuilder.put(
			"instructions", "tip"
		).put(
			"label", "label"
		).put(
			"predefinedValue", "predefinedValue"
		).build();

		_ddmTypesToJournalTypes = HashMapBuilder.put(
			"checkbox", "boolean"
		).put(
			"ddm-documentlibrary", "document_library"
		).put(
			"ddm-image", "image"
		).put(
			"ddm-link-to-page", "link_to_layout"
		).put(
			"ddm-separator", "selection_break"
		).put(
			"ddm-text-html", "text_area"
		).put(
			"select", "list"
		).put(
			"text", "text"
		).put(
			"textarea", "text_box"
		).build();

		_journalTypesToDDMTypes = HashMapBuilder.put(
			"boolean", "checkbox"
		).put(
			"document_library", "ddm-documentlibrary"
		).put(
			"image", "ddm-image"
		).put(
			"image_gallery", "ddm-documentlibrary"
		).put(
			"link_to_layout", "ddm-link-to-page"
		).put(
			"list", "select"
		).put(
			"multi-list", "select"
		).put(
			"selection_break", "ddm-separator"
		).put(
			"text", "text"
		).put(
			"text_area", "ddm-text-html"
		).put(
			"text_box", "textarea"
		).build();
	}

	@Override
	public String getContent(DDMStructure ddmStructure, Fields ddmFields)
		throws Exception {

		return getContent(ddmStructure, ddmFields, ddmStructure.getGroupId());
	}

	@Override
	public String getContent(
			DDMStructure ddmStructure, Fields ddmFields, long groupId)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute(
			"available-locales", getAvailableLocales(ddmFields));

		Locale defaultLocale = ddmFields.getDefaultLocale();

		if (!LanguageUtil.isAvailableLocale(groupId, defaultLocale)) {
			defaultLocale = LocaleUtil.getSiteDefault();
		}

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

		try {
			String content = XMLUtil.stripInvalidChars(document.asXML());

			return XMLUtil.formatXML(content);
		}
		catch (Exception exception) {
			throw new ArticleContentException(
				"Unable to read content with an XML parser", exception);
		}
	}

	@Override
	public Fields getDDMFields(DDMStructure ddmStructure, Document document)
		throws PortalException {

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDM.FIELDS_DISPLAY_NAME,
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
		catch (DocumentException documentException) {
			throw new PortalException(documentException);
		}
	}

	@Override
	public DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, Fields fields)
		throws PortalException {

		return _fieldsToDDMFormValuesConverter.convert(ddmStructure, fields);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getDDMXSD(String journalXSD) throws Exception {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return getDDMXSD(journalXSD, defaultLocale);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
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

		return XMLUtil.formatXML(document);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
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

		return XMLUtil.formatXML(document);
	}

	protected void addDDMFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			Fields ddmFields, String[] availableLanguageIds,
			String defaultLanguageId)
		throws PortalException {

		String name = dynamicElementElement.attributeValue("name");

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

		String instanceId = dynamicElementElement.attributeValue("instance-id");

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

	protected void addMetadataEntry(
		Element metadataElement, String name, String value) {

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", name);
		entryElement.addCDATA(value);
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

		Field fieldsDisplayField = ddmFields.get(DDM.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = getDDMFieldsDisplayValues(
			fieldsDisplayField);

		int offset = -1;

		int repetitions = 0;

		for (String fieldDisplayName : fieldsDisplayValues) {
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

	protected String decodeURL(String url) {
		try {
			return _http.decodeURL(url);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			return url;
		}
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

	protected String[] getDDMFieldsDisplayValues(Field ddmFieldsDisplayField)
		throws PortalException {

		try {
			DDMStructure ddmStructure = ddmFieldsDisplayField.getDDMStructure();

			List<String> fieldsDisplayValues = new ArrayList<>();

			String[] values = splitFieldsDisplayValue(ddmFieldsDisplayField);

			for (String value : values) {
				String fieldName = StringUtil.extractFirst(
					value, DDM.INSTANCE_SEPARATOR);

				if (ddmStructure.hasField(fieldName)) {
					fieldsDisplayValues.add(fieldName);
				}
			}

			return fieldsDisplayValues.toArray(new String[0]);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	protected Field getField(
			Element dynamicElementElement, DDMStructure ddmStructure,
			String[] availableLanguageIds, String defaultLanguageId)
		throws PortalException {

		Field ddmField = new Field();

		ddmField.setDDMStructureId(ddmStructure.getStructureId());

		Locale defaultLocale = null;

		if (defaultLanguageId == null) {
			defaultLocale = LocaleUtil.getSiteDefault();
		}
		else {
			defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		ddmField.setDefaultLocale(defaultLocale);

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
			Locale locale = defaultLocale;

			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			if (Validator.isNotNull(languageId)) {
				locale = LocaleUtil.fromLanguageId(languageId, true, false);

				if (locale == null) {
					continue;
				}

				missingLanguageIds.remove(languageId);
			}

			Serializable serializable = getFieldValue(
				dataType, type, dynamicContentElement, defaultLocale);

			ddmField.addValue(locale, serializable);
		}

		addMissingFieldValues(ddmField, defaultLanguageId, missingLanguageIds);

		return ddmField;
	}

	protected String getFieldInstanceId(
		Fields ddmFields, String fieldName, int index) {

		Field fieldsDisplayField = ddmFields.get(DDM.FIELDS_DISPLAY_NAME);

		String prefix = fieldName.concat(DDM.INSTANCE_SEPARATOR);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		for (String fieldsDisplayValue : fieldsDisplayValues) {
			if (fieldsDisplayValue.startsWith(prefix)) {
				index--;

				if (index < 0) {
					return StringUtil.extractLast(
						fieldsDisplayValue, DDM.INSTANCE_SEPARATOR);
				}
			}
		}

		return null;
	}

	protected Serializable getFieldValue(
		String dataType, String type, Element dynamicContentElement,
		Locale defaultLocale) {

		Serializable serializable = null;

		if (Objects.equals(DDMFormFieldType.DOCUMENT_LIBRARY, type)) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					dynamicContentElement.getText());

				if (!ExportImportThreadLocal.isImportInProcess()) {
					String uuid = jsonObject.getString("uuid");
					long groupId = jsonObject.getLong("groupId");

					_dlAppLocalService.getFileEntryByUuidAndGroupId(
						uuid, groupId);
				}

				serializable = dynamicContentElement.getText();
			}
			catch (Exception exception) {
				return StringPool.BLANK;
			}
		}
		else if (Objects.equals(DDMFormFieldType.JOURNAL_ARTICLE, type)) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					dynamicContentElement.getText());

				Long classPK = jsonObject.getLong("classPK");

				if (Validator.isNotNull(classPK)) {
					JournalArticle article =
						_journalArticleLocalService.fetchLatestArticle(classPK);

					if (article != null) {
						jsonObject.put("groupId", article.getGroupId());

						String title = article.getTitle(defaultLocale);

						if (article.isInTrash()) {
							jsonObject.put(
								"message",
								LanguageUtil.get(
									_getResourceBundle(defaultLocale),
									"the-selected-web-content-was-moved-to-" +
										"the-recycle-bin"));
						}

						jsonObject.put(
							"title", title
						).put(
							"uuid", article.getUuid()
						);
					}
					else {
						jsonObject.put(
							"message",
							LanguageUtil.get(
								_getResourceBundle(defaultLocale),
								"the-selected-web-content-was-deleted"));
					}
				}

				serializable = jsonObject.toString();
			}
			catch (JSONException jsonException) {
				serializable = StringPool.BLANK;
			}
		}
		else if (Objects.equals(DDMFormFieldType.LINK_TO_PAGE, type)) {
			String[] values = StringUtil.split(
				dynamicContentElement.getText(), CharPool.AT);

			if (ArrayUtil.isEmpty(values)) {
				return StringPool.BLANK;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			long layoutId = GetterUtil.getLong(values[0]);
			boolean privateLayout = !Objects.equals(values[1], "public");

			if (values.length > 2) {
				long groupId = GetterUtil.getLong(values[2]);

				jsonObject.put("groupId", groupId);

				Layout layout = _layoutLocalService.fetchLayout(
					groupId, privateLayout, layoutId);

				if (layout != null) {
					jsonObject.put("label", layout.getName(defaultLocale));
				}
			}

			jsonObject.put(
				"layoutId", layoutId
			).put(
				"privateLayout", privateLayout
			);

			serializable = jsonObject.toString();
		}
		else if (Objects.equals(DDMFormFieldType.SELECT, type)) {
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
					ddmFields, fieldName, count + i);

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

				if (fieldValue == null) {
					fieldValue = ddmField.getValue(
						ddmField.getDefaultLocale(), count);
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

		if (Objects.equals(parentType, "list") ||
			Objects.equals(parentType, "multi-list")) {

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

		String[] removableAttributeNames = {
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

		if (DDMFormFieldType.CHECKBOX.equals(fieldType)) {
			if (fieldValue.equals(Boolean.FALSE.toString())) {
				fieldValue = StringPool.BLANK;
			}

			dynamicContentElement.addCDATA(fieldValue);
		}
		else if (DDMFormFieldType.LINK_TO_PAGE.equals(fieldType) &&
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
		else if (DDMFormFieldType.SELECT.equals(fieldType) &&
				 Validator.isNotNull(fieldValue)) {

			JSONArray jsonArray = null;

			try {
				jsonArray = JSONFactoryUtil.createJSONArray(fieldValue);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse object", jsonException);
				}

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

		String fieldsDisplayValue = StringBundler.concat(
			fieldName, DDM.INSTANCE_SEPARATOR, instanceId);

		Field fieldsDisplayField = ddmFields.get(DDM.FIELDS_DISPLAY_NAME);

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

				addMetadataEntry(metadataElement, "label", decodeURL(name));

				removeAttribute(element, "index-type");

				element.addAttribute(
					"name",
					"option" + parentElement.attributeValue("name") +
						StringUtil.randomString(8));
				element.addAttribute("type", "option");
				element.addAttribute("value", decodeURL(type));

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

	private ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundle classResourceBundle = ResourceBundleUtil.getBundle(
			locale, "com.liferay.journal.lang");

		return new AggregateResourceBundle(
			classResourceBundle, _portal.getResourceBundle(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalConverterImpl.class);

	private final Map<String, String> _ddmDataTypes;
	private final Map<String, String> _ddmMetadataAttributes;
	private final Map<String, String> _ddmTypesToJournalTypes;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	private final Map<String, String> _journalTypesToDDMTypes;

	@Reference(unbind = "-")
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}