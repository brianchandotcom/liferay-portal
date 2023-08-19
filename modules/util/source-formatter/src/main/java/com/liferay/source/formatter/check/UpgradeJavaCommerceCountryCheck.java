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
public class UpgradeJavaCommerceCountryCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String variableTypeName = getVariableTypeName(
			newContent, newContent, matcher.group(1));

		if (!variableTypeName.equals("Country") &&
			!variableTypeName.equals("CommerceAddress")) {

			return newContent;
		}

		String newMethodStart = null;
		String methodStart = matcher.group();
		String methodName = matcher.group(2);

		if (methodName.contains("Two")) {
			newMethodStart = StringUtil.replace(
				methodStart, "TwoLettersISOCode", "A2");
		}
		else {
			newMethodStart = StringUtil.replace(
				methodStart, "ThreeLettersISOCode", "A3");
		}

		return StringUtil.replaceFirst(newContent, methodStart, newMethodStart);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile(
			"(\\w+)\\.\\w*\\(?\\s*\\)?\\s*\\.?" +
				"(\\w+(?:Two|Three)LettersISOCode)\\(");
	}

}