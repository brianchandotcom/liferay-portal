/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Renato Rêgo
 */
public class UpgradeJavaCaptchaUtilCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		List<String> parameterList = JavaSourceUtil.getParameterList(
			content.substring(matcher.start()));

		if (parameterList.size() != 2) {
			return newContent;
		}

		String[] parameterTypes = {"ResourceRequest", "ResourceResponse"};

		if (!hasParameterTypes(
				content, content, ArrayUtil.toStringArray(parameterList),
				parameterTypes)) {

			return newContent;
		}

		StringBundler sb = new StringBundler(6);

		String methodCall = matcher.group();

		sb.append(
			methodCall.substring(
				0, methodCall.indexOf(StringPool.OPEN_PARENTHESIS) + 1));

		sb.append("PortalUtil.getHttpServletRequest(");
		sb.append(parameterList.get(0));
		sb.append("), PortalUtil.getHttpServletResponse(");
		sb.append(parameterList.get(1));
		sb.append("))");

		return StringUtil.replace(newContent, methodCall, sb.toString());
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"com.liferay.portal.kernel.util.PortalUtil"};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile(
			"CaptchaUtil\\.\\s*serveImage\\(\\s*.+,\\s*.+(,\\s*.+)?\\s*\\)");
	}

}