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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

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

		List<String> contentBlocks = YMLSourceUtil.getContentBlocks(
			content, _styleBlockPattern);

		StringBundler sb = new StringBundler(contentBlocks.size() * 2);

		for (int i = 0; i < contentBlocks.size(); i++) {
			String contentBlock = contentBlocks.get(i);

			if ((i % 2) != 0) {
				sb.append(contentBlock);
				sb.append(StringPool.NEW_LINE);

				continue;
			}

			contentBlock = contentBlock.replaceAll(
				"(\\{\\{)(?!(-| [^ ])[^\\}]*[^ ] \\}\\})( *)(?!-)(.*?) *(\\}" +
					"\\})",
				"$1 $4 $5");

			contentBlock = StringUtil.replace(
				contentBlock, CharPool.TAB, StringPool.FOUR_SPACES);

			contentBlock = super.doProcess(
				fileName, absolutePath, contentBlock);

			if (contentBlock.startsWith("---") && (i != 0)) {
				contentBlock = StringPool.NEW_LINE + contentBlock;
			}

			sb.append(contentBlock);

			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		content = _formatDefinitions(
			fileName, sb.toString(), StringPool.BLANK, 0);

		content = _formatSequencesAndMappings(content);

		if (isAllowTrailingEmptyLines(fileName, absolutePath) &&
			content.endsWith("\n")) {

			return content;
		}

		return _formatWhitespace(content);
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
			StringBundler sb = new StringBundler(definitionLines.length * 3);

			for (String line : definitionLines) {
				sb.append(expectedIndent);
				sb.append(line.substring(indent.length()));
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
					"Incorrect whitespace, expected '", expectedIndent,
					"    '\n", oldNestedContent);

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

		int pos = lines[0].length();

		for (String definition : definitions) {
			lines = definition.split("\n");

			if ((lines.length != 0) && lines[0].endsWith("|-")) {
				String newDefinition = _formatDefinition(
					fileName, definition, lines, indent, level, false);

				if (!newDefinition.equals(definition)) {
					content = StringUtil.replaceFirst(
						content, definition, newDefinition, pos);
				}

				pos = pos + newDefinition.length();

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

			pos = pos + newDefinition.length();
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

	private String _formatWhitespace(String content) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = formatIncorrectSyntax(line, "{ ", "{", false);
				line = formatIncorrectSyntax(line, "[ ", "[", false);

				String trimmedLine = StringUtil.trimLeading(line);

				if (!trimmedLine.startsWith("}") &&
					!trimmedLine.startsWith("]")) {

					line = formatIncorrectSyntax(line, " }", "}", false);
					line = formatIncorrectSyntax(line, " ]", "]", false);
				}

				sb.append(line);

				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
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

	private static final Pattern _mappingEntryPattern = Pattern.compile(
		"^( *)- *?(\n|\\Z)((\\1 +.+)(\n|\\Z)+)+", Pattern.MULTILINE);
	private static final Pattern _styleBlockPattern = Pattern.compile(
		"(?<=\\|-)(?: *\n)(( +).*(\n\\2.*)*)");

}