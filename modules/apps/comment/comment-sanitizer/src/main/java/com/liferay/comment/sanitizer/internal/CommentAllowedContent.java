/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.sanitizer.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergio González
 */
public class CommentAllowedContent {

	public CommentAllowedContent(String allowedContent) {
		Matcher matcher = _pattern.matcher(allowedContent);

		allowedContent = matcher.replaceAll(StringPool.BLANK);

		String[] allowedContentParts = StringUtil.split(
			allowedContent, StringPool.SEMICOLON);

		for (String allowedContentPart : allowedContentParts) {
			String elementName = allowedContentPart;
			String[] attributeNames = new String[0];

			int x = allowedContentPart.indexOf(StringPool.OPEN_BRACKET);
			int y = allowedContentPart.indexOf(StringPool.CLOSE_BRACKET);

			if ((x != -1) && (y != -1)) {
				elementName = allowedContentPart.substring(0, x);
				attributeNames = StringUtil.split(
					allowedContentPart.substring(x + 1, y));
			}

			_attributeNamesMap.put(elementName, attributeNames);
		}
	}

	public Map<String, String[]> getAttributeNames() {
		return _attributeNamesMap;
	}

	private static final Pattern _pattern = Pattern.compile("\\s+");

	private final Map<String, String[]> _attributeNamesMap = new HashMap<>();

}