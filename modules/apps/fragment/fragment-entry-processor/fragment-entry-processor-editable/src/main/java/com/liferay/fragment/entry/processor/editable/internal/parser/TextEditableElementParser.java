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

import com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.editable.parser.util.EditableElementParserUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "type=text",
	service = EditableElementParser.class
)
public class TextEditableElementParser implements EditableElementParser {

	@Override
	public String getFieldTemplate() {
		return _TMPL_VALIDATE_TEXT_FIELD;
	}

	@Override
	public String getValue(Element element) {
		return element.html();
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

	private static final String _TMPL_VALIDATE_TEXT_FIELD = StringUtil.read(
		EditableFragmentEntryProcessor.class,
		"/META-INF/resources/fragment/entry/processor/editable" +
			"/text_field_template.tmpl");

}