/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.DocumentFragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

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
	service = DocumentFragmentEntryValidator.class
)
public class EditableFragmentEntryValidator
	implements DocumentFragmentEntryValidator {

	@Override
	public void validateFragmentEntryHTML(
			Document document, String configuration, Locale locale)
		throws PortalException {

		Elements elements = document.select(
			"lfr-editable,*[data-lfr-editable-id]");

		_validateAttributes(elements, locale);
		_validateDuplicatedIds(elements, locale);
		_validateEditableElements(elements, locale);
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

	private EditableElementParser _getEditableElementParser(Element element) {
		String type = EditableFragmentEntryProcessorUtil.getElementType(
			element);

		return _editableElementParserServiceTrackerMap.getService(type);
	}

	private void _validateAttribute(
			Element element, String attributeName, Locale locale)
		throws FragmentEntryContentException {

		if (Validator.isNotNull(element.attr(attributeName))) {
			return;
		}

		throw new FragmentEntryContentException(
			_language.format(
				locale,
				"you-must-define-all-required-attributes-x-for-each-editable-" +
					"element",
				StringUtil.merge(_REQUIRED_ATTRIBUTE_NAMES)));
	}

	private void _validateAttributes(Elements elements, Locale locale)
		throws FragmentEntryContentException {

		for (Element element : elements) {
			if (Objects.equals(element.tagName(), "lfr-editable")) {
				_validateAttribute(element, "id", locale);
				_validateAttribute(element, "type", locale);
			}
			else {
				_validateAttribute(element, "data-lfr-editable-id", locale);
				_validateAttribute(element, "data-lfr-editable-type", locale);
			}

			_validateType(element, locale);
		}
	}

	private void _validateDuplicatedIds(Elements elements, Locale locale)
		throws FragmentEntryContentException {

		Set<String> ids = new HashSet<>();

		for (Element element : elements) {
			if (ids.add(
					EditableFragmentEntryProcessorUtil.getElementId(element))) {

				continue;
			}

			throw new FragmentEntryContentException(
				_language.get(
					locale,
					"you-must-define-a-unique-id-for-each-editable-element"));
		}
	}

	private void _validateEditableElements(Elements elements, Locale locale)
		throws FragmentEntryContentException {

		for (Element element : elements) {
			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			_validateNestedEditableElements(element, locale);

			editableElementParser.validate(element);
		}
	}

	private void _validateNestedEditableElements(Element element, Locale locale)
		throws FragmentEntryContentException {

		String html = element.html();

		if (html.contains("data-lfr-editable-id=\"") ||
			html.contains("<lfr-drop-zone") || html.contains("<lfr-editable") ||
			html.contains("<lfr-widget-")) {

			throw new FragmentEntryContentException(
				_language.get(
					locale,
					"editable-fields-cannot-include-nested-editables-drop-" +
						"zones-or-widgets-in-it"));
		}
	}

	private void _validateType(Element element, Locale locale)
		throws FragmentEntryContentException {

		EditableElementParser editableElementParser = _getEditableElementParser(
			element);

		if (editableElementParser != null) {
			return;
		}

		throw new FragmentEntryContentException(
			_language.get(
				locale,
				"you-must-define-a-valid-type-for-each-editable-element"));
	}

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private Language _language;

}