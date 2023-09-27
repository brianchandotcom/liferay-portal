/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Seiphon Wang
 */
public class XMLEchoMessageCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".xml")) {
			return content;
		}

		Matcher matcher = _echoMessagePattern.matcher(content);

		while (matcher.find()) {
			addMessage(
				fileName,
				"Do not use self-closing tag for attribute 'message' in '" +
					"<echo>' tag",
				getLineNumber(content, matcher.start()));
		}

		return content;
	}

	private static final Pattern _echoMessagePattern = Pattern.compile(
		"<echo (.(?!(/>|</)))*?message=.*?/>");

}