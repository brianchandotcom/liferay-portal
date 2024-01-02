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
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

		Set<String> ids = new HashSet<>();

		for (Element element :
				document.select("lfr-editable,*[data-lfr-editable-id]")) {

			String id = EditableFragmentEntryProcessorUtil.getElementId(
				element);

			if (Validator.isNull(id) || !ids.add(id)) {
				throw new FragmentEntryContentException(
					_language.get(
						locale,
						"you-must-define-a-unique-id-for-each-editable-" +
							"element"));
			}

			String type = EditableFragmentEntryProcessorUtil.getElementType(
				element);

			EditableElementParser editableElementParser =
				_editableElementParserServiceTrackerMap.getService(type);

			if (editableElementParser == null) {
				throw new FragmentEntryContentException(
					_language.get(
						locale,
						"you-must-define-a-valid-type-for-each-editable-" +
							"element"));
			}

			editableElementParser.validate(element);

			String html = element.html();

			if (html.contains("data-lfr-editable-id=\"") ||
				html.contains("<lfr-drop-zone") ||
				html.contains("<lfr-editable") ||
				html.contains("<lfr-widget-")) {

				throw new FragmentEntryContentException(
					_language.get(
						locale,
						"editable-fields-cannot-include-nested-editables-" +
							"drop-zones-or-widgets-in-it"));
			}
		}
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

	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private Language _language;

}