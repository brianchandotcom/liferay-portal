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

package com.liferay.source.formatter.checks;

import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ValidatorEqualsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkValidatorEquals(fileName, content);

		return content;
	}

	private void _checkValidatorEquals(String fileName, String content) {
		Matcher matcher = _validatorEqualsPattern.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				addMessage(
					fileName,
					"Use Objects.equals(Object, Object) instead of " +
						"Validator.equals(Object, Object)",
					getLineNumber(content, matcher.start()));
			}
		}
	}

	private static final Pattern _validatorEqualsPattern = Pattern.compile(
		"\\WValidator\\.equals\\(");

}