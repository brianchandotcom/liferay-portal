/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.prose;

import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

/**
 * @author Yi-Chen Tsai
 */
public class PoshiProseStatement extends BasePoshiProse {

	public PoshiProseStatement(String proseStatement) {
		for (String proseKeyword : PoshiProseStatement.KEYWORDS) {
			if (proseStatement.startsWith(proseKeyword)) {
				proseStatement = StringUtil.replaceFirst(
					proseStatement, proseKeyword, "");

				break;
			}
		}

		_proseStatement = proseStatement;

		proseStatement = formatProseStatement(proseStatement);

		String proseStatementMatchingString = getProseStatementMatchingString();

		_poshiProseMatcher = PoshiProseMatcher.getPoshiProseMatcher(
			proseStatementMatchingString);

		if (_poshiProseMatcher == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to find matching prose for '");
			sb.append(proseStatementMatchingString);
			sb.append("'");

			throw new RuntimeException(sb.toString());
		}

		List<String> varNames = _poshiProseMatcher.getVarNames();

		List<String> varValues = new ArrayList<>();

		Matcher varValueMatcher = _varValuePattern.matcher(proseStatement);

		while (varValueMatcher.find()) {
			varValues.add(varValueMatcher.group(1));
		}

		for (int i = 0; i < varNames.size(); i++) {
			String varName = varNames.get(i);

			if (_varMap.containsKey(varName)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Duplicate variable value assignment: ${");
				sb.append(varName);
				sb.append("} already has a value of ");
				sb.append(_varMap.get(varName));
				sb.append("\nProse statement: ");
				sb.append(_proseStatement);
				sb.append("\nMatching macro prose statement: ");
				sb.append(_poshiProseMatcher.getPoshiProse());

				throw new RuntimeException(sb.toString());
			}

			String varValue;

			if ((i + 1) == varNames.size()) {
				Matcher multiLineStringMatcher =
					_multiLineStringPattern.matcher(proseStatement);
				Matcher tableMatcher = _tablePattern.matcher(proseStatement);

				if (multiLineStringMatcher.find()) {
					varValue = multiLineStringMatcher.group(1);
				}
				else if (tableMatcher.find()) {
					varValue = tableMatcher.group(1);
				}
				else {
					varValue = varValues.get(i);
				}
			}
			else {
				varValue = varValues.get(i);
			}

			_varMap.put(varName, varValue);
		}
	}

	@Override
	public Element toElement() {
		Element element = Dom4JUtil.getNewElement(
			"execute", null,
			new DefaultAttribute(
				"macro",
				_poshiProseMatcher.getMacroNamespacedClassCommandName()));

		Element proseElement = Dom4JUtil.getNewElement("prose", element);

		proseElement.addCDATA(_proseStatement);

		for (Map.Entry<String, String> varMapEntry : _varMap.entrySet()) {
			Element varElement = Dom4JUtil.getNewElement(
				"var", null,
				new DefaultAttribute("name", varMapEntry.getKey()));

			String value = varMapEntry.getValue();

			if (value.matches(_tablePattern.pattern())) {
				varElement.addAttribute("type", "Table");

				varElement.addCDATA(value);
			}
			else if (value.contains(_LINE_SEPARATOR)) {
				varElement.addCDATA(value);
			}
			else {
				varElement.addAttribute("value", value);
			}

			Dom4JUtil.addToElement(element, varElement);
		}

		return element;
	}

	protected String formatProseStatement(String proseStatement) {
		String formattedProseStatement = proseStatement.trim();

		return formattedProseStatement.replaceAll(
			_LINE_SEPARATOR + "\t\t", _LINE_SEPARATOR);
	}

	protected String getProseStatementMatchingString() {
		String proseStatement = formatProseStatement(_proseStatement);

		String proseStatementMatchingString = proseStatement.replaceAll(
			_multiLineStringPattern.pattern(), " \"\"");

		proseStatementMatchingString = proseStatementMatchingString.replaceAll(
			_tablePattern.pattern(), " \"\"");

		proseStatementMatchingString = proseStatementMatchingString.replaceAll(
			_varValuePattern.pattern(), "\"\"");

		return proseStatementMatchingString;
	}

	protected static final String[] KEYWORDS = {
		"*", "And", "Given", "Then", "When"
	};

	private static final String _LINE_SEPARATOR = System.lineSeparator();

	private static final Pattern _multiLineStringPattern = Pattern.compile(
		"(?s)\\s*\"\"\".*?\\R(.*?)\\s*\"\"\"");
	private static final Pattern _tablePattern = Pattern.compile(
		"(?s)\\s*(\\|.*\\|$)");
	private static final Pattern _varValuePattern = Pattern.compile(
		"\"(.*?)\"");

	private final PoshiProseMatcher _poshiProseMatcher;
	private final String _proseStatement;
	private final Map<String, String> _varMap = new LinkedHashMap<>();

}