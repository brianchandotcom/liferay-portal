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
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPTaglibVariableCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		content = _formatTaglibVariable(
			sourceFormatterMessages, fileName, content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private String _formatTaglibVariable(
		Set<SourceFormatterMessage> sourceFormatterMessage, String fileName,
		String content) {

		Matcher matcher = _taglibVariablePattern.matcher(content);

		while (matcher.find()) {
			String taglibValue = matcher.group(3);

			if (taglibValue.contains("\\\"") ||
				(taglibValue.contains(StringPool.APOSTROPHE) &&
				 taglibValue.contains(StringPool.QUOTE))) {

				continue;
			}

			String taglibName = matcher.group(2);
			String nextTag = matcher.group(4);

			if (!nextTag.contains(taglibName)) {
				addMessage(
					sourceFormatterMessage, fileName,
					"No need to specify taglib variable '" + taglibName + "'",
					getLineCount(content, matcher.start()));

				continue;
			}

			content = StringUtil.replaceFirst(
				content, taglibName, taglibValue, matcher.start(4));

			return content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return content;
	}

	private final Pattern _taglibVariablePattern = Pattern.compile(
		"(\n\t*String (taglib\\w+) = (.*);)\n\\s*%>\\s+(<[\\S\\s]*?>)\n");

}