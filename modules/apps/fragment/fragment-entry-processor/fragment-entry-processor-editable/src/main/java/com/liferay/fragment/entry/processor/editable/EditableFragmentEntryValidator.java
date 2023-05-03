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
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryValidator;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=2",
	service = FragmentEntryValidator.class
)
public class EditableFragmentEntryValidator implements FragmentEntryValidator {

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		_validateAttributes(document);

		Elements elements = document.select(
			"lfr-editable,*[data-lfr-editable-id]");

		_validateDuplicatedIds(elements);

		_validateEditableElements(elements);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_editableElementParserServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementParser.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_editableElementParserServiceTrackerMap.close();
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private EditableElementParser _getEditableElementParser(Element element) {
		String type = EditableFragmentEntryProcessorUtil.getElementType(
			element);

		return _editableElementParserServiceTrackerMap.getService(type);
	}

	private boolean _hasNestedWidget(Element element) {
		List<String> portletAliases = _portletRegistry.getPortletAliases();

		for (String portletAlias : portletAliases) {
			Elements tagElements = element.select(
				"> lfr-widget-" + portletAlias);

			if (tagElements.size() > 0) {
				return true;
			}
		}

		return false;
	}

	private void _validateAttribute(Element element, String attributeName)
		throws FragmentEntryContentException {

		if (Validator.isNotNull(element.attr(attributeName))) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			_language.format(
				resourceBundle,
				"you-must-define-all-required-attributes-x-for-each-editable-" +
					"element",
				StringUtil.merge(_REQUIRED_ATTRIBUTE_NAMES)));
	}

	private void _validateAttributes(Document document)
		throws FragmentEntryContentException {

		for (Element element : document.getElementsByTag("lfr-editable")) {
			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				_validateAttribute(element, attributeName);
			}

			_validateType(element);
		}

		for (Element element :
				document.select(
					"*[data-lfr-editable-id],*[data-lfr-editable-type]")) {

			_validateAttribute(element, "data-lfr-editable-id");
			_validateAttribute(element, "data-lfr-editable-type");

			_validateType(element);
		}
	}

	private void _validateDuplicatedIds(Elements elements)
		throws FragmentEntryContentException {

		Set<String> ids = new HashSet<>();

		for (Element element : elements) {
			if (ids.add(
					EditableFragmentEntryProcessorUtil.getElementId(element))) {

				continue;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-editable-element"));
		}
	}

	private void _validateEditableElements(Elements elements)
		throws FragmentEntryContentException {

		for (Element element : elements) {
			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			_validateNestedEditableElements(element);

			editableElementParser.validate(element);
		}
	}

	private void _validateNestedEditableElements(Element element)
		throws FragmentEntryContentException {

		Elements attributeElements = element.getElementsByAttribute(
			"[data-lfr-editable-id]");

		Elements dropZoneElements = element.select("> lfr-drop-zone");

		Elements tagElements = element.select("> lfr-editable");

		if ((attributeElements.size() > 0) || (dropZoneElements.size() > 0) ||
			_hasNestedWidget(element) || (tagElements.size() > 0)) {

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"editable-fields-cannot-include-nested-editables-drop-" +
						"zones-or-widgets-in-it"));
		}
	}

	private void _validateType(Element element)
		throws FragmentEntryContentException {

		EditableElementParser editableElementParser = _getEditableElementParser(
			element);

		if (editableElementParser != null) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			_language.get(
				resourceBundle,
				"you-must-define-a-valid-type-for-each-editable-element"));
	}

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private Language _language;

	@Reference
	private PortletRegistry _portletRegistry;

}