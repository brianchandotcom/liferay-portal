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

import java.util.ArrayList;
import java.util.List;
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

	private String[] _checkAttributeArray(String[] attributeArray) {
		if (attributeArray.length == 1) {
			return attributeArray;
		}

		List<String> attributes = new ArrayList<>();

		boolean continueFlg = false;

		int newArrayIndex = 0;

		for (int i = 0; i < attributeArray.length; i++) {
			if (continueFlg) {
				continueFlg = false;

				continue;
			}

			String attribute = attributeArray[i];

			if (StringUtil.equals(attribute, StringPool.EQUAL)) {
				if ((i == 0) || ((i + 1) == attributeArray.length)) {
					return new String[0];
				}

				newArrayIndex--;

				attributes.set(
					newArrayIndex,
					StringBundler.concat(
						attributes.get(newArrayIndex), StringPool.SPACE,
						attribute, StringPool.SPACE, attributeArray[i + 1]));

				newArrayIndex++;
				continueFlg = true;
			}
			else {
				attributes.add(attribute);
				newArrayIndex++;
			}
		}

		return attributes.toArray(new String[0]);
	}

	private boolean _checkTab(String indent, String attribute) {
		if (!attribute.startsWith(indent)) {
			return false;
		}

		int index = attribute.indexOf(indent);

		String substringResult = attribute.substring(index + indent.length());

		return !substringResult.startsWith(StringPool.TAB);
	}

	private String _formatMacroTagAttributes(String content) {
		Matcher matcher = _macroTagAttributePattern.matcher(content);

		while (matcher.find()) {
			String indent = matcher.group(1) + StringPool.TAB;

			String attributes = matcher.group(2);

			String[] rowAttributeArray = attributes.split(StringPool.NEW_LINE);

			int rowNumber = 0;

			for (String rowAttribute : rowAttributeArray) {
				rowNumber++;

				String[] attributeArray = rowAttribute.split(StringPool.SPACE);

				if ((attributeArray.length == 1) &&
					((rowNumber == 1) ||
					 _checkTab(indent, attributeArray[0]))) {

					continue;
				}

				attributeArray = _checkAttributeArray(attributeArray);

				if (attributeArray.length == 0) {
					continue;
				}

				StringBundler sb = new StringBundler();

				for (int i = 0; i < attributeArray.length; i++) {
					String attribute = attributeArray[i];

					if ((rowNumber != 1) || (i != 0)) {
						sb.append(indent);
					}

					sb.append(StringUtil.trimLeading(attribute));
					sb.append(StringPool.NEW_LINE);
				}

				if (sb.index() > 0) {
					sb.setIndex(sb.index() - 1);
				}

				String replace = sb.toString();

				if ((attributeArray.length == 1) &&
					StringUtil.equals(rowAttribute, replace)) {

					continue;
				}

				return StringUtil.replaceFirst(
					content, rowAttribute, replace, matcher.start(2));
			}

			int tagLine = getLineNumber(content, matcher.start(1));
			int tagEndLine = getLineNumber(content, matcher.end(2));

			if (!attributes.endsWith(StringPool.NEW_LINE) &&
				(tagLine != tagEndLine)) {

				return StringUtil.insert(
					content, StringPool.NEW_LINE, matcher.end(2));
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

	private static final Pattern _macroTagAttributePattern = Pattern.compile(
		"(\t*)<#macro ([\\s\\S]*?)>");

}