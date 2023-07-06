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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeMatcherReplacementCheck
	extends BaseUpgradeCheck {

	protected String beforeFormatIteration(
		String fileName, String absolutePath, String content) {

		return content;
	}

	@Override
	protected String format(
		String fileName, String absolutePath, String content) {

		String newContent = beforeFormatIteration(
			fileName, absolutePath, content);

		Matcher matcher = getPattern().matcher(content);

		while (matcher.find()) {
			newContent = formatIteration(content, newContent, matcher);
		}

		return newContent;
	}

	protected abstract String formatIteration(
		String content, String newContent, Matcher matcher);

	protected abstract Pattern getPattern();

}