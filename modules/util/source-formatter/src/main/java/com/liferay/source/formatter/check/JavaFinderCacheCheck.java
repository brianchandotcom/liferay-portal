/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaFinderCacheCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("FinderImpl.java") &&
			content.contains("public static final FinderPath")) {

			_checkFinderCacheInterfaceMethod(fileName, content);

			content = _fixClearCache(fileName, content);
		}

		return content;
	}

	private void _checkFinderCacheInterfaceMethod(
		String fileName, String content) {

		Matcher matcher = _fetchByPrimaryKeysMethodPattern.matcher(content);

		if (!matcher.find()) {
			addMessage(
				fileName,
				"Missing override of BasePersistenceImpl." +
					"fetchByPrimaryKeys(Set<Serializable>)");
		}
	}

	private String _fixClearCache(String fileName, String content) {

		// LPS-47648

		if (fileName.contains("/test/integration/") ||
			fileName.contains("/testIntegration/java")) {

			content = StringUtil.removeSubstring(
				content, "FinderCacheUtil.clearCache();");
		}

		return content;
	}

	private static final Pattern _fetchByPrimaryKeysMethodPattern =
		Pattern.compile("@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");

}