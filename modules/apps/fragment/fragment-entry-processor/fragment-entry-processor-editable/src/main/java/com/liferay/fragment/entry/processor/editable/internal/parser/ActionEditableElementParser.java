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

package com.liferay.fragment.entry.processor.editable.internal.parser;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.editable.parser.util.EditableElementParserUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(property = "type=action", service = EditableElementParser.class)
public class ActionEditableElementParser implements EditableElementParser {

	@Override
	public String getValue(Element element) {
		String html = element.html();

		if (Validator.isNull(html.trim())) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			return _language.get(resourceBundle, "example-action");
		}

		return html;
	}

	@Override
	public void replace(Element element, String value) {
		Element bodyElement = EditableElementParserUtil.getDocumentBody(value);

		element.html(bodyElement.html());
	}

	@Override
	public void validate(Element element) throws FragmentEntryContentException {
		if (!Objects.equals(element.tagName(), "button")) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.format(
					resourceBundle,
					"an-editable-of-type-x-can-only-be-used-in-a-tag-of-type-x",
					new Object[] {"action", "button"}, false));
		}
	}

	@Reference
	private Language _language;

}