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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.LinkedHashMap;
import java.util.Map;
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

			outLoop:
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

					String attributesString = tagLine.substring(
						nameEndIndex + 1, tagLine.length() - closeTag.length());

					String[] attributeArray = attributesString.split(
						StringPool.SPACE);

					Map<String, String> attributeMap = new LinkedHashMap<>();

					for (int i = 0; i < attributeArray.length;) {
						if (StringUtil.equals(
								StringPool.GREATER_THAN, attributeArray[i])) {

							i++;

							continue;
						}

						if (((i + 1) < attributeArray.length) &&
							StringUtil.equals(
								StringPool.EQUAL, attributeArray[i + 1])) {

							if ((i + 2) == attributeArray.length) {
								continue outLoop;
							}

							attributeMap.put(
								attributeArray[i], attributeArray[i + 2]);

							i += 3;
						}
						else {
							attributeMap.put(attributeArray[i], null);
							i++;
						}
					}

					String formatTag = _tagToString(
						indent, name, attributeMap, closeTag);

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
		String indent, String fullName, Map<String, String> attributeMap,
		String closeTag) {

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(StringPool.LESS_THAN);
		sb.append(fullName);

		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			sb.append(StringPool.NEW_LINE);
			sb.append(indent);
			sb.append(StringPool.TAB);

			sb.append(entry.getKey());

			String attributeValue = entry.getValue();

			if (Validator.isNotNull(attributeValue)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.EQUAL);
				sb.append(StringPool.SPACE);
				sb.append(attributeValue);
			}
		}

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append(closeTag);

		return sb.toString();
	}

	private static final Pattern _closeTagPattern = Pattern.compile(".+(/?>)");

}