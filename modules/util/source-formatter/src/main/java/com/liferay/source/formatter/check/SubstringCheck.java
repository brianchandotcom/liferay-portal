/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class SubstringCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
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

			List<String> parameterList = JavaSourceUtil.getParameterList(match);

			if (parameterList.size() != 2) {
				continue;
			}

			String secondParameter = StringUtil.trim(parameterList.get(1));
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

	private static final Pattern _substringPattern = Pattern.compile(
		"\\W([a-z][\\w]*)\\.substring\\(");

}