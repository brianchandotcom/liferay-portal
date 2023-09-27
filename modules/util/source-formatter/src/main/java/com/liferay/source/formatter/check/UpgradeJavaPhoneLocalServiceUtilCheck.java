/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaPhoneLocalServiceUtilCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String methodCall = JavaSourceUtil.getMethodCall(
			content, matcher.start());

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() == 8) {
			return newContent;
		}

		String[] parameterTypes = {
			"long", "String", "long", "String", "String", "long", "boolean"
		};

		if (!hasParameterTypes(
				content, content, ArrayUtil.toStringArray(parameterList),
				parameterTypes)) {

			return newContent;
		}

		return StringUtil.replace(
			newContent, methodCall,
			StringUtil.replaceLast(
				methodCall, CharPool.CLOSE_PARENTHESIS,
				", ServiceContextThreadLocal.getServiceContext())"));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.service.ServiceContextThreadLocal"
		};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("PhoneLocalServiceUtil\\.addPhone\\(");
	}

}