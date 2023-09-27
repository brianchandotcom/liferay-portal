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
 * @author Peter Shin
 */
public class PropertiesSourceFormatterContentCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/source-formatter.properties")) {
			content = _checkConvertedKeys(content);
			content = _checkGitLiferayPortalBranch(content);
		}

		return content;
	}

	private String _checkConvertedKeys(String content) {
		for (String[] array : _CONVERTED_KEYS) {
			content = StringUtil.replace(content, array[0], array[1]);
		}

		return content;
	}

	private String _checkGitLiferayPortalBranch(String content) {
		Matcher matcher = _gitLiferayPortalBranchPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return content;
	}

	private static final String[][] _CONVERTED_KEYS = {
		{
			"blob/master/portal-impl/src/source-formatter.properties",
			"blob/master/source-formatter.properties"
		}
	};

	private static final Pattern _gitLiferayPortalBranchPattern =
		Pattern.compile("\\sgit\\.liferay\\.portal\\.branch=(\\\\\\s+)");

}