/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJSPFieldSetGroupCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		return StringUtil.removeSubstring(newContent, matcher.group());
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("\\t*<[^;]?liferay-frontend\\:fieldset-group>");
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"jsp"};
	}

}