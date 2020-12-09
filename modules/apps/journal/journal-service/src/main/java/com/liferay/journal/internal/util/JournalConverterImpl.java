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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.trash.TrashHelper;

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

		if (!GetterUtil.getBoolean(
				ddmStructure.getFieldProperty(name, "localizable"))) {

			availableLanguageIds = StringPool.EMPTY_ARRAY;
		}

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

		if (Objects.equals(DDMFormFieldType.DOCUMENT_LIBRARY, type) ||
			Objects.equals(DDMFormFieldType.IMAGE, type)) {

			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(
					dynamicContentElement.getText());
			}
			catch (JSONException jsonException) {
				return StringPool.BLANK;
			}

			if (jsonObject == null) {
				return StringPool.BLANK;
			}

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) || (groupId <= 0)) {
				return StringPool.BLANK;
			}

			try {
				if (!ExportImportThreadLocal.isImportInProcess()) {
					FileEntry fileEntry =
						_dlAppLocalService.getFileEntryByUuidAndGroupId(
							uuid, groupId);

					String title = fileEntry.getTitle();

					if (fileEntry.isInTrash()) {
						title = _trashHelper.getOriginalTitle(
							fileEntry.getTitle());

						jsonObject.put(
							"message",
							LanguageUtil.get(
								_getResourceBundle(defaultLocale),
								"the-selected-document-was-moved-to-the-" +
									"recycle-bin"));
					}

					jsonObject.put("title", title);
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to get file entry for UUID ", uuid,
							" and group ID ", groupId));
				}

				jsonObject.put(
					"message",
					LanguageUtil.get(
						_getResourceBundle(defaultLocale),
						"the-selected-document-was-deleted"));
			}

			serializable = jsonObject.toString();
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
							"titleMap", article.getTitleMap()
						).put(
							"uuid", article.getUuid()
						);
					}
					else {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to get article for  " + classPK);
						}

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

	private ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundle classResourceBundle = ResourceBundleUtil.getBundle(
			locale, "com.liferay.journal.lang");

		return new AggregateResourceBundle(
			classResourceBundle, _portal.getResourceBundle(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalConverterImpl.class);

	private final Map<String, String> _ddmTypesToJournalTypes;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(unbind = "-")
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}