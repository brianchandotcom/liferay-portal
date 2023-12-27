/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.editable.parser.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

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

}