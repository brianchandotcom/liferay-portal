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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaCookieUtilCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String newMethodCall = _getCookieValueImplementation(
			JavaSourceUtil.getParameterList(
				content.substring(matcher.start())));

		return StringUtil.replace(newContent, matcher.group(), newMethodCall);
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.cookies.CookiesManagerUtil"
		};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile(
			"CookieUtil\\.\\s*get\\(\\s*.+,\\s*.+(,\\s*.+)?\\s*\\)");
	}

	private String _getCookieValueImplementation(List<String> parameterList) {
		StringBundler sb = new StringBundler(7);

		sb.append("CookiesManagerUtil.getCookieValue(");
		sb.append(parameterList.get(1));
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(parameterList.get(0));

		if (parameterList.size() == 3) {
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(parameterList.get(2));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

}