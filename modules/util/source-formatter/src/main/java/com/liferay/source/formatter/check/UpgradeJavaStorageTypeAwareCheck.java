/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaStorageTypeAwareCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		Matcher deprecatedInterfaceMatcher =
			_deprecatedInterfacePattern.matcher(content);

		if (deprecatedInterfaceMatcher.find()) {
			String deprecatedInterface = deprecatedInterfaceMatcher.group();

			if (deprecatedInterface.contains(", StorageTypeAware")) {
				newContent = StringUtil.removeSubstring(
					newContent, ", StorageTypeAware");
			}
			else if (deprecatedInterface.contains("StorageTypeAware,")) {
				newContent = StringUtil.removeSubstring(
					newContent, "StorageTypeAware,");
			}
			else {
				newContent = StringUtil.removeSubstring(
					newContent, deprecatedInterface);
			}

			Matcher overrideMatcher = _overridePattern.matcher(content);

			if (overrideMatcher.find()) {
				newContent = StringUtil.removeSubstring(
					newContent, overrideMatcher.group(1));
			}

			newContent = StringUtil.removeSubstring(
				newContent,
				"import com.liferay.portal.kernel.scheduler.StorageTypeAware;" +
					StringPool.NEW_LINE);
		}

		return newContent;
	}

	private static final Pattern _deprecatedInterfacePattern = Pattern.compile(
		"implements[\\w\\s,]*,?\\s*StorageTypeAware,?");
	private static final Pattern _overridePattern = Pattern.compile(
		"(\\t*@Override\\n+)\\s*\\w*\\s*StorageType");

}