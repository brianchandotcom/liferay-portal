/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Micaelle Silva
 */
public class UpgradeJavaGetLeftCategoryIdMethodCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String methodStart = matcher.group();

		String className = matcher.group(1);

		if (!className.equals("AssetCategory") &&
			!hasClassOrVariableName(
				"AssetCategory", content, content, methodStart)) {

			return content;
		}

		return StringUtil.replace(
			newContent, methodStart,
			StringUtil.replace(
				methodStart, "getLeftCategoryId", "getCategoryId"));
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(\\w*)(::|\\.)getLeftCategoryId");
	}

}