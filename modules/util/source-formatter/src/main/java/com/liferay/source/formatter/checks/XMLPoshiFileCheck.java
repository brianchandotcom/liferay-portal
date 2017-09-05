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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPoshiFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith(".action") || fileName.endsWith(".function") ||
			fileName.endsWith(".macro") || fileName.endsWith(".testcase")) {

			content = _formatPoshiXML(fileName, content);
		}

		return content;
	}

	private void _checkPoshiCharactersAfterDefinition(
		String fileName, String content) {

		if (content.contains("/definition>") &&
			!content.endsWith("/definition>")) {

			addMessage(fileName, "Characters found after definition element");
		}
	}

	private void _checkPoshiCharactersBeforeDefinition(
		String fileName, String content) {

		if (!content.startsWith("<definition")) {
			addMessage(fileName, "Characters found before definition element");
		}
	}

	private String _fixPoshiXMLElementWithNoChild(String content) {
		Matcher matcher = _POSHI_ELEMENT_WITH_NO_CHILD_PATTERN.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replace(content, matcher.group(), "\" />");
		}

		return content;
	}

	private String _fixPoshiXMLEndLines(String content) {
		Matcher matcher = _POSHI_END_LINES_PATTERN.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(), ">\n\n" + matcher.group(1));

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	private String _fixPoshiXMLEndLinesAfterClosingElement(String content) {
		Matcher matcher =
			_POSHI_END_LINES_AFTER_CLOSING_ELEMENT_PATTERN.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String closingElementName = matcher.group(1);

			if (StringUtil.equalsIgnoreCase("</and>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</elseif>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</not>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</or>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</then>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
			else if (!StringUtil.equalsIgnoreCase(
						"</var>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
		}

		return content;
	}

	private String _fixPoshiXMLEndLinesBeforeClosingElement(String content) {
		Matcher matcher =
			_POSHI_END_LINES_BEFORE_CLOSING_ELEMENT_PATTERN.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(1), "\n");

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	private String _fixPoshiXMLNumberOfTabs(String content) {
		Matcher matcher = _POSHI_TABS_PATTERN.matcher(content);

		int tabCount = 0;

		boolean ignoredCdataBlock = false;
		boolean ignoredCommentBlock = false;

		while (matcher.find()) {
			String statement = matcher.group();

			Matcher quoteWithSlashMatcher =
				_POSHI_QUOTE_WITH_SLASH_PATTERN.matcher(statement);

			String fixedQuoteStatement = statement;

			if (quoteWithSlashMatcher.find()) {
				fixedQuoteStatement = StringUtil.replace(
					statement, quoteWithSlashMatcher.group(), "\"\"");
			}

			Matcher closingTagMatcher = _POSHI_CLOSING_TAG_PATTERN.matcher(
				fixedQuoteStatement);
			Matcher openingTagMatcher = _POSHI_OPENING_TAG_PATTERN.matcher(
				fixedQuoteStatement);
			Matcher wholeTagMatcher = _POSHI_WHOLE_TAG_PATTERN.matcher(
				fixedQuoteStatement);

			if (closingTagMatcher.find() && !openingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount--;
			}

			if (statement.contains("]]>")) {
				ignoredCdataBlock = false;
			}
			else if (statement.contains("<![CDATA[")) {
				ignoredCdataBlock = true;
			}

			if (statement.contains("-->")) {
				ignoredCommentBlock = false;
			}
			else if (statement.contains("<!--")) {
				ignoredCommentBlock = true;
			}

			if (!ignoredCommentBlock && !ignoredCdataBlock) {
				StringBundler sb = new StringBundler(tabCount + 1);

				for (int i = 0; i < tabCount; i++) {
					sb.append(StringPool.TAB);
				}

				sb.append(StringPool.LESS_THAN);

				String replacement = sb.toString();

				if (!replacement.equals(matcher.group(1))) {
					String newStatement = StringUtil.replace(
						statement, matcher.group(1), replacement);

					return StringUtil.replaceFirst(
						content, statement, newStatement, matcher.start());
				}
			}

			if (openingTagMatcher.find() && !closingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount++;
			}
		}

		return content;
	}

	private String _formatPoshiXML(String fileName, String content)
		throws Exception {

		_checkPoshiCharactersAfterDefinition(fileName, content);
		_checkPoshiCharactersBeforeDefinition(fileName, content);

		try {
			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				checkElementOrder(
					fileName, commandElement, "property", null,
					new ElementComparator());
			}
		}
		catch (Exception e) {
		}

		content = _sortPoshiCommands(content);
		content = _sortPoshiVariables(content);

		content = _fixPoshiXMLElementWithNoChild(content);

		content = _fixPoshiXMLEndLinesAfterClosingElement(content);

		content = _fixPoshiXMLEndLinesBeforeClosingElement(content);

		content = _fixPoshiXMLEndLines(content);

		return _fixPoshiXMLNumberOfTabs(content);
	}

	private String _sortPoshiCommands(String content) {
		Matcher matcher = _POSHI_COMMANDS_PATTERN.matcher(content);

		Map<String, String> commandBlocksMap = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		String previousName = StringPool.BLANK;

		boolean hasUnsortedCommands = false;

		while (matcher.find()) {
			String commandBlock = matcher.group();
			String commandName = matcher.group(1);

			commandBlocksMap.put(commandName, commandBlock);

			if (!hasUnsortedCommands &&
				(commandName.compareToIgnoreCase(previousName) < 0)) {

				hasUnsortedCommands = true;
			}

			previousName = commandName;
		}

		if (!hasUnsortedCommands) {
			return content;
		}

		StringBundler sb = new StringBundler();

		matcher = _POSHI_SET_UP_PATTERN.matcher(content);

		if (matcher.find()) {
			String setUpBlock = matcher.group();

			content = content.replace(setUpBlock, "");

			sb.append(setUpBlock);
		}

		matcher = _POSHI_TEAR_DOWN_PATTERN.matcher(content);

		if (matcher.find()) {
			String tearDownBlock = matcher.group();

			content = content.replace(tearDownBlock, "");

			sb.append(tearDownBlock);
		}

		for (Map.Entry<String, String> entry : commandBlocksMap.entrySet()) {
			sb.append("\n\t");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		int x = content.indexOf("<command");
		int y = content.lastIndexOf("</command>");

		String commandBlock = content.substring(x, y);

		commandBlock = "\n\t" + commandBlock + "</command>\n";

		String newCommandBlock = sb.toString();

		return StringUtil.replaceFirst(content, commandBlock, newCommandBlock);
	}

	private String _sortPoshiVariables(String content) {
		Matcher matcher = _POSHI_VARIABLES_BLOCK_PATTERN.matcher(content);

		while (matcher.find()) {
			String previousName = StringPool.BLANK;
			String tabs = StringPool.BLANK;

			Map<String, String> variableLinesMap = new TreeMap<>(
				String.CASE_INSENSITIVE_ORDER);

			String variableBlock = matcher.group(1);

			variableBlock = variableBlock.trim();

			Matcher variableLineMatcher = _POSHI_VARIABLE_LINE_PATTERN.matcher(
				variableBlock);

			boolean hasUnsortedVariables = false;

			while (variableLineMatcher.find()) {
				if (tabs.equals(StringPool.BLANK)) {
					tabs = variableLineMatcher.group(1);
				}

				String variableLine = variableLineMatcher.group(2);
				String variableName = variableLineMatcher.group(3);

				variableLinesMap.put(variableName, variableLine);

				if (!hasUnsortedVariables &&
					(variableName.compareToIgnoreCase(previousName) < 0)) {

					hasUnsortedVariables = true;
				}

				previousName = variableName;
			}

			if (!hasUnsortedVariables) {
				continue;
			}

			StringBundler sb = new StringBundler();

			for (Map.Entry<String, String> entry :
					variableLinesMap.entrySet()) {

				sb.append(tabs);
				sb.append(entry.getValue());
				sb.append("\n");
			}

			String newVariableBlock = sb.toString();

			newVariableBlock = newVariableBlock.trim();

			content = StringUtil.replaceFirst(
				content, variableBlock, newVariableBlock);
		}

		return content;
	}

	private static final Pattern _POSHI_CLOSING_TAG_PATTERN =
		RegexUtil.getPattern("</[^>/]*>");

	private static final Pattern _POSHI_COMMANDS_PATTERN = RegexUtil.getPattern(
		"\\<command.*name=\\\"([^\\\"]*)\\\".*\\>[\\s\\S]*?\\</command\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+");

	private static final Pattern _POSHI_ELEMENT_WITH_NO_CHILD_PATTERN =
		RegexUtil.getPattern("\\\"[\\s]*\\>[\\n\\s\\t]*\\</[a-z\\-]+>");

	private static final Pattern
		_POSHI_END_LINES_AFTER_CLOSING_ELEMENT_PATTERN = RegexUtil.getPattern(
			"(\\</[a-z\\-]+>)(\\n+)\\t*\\<[a-z]+");

	private static final Pattern
		_POSHI_END_LINES_BEFORE_CLOSING_ELEMENT_PATTERN = RegexUtil.getPattern(
			"(\\n+)(\\t*</[a-z\\-]+>)");

	private static final Pattern _POSHI_END_LINES_PATTERN =
		RegexUtil.getPattern("\\>\\n\\n\\n+(\\t*\\<)");

	private static final Pattern _POSHI_OPENING_TAG_PATTERN =
		RegexUtil.getPattern("<[^/][^>]*[^/]>");

	private static final Pattern _POSHI_QUOTE_WITH_SLASH_PATTERN =
		RegexUtil.getPattern("\"[^\"]*\\>[^\"]*\"");

	private static final Pattern _POSHI_SET_UP_PATTERN = RegexUtil.getPattern(
		"\\n[\\t]++\\<set-up\\>([\\s\\S]*?)\\</set-up\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");

	private static final Pattern _POSHI_TABS_PATTERN = RegexUtil.getPattern(
		"\\n*([ \\t]*<).*");

	private static final Pattern _POSHI_TEAR_DOWN_PATTERN =
		RegexUtil.getPattern(
			"\\n[\\t]++\\<tear-down\\>([\\s\\S]*?)\\</tear-down\\>" +
				"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");

	private static final Pattern _POSHI_VARIABLE_LINE_PATTERN =
		RegexUtil.getPattern(
			"([\\t]*+)(\\<var.*?name=\\\"([^\\\"]*)\\\".*?/\\>.*+" +
				"(?:\\</var\\>)??)");

	private static final Pattern _POSHI_VARIABLES_BLOCK_PATTERN =
		RegexUtil.getPattern(
			"((?:[\\t]*+\\<var.*?\\>\\n[\\t]*+){2,}?)" +
				"(?:(?:\\n){1,}+|\\</execute\\>)");

	private static final Pattern _POSHI_WHOLE_TAG_PATTERN =
		RegexUtil.getPattern("<[^\\>^/]*\\/>");

}