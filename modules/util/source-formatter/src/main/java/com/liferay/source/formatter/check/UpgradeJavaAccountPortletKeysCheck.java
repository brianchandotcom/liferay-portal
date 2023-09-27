/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaAccountPortletKeysCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String constant = matcher.group(1);

		String constantCall = matcher.group();

		String newConstantCall = constantCall;

		if (constant.equals("COMMERCE_ACCOUNT_GROUP_ADMIN")) {
			newConstantCall = StringUtil.replace(
				newConstantCall, constant, "ACCOUNT_GROUPS_ADMIN");
		}
		else if (constant.equals("COMMERCE_ACCOUNT_ADMIN")) {
			newConstantCall = StringUtil.replace(
				newConstantCall, constant, "ACCOUNT_USERS_ADMIN");
		}

		return StringUtil.replace(newContent, constantCall, newConstantCall);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("AccountPortletKeys\\.([A-Z_]+)");
	}

}