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

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.util.FragmentEntryProcessorUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=2",
	service = FragmentEntryProcessor.class
)
public class EditableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String, EditableElementParser> editableElementParser :
				_editableElementParsers.entrySet()) {

			StringBundler sb = new StringBundler(
				2 + (5 * _REQUIRED_ATTRIBUTE_NAMES.length));

			sb.append("<lfr-editable");

			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				sb.append(StringPool.SPACE);
				sb.append(attributeName);
				sb.append("=\"");

				String value = StringPool.BLANK;

				if (attributeName.equals("type")) {
					value = editableElementParser.getKey();
				}

				sb.append(value);
				sb.append("\"");
			}

			sb.append("></lfr-editable>");

			JSONObject jsonObject = JSONUtil.put(
				"content", sb.toString()
			).put(
				"name", "lfr-editable:" + editableElementParser.getKey()
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(String html) {
		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		Document document = _getDocument(html);

		for (Element element : document.select("lfr-editable")) {
			EditableElementParser editableElementParser =
				_editableElementParsers.get(element.attr("type"));

			if (editableElementParser == null) {
				continue;
			}

			JSONObject defaultValueJSONObject = JSONUtil.put(
				"config", editableElementParser.getAttributes(element)
			).put(
				"defaultValue", editableElementParser.getValue(element)
			);

			defaultEditableValuesJSONObject.put(
				element.attr("id"), defaultValueJSONObject);
		}

		return defaultEditableValuesJSONObject;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK,
			int previewType)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Document document = _getDocument(html);

		Map<Long, Map<String, Object>> assetEntriesFieldValues =
			new HashMap<>();

		for (Element element : document.select("lfr-editable")) {
			EditableElementParser editableElementParser =
				_editableElementParsers.get(element.attr("type"));

			if (editableElementParser == null) {
				continue;
			}

			String id = element.attr("id");

			Class<?> clazz = getClass();

			JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
				clazz.getName());

			if ((editableValuesJSONObject == null) ||
				!editableValuesJSONObject.has(id)) {

				continue;
			}

			JSONObject editableValueJSONObject =
				editableValuesJSONObject.getJSONObject(id);

			JSONObject configJSONObject = editableValueJSONObject.getJSONObject(
				"config");

			String value = StringPool.BLANK;

			if (_fragmentEntryProcessorUtil.isAssetDisplayPage(mode) ||
				_fragmentEntryProcessorUtil.isMapped(editableValueJSONObject)) {

				JSONObject mappedValueConfigJSONObject =
					_getMappedValueConfigJSONObject(
						editableElementParser, editableValueJSONObject,
						assetEntriesFieldValues, mode, locale, previewClassPK,
						previewType);

				configJSONObject = JSONUtil.merge(
					configJSONObject, mappedValueConfigJSONObject);

				value = _getMappedValue(
					editableElementParser, editableValueJSONObject,
					assetEntriesFieldValues, mode, locale, previewClassPK,
					previewType);
			}

			if (Validator.isNull(value)) {
				value = _fragmentEntryProcessorUtil.getEditableValue(
					editableValueJSONObject, locale, segmentsExperienceIds);
			}

			if (Objects.equals(mode, FragmentEntryLinkConstants.EDIT)) {
				editableElementParser.replace(element, value);
			}
			else {
				editableElementParser.replace(element, value, configJSONObject);
			}
		}

		if (Objects.equals(
				mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(mode, FragmentEntryLinkConstants.VIEW)) {

			for (Element element : document.select("lfr-editable")) {
				element.removeAttr("id");
				element.removeAttr("type");
				element.tagName("div");
			}
		}

		Element bodyElement = document.body();

		if (!assetEntriesFieldValues.containsKey(previewClassPK)) {
			return bodyElement.html();
		}

		Element previewElement = new Element("div");

		previewElement.attr("style", "border: 1px solid #0B5FFF");

		bodyElement = previewElement.html(bodyElement.html());

		return bodyElement.outerHtml();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	public void registerEditableElementParser(
		EditableElementParser editableElementParser,
		Map<String, Object> properties) {

		String editableTagName = (String)properties.get("type");

		_editableElementParsers.put(editableTagName, editableElementParser);
	}

	public void unregisterEditableElementParser(
		EditableElementParser editableElementParser,
		Map<String, Object> properties) {

		String editableTagName = (String)properties.get("type");

		_editableElementParsers.remove(editableTagName);
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		_validateAttributes(html);
		_validateDuplicatedIds(html);
		_validateEditableElements(html);
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private String _getMappedValue(
			EditableElementParser editableElementParser, JSONObject jsonObject,
			Map<Long, Map<String, Object>> assetEntriesFieldValues, String mode,
			Locale locale, long previewClassPK, int previewType)
		throws PortalException {

		String value = jsonObject.getString("mappedField");

		if (Validator.isNotNull(value)) {
			return StringUtil.replace(
				editableElementParser.getFieldTemplate(), "field_name", value);
		}

		Object fieldValue = _fragmentEntryProcessorUtil.getValue(
			jsonObject, assetEntriesFieldValues, mode, locale, previewClassPK,
			previewType);

		if (fieldValue == null) {
			return StringPool.BLANK;
		}

		return editableElementParser.parseFieldValue(fieldValue);
	}

	private JSONObject _getMappedValueConfigJSONObject(
			EditableElementParser editableElementParser, JSONObject jsonObject,
			Map<Long, Map<String, Object>> assetEntriesFieldValues, String mode,
			Locale locale, long previewClassPK, int previewType)
		throws PortalException {

		String value = jsonObject.getString("mappedField");

		if (Validator.isNotNull(value)) {
			return editableElementParser.getFieldTemplateConfigJSONObject(
				value, locale, null);
		}

		Object fieldValue = _fragmentEntryProcessorUtil.getValue(
			jsonObject, assetEntriesFieldValues, mode, locale, previewClassPK,
			previewType);

		if (fieldValue == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		String fieldId = jsonObject.getString("fieldId");

		return editableElementParser.getFieldTemplateConfigJSONObject(
			fieldId, locale, fieldValue);
	}

	private void _validateAttribute(Element element, String attributeName)
		throws FragmentEntryContentException {

		if (element.hasAttr(attributeName)) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			LanguageUtil.format(
				resourceBundle,
				"you-must-define-all-require-attributes-x-for-each-editable-" +
					"element",
				String.join(StringPool.COMMA, _REQUIRED_ATTRIBUTE_NAMES)));
	}

	private void _validateAttributes(String html)
		throws FragmentEntryContentException {

		Document document = _getDocument(html);

		for (Element element : document.getElementsByTag("lfr-editable")) {
			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				_validateAttribute(element, attributeName);
			}

			_validateType(element);
		}
	}

	private void _validateDuplicatedIds(String html)
		throws FragmentEntryContentException {

		Document document = _getDocument(html);

		Elements elements = document.getElementsByTag("lfr-editable");

		Stream<Element> uniqueNodesStream = elements.stream();

		Map<String, Long> idsMap = uniqueNodesStream.collect(
			Collectors.groupingBy(
				element -> element.attr("id"), Collectors.counting()));

		Collection<String> ids = idsMap.keySet();

		Stream<String> idsStream = ids.stream();

		idsStream = idsStream.filter(id -> idsMap.get(id) > 1);

		if (idsStream.count() > 0) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-editable-element"));
		}
	}

	private void _validateEditableElements(String html)
		throws FragmentEntryContentException {

		Document document = _getDocument(html);

		for (Element element : document.select("lfr-editable")) {
			EditableElementParser editableElementParser =
				_editableElementParsers.get(element.attr("type"));

			if (editableElementParser == null) {
				continue;
			}

			editableElementParser.validate(element);
		}
	}

	private void _validateType(Element element)
		throws FragmentEntryContentException {

		EditableElementParser editableElementParser =
			_editableElementParsers.get(element.attr("type"));

		if (editableElementParser != null) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			LanguageUtil.get(
				resourceBundle,
				"you-must-define-a-valid-type-for-each-editable-element"));
	}

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private final Map<String, EditableElementParser> _editableElementParsers =
		new HashMap<>();

	@Reference
	private FragmentEntryProcessorUtil _fragmentEntryProcessorUtil;

}