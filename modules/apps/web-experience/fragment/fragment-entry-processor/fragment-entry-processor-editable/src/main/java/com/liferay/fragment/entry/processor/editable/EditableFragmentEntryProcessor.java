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

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode)
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

			if (!editableValuesJSONObject.has(id)) {
				continue;
			}

			Locale locale = LocaleUtil.getMostRelevantLocale();

			JSONObject editableValueJSONObject =
				editableValuesJSONObject.getJSONObject(id);

			String value = editableValueJSONObject.getString(
				LanguageUtil.getLanguageId(locale));

			if (Validator.isNull(value)) {
				value = editableValueJSONObject.getString("defaultValue");
			}

			editableElementParser.replace(element, value);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unregisterEditableElementParser"
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

	private void _validateAttribute(Element element, String attribute)
		throws FragmentEntryContentException {

		if (element.hasAttr(attribute)) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			LanguageUtil.format(
				resourceBundle,
				"you-must-define-all-require-attributes-x-for-each-editable-" +
					"element",
				String.join(StringPool.COMMA, _REQUIRED_ATTRIBUTES)));
	}

	private void _validateAttributes(String html)
		throws FragmentEntryContentException {

		Document document = _getDocument(html);

		for (Element element : document.getElementsByTag("lfr-editable")) {
			for (String attribute : _REQUIRED_ATTRIBUTES) {
				_validateAttribute(element, attribute);
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

	private static final String[] _REQUIRED_ATTRIBUTES = {"id", "type"};

	private final Map<String, EditableElementParser> _editableElementParsers =
		new HashMap<>();

}