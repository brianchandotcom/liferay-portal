/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeMatcherReplacementCheck
	extends BaseUpgradeCheck {

	protected String beforeFormatMatcherIteration(
		String fileName, String absolutePath, String content) {

		return content;
	}

	@Override
	protected String format(
		String fileName, String absolutePath, String content) {

		String newContent = beforeFormatMatcherIteration(
			fileName, absolutePath, content);

		Pattern pattern = getPattern();

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			newContent = formatMatcherIteration(content, newContent, matcher);
		}

		return newContent;
	}

	protected abstract String formatMatcherIteration(
		String content, String newContent, Matcher matcher);

	protected abstract Pattern getPattern();

}