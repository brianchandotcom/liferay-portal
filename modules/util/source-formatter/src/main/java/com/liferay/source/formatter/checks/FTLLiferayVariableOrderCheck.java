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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLLiferayVariableOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortLiferayVariables(content);
	}

	private String _sortLiferayVariables(String content) {
		Matcher matcher = _liferayVariablesPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			Matcher matcher2 = _liferayVariablePattern.matcher(match);

			String previousVariable = null;

			while (matcher2.find()) {
				String variable = matcher2.group();

				if (Validator.isNotNull(previousVariable) &&
					(previousVariable.compareTo(variable) > 0)) {

					String replacement = StringUtil.replaceFirst(
						match, previousVariable, variable);

					replacement = StringUtil.replaceLast(
						replacement, variable, previousVariable);

					return StringUtil.replace(content, match, replacement);
				}

				previousVariable = variable;
			}
		}

		return content;
	}

	private final Pattern _liferayVariablePattern = Pattern.compile(
		"^\t*<#assign liferay_.*>\n", Pattern.MULTILINE);
	private final Pattern _liferayVariablesPattern = Pattern.compile(
		"(^\t*<#assign liferay_.*>\n)+", Pattern.MULTILINE);

}