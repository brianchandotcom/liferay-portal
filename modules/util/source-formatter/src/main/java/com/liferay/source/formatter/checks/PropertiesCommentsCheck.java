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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PropertiesCommentsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatComments(content);
	}

	private String _formatComments(String content) {
		Matcher commentMatcher = _commentPattern.matcher(content);

		while (commentMatcher.find()) {
			if ((commentMatcher.group(1) != null) ||
				(commentMatcher.group(4) != null)) {

				continue;
			}

			String comment = commentMatcher.group(3);

			String[] words = comment.split("\\s+");

			if ((words.length == 0) || (words.length > 5)) {
				continue;
			}

			List<String> list = new ArrayList<>();

			for (String word : words) {
				if (word.isEmpty()) {
					continue;
				}

				if (_words.containsKey(StringUtil.toLowerCase(word))) {
					list.add(_words.get(StringUtil.toLowerCase(word)));
				}
				else if (Character.isUpperCase(word.charAt(0))) {
					list.add(word);
				}
				else {
					list.add(StringUtil.upperCaseFirstLetter(word));
				}
			}

			if (list.isEmpty()) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			sb.append(commentMatcher.group(2));
			sb.append(StringPool.SPACE);
			sb.append(StringUtil.merge(list, StringPool.SPACE));

			content = StringUtil.replaceFirst(
				content, commentMatcher.group(), sb.toString());
		}

		return content;
	}

	private static final Map<String, String> _words = MapUtil.fromArray(
		new String[] {"and", "and", "sf", "Source Formatter", "tc", "TC"});

	private final Pattern _commentPattern = Pattern.compile(
		"([^#]\\s*)?(\\n\\s*#+)([\\s\\w]+)(\\n\\s*#+.*[\\w]+.*)?$",
		Pattern.MULTILINE);

}