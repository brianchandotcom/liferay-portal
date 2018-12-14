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