/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (fileName.endsWith(".path")) {
			return content;
		}

		if (SourceUtil.isXML(content)) {
			addMessage(fileName, "Use Poshi Script instead of Poshi XML");

			return content;
		}

		content = content.replaceAll(
			"(?<!!)\\(contains\\(\"\\$\\{(.+?)\\}\", \"\\{\\1\\}\"\\)\\)",
			"(!(isSet($1)))");
		content = content.replaceAll(
			"\\!\\(contains\\(\"\\$\\{(.+?)\\}\", \"\\{\\1\\}\"\\)\\)",
			"isSet($1)");

		return _formatComments(content);
	}

	private String _formatComments(String content) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String newComment = StringPool.BLANK;
			String previousComment = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				Matcher matcher = _singleLineCommentPattern.matcher(line);

				if (!matcher.find()) {
					previousComment = StringPool.BLANK;

					sb.append(line);
					sb.append("\n");

					continue;
				}

				String comment = matcher.group(2);

				if (Validator.isNull(comment)) {
					previousComment = StringPool.BLANK;

					continue;
				}

				String indent = matcher.group(1);

				if (comment.startsWith("Ignore") ||
					comment.startsWith("Ignoring") ||
					comment.startsWith("Quarantine") ||
					comment.startsWith("TODO") ||
					comment.startsWith("Workaround") ||
					(!comment.endsWith(StringPool.COMMA) &&
					 !comment.endsWith(StringPool.OPEN_CURLY_BRACE) &&
					 !comment.endsWith(StringPool.OPEN_PARENTHESIS) &&
					 !comment.endsWith(StringPool.SEMICOLON) &&
					 !comment.equals(StringPool.CLOSE_CURLY_BRACE)) ||
					(comment.endsWith(StringPool.COMMA) &&
					 !comment.contains(" = "))) {

					String trimmedComment = comment.trim();

					if (!previousComment.startsWith(
							StringPool.DOUBLE_SLASH + StringPool.SPACE)) {

						String upperCaseFirstChar = StringUtil.toUpperCase(
							trimmedComment.substring(0, 1));

						trimmedComment =
							upperCaseFirstChar + trimmedComment.substring(1);
					}

					if (previousComment.matches("//[^ ].+")) {
						sb.append("\n");
					}

					newComment =
						StringPool.DOUBLE_SLASH + StringPool.SPACE +
							trimmedComment;
				}
				else {
					if (previousComment.startsWith(
							StringPool.DOUBLE_SLASH + StringPool.SPACE)) {

						sb.append("\n");
					}

					newComment = StringPool.DOUBLE_SLASH + comment;
				}

				sb.append(indent);
				sb.append(newComment);

				sb.append("\n");
				previousComment = newComment;
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Pattern _singleLineCommentPattern = Pattern.compile(
		"^([ \t]*)// *(\t*.*)");

}