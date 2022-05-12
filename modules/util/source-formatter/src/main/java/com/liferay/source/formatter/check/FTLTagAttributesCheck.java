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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLTagAttributesCheck extends BaseTagAttributesCheck {

	@Override
	protected Tag doFormatLineBreaks(Tag tag, String absolutePath) {
		return tag;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = formatIncorrectLineBreak(fileName, content);

		content = _formatTagAttributes(absolutePath, content);

		content = _formatMacroTagAttributes(content);

		return content;
	}

	private String _formatMacroTagAttributes(String content) throws Exception {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trimLeading(line);

				lineNumber++;

				if (trimmedLine.startsWith("<#macro")) {
					int startPos = getLineStartPos(content, lineNumber);

					String tagString = getTag(content, startPos);

					String tagLine = tagString.replaceAll(
						"\n\t*", StringPool.SPACE);

					int nameEndIndex = Math.max(
						tagLine.indexOf(StringPool.SPACE, 8), 8);

					if (nameEndIndex == 8) {
						continue;
					}

					Matcher matcher = _closeTagPattern.matcher(tagLine);

					String closeTag;

					if (matcher.find()) {
						closeTag = matcher.group(1);
					}
					else {
						continue;
					}

					String indent = SourceUtil.getIndent(line);

					String name = tagLine.substring(1, nameEndIndex);

					String attributesString = StringUtil.trim(
						tagLine.substring(
							nameEndIndex + 1,
							tagLine.length() - closeTag.length()));

					String formatTag = _tagToString(
						indent, name, closeTag, attributesString);

					if (!StringUtil.equals(tagString, formatTag)) {
						return StringUtil.replaceFirst(
							content, tagString, formatTag, startPos);
					}
				}
			}
		}

		return content;
	}

	private String _formatTagAttributes(String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith(StringPool.LESS_THAN) &&
					trimmedLine.endsWith(StringPool.GREATER_THAN) &&
					!trimmedLine.startsWith("<#")) {

					line = formatTagAttributes(
						absolutePath, line, false, false);
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

	private String _tagToString(
		String indent, String fullName, String closeTag,
		String attributesString) {

		StringBundler sb = new StringBundler(10);

		sb.append(indent);
		sb.append(StringPool.LESS_THAN);
		sb.append(fullName);

		int x = -1;
		int nextQuoteIndex = -1;

		String replacement = StringPool.NEW_LINE + indent + StringPool.TAB;

		while (true) {
			x = attributesString.indexOf(StringPool.SPACE, x);

			if (x == -1) {
				break;
			}

			char frontChar = attributesString.charAt(x - 1);
			char nextChar = attributesString.charAt(x + 1);

			if ((nextChar != CharPool.EQUAL) && (frontChar != CharPool.EQUAL) &&
				(x > nextQuoteIndex)) {

				attributesString = StringUtil.replaceFirst(
					attributesString, StringPool.SPACE, replacement, x);

				x = -1;
				nextQuoteIndex = -1;

				continue;
			}

			if (nextChar == CharPool.QUOTE) {
				nextQuoteIndex = attributesString.indexOf(
					StringPool.QUOTE, x + 2);
			}

			x++;
		}

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append(StringPool.TAB);
		sb.append(attributesString);

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append(closeTag);

		return sb.toString();
	}

	private static final Pattern _closeTagPattern = Pattern.compile(".+(/?>)");

}