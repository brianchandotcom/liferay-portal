/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.groovy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenji Heigel
 */
public class GroovyStyleChecker {

	public static List<Violation> checkMissingSemicolons(String blockText) {
		if ((blockText == null) || blockText.isEmpty()) {
			return Collections.emptyList();
		}

		List<Violation> violations = new ArrayList<>();

		blockText = _removeComment(blockText);

		String[] lines = blockText.split("\n", -1);

		int bracketDepth = 0;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();

			String nextLine = "";

			for (int j = i + 1; j < lines.length; j++) {
				nextLine = lines[j].trim();

				if (!nextLine.isEmpty()) {
					break;
				}
			}

			if (_isMissingSemicolon(bracketDepth, line, nextLine)) {
				violations.add(new Violation(lines[i], i + 1));
			}

			bracketDepth += _getBracketDelta(line);
		}

		return violations;
	}

	public static List<Violation> checkUnescapedDollarInterpolation(
		String blockText) {

		if ((blockText == null) || blockText.isEmpty()) {
			return Collections.emptyList();
		}

		List<Violation> violations = new ArrayList<>();

		blockText = _removeComment(blockText);

		String[] lines = blockText.split("\n", -1);

		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			String line = lines[lineIndex];

			boolean inDoubleQuote = false;
			boolean inSingleQuote = false;

			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				char nextChar =
					((i + 1) < line.length()) ? line.charAt(i + 1) : 0;

				if (inDoubleQuote) {
					if (c == '\\') {
						i++;

						continue;
					}

					if (c == '"') {
						inDoubleQuote = false;

						continue;
					}

					if ((c == '$') && (nextChar == '{')) {
						violations.add(new Violation(line, lineIndex + 1));

						break;
					}

					continue;
				}

				if (inSingleQuote) {
					if (c == '\\') {
						i++;

						continue;
					}

					if (c == '\'') {
						inSingleQuote = false;
					}

					continue;
				}

				if (c == '"') {
					inDoubleQuote = true;
				}

				if (c == '\'') {
					inSingleQuote = true;
				}
			}
		}

		return violations;
	}

	public static final class Violation {

		public String getLine() {
			return _line;
		}

		public int getLineNumber() {
			return _lineNumber;
		}

		private Violation(String line, int lineNumber) {
			_line = line;
			_lineNumber = lineNumber;
		}

		private final String _line;
		private final int _lineNumber;

	}

	private static int _getBracketDelta(String line) {
		int delta = 0;

		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (!stack.isEmpty()) {
				if (c == '\\') {
					i++;

					continue;
				}

				if (c == stack.peek()) {
					stack.pop();
				}

				continue;
			}

			if ((c == '"') || (c == '\'')) {
				stack.push(c);
			}

			if ((c == '(') || (c == '[')) {
				delta++;
			}

			if ((c == ')') || (c == ']')) {
				delta--;
			}
		}

		return delta;
	}

	private static boolean _isContinuedOnNextLine(String nextLine) {
		if (nextLine.equals("{") || nextLine.startsWith(".") ||
			nextLine.startsWith("throws ") || nextLine.startsWith("{ ")) {

			return true;
		}

		return false;
	}

	private static boolean _isMissingSemicolon(
		int bracketDepth, String line, String nextLine) {

		if (line.isEmpty() || (bracketDepth > 0) ||
			_isStandaloneKeyword(line) || (line.charAt(0) == '@') ||
			_isTerminated(line) || _isContinuedOnNextLine(nextLine)) {

			return false;
		}

		return true;
	}

	private static boolean _isStandaloneKeyword(String line) {
		Matcher matcher = _standaloneKeywordPattern.matcher(line);

		return matcher.matches();
	}

	private static boolean _isTerminated(String line) {
		char c = line.charAt(line.length() - 1);

		if (_terminators.contains(c)) {
			return true;
		}

		if (c == ')') {
			Matcher matcher = _controlHeaderPattern.matcher(line);

			return matcher.matches();
		}

		return false;
	}

	private static String _removeComment(String text) {
		StringBuilder sb = new StringBuilder(text.length());

		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (c == '\n') {
				sb.append(c);

				if (!stack.isEmpty() && (stack.peek() == '/')) {
					stack.pop();
				}

				continue;
			}

			char nextChar = ((i + 1) < text.length()) ? text.charAt(i + 1) : 0;

			if (!stack.isEmpty()) {
				char stackPeek = stack.peek();

				if (stackPeek == '/') {
					continue;
				}

				if (stackPeek == '*') {
					if ((c == '*') && (nextChar == '/')) {
						stack.pop();
						i++;
					}

					continue;
				}

				sb.append(c);

				if (c == '\\') {
					if (nextChar != 0) {
						sb.append(nextChar);
						i++;
					}
				}
				else if (c == stackPeek) {
					stack.pop();
				}

				continue;
			}

			if ((c == '/') && ((nextChar == '/') || (nextChar == '*'))) {
				stack.push(nextChar);
				i++;

				continue;
			}

			sb.append(c);

			if ((c == '"') || (c == '\'')) {
				stack.push(c);
			}
		}

		return sb.toString();
	}

	private static final Pattern _controlHeaderPattern = Pattern.compile(
		"^(catch|for|if|switch|synchronized|while)\\b.*\\)$");
	private static final Pattern _standaloneKeywordPattern = Pattern.compile(
		"^(\\} )?(do|else|finally|try)( \\{)?$");
	private static final Set<Character> _terminators = new HashSet<>(
		Arrays.asList(
			'%', '&', '(', '*', '+', ',', '-', '/', ':', ';', '<', '=', '>',
			'?', '[', '^', '{', '|', '}', '~'));

}