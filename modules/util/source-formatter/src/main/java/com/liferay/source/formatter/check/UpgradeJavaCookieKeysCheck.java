/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaCookieKeysCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		String newImport = StringPool.BLANK;

		if (newContent.contains("CookiesConstants.")) {
			newImport +=
				"import com.liferay.portal.kernel.cookies.constants." +
					"CookiesConstants;\n";
		}

		if (newContent.contains("CookiesManagerUtil.")) {
			newImport +=
				"import com.liferay.portal.kernel.cookies." +
					"CookiesManagerUtil;\n";
		}

		return StringUtil.replace(
			newContent, "import com.liferay.portal.kernel.util.CookieKeys;",
			newImport);
	}

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String matched = matcher.group();

		if (matched.contains(StringPool.OPEN_PARENTHESIS)) {
			return _replaceMethodCall(content, newContent, matcher);
		}

		return _replaceConstant(newContent, matcher);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile(
			"CookieKeys\\.\\s*(([A-Z_]+)|(\\w+)\\s*\\(\\s*.+(,\\s*.+)+\\s*" +
				"\\))");
	}

	private String _reorderParameters(
		String method, List<String> parameterList) {

		if (parameterList.size() == 2) {
			if (method.equals("getCookieValue")) {
				return StringBundler.concat(
					parameterList.get(1), StringPool.COMMA_AND_SPACE,
					parameterList.get(0));
			}

			return StringBundler.concat(
				parameterList.get(0), StringPool.COMMA_AND_SPACE,
				parameterList.get(1));
		}
		else if (parameterList.size() == 3) {
			if (method.equals("getCookieValue")) {
				return StringBundler.concat(
					parameterList.get(1), StringPool.COMMA_AND_SPACE,
					parameterList.get(0), StringPool.COMMA_AND_SPACE,
					parameterList.get(2));
			}

			return StringBundler.concat(
				parameterList.get(2), StringPool.COMMA_AND_SPACE,
				parameterList.get(0), StringPool.COMMA_AND_SPACE,
				parameterList.get(1));
		}

		return StringBundler.concat(
			parameterList.get(2), StringPool.COMMA_AND_SPACE,
			parameterList.get(0), StringPool.COMMA_AND_SPACE,
			parameterList.get(1), StringPool.COMMA_AND_SPACE,
			parameterList.get(3));
	}

	private String _replaceConstant(String content, Matcher matcher) {
		String constant = matcher.group(1);

		if (!Objects.equals(constant, "MAX_AGE")) {
			constant = "NAME_" + constant;
		}

		if (Objects.equals(constant, "NAME_SCREEN_NAME")) {
			constant = "NAME_USER_UUID";
		}

		return StringUtil.replace(
			content, matcher.group(), "CookiesConstants." + constant);
	}

	private String _replaceMethodCall(
		String content, String newContent, Matcher matcher) {

		List<String> parameterList = JavaSourceUtil.getParameterList(
			content.substring(matcher.start()));

		StringBundler sb = new StringBundler(5);

		sb.append("CookiesManagerUtil.");

		String method = matcher.group(3);

		if (Objects.equals(matcher.group(3), "getCookie")) {
			method = "getCookieValue";
		}

		sb.append(method);

		sb.append(StringPool.OPEN_PARENTHESIS);

		String parameter = parameterList.get(0);

		if (parameterList.size() > 1) {
			parameter = _reorderParameters(method, parameterList);
		}

		sb.append(parameter);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return StringUtil.replace(newContent, matcher.group(), sb.toString());
	}

}