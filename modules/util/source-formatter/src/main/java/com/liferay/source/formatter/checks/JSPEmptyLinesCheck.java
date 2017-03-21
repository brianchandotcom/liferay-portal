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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPEmptyLinesCheck extends EmptyLinesCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		content = fixMissingEmptyLines(content);

		content = fixRedundantEmptyLines(content);

		content = fixIncorrectEmptyLineBeforeCloseCurlyBrace(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = fixEmptyLinesBetweenTags(content);

		content = _fixMissingEmptyLines(content);

		content = _fixRedundantEmptyLines(content);

		return new Tuple(content, Collections.emptySet());
	}

	@Override
	protected boolean isJavaSource(String content, int pos) {
		return JSPSourceUtil.isJavaSource(content, pos);
	}

	private String _fixMissingEmptyLines(String content) {
		while (true) {
			Matcher matcher = _missingEmptyLinePattern1.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			matcher = _missingEmptyLinePattern2.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());

				continue;
			}

			matcher = _missingEmptyLinePattern3.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			matcher = _missingEmptyLinePattern4.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			break;
		}

		return content;
	}

	private String _fixRedundantEmptyLines(String content) {
		while (true) {
			Matcher matcher = _redundantEmptyLinePattern1.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue;
			}

			matcher = _redundantEmptyLinePattern2.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue;
			}

			break;
		}

		return content;
	}

	private final Pattern _missingEmptyLinePattern1 = Pattern.compile(
		"[\t\n](--)?%>\n\t*(?!-->)\\S");
	private final Pattern _missingEmptyLinePattern2 = Pattern.compile(
		"\\S(?!<\\!--)\n\t*<%(--)?\n");
	private final Pattern _missingEmptyLinePattern3 = Pattern.compile(
		"[\t\n]<%\n\t*//");
	private final Pattern _missingEmptyLinePattern4 = Pattern.compile(
		"[\t\n]//.*\n\t*%>\n");
	private final Pattern _redundantEmptyLinePattern1 = Pattern.compile(
		"[\n\t]<%\n\n(\t*)[^/\n\t]");
	private final Pattern _redundantEmptyLinePattern2 = Pattern.compile(
		"[\n\t][^/\n\t].*\n\n\t*%>");

}