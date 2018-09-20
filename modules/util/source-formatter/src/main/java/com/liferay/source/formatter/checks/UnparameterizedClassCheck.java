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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class UnparameterizedClassCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixUnparameterizedClassType(content);

		return content;
	}

	private String _fixUnparameterizedClassType(String content) {
		Matcher matcher = _unparameterizedClassTypePattern1.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "Class", "Class<?>", matcher.start());
		}

		matcher = _unparameterizedClassTypePattern2.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "Class", "Class<?>", matcher.start());
		}

		return content;
	}

	private static final Pattern _unparameterizedClassTypePattern1 =
		Pattern.compile("\\Wnew Class[^<\\w]");
	private static final Pattern _unparameterizedClassTypePattern2 =
		Pattern.compile("\\WClass[\\[\\]]* \\w+ =");

}