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
import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
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
		return _getDefaultEditableValuesJSONObject(html);
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return _getDefaultEditableValuesJSONObject(html);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		if (jsonObject.length() == 0) {
			Class<?> clazz = getClass();

			jsonObject.put(
				clazz.getName(),
				getDefaultEditableValuesJSONObject(
					fragmentEntryLink.getHtml(),
					fragmentEntryLink.getConfiguration()));
		}

		Document document = _getDocument(html);

		Map<Long, Map<String, Object>> infoDisplaysFieldValues =
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

			String value = StringPool.BLANK;

			JSONObject mappedValueConfigJSONObject =
				JSONFactoryUtil.createJSONObject();

			if (_fragmentEntryProcessorHelper.isAssetDisplayPage(
					fragmentEntryProcessorContext.getMode())) {

				value = editableValueJSONObject.getString("mappedField");

				if (Validator.isNotNull(value)) {
					mappedValueConfigJSONObject =
						editableElementParser.getFieldTemplateConfigJSONObject(
							value, fragmentEntryProcessorContext.getLocale(),
							null);

					value = StringUtil.replace(
						editableElementParser.getFieldTemplate(), "field_name",
						value);

					value = _fragmentEntryProcessorHelper.processTemplate(
						value, fragmentEntryProcessorContext);
				}
			}

			if (_fragmentEntryProcessorHelper.isMapped(
					editableValueJSONObject)) {

				Object fieldValue =
					_fragmentEntryProcessorHelper.getMappedValue(
						editableValueJSONObject, infoDisplaysFieldValues,
						fragmentEntryProcessorContext);

				if (fieldValue != null) {
					String fieldId = editableValueJSONObject.getString(
						"fieldId");

					mappedValueConfigJSONObject =
						editableElementParser.getFieldTemplateConfigJSONObject(
							fieldId, fragmentEntryProcessorContext.getLocale(),
							fieldValue);

					value = editableElementParser.parseFieldValue(fieldValue);

					value = _fragmentEntryProcessorHelper.processTemplate(
						value, fragmentEntryProcessorContext);
				}
			}

			if (Validator.isNull(value)) {
				value = _fragmentEntryProcessorHelper.getEditableValue(
					editableValueJSONObject,
					fragmentEntryProcessorContext.getLocale(),
					fragmentEntryProcessorContext.getSegmentsExperienceIds());
			}

			if (Objects.equals(
					fragmentEntryProcessorContext.getMode(),
					FragmentEntryLinkConstants.EDIT)) {

				editableElementParser.replace(element, value);
			}
			else {
				JSONObject configJSONObject = JSONUtil.merge(
					editableValueJSONObject.getJSONObject("config"),
					mappedValueConfigJSONObject);

				editableElementParser.replace(element, value, configJSONObject);

				String mapperType = configJSONObject.getString(
					"mapperType", element.attr("type"));

				EditableElementMapper editableElementMapper =
					_editableElementMappers.get(mapperType);

				if (editableElementMapper != null) {
					editableElementMapper.map(
						element, configJSONObject,
						fragmentEntryProcessorContext);
				}
			}
		}

		if (Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.VIEW)) {

			for (Element element : document.select("lfr-editable")) {
				element.removeAttr("id");
				element.removeAttr("type");
				element.tagName("div");
			}
		}

		Element bodyElement = document.body();

		if (!infoDisplaysFieldValues.containsKey(
				fragmentEntryProcessorContext.getPreviewClassPK())) {

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
	public void registerEditableElementMapper(
		EditableElementMapper editableElementMapper,
		Map<String, Object> properties) {

		String type = (String)properties.get("type");

		_editableElementMappers.put(type, editableElementMapper);
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

	public void unregisterEditableElementMapper(
		EditableElementMapper editableElementMapper,
		Map<String, Object> properties) {

		String type = (String)properties.get("type");

		_editableElementMappers.remove(type);
	}

	public void unregisterEditableElementParser(
		EditableElementParser editableElementParser,
		Map<String, Object> properties) {

		String editableTagName = (String)properties.get("type");

		_editableElementParsers.remove(editableTagName);
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		_validateAttributes(html);
		_validateDuplicatedIds(html);
		_validateEditableElements(html);
	}

	private JSONObject _getDefaultEditableValuesJSONObject(String html) {
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

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private void _validateAttribute(Element element, String attributeName)
		throws FragmentEntryContentException {

		if (Validator.isNotNull(element.attr(attributeName))) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			LanguageUtil.format(
				resourceBundle,
				"you-must-define-all-require-attributes-x-for-each-editable-" +
					"element",
				StringUtil.merge(_REQUIRED_ATTRIBUTE_NAMES)));
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

			_validateNestedEditableElements(element);

			editableElementParser.validate(element);
		}
	}

	private void _validateNestedEditableElements(Element element)
		throws FragmentEntryContentException {

		Elements elements = element.select("> lfr-editable");

		if (elements.size() > 0) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"editable-fields-cannot-include-nested-editables-in-it"));
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

	private final Map<String, EditableElementMapper> _editableElementMappers =
		new HashMap<>();
	private final Map<String, EditableElementParser> _editableElementParsers =
		new HashMap<>();

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

}