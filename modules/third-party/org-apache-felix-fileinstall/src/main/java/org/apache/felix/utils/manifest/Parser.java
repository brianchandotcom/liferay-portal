/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.utils.manifest;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

	public static Clause[] parseClauses(String[] clauses)
		throws IllegalArgumentException {

		if (clauses == null) {
			return null;
		}

		List<Clause> completeList = new ArrayList<>();

		for (String clause : clauses) {

			// Break string into semi-colon delimited pieces.

			String[] tokens = parseDelimitedString(clause, ";");

			// Count the number of different clauses; clauses
			// will not have an '=' in their string. This assumes
			// that clauses come first, before directives and
			// attributes.

			int pathCount = 0;

			for (String token : tokens) {
				if (token.indexOf('=') != -1) {
					break;
				}

				pathCount++;
			}

			// Error if no packages were specified.

			if (pathCount == 0) {
				throw new IllegalArgumentException(
					"No path specified on clause: " + clause);
			}

			// Parse the directives/attributes.

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

				index = piece.indexOf("=");

				if (index <= 0) {
					throw new IllegalArgumentException(
						"Not a directive/attribute: " + clause);
				}

				// This a directive.

				if (piece.charAt(index - 1) == ':') {
					index--;
					separator = ":=";
				}

				// This an attribute.

				else {
					separator = "=";
				}

				String key = piece.substring(0, index);

				key = key.trim();

				String value = piece.substring(index + separator.length());

				value = value.trim();

				// Remove quotes, if value is quoted.

				if (value.startsWith("\"") && value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}

				// Save the directive/attribute in the appropriate array.

				if (separator.equals(":=")) {
					directives[directiveCount++] = new Directive(key, value);
				}
				else {
					attributes[attributeCount++] = new Attribute(key, value);
				}
			}

			// Shrink directive array.

			Directive[] dirsFinal = new Directive[directiveCount];

			System.arraycopy(directives, 0, dirsFinal, 0, directiveCount);

			// Shrink attribute array.

			Attribute[] attrsFinal = new Attribute[attributeCount];

			System.arraycopy(attributes, 0, attrsFinal, 0, attributeCount);

			// Create package attributes for each package and
			// set directives/attributes. Add each package to
			// completel list of packages.

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

	/**
	 * Parses delimited string and returns an array containing the tokens. This
	 * parser obeys quotes, so the delimiter character will be ignored if it is
	 * inside of a quote. This method assumes that the quote character is not
	 * included in the set of delimiter characters.
	 * @param value the delimited string to parse.
	 * @param delimiter the characters delimiting the tokens.
	 * @return an array of string tokens or null if there were no tokens.
	 */
	public static String[] parseDelimitedString(
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
			else if ((c == '"') && ((expecting & _STARTQUOTE) > 0)) {
				sb.append(c);
				expecting = _CHAR | _ENDQUOTE;
			}
			else if ((c == '"') && ((expecting & _ENDQUOTE) > 0)) {
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

	public static Clause[] parseHeader(String header)
		throws IllegalArgumentException {

		Clause[] clauses = null;

		if (header != null) {
			if (header.length() == 0) {
				throw new IllegalArgumentException(
					"The header cannot be an empty string");
			}

			String[] headers = parseDelimitedString(header, ",");

			clauses = parseClauses(headers);
		}

		if (clauses == null) {
			return new Clause[0];
		}

		return clauses;
	}

	private static final int _CHAR = 1;

	private static final int _DELIMITER = 2;

	private static final int _ENDQUOTE = 8;

	private static final int _STARTQUOTE = 4;

}
/* @generated */