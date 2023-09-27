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

		content = _formatMacroTagAttributes(content);
		content = _formatTagAttributes(absolutePath, content);

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

				if (!trimmedLine.startsWith("<#macro")) {
					continue;
				}

				int startPos = getLineStartPos(content, lineNumber);

				String tagString = getTag(content, startPos);

				String tagLine = tagString.replaceAll(
					"\n\t*", StringPool.SPACE);

				int tagNameEndIndex = tagLine.indexOf(StringPool.SPACE, 8);

				if (tagNameEndIndex == -1) {
					continue;
				}

				String tagName = tagLine.substring(0, tagNameEndIndex);

				String tagAttributes = StringUtil.trim(
					tagLine.substring(tagNameEndIndex, tagLine.length() - 1));

				if (Validator.isNull(tagAttributes)) {
					continue;
				}

				String indent = SourceUtil.getIndent(line) + StringPool.TAB;
				String newTagAttributes = StringPool.BLANK;

				int x = -1;

				while (true) {
					x = tagAttributes.indexOf(StringPool.SPACE, x + 1);

					if (x == -1) {
						break;
					}

					if (ToolsUtil.isInsideQuotes(tagAttributes, x)) {
						continue;
					}

					if (x > 0) {
						char previousChar = tagAttributes.charAt(x - 1);

						if (previousChar == CharPool.EQUAL) {
							continue;
						}
					}

					if (x < (tagAttributes.length() - 1)) {
						char nextChar = tagAttributes.charAt(x + 1);

						if (nextChar == CharPool.EQUAL) {
							continue;
						}
					}

					newTagAttributes +=
						StringPool.NEW_LINE + indent +
							tagAttributes.substring(0, x);

					tagAttributes = tagAttributes.substring(x + 1);

					x = -1;
				}

				if (Validator.isNotNull(tagAttributes)) {
					newTagAttributes +=
						StringPool.NEW_LINE + indent + tagAttributes;
				}

				String newTagString = StringBundler.concat(
					tagName, newTagAttributes, StringPool.NEW_LINE,
					StringPool.GREATER_THAN);

				if (!tagString.equals(newTagString)) {
					return StringUtil.replaceFirst(
						content, tagString, newTagString, startPos);
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

}