/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeGetPortletGroupIdMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java") && !fileName.endsWith(".jsp") &&
			!fileName.endsWith(".jspf") && !fileName.endsWith(".ftl")) {

			return content;
		}

		String newContent = content;

		Matcher matcher = _getPortletGroupIdPattern.matcher(content);

		while (matcher.find()) {
			String methodCall = matcher.group(0);
			String variableName = matcher.group(1);

			if (fileName.endsWith(".java")) {
				if (!hasClassOrVariableName(
						"ThemeDisplay", newContent, newContent, methodCall)) {

					continue;
				}
			}
			else if (!variableName.equals("themeDisplay")) {
				continue;
			}

			newContent = StringUtil.replace(
				newContent, methodCall,
				StringUtil.replace(
					methodCall, ".getPortletGroupId()", ".getScopeGroupId()"));
		}

		return newContent;
	}

	private static final Pattern _getPortletGroupIdPattern = Pattern.compile(
		"(\\w+)\\.getPortletGroupId\\(\\)");

}