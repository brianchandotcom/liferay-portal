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