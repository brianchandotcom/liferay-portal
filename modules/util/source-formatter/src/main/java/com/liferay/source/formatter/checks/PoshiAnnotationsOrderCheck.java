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

import java.io.IOException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiAnnotationsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Matcher matcher = _annotationsPattern.matcher(content);

		if (matcher.find()) {
			String s = matcher.group();

			String[] annotations = s.split("\n");

			Arrays.sort(annotations);

			content = StringUtil.replaceFirst(
				content, matcher.group(0),
				StringUtil.merge(annotations, "\n") + "\n");
		}

		return content;
	}

	private static final Pattern _annotationsPattern = Pattern.compile(
		"(@.+?=.+?\n){2,}(?=definition \\{)");

}