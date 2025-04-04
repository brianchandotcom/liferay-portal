/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.check.util.YMLSourceUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class YMLWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatDefinitions(fileName, content, StringPool.BLANK, 0);

		content = _formatSequencesAndMappings(content);

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return _removeWhitespace(content);
	}

	private String _formatDefinition(
		String fileName, String definition, String[] definitionLines,
		String indent, int level, boolean hasNestedDefinitions) {

		String expectedIndent = StringPool.BLANK;

		for (int j = 0; j < level; j++) {
			expectedIndent = expectedIndent + StringPool.FOUR_SPACES;
		}

		String newDefinition = definition;

		if (!expectedIndent.equals(indent)) {
			newDefinition = expectedIndent + StringUtil.trimLeading(definition);
		}

		if (hasNestedDefinitions) {
			return newDefinition;
		}

		if (definitionLines[0].endsWith("|-")) {
			String leadingSpaces = null;
			int leadingSpacesLength = 0;
			StringBundler sb = new StringBundler(definitionLines.length * 3);

			for (int i = 0; i < definitionLines.length; i++) {
				if (i == 0) {
					leadingSpaces = SourceUtil.getLeadingSpaces(
						definitionLines[i]);

					leadingSpacesLength = leadingSpaces.length();

					sb.append(expectedIndent);
					sb.append(
						definitionLines[i].substring(leadingSpacesLength));
					sb.append("\n");

					continue;
				}

				if (i == 1) {
					leadingSpaces = SourceUtil.getLeadingSpaces(
						definitionLines[i]);

					leadingSpacesLength = leadingSpaces.length();
				}

				sb.append(expectedIndent + StringPool.FOUR_SPACES);
				sb.append(definitionLines[i].substring(leadingSpacesLength));
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			return sb.toString();
		}

		if (definitionLines.length <= 1) {
			return newDefinition;
		}

		String firstLine = definitionLines[1];

		String newNestedContent = StringPool.BLANK;
		String oldNestedContent = StringPool.BLANK;

		String nestedIndent = firstLine.replaceAll("^(\\s+).+", "$1");

		if (nestedIndent.equals(firstLine)) {
			nestedIndent = StringPool.BLANK;
		}

		for (int j = 1; j < definitionLines.length; j++) {
			String line = definitionLines[j];

			if (j > 1) {
				newNestedContent = newNestedContent + StringPool.NEW_LINE;
				oldNestedContent = oldNestedContent + StringPool.NEW_LINE;
			}

			newNestedContent = newNestedContent + line;
			oldNestedContent = oldNestedContent + line;

			if (Validator.isNull(line)) {
				continue;
			}

			String curIndent = line.replaceAll("^(\\s+).+", "$1");

			if (curIndent.equals(line)) {
				curIndent = StringPool.BLANK;
			}

			if (!curIndent.equals(nestedIndent)) {
				continue;
			}

			String trimmedLine = StringUtil.trimLeading(line);

			newNestedContent = StringUtil.replaceLast(
				newNestedContent, line,
				expectedIndent + StringPool.FOUR_SPACES + trimmedLine);
		}

		if (!newNestedContent.equals(oldNestedContent)) {
			if (!_hasMapInsideList(definitionLines)) {
				newDefinition = StringUtil.replaceFirst(
					newDefinition, oldNestedContent, newNestedContent);
			}
			else {
				String message = StringBundler.concat(
					"Incorrect whitespace, expected \"", expectedIndent,
					"    \"\n", oldNestedContent);

				addMessage(fileName, message);
			}
		}

		return newDefinition;
	}

	private String _formatDefinitions(
		String fileName, String content, String indent, int level) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		String[] lines = content.split("\n");

		if (lines.length == 1) {
			return content;
		}

		int pos = lines[0].length() + 1;

		for (String definition : definitions) {
			lines = definition.split("\n");

			if ((lines.length != 0) && lines[0].endsWith("|-")) {
				String newDefinition = _formatDefinition(
					fileName, definition, lines, indent, level, false);

				if (!newDefinition.equals(definition)) {
					content = StringUtil.replaceFirst(
						content, definition, newDefinition, 0);
				}

				pos = pos + newDefinition.length() + 1;

				continue;
			}

			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			List<String> nestedDefinitions = Collections.emptyList();

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				nestedDefinitions = YMLSourceUtil.getDefinitions(
					definition, nestedDefinitionIndent);

				String newDefinition = _formatDefinitions(
					fileName, definition, nestedDefinitionIndent, level + 1);

				if (!newDefinition.equals(definition)) {
					content = StringUtil.replaceFirst(
						content, definition, newDefinition, 0);

					definition = newDefinition;
				}
			}

			lines = definition.split("\n");

			String newDefinition = _formatDefinition(
				fileName, definition, lines, indent, level,
				!nestedDefinitions.isEmpty());

			if (!newDefinition.equals(definition)) {
				content = StringUtil.replaceFirst(
					content, definition, newDefinition, pos);
			}

			pos = pos + newDefinition.length() + 1;
		}

		return content;
	}

	private String _formatSequencesAndMappings(String content) {
		Matcher matcher = _mappingEntryPattern.matcher(content);

		while (matcher.find()) {
			String s = matcher.group();

			String[] lines = s.split("\n");

			if ((lines.length <= 1) ||
				StringUtil.startsWith(lines[0].trim(), "- '")) {

				continue;
			}

			StringBundler sb = new StringBundler();

			for (int i = 1; i < lines.length; i++) {
				sb.append(StringPool.NEW_LINE);

				if (Validator.isNotNull(lines[i])) {
					sb.append(lines[i].substring(2));
				}
			}

			sb.append(StringPool.NEW_LINE);

			String newContent = _formatSequencesAndMappings(sb.toString());

			if (s.endsWith("\n\n")) {
				newContent = newContent + "\n";
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(), lines[0] + newContent);
		}

		return content;
	}

	private boolean _hasMapInsideList(String[] lines) {
		if (lines.length <= 1) {
			return false;
		}

		String trimmedFirstLine = StringUtil.trimLeading(lines[1]);

		if (!trimmedFirstLine.startsWith(StringPool.DASH)) {
			return false;
		}

		for (int j = 1; j < lines.length; j++) {
			String trimmedLine = StringUtil.trimLeading(lines[j]);

			if (trimmedLine.matches("\\w+:.*")) {
				return true;
			}
		}

		return false;
	}

	private String _removeWhitespace(String content) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = _removeWhitespaceAfterOpenBracket(line);
				line = _removeWhitespaceAfterOpenCurlyBrace(line);
				line = _removeWhitespaceBeforeCloseBracket(line);
				line = _removeWhitespaceBeforeCloseCurlyBrace(line);

				sb.append(line);

				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _removeWhitespaceAfterOpenBracket(String line) {
		String trimmedLine = line.trim();

		if (trimmedLine.startsWith("elif [") ||
			trimmedLine.startsWith("if [")) {

			return line;
		}

		int x = line.indexOf("[ ");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "[", "]") != 0)) {

			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceAfterOpenCurlyBrace(String line) {
		int x = line.indexOf("{ ");

		if (x == -1) {
			return line;
		}

		if (x == 0) {
			return StringUtil.replaceFirst(
				line, StringPool.SPACE, StringPool.BLANK, 0);
		}

		if (ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "{", "}") != 0)) {

			return line;
		}

		char c = line.charAt(x - 1);

		if (c == CharPool.OPEN_CURLY_BRACE) {
			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceBeforeCloseBracket(String line) {
		String trimmedLine = line.trim();

		if (trimmedLine.startsWith("elif [") ||
			trimmedLine.startsWith("if [")) {

			return line;
		}

		int x = line.indexOf(" ]");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "[", "]") != 0)) {

			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceBeforeCloseCurlyBrace(String line) {
		int x = line.indexOf(" }");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "{", "}") != 0)) {

			return line;
		}

		if (x == (line.length() - 2)) {
			return StringUtil.replaceFirst(
				line, StringPool.SPACE, StringPool.BLANK, x);
		}

		char c = line.charAt(x + 2);

		if (c == CharPool.CLOSE_CURLY_BRACE) {
			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private static final Pattern _mappingEntryPattern = Pattern.compile(
		"^( *)- *?(\n|\\Z)((\\1 +.+)(\n|\\Z)+)+", Pattern.MULTILINE);

}