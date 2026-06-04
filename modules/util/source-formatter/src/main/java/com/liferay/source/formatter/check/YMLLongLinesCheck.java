/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class YMLLongLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int maxLineLength = 0;

		try {
			maxLineLength = Integer.parseInt(
				getAttributeValue(_MAX_LINE_LENGTH, absolutePath));
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(numberFormatException);
			}

			return content;
		}

		Matcher matcher = _descriptionPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(3);

			if (match.contains(": ")) {
				continue;
			}

			String description = StringUtil.trim(
				match.replaceAll("\n +", StringPool.SPACE));

			String indent = matcher.group(2) + StringPool.FOUR_SPACES;

			if (!fileName.endsWith("/rest-openapi.yaml")) {
				description = _formatDescription(
					indent, description, maxLineLength);
			}
			else {
				description = indent + description;
			}

			description = StringPool.NEW_LINE + description;

			if (!StringUtil.equals(match, description)) {
				return StringUtil.replaceFirst(
					content, match, description, matcher.start(3));
			}
		}

		return content;
	}

	private String _formatDescription(
		String indent, String description, int maxLineLength) {

		if (Validator.isNull(description)) {
			return StringPool.BLANK;
		}

		if ((indent.length() + description.length()) <= maxLineLength) {
			return indent + description;
		}

		description = indent + description;

		int x = description.indexOf(CharPool.SPACE, indent.length());

		if (x == -1) {
			return description;
		}

		if (x > maxLineLength) {
			String s = description.substring(x + 1);

			return description.substring(0, x) + "\n" +
				_formatDescription(indent, s, maxLineLength);
		}

		x = description.lastIndexOf(CharPool.SPACE, maxLineLength);

		String s = description.substring(x + 1);

		return description.substring(0, x) + "\n" +
			_formatDescription(indent, s, maxLineLength);
	}

	private static final String _MAX_LINE_LENGTH = "maxLineLength";

	private static final Log _log = LogFactoryUtil.getLog(
		YMLLongLinesCheck.class);

	private static final Pattern _descriptionPattern = Pattern.compile(
		"(\n( +)description:)((\n\\2 +.+)+)");

}