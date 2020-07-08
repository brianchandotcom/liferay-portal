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
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Tambara
 */
public final class Parser {

	public static Clause[] parseHeader(String header)
		throws IllegalArgumentException {

		Clause[] clauses = null;

		if (header != null) {
			if (header.length() == 0) {
				throw new IllegalArgumentException(
					"The header cannot be an empty string");
			}

			String[] headers = _parseDelimitedString(header, StringPool.COMMA);

			clauses = _parseClauses(headers);
		}

		if (clauses == null) {
			return new Clause[0];
		}

		return clauses;
	}

	private static Clause[] _parseClauses(String[] clauses)
		throws IllegalArgumentException {

		if (clauses == null) {
			return null;
		}

		List<Clause> completeList = new ArrayList<>();

		for (String clause : clauses) {
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

			int size = tokens.length - pathCount;

			Directive[] directives = new Directive[size];
			Attribute[] attributes = new Attribute[size];

			int directiveCount = 0;
			int attributeCount = 0;

			int index = -1;

			String separator = null;

			for (int pieceIndex = pathCount; pieceIndex < tokens.length;
				 pieceIndex++) {

				String piece = tokens[pieceIndex];

				index = piece.indexOf(StringPool.EQUAL);

				if (index <= 0) {
					throw new IllegalArgumentException(
						"Not a directive/attribute: " + clause);
				}

				if (piece.charAt(index - 1) == CharPool.COLON) {
					index--;
					separator = ":=";
				}
				else {
					separator = StringPool.EQUAL;
				}

				String key = piece.substring(0, index);

				key = key.trim();

				String value = piece.substring(index + separator.length());

				value = value.trim();

				if (value.startsWith(StringPool.QUOTE) &&
					value.endsWith(StringPool.QUOTE)) {

					value = value.substring(1, value.length() - 1);
				}

				if (separator.equals(":=")) {
					directives[directiveCount++] = new Directive(key, value);
				}
				else {
					attributes[attributeCount++] = new Attribute(key, value);
				}
			}

			Directive[] dirsFinal = new Directive[directiveCount];

			System.arraycopy(directives, 0, dirsFinal, 0, directiveCount);

			Attribute[] attrsFinal = new Attribute[attributeCount];

			System.arraycopy(attributes, 0, attrsFinal, 0, attributeCount);

			Clause[] packages = new Clause[pathCount];

			for (int packageIndex = 0; packageIndex < pathCount;
				 packageIndex++) {

				packages[packageIndex] = new Clause(
					tokens[packageIndex], dirsFinal, attrsFinal);

				completeList.add(packages[packageIndex]);
			}
		}

		return completeList.toArray(new Clause[0]);
	}

	private static String[] _parseDelimitedString(
		String value, String delimiter) {

		if (value == null) {
			value = "";
		}

		List<String> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		int expecting = _CHAR | _DELIMITER | _STARTQUOTE;

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);

			if ((delimiter.indexOf(c) != -1) &&
				((expecting & _DELIMITER) > 0)) {

				String string = sb.toString();

				list.add(string.trim());

				sb.delete(0, sb.length());

				expecting = _CHAR | _DELIMITER | _STARTQUOTE;
			}
			else if ((c == CharPool.QUOTE) && ((expecting & _STARTQUOTE) > 0)) {
				sb.append(c);
				expecting = _CHAR | _ENDQUOTE;
			}
			else if ((c == CharPool.QUOTE) && ((expecting & _ENDQUOTE) > 0)) {
				sb.append(c);
				expecting = _CHAR | _STARTQUOTE | _DELIMITER;
			}
			else if ((expecting & _CHAR) > 0) {
				sb.append(c);
			}
			else {
				throw new IllegalArgumentException(
					"Invalid delimited string: " + value);
			}
		}

		String string = sb.toString();

		string = string.trim();

		if (string.length() > 0) {
			list.add(string);
		}

		return (String[])list.toArray(new String[0]);
	}

	private static final int _CHAR = 1;

	private static final int _DELIMITER = 2;

	private static final int _ENDQUOTE = 8;

	private static final int _STARTQUOTE = 4;

}