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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLogLevelCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("Log") || hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkLogLevel(sourceFormatterMessages, content, fileName);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkLogLevel(
		Set<SourceFormatterMessage> sourceFormatterMessages, String content,
		String fileName) {

		Matcher matcher = _logLevelPattern.matcher(content);

		while (matcher.find()) {
			int pos = matcher.start();

			while (true) {
				pos = content.lastIndexOf(
					StringPool.NEW_LINE + StringPool.TAB, pos - 1);

				char c = content.charAt(pos + 2);

				if (c != CharPool.TAB) {
					break;
				}
			}

			String codeBlock = content.substring(pos, matcher.start());
			String s =
				"_log.is" + StringUtil.upperCaseFirstLetter(matcher.group(2)) +
					"Enabled()";

			if (codeBlock.contains(s) ^ !s.equals("_log.isErrorEnabled()")) {
				int lineCount = getLineCount(content, matcher.start(1));

				if (codeBlock.contains(s)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"Do not use _log.isErrorEnabled()", lineCount);
				}
				else {
					addMessage(
						sourceFormatterMessages, fileName, "Use " + s,
						lineCount);
				}
			}
		}
	}

	private final Pattern _logLevelPattern = Pattern.compile(
		"\n(\t+)_log.(debug|error|info|trace|warn)\\(");

}