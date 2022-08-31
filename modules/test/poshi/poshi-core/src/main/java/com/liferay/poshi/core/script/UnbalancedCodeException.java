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

package com.liferay.poshi.core.script;

import java.net.URL;

/**
 * @author Kenji Heigel
 */
public class UnbalancedCodeException extends PoshiScriptParserException {

	public UnbalancedCodeException(
		String msg, int index, String code, URL filePathURL) {

		super(
			msg, _getErrorLineNumber(index, code),
			_getErrorSnippet(index, code), filePathURL);
	}

	private static int _getErrorLineNumber(int index, String code) {
		int lineNumber = 1;

		for (int i = 0; i < index; i++) {
			if (code.charAt(i) == '\n') {
				lineNumber++;
			}
		}

		return lineNumber;
	}

	private static String _getErrorSnippet(int index, String code) {
		int lineNumber = 1;

		int newLineIndex = -1;

		for (int i = 0; i < index; i++) {
			if (code.charAt(i) == '\n') {
				lineNumber++;

				newLineIndex = i;
			}
		}

		int column = 1;

		for (int i = newLineIndex + 1; i < index; i++) {
			if (code.charAt(i) == '\t') {
				column += 4;

				continue;
			}

			column++;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_getLine(lineNumber, code));
		sb.append("\n");

		for (int i = 1; i < column; i++) {
			sb.append(" ");
		}

		sb.append("^");

		return sb.toString();
	}

	private static String _getLine(int lineNumber, String code) {
		String[] lines = code.split("\n");

		return lines[lineNumber - 1].replace("\t", "    ");
	}

}