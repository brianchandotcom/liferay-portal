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
			String methodStart = matcher.group();

			String variableTypeName = getVariableTypeName(
				newContent, newContent, matcher.group(1), true);

			if (!variableTypeName.equals("PortletSharedSearchSettings")) {
				continue;
			}

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