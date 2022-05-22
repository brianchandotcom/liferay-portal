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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class ConfigWhitespaceCheck extends WhitespaceCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.contains("/portal-web/test/")) {
			return content;
		}

		Matcher matcher = _incorrectWhitesapcePattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.insert(content, StringPool.SPACE, matcher.end());
		}

		return super.doProcess(fileName, absolutePath, content);
	}

	private static final Pattern _incorrectWhitesapcePattern = Pattern.compile(
		"\\\\\":(?=\\\\)");

}