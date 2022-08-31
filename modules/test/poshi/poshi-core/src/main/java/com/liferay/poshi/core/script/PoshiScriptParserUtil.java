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

import com.liferay.poshi.core.elements.PoshiNode;

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

		StringBuilder sb = new StringBuilder();

		String methodParameterValue = sb.toString();

		for (char c : content.toCharArray()) {
			if ((c == ',') && isBalancedPoshiScript(methodParameterValue)) {
				if (pattern != null) {
					Matcher matcher = pattern.matcher(methodParameterValue);

					if (!matcher.matches()) {
						sb.append(c);

						continue;
					}
				}

				methodParameterValues.add(methodParameterValue);

				sb.setLength(0);

				continue;
			}

			sb.append(c);

			methodParameterValue = sb.toString();
		}

		if (pattern != null) {
			Matcher matcher = pattern.matcher(methodParameterValue);

			if (!matcher.matches()) {
				throw new PoshiScriptParserException(
					"Invalid Poshi Script parameter syntax", poshiNode);
			}
		}

		methodParameterValues.add(methodParameterValue);

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

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('(', ')');
				put('[', ']');
				put('\"', '\"');
				put('\'', '\'');
				put('{', '}');
			}
		};

}