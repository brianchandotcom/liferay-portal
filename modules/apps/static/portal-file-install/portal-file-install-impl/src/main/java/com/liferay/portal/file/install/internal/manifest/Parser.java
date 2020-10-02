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

package com.liferay.portal.file.install.internal.manifest;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * // TODO Temporary class needs to be removed once the refactor is complete
 *
 * @author Matthew Tambara
 */
public class Parser {

	public static Map<String, Map<String, String>> parseHeader(String header)
		throws IllegalArgumentException {

		List<String> imports = _parseDelimitedString(header, CharPool.COMMA);

		Map<String, Map<String, String>> clauses = _parseImports(imports);

		if (clauses == null) {
			return new HashMap<>();
		}

		return clauses;
	}

	private static List<String> _parseDelimitedString(
		String value, char delimiter) {

		if (value == null) {
			return Collections.<String>emptyList();
		}

		List<String> strings = new ArrayList<>();

		StringBundler sb = new StringBundler();

		boolean inQuotes = false;

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);

			if ((c == delimiter) && !inQuotes) {
				String string = sb.toString();

				strings.add(string.trim());

				sb = new StringBundler();
			}
			else if (c == CharPool.QUOTE) {
				sb.append(c);

				inQuotes = !inQuotes;
			}
			else {
				sb.append(c);
			}
		}

		String string = sb.toString();

		string = string.trim();

		if (string.length() > 0) {
			strings.add(string);
		}

		return strings;
	}

	private static Map<String, Map<String, String>> _parseImports(
			List<String> imports)
		throws IllegalArgumentException {

		if (imports == null) {
			return null;
		}

		Map<String, Map<String, String>> finalImports = new HashMap<>();

		for (String clause : imports) {
			List<String> tokens = _parseDelimitedString(
				clause, CharPool.SEMICOLON);

			List<String> paths = new ArrayList<>();

			Map<String, String> attributes = new HashMap<>();

			for (String token : tokens) {
				int index = token.indexOf(StringPool.EQUAL);

				if (index == -1) {
					paths.add(token);

					continue;
				}

				String key = token.substring(0, index);

				if (token.charAt(index - 1) == CharPool.COLON) {
					key = key.substring(0, key.length() - 1);
				}

				key = key.trim();

				String value = token.substring(index + 1);

				value = value.trim();

				if (value.startsWith(StringPool.QUOTE) &&
					value.endsWith(StringPool.QUOTE)) {

					value = value.substring(1, value.length() - 1);
				}

				attributes.put(key, value);
			}

			for (String path : paths) {
				finalImports.put(path, attributes);
			}
		}

		return finalImports;
	}

}