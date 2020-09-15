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

package com.liferay.poshi.runner.script;

/**
 * @author Kenji Heigel
 */
public class UnbalancedCodeException extends PoshiScriptParserException {

	public UnbalancedCodeException(String msg, int index, String code) {
		super(msg);

		_processLine(index, code);
	}

	@Override
	public String getErrorSnippet() {
		return _errorSnippet;
	}

	public void setErrorSnippet(String errorSnippet) {
		_errorSnippet = errorSnippet;
	}

	private static String _getLine(int lineNumber, String code) {
		String[] lines = code.split("\n");

		return lines[lineNumber - 1].replace("\t", "    ");
	}

	private void _processLine(int index, String code) {
		int lineNumber = 1;

		int newLineIndex = -1;

		for (int i = 0; i < index; i++) {
			if (code.charAt(i) == '\n') {
				lineNumber++;

				newLineIndex = i;
			}
		}

		setErrorLineNumber(lineNumber);

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

		setErrorSnippet(sb.toString());
	}

	private String _errorSnippet = "";

}