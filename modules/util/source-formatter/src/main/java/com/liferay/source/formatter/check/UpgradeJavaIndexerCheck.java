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
public class UpgradeJavaIndexerCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		return StringUtil.replace(
			newContent, matcher.group(),
			StringUtil.replace(matcher.group(), "Indexer", "Indexer<?>"));
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("[(<,\\s]Indexer[\\s,>]");
	}

}