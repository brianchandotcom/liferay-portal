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

import com.liferay.petra.string.StringBundler;
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

		while (matcher.find()) {
			String s = matcher.group();

			String[] annotations = s.split("\n");

			StringBundler sb = new StringBundler();

			Arrays.sort(annotations);

			for (String annotation : annotations) {
				sb.append(annotation);
				sb.append("\n");
			}

			String orderedAnnotations = sb.toString();

			if (!StringUtil.equals(matcher.group(), orderedAnnotations)) {
				content = StringUtil.replaceFirst(
					content, matcher.group(), orderedAnnotations,
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _annotationsPattern = Pattern.compile(
		"(^\t*)(@.+?=.+?\n)(\\1(@.+?=.+?\n))+", Pattern.MULTILINE);

}