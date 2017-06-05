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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaFinderCacheCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
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
					"fetchByPrimaryKeys(Set<Serializable>)",
				"finderpath.markdown");
		}
	}

	private String _fixClearCache(String fileName, String content) {

		// LPS-47648

		if (fileName.contains("/test/integration/") ||
			fileName.contains("/testIntegration/java")) {

			content = StringUtil.replace(
				content, "FinderCacheUtil.clearCache();", StringPool.BLANK);
		}

		return content;
	}

	private final Pattern _fetchByPrimaryKeysMethodPattern = Pattern.compile(
		"@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");

}