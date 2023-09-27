/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MarkdownStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatCodeSyntax(content);
		content = _formatNumberedList(content);

		return _formatHeaders(content);
	}

	private String _formatCodeSyntax(String content) {
		Matcher matcher = _incorrectCodeSyntaxPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, matcher.group(), "`" + matcher.group(1) + "`");
		}

		return content;
	}

	private String _formatHeaders(String content) {
		Matcher matcher = _incorrectHeaderNotationPattern.matcher(content);

		content = matcher.replaceAll("$1$2$4");

		matcher = _boldHeaderPattern.matcher(content);

		return matcher.replaceAll("$1$2$4$6");
	}

	private String _formatNumberedList(String content) {
		int[] multiLineStringsPositions = SourceUtil.getMultiLinePositions(
			content, _codeBlockPattern);
		Matcher matcher = _numberedListPattern.matcher(content);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			if (SourceUtil.isInsideMultiLines(
					SourceUtil.getLineNumber(content, matcher.start()),
					multiLineStringsPositions)) {

				continue;
			}

			String replacement = matcher.group();

			if (content.charAt(matcher.start() - 1) != CharPool.NEW_LINE) {
				replacement = "\n" + replacement;
			}

			String number = matcher.group(1);

			if (!number.equals("1")) {
				replacement = replacement.replaceFirst("\\d+", "1");
			}

			matcher.appendReplacement(sb, replacement);
		}

		if (sb.length() > 0) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	private static final Pattern _boldHeaderPattern = Pattern.compile(
		"(\\A|\n)(#+ ?)(\\*+)([^\\*\n]+)(\\*+)(\n)");
	private static final Pattern _codeBlockPattern = Pattern.compile(
		"```.+?```", Pattern.DOTALL);
	private static final Pattern _incorrectCodeSyntaxPattern = Pattern.compile(
		"```(.+?)```");
	private static final Pattern _incorrectHeaderNotationPattern =
		Pattern.compile("(\\A|\n)(#+[^#\n]+)(#+)(\n)");
	private static final Pattern _numberedListPattern = Pattern.compile(
		"\n[ \t]*(\\d+)\\. ");

}