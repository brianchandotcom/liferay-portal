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

		String[] imports = _parseDelimitedString(header, StringPool.COMMA);

		Map<String, Map<String, String>> clauses = _parseImports(imports);

		if (clauses == null) {
			return new HashMap<>();
		}

		return clauses;
	}

	private static String[] _parseDelimitedString(
		String value, String delimiter) {

		if (value == null) {
			value = "";
		}

		List<String> strings = new ArrayList<>();

		StringBundler sb = new StringBundler();

		int expecting = _DELIMITER | _STARTQUOTE;

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);

			if ((delimiter.indexOf(c) != -1) &&
				((expecting & _DELIMITER) > 0)) {

				String string = sb.toString();

				strings.add(string.trim());

				sb = new StringBundler();

				expecting = _DELIMITER | _STARTQUOTE;
			}
			else if ((c == CharPool.QUOTE) && ((expecting & _STARTQUOTE) > 0)) {
				sb.append(c);
				expecting = _ENDQUOTE;
			}
			else if ((c == CharPool.QUOTE) && ((expecting & _ENDQUOTE) > 0)) {
				sb.append(c);
				expecting = _STARTQUOTE | _DELIMITER;
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

		return (String[])strings.toArray(new String[0]);
	}

	private static Map<String, Map<String, String>> _parseImports(
			String[] imports)
		throws IllegalArgumentException {

		if (imports == null) {
			return null;
		}

		Map<String, Map<String, String>> finalImports = new HashMap<>();

		for (String clause : imports) {
			String[] tokens = _parseDelimitedString(
				clause, StringPool.SEMICOLON);

			int pathCount = 0;

			for (String token : tokens) {
				if (token.indexOf(CharPool.EQUAL) != -1) {
					break;
				}

				pathCount++;
			}

			if (pathCount == 0) {
				throw new IllegalArgumentException(
					"No path specified on clause: " + clause);
			}

			Map<String, String> attributes = new HashMap<>();

			for (int pieceIndex = pathCount; pieceIndex < tokens.length;
				 pieceIndex++) {

				String piece = tokens[pieceIndex];

				int index = piece.indexOf(StringPool.EQUAL);

				if (index <= 0) {
					throw new IllegalArgumentException(
						"Not a directive/attribute: " + clause);
				}

				String key = piece.substring(0, index);

				if (piece.charAt(index - 1) == CharPool.COLON) {
					key = key.substring(0, key.length() - 1);
				}

				key = key.trim();

				String value = piece.substring(index + 1);

				value = value.trim();

				if (value.startsWith(StringPool.QUOTE) &&
					value.endsWith(StringPool.QUOTE)) {

					value = value.substring(1, value.length() - 1);
				}

				attributes.put(key, value);
			}

			for (int packageIndex = 0; packageIndex < pathCount;
				 packageIndex++) {

				finalImports.put(tokens[packageIndex], attributes);
			}
		}

		return finalImports;
	}

	private static final int _DELIMITER = 2;

	private static final int _ENDQUOTE = 8;

	private static final int _STARTQUOTE = 4;

}