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
public class FTLStringRelationalOperatorCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatStringRelationalOperations(content);
	}

	private String _formatStringRelationalOperations(String content) {
		Matcher matcher = _stringRelationalOperationPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();

		String firstChar = matcher.group(1);
		String lastChar = matcher.group(5);

		if (!firstChar.equals(StringPool.OPEN_PARENTHESIS) ||
			!lastChar.equals(StringPool.CLOSE_PARENTHESIS)) {

			match = content.substring(matcher.end(1), matcher.start(5));
		}

		String operator = matcher.group(3);
		String quotedString = matcher.group(4);
		String variableName = matcher.group(2);

		String replacement = null;

		if (Validator.isNull(quotedString)) {
			if (operator.equals("==")) {
				replacement = "validator.isNull(" + variableName + ")";
			}
			else {
				replacement = "validator.isNotNull(" + variableName + ")";
			}
		}
		else {
			StringBundler sb = new StringBundler(6);

			if (operator.equals("!=")) {
				sb.append(StringPool.EXCLAMATION);
			}

			sb.append("stringUtil.equals(");
			sb.append(variableName);
			sb.append(", \"");
			sb.append(quotedString);
			sb.append("\")");

			replacement = sb.toString();
		}

		return StringUtil.replaceFirst(
			content, match, replacement, matcher.start());
	}

	private final Pattern _stringRelationalOperationPattern = Pattern.compile(
		"(\\W)([\\w.]+) ([!=]=) \"(\\w*)\"(.)");

}