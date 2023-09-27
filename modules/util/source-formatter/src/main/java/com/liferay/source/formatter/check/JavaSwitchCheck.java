/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSwitchCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _switchStatementPattern.matcher(content);

		while (matcher.find()) {
			addMessage(
				fileName, "Use 'if/else' statement instead of 'switch'",
				getLineNumber(content, matcher.start()));
		}

		return content;
	}

	private static final Pattern _switchStatementPattern = Pattern.compile(
		"\tswitch \\(.*\\) \\{");

}