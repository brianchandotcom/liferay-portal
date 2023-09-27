/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaPortletSharedSearchSettingsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		String newContent = content;

		Matcher matcher =
			_getParameterValuesOrGetPortletPreferencesPattern.matcher(content);

		while (matcher.find()) {
			String variableTypeName = getVariableTypeName(
				newContent, newContent, matcher.group(1));

			if (!variableTypeName.equals("PortletSharedSearchSettings")) {
				continue;
			}

			String methodStart = matcher.group();

			String newMethodStart = StringUtil.removeSubstring(
				methodStart, "Optional");

			newMethodStart = StringUtil.removeFirst(
				newMethodStart, StringPool.LESS_THAN);
			newMethodStart = StringUtil.removeFirst(
				newMethodStart, StringPool.GREATER_THAN);

			newContent = StringUtil.replaceFirst(
				newContent, methodStart, newMethodStart);
		}

		return newContent;
	}

	private static final Pattern
		_getParameterValuesOrGetPortletPreferencesPattern = Pattern.compile(
			"Optional\\s*<(?:String\\[\\]|PortletPreferences)>\\s*\\w+" +
				"\\s*=\\s*(\\w+)\\.(?:getParameterValues|" +
					"getPortletPreferences)\\(");

}