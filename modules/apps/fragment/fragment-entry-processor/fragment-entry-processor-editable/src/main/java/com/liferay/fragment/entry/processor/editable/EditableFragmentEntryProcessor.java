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
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

			EditableElementParser parser = editableElementParser.getValue();

			if (parser.isCss()) {
				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

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

			jsonObject.put("content", sb.toString());

			jsonObject.put(
				"name", "lfr-editable:" + editableElementParser.getKey());

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

			JSONObject defaultValueJSONObject =
				JSONFactoryUtil.createJSONObject();

			defaultValueJSONObject.put(
				"defaultValue", editableElementParser.getValue(element));

			defaultEditableValuesJSONObject.put(
				element.attr("id"), defaultValueJSONObject);
		}

		return defaultEditableValuesJSONObject;
	}

	@Override
	public String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css, String mode,
			Locale locale)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Class<?> clazz = getClass();

		JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
			clazz.getName());

		Map<String, Map<String, String>> stylesheet = _getStylesheet(css);

		for (Map.Entry<String, Map<String, String>> selector :
				stylesheet.entrySet()) {

			Map<String, String> properties = selector.getValue();

			for (Map.Entry<String, String> property : properties.entrySet()) {
				String id = StringUtil.trim(
					StringUtil.add(
						selector.getKey(), property.getKey(),
						StringPool.SPACE));

				if ((editableValuesJSONObject == null) ||
					!editableValuesJSONObject.has(id)) {

					continue;
				}

				JSONObject editableValueJSONObject =
					editableValuesJSONObject.getJSONObject(id);

				String value = StringPool.BLANK;

				if (Objects.equals(
						mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE)) {

					EditableElementParser editableElementParser =
						_editableElementParsers.get(property);

					value = _getMappedValue(
						editableElementParser, editableValueJSONObject);
				}

				if (Validator.isNull(value)) {
					value = _getEditableValue(
						editableValueJSONObject, locale,
						Collections.emptyList());
				}

				properties.put(property.getKey(), value);
			}
		}

		return _toCSSString(stylesheet);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, List<Long> segmentsIds)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Document document = _getDocument(html);

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

			if (Objects.equals(
					mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE)) {

				value = _getMappedValue(
					editableElementParser, editableValueJSONObject);
			}

			if (Validator.isNull(value)) {
				value = _getEditableValue(
					editableValueJSONObject, locale, segmentsIds);
			}

			editableElementParser.replace(element, value);
		}

		if (Objects.equals(
				mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(mode, FragmentEntryLinkConstants.VIEW)) {

			for (Element element : document.select("lfr-editable")) {
				Document elementDocument = _getDocument(element.html());

				element.replaceWith(elementDocument.body());
			}
		}

		Element bodyElement = document.body();

		return bodyElement.html();
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

	private String _getEditableValue(
		JSONObject jsonObject, Locale locale, List<Long> segmentsIds) {

		if (_isPersonalizationSupported(jsonObject)) {
			return _getEditableValueBySegmentsAndLocale(
				jsonObject, locale, segmentsIds);
		}

		return _getEditableValueByLocale(jsonObject, locale);
	}

	private String _getEditableValueByLocale(
		JSONObject jsonObject, Locale locale) {

		String value = jsonObject.getString(LanguageUtil.getLanguageId(locale));

		if (Validator.isNull(value)) {
			value = jsonObject.getString("defaultValue");
		}

		return value;
	}

	private String _getEditableValueBySegmentsAndLocale(
		JSONObject jsonObject, Locale locale, List<Long> segmentsIds) {

		for (long segmentId : segmentsIds) {
			String value = _getSegmentValue(jsonObject, locale, segmentId);

			if (Validator.isNotNull(value)) {
				return value;
			}
		}

		return jsonObject.getString("defaultValue");
	}

	private String _getMappedValue(
		EditableElementParser editableElementParser, JSONObject jsonObject) {

		String value = jsonObject.getString("mappedField");

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(
			editableElementParser.getFieldTemplate(), "field_name", value);
	}

	private String _getSegmentValue(
		JSONObject jsonObject, Locale locale, Long segmentId) {

		JSONObject segmentJSONObject = jsonObject.getJSONObject(
			_EDITABLE_VALUES_SEGMENTS_PREFIX + segmentId);

		if (segmentJSONObject == null) {
			return StringPool.BLANK;
		}

		String value = segmentJSONObject.getString(
			LanguageUtil.getLanguageId(locale));

		if (Validator.isNotNull(value)) {
			return value;
		}

		value = segmentJSONObject.getString(
			LanguageUtil.getLanguageId(LocaleUtil.getMostRelevantLocale()));

		if (Validator.isNotNull(value)) {
			return value;
		}

		return StringPool.BLANK;
	}

	private Map<String, Map<String, String>> _getStylesheet(String css) {
		Map<String, Map<String, String>> stylesheet = new HashMap<>();

		Matcher selectorMatcher = _cssSelectorPattern.matcher(css);

		while (selectorMatcher.find()) {
			String selector = StringUtil.trim(selectorMatcher.group(1));

			String cssText = selectorMatcher.group(2);

			Matcher propertiesMatcher = _cssPropertyPattern.matcher(cssText);

			Map<String, String> properties = stylesheet.getOrDefault(
				selector, new HashMap<>());

			while (propertiesMatcher.find()) {
				String property = StringUtil.trim(propertiesMatcher.group(1));
				String value = propertiesMatcher.group(2);

				properties.put(property, value);
			}

			stylesheet.put(selector, properties);
		}

		return stylesheet;
	}

	private boolean _isPersonalizationSupported(JSONObject jsonObject) {
		Iterator<String> segmentsKeys = jsonObject.keys();

		while (segmentsKeys.hasNext()) {
			String segmentKey = segmentsKeys.next();

			if (segmentKey.startsWith(_EDITABLE_VALUES_SEGMENTS_PREFIX)) {
				return true;
			}
		}

		return false;
	}

	private String _toCSSString(Map<String, Map<String, String>> stylesheet) {
		StringBundler sb = new StringBundler(stylesheet.size() * 7);

		for (Map.Entry<String, Map<String, String>> selector :
				stylesheet.entrySet()) {

			Map<String, String> properties = selector.getValue();

			StringBundler propertiesSB = new StringBundler(
				properties.size() * 4);

			for (Map.Entry<String, String> property : properties.entrySet()) {
				propertiesSB.append(property.getKey());
				propertiesSB.append(StringPool.COLON);
				propertiesSB.append(property.getValue());
				propertiesSB.append(StringPool.SEMICOLON);
			}

			sb.append(selector.getKey());
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_CURLY_BRACE);
			sb.append(propertiesSB.toString());
			sb.append(StringPool.CLOSE_CURLY_BRACE);
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
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
					"you-must-define-an-unique-id-for-each-editable-element"));
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

	private static final String _EDITABLE_VALUES_SEGMENTS_PREFIX =
		"segment-id-";

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private static final Pattern _cssPropertyPattern = Pattern.compile(
		"([^:]+)\\s*:([^;]+);");
	private static final Pattern _cssSelectorPattern = Pattern.compile(
		"([^\\{]+)\\s*\\{([^\\}]+)\\}");

	private final Map<String, EditableElementParser> _editableElementParsers =
		new HashMap<>();

}