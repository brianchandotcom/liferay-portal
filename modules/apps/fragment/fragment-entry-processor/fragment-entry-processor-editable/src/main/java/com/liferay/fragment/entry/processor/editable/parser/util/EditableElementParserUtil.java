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

package com.liferay.fragment.entry.processor.editable.parser.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Jürgen Kappler
 */
public class EditableElementParserUtil {

	public static void addAttribute(
		Element element, JSONObject configJSONObject, String attribute,
		String property) {

		if (configJSONObject == null) {
			return;
		}

		String value = configJSONObject.getString(property);

		if (Validator.isNotNull(value)) {
			element.attr(attribute, value);
		}
	}

	public static void addClass(
		Element element, JSONObject configJSONObject, String prefix,
		String property) {

		if (configJSONObject == null) {
			return;
		}

		String value = configJSONObject.getString(property);

		if (Validator.isNotNull(value)) {
			element.addClass(prefix + value);
		}
	}

	public static Element getDocumentBody(String value) {
		Document document = Jsoup.parseBodyFragment(value);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document.body();
	}

}