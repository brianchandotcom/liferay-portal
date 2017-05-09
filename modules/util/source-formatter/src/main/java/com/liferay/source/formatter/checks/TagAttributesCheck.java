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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class TagAttributesCheck extends BaseFileCheck {

	protected String formatTagAttributes(
			String fileName, String line, String tag, int lineCount,
			boolean escapeQuotes)
		throws Exception {

		String s = tag;

		int y = s.indexOf(CharPool.SPACE);

		if (y == -1) {
			return line;
		}

		String tagName = s.substring(1, y);

		s = s.substring(y + 1);

		String previousAttribute = null;
		String previousAttributeAndValue = null;

		boolean wrongOrder = false;

		for (int x = 0;;) {
			x = s.indexOf(CharPool.EQUAL);

			if ((x == -1) || (s.length() <= (x + 1))) {
				return line;
			}

			String attribute = s.substring(0, x);

			String trimmedAttribute = StringUtil.trim(attribute);

			if (!_isAttributName(trimmedAttribute)) {
				return line;
			}

			if (!attribute.equals(trimmedAttribute)) {
				return StringUtil.replace(
					line, attribute + "=", trimmedAttribute + "=");
			}

			if (Validator.isNotNull(previousAttribute) &&
				(previousAttribute.compareToIgnoreCase(attribute) > 0)) {

				wrongOrder = true;
			}

			s = s.substring(x + 1);

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				if (delimeter == CharPool.SPACE) {
					return StringUtil.replace(
						line, attribute + "= ", attribute + "=");
				}

				if (delimeter != CharPool.AMPERSAND) {
					addMessage(
						fileName, "Incorrect delimeter '" + delimeter + "'",
						lineCount);
				}

				return line;
			}

			s = s.substring(1);

			String value = null;

			y = -1;

			while (true) {
				y = s.indexOf(delimeter, y + 1);

				if ((y == -1) || (s.length() <= (y + 1))) {
					return line;
				}

				value = s.substring(0, y);

				if (value.startsWith("<%")) {
					if (getLevel(value, "<%", "%>") == 0) {
						break;
					}
				}
				else if (getLevel(
							value, StringPool.LESS_THAN,
							StringPool.GREATER_THAN) == 0) {

					break;
				}
			}

			if (delimeter == CharPool.APOSTROPHE) {
				if (escapeQuotes) {
					String newValue = StringUtil.replace(
						value, CharPool.QUOTE, "&quot;");

					return StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + newValue + StringPool.QUOTE);
				}

				if (!value.contains(StringPool.QUOTE) ||
					!tagName.contains(StringPool.COLON)) {

					return StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + value + StringPool.QUOTE);
				}
			}

			if ((delimeter == CharPool.QUOTE) &&
				value.contains(StringPool.QUOTE) &&
				tagName.contains(StringPool.COLON)) {

				return StringUtil.replace(
					line, StringPool.QUOTE + value + StringPool.QUOTE,
					StringPool.APOSTROPHE + value + StringPool.APOSTROPHE);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(attribute);
			sb.append(StringPool.EQUAL);
			sb.append(delimeter);
			sb.append(value);
			sb.append(delimeter);

			String currentAttributeAndValue = sb.toString();

			if (!tagName.equals("liferay-ui:tabs")) {
				String newLine = sortHTMLTagAttributes(
					line, value, currentAttributeAndValue);

				if (!newLine.equals(line)) {
					return newLine;
				}
			}

			String newLine = formatTagAttributeType(
				line, tagName, currentAttributeAndValue);

			if (!newLine.equals(line)) {
				return newLine;
			}

			if (wrongOrder) {
				if ((StringUtil.count(line, currentAttributeAndValue) == 1) &&
					(StringUtil.count(line, previousAttributeAndValue) == 1)) {

					line = StringUtil.replaceFirst(
						line, previousAttributeAndValue,
						currentAttributeAndValue);

					return StringUtil.replaceLast(
						line, currentAttributeAndValue,
						previousAttributeAndValue);
				}

				return line;
			}

			s = s.substring(y + 1);

			if (s.startsWith(StringPool.GREATER_THAN)) {
				return line;
			}

			s = StringUtil.trimLeading(s);

			previousAttribute = attribute;
			previousAttributeAndValue = currentAttributeAndValue;
		}
	}

	protected String formatTagAttributeType(
			String line, String tagName, String attributeAndValue)
		throws Exception {

		return line;
	}

	protected String sortHTMLTagAttributes(
		String line, String value, String attributeAndValue) {

		return line;
	}

	private boolean _isAttributName(String attributeName) {
		if (Validator.isNull(attributeName)) {
			return false;
		}

		Matcher matcher = _attributeNamePattern.matcher(attributeName);

		return matcher.matches();
	}

	private static final Pattern _attributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");

}