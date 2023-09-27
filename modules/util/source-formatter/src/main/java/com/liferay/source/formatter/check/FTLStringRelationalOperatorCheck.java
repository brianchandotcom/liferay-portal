/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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

	private static final Pattern _stringRelationalOperationPattern =
		Pattern.compile("(\\W)([\\w.]+) ([!=]=) \"(\\w*)\"(.)");

}