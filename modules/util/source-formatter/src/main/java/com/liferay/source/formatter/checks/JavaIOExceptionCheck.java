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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaIOExceptionCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher = _ioExceptionPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = StringUtil.replace(
				match, ".getMessage()", StringPool.BLANK);

			content = StringUtil.replace(content, match, replacement);
		}

		return new Tuple(content, Collections.emptySet());
	}

	private final Pattern _ioExceptionPattern = Pattern.compile(
		"\tthrow new IOException\\([\\s\\S]*?;\n");

}