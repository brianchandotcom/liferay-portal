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
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaFinderCacheCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("FinderImpl.java") ||
			!content.contains("public static final FinderPath")) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkFinderCacheInterfaceMethod(
			sourceFormatterMessages, fileName, content);

		content = _fixClearCache(fileName, content);

		return new Tuple(content, sourceFormatterMessages);
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

	private void _checkFinderCacheInterfaceMethod(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

		Matcher matcher = _fetchByPrimaryKeysMethodPattern.matcher(content);

		if (!matcher.find()) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Missing override of BasePersistenceImpl." +
					"fetchByPrimaryKeys(Set<Serializable>), see LPS-49552");
		}
	}

	private final Pattern _fetchByPrimaryKeysMethodPattern = Pattern.compile(
		"@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");

}