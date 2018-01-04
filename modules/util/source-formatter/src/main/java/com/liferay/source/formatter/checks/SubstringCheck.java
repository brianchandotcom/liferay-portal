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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class SubstringCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _substringPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start(1))) {
				continue;
			}

			String match = content.substring(matcher.start(1));

			List<String> parametersList = JavaSourceUtil.getParameterList(
				match);

			if (parametersList.size() != 2) {
				continue;
			}

			String secondParameter = StringUtil.trim(parametersList.get(1));
			String variableName = matcher.group(1);

			if (secondParameter.equals(variableName + ".length()")) {
				String replacement = match.replaceFirst(
					",\\s*" + variableName + "\\.length\\(\\)",
					StringPool.BLANK);

				return StringUtil.replaceLast(content, match, replacement);
			}
		}

		return content;
	}

	private final Pattern _substringPattern = Pattern.compile(
		"\\W([a-z][\\w]*)\\.substring\\(");

}