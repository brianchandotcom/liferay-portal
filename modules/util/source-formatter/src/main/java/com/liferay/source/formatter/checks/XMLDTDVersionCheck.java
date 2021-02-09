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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class XMLDTDVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith(".xml")) {
			return _checkDTDVersion(content);
		}

		return content;
	}

	private String _checkDTDVersion(String content) {
		Matcher matcher = _doctypePattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String version = ReleaseInfo.getVersion();

		return StringUtil.replaceFirst(
			content, matcher.group(),
			StringBundler.concat(
				matcher.group(1), version, matcher.group(3),
				StringUtil.replace(
					version, CharPool.PERIOD, CharPool.UNDERLINE),
				matcher.group(5)),
			matcher.start());
	}

	private static final Pattern _doctypePattern = Pattern.compile(
		"(<!DOCTYPE .+ PUBLIC \"-//Liferay//DTD .+ )" +
			"([0-9]+\\.[0-9]+\\.[0-9]+)(//EN\" \"http://www.liferay.com/dtd/" +
				".+_)([0-9]+_[0-9]+_[0-9]+)(\\.dtd\">)");

}