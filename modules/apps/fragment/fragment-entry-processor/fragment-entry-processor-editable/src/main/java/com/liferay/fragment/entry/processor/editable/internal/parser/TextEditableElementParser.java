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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "type=text",
	service = EditableElementParser.class
)
public class TextEditableElementParser implements EditableElementParser {

	@Override
	public String getValue(Element element) {
		String html = element.html();

		if (Validator.isNull(html.trim())) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			return _language.get(resourceBundle, "example-text");
		}

		return html;
	}

	@Override
	public void replace(Element element, String value) {
		replace(element, value, null);
	}

	@Override
	public void replace(
		Element element, String value, JSONObject configJSONObject) {

		Element bodyElement = EditableElementParserUtil.getDocumentBody(value);

		if (configJSONObject == null) {
			element.html(bodyElement.html());

			return;
		}

		EditableElementParserUtil.addClass(
			element, configJSONObject, "text-", "textAlignment");
		EditableElementParserUtil.addClass(
			element, configJSONObject, "text-", "textColor");
		EditableElementParserUtil.addClass(
			element, configJSONObject, StringPool.BLANK, "textStyle");

		element.html(bodyElement.html());
	}

	@Override
	public void validate(Element element) throws FragmentEntryContentException {
		for (String tag : _TAGS_BLACKLIST) {
			if (Objects.equals(element.tagName(), tag)) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", getClass());

				throw new FragmentEntryContentException(
					_language.format(
						resourceBundle,
						"an-editable-of-type-x-cannot-be-used-in-a-tag-of-" +
							"type-x",
						new Object[] {getEditableElementType(), tag}, false));
			}
		}
	}

	protected String getEditableElementType() {
		return "text";
	}

	private static final String[] _TAGS_BLACKLIST = {"img", "a"};

	@Reference
	private Language _language;

}