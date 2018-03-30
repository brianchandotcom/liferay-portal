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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSONPropertyOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _incorrectLineBreakPattern.matcher(content);

		if (matcher.find()) {
			addMessage(
				fileName, "There should be a line break after '}'",
				getLineCount(content, matcher.start()));

			return content;
		}

		return _sortProperties(content);
	}

	private String _sortProperties(String content) {
		String tabs = StringPool.BLANK;

		while (true) {
			Pattern pattern1 = Pattern .compile(
				"(\n|^)" + tabs + "[^\n\t]*[\\{\\[]\n");

			Matcher matcher1 = pattern1.matcher(content);

			if (!matcher1.find()) {
				break;
			}

			Pattern pattern2 = Pattern.compile(
				StringBundler.concat(
					"((\n|^)", tabs, "[^\n\t]*\\{\n", tabs,
					"\t[^\n\t][\\s\\S]*?)\n", tabs, "\\}"));

			Matcher matcher2 = pattern2.matcher(content);

			while (matcher2.find()) {
				Pattern pattern3 = Pattern.compile(
					StringBundler.concat(
						"(", tabs,
						"\t[^\n\t]*?([^\\{\\[]|([\\{\\[]\n[\\s\\S]*?\n", tabs,
						"\t[\\}\\]]))),?(\n|$)"));

				String match = matcher2.group(1);

				Matcher matcher3 = pattern3.matcher(match);

				String previousProperty = null;

				while (matcher3.find()) {
					String property = "\n" + matcher3.group(1);

					if (Validator.isNotNull(previousProperty) &&
						(previousProperty.compareTo(property) > 0)) {

						String replacement = StringUtil.replaceFirst(
							match, previousProperty, property);

						replacement = StringUtil.replaceLast(
							replacement, property, previousProperty);

						return StringUtil.replace(content, match, replacement);
					}

					previousProperty = property;
				}
			}

			tabs += "\t";
		}

		return content;
	}

	private final Pattern _incorrectLineBreakPattern = Pattern.compile(
		"\t[\\}\\]]{2}");

}