/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaCommerceRegionCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String variableTypeName = getVariableTypeName(
			newContent, newContent, matcher.group(1));

		if (!variableTypeName.equals("Region") &&
			!variableTypeName.equals("CommerceAddress")) {

			return newContent;
		}

		String methodStart = matcher.group();

		return StringUtil.replaceFirst(
			newContent, methodStart,
			StringUtil.replace(methodStart, "getCode", "getRegionCode"));
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(\\w+)\\.\\w*\\(?\\s*\\)?\\s*\\.?getCode\\(");
	}

}