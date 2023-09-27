/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.script;

import com.liferay.poshi.core.elements.PoshiNode;
import com.liferay.poshi.core.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Calum Ragan
 */
public class PoshiScriptParserUtil {

	public static List<String> getMethodParameterValues(
			String content, Pattern pattern, PoshiNode poshiNode)
		throws PoshiScriptParserException {

		List<String> methodParameterValues = new ArrayList<>();

		if (content.length() == 0) {
			return methodParameterValues;
		}

		boolean expectedComma = false;

		StringBuilder sb = new StringBuilder();

		String methodParameterValue = sb.toString();

		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < content.length(); i++) {
			Character c1 = content.charAt(i);

			Character c2 = null;
			Character c3 = null;

			if ((i + 2) < content.length()) {
				c2 = content.charAt(i + 1);
				c3 = content.charAt(i + 2);
			}

			if ((c1 == ',') && expectedComma &&
				_patternIsNullOrMatches(pattern, methodParameterValue)) {

				methodParameterValues.add(methodParameterValue.trim());

				sb.setLength(0);
			}
			else {
				if ((c1 == ',') && stack.isEmpty() &&
					_patternIsNullOrMatches(pattern, methodParameterValue)) {

					expectedComma = true;

					methodParameterValues.add(methodParameterValue.trim());

					sb.setLength(0);
				}
				else {
					sb.append(c1);

					methodParameterValue = sb.toString();
				}
			}

			if (c1 == '`') {
				continue;
			}

			if (!stack.isEmpty()) {
				Character topCodeBoundary = stack.peek();

				if (_isMultilineVariableBoundary(c1, c2, c3)) {
					c1 = '`';
				}

				if ((c1 == _codeBoundariesMap.get(topCodeBoundary)) &&
					(content.charAt(i - 1) != '\\')) {

					stack.pop();

					if (stack.isEmpty()) {
						expectedComma = true;
					}

					continue;
				}

				if ((topCodeBoundary == '\"') || (topCodeBoundary == '\'') ||
					(topCodeBoundary == '`')) {

					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c1)) {
				if (_isMultilineVariableBoundary(c1, c2, c3)) {
					c1 = '`';

					sb.append(c2);
					sb.append(c3);

					methodParameterValue = sb.toString();

					i = i + 2;
				}

				stack.push(c1);

				continue;
			}

			if (expectedComma) {
				if (c1 == ',') {
					expectedComma = false;

					continue;
				}

				if (Character.isWhitespace(c1)) {
					continue;
				}

				PoshiScriptParserException poshiScriptParserException =
					new PoshiScriptParserException(
						"Missing comma in Poshi file call", poshiNode);

				int additionalLines = Math.max(
					StringUtil.count(content, "\n", i) - 1, 0);

				poshiScriptParserException.setErrorLineNumber(
					poshiScriptParserException.getErrorLineNumber() +
						additionalLines);

				throw poshiScriptParserException;
			}
		}

		if (!_patternIsNullOrMatches(pattern, methodParameterValue)) {
			throw new PoshiScriptParserException(
				"Invalid Poshi Script parameter syntax", poshiNode);
		}

		methodParameterValues.add(methodParameterValue.trim());

		return methodParameterValues;
	}

	public static List<String> getMethodParameterValues(
		String content, PoshiNode poshiNode) {

		try {
			return getMethodParameterValues(content, null, poshiNode);
		}
		catch (PoshiScriptParserException poshiScriptParserException) {
			poshiScriptParserException.printStackTrace();

			return new ArrayList<>();
		}
	}

	public static boolean isBalancedPoshiScript(String poshiScript) {
		try {
			return isBalancedPoshiScript(poshiScript, null, false);
		}
		catch (UnbalancedCodeException unbalancedCodeException) {
			return false;
		}
	}

	public static boolean isBalancedPoshiScript(
			String poshiScript, URL filePathURL, boolean throwException)
		throws UnbalancedCodeException {

		poshiScript = _fixPoshiScript(poshiScript);

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < poshiScript.length(); i++) {
			char c = poshiScript.charAt(i);

			if (c == '`') {
				continue;
			}

			if (!stack.isEmpty()) {
				int topIndex = stack.peek();

				Character topCodeBoundary = poshiScript.charAt(topIndex);

				if ((c == _codeBoundariesMap.get(topCodeBoundary)) && (i > 0) &&
					(poshiScript.charAt(i - 1) != '\\')) {

					stack.pop();

					continue;
				}

				if ((topCodeBoundary == '\"') || (topCodeBoundary == '\'')) {
					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c)) {
				stack.push(i);

				continue;
			}

			if (_codeBoundariesMap.containsValue(c)) {
				if (throwException) {
					throw new UnbalancedCodeException(
						"Unexpected closing boundary", i, poshiScript,
						filePathURL);
				}

				return false;
			}
		}

		boolean balanced = stack.isEmpty();

		if (!balanced && throwException) {
			throw new UnbalancedCodeException(
				"Unmatched opening boundary", stack.peek(), poshiScript,
				filePathURL);
		}

		return balanced;
	}

	private static String _fixPoshiScript(String poshiScript) {
		if (poshiScript.contains("/*") && poshiScript.contains("*/")) {
			poshiScript = poshiScript.replaceAll("(?s)/\\*.*?\\*/", "/\\*\\*/");
		}

		if (poshiScript.contains("'''")) {
			poshiScript = poshiScript.replaceAll(
				"(?s)\'\'\'.*?\'\'\'", "\'\'\'\'\'\'");
		}

		if (poshiScript.contains("//")) {
			poshiScript = poshiScript.replaceAll("(?m)\n[\\s]*//.*?$", "//\n");
		}

		return poshiScript.trim();
	}

	private static boolean _isMultilineVariableBoundary(
		Character c1, Character c2, Character c3) {

		if ((c1 != null) && (c2 != null) && (c3 != null) && (c1 == '\'') &&
			(c2 == '\'') && (c3 == '\'')) {

			return true;
		}

		return false;
	}

	private static boolean _patternIsNullOrMatches(
		Pattern pattern, String input) {

		if ((pattern == null) || (input == null)) {
			return true;
		}

		Matcher matcher = pattern.matcher(input);

		return matcher.matches();
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('(', ')');
				put('[', ']');
				put('\"', '\"');
				put('\'', '\'');
				put('`', '`');
				put('{', '}');
			}
		};

}