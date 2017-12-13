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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher = _versionPattern.matcher(content);

		while (matcher.find()) {
			String name = matcher.group(2);
			String version = matcher.group(3);

			_checkDefaultVersion(
				fileName, content, name, version, matcher.start());
		}

		return content;
	}

	private void _checkDefaultVersion(
		String fileName, String content, String name, String version, int pos) {

		if (version.equals("default") &&
			!name.equals("com.liferay.portal.impl") &&
			!name.equals("com.liferay.portal.kernel") &&
			!name.equals("com.liferay.util.bridges") &&
			!name.equals("com.liferay.util.taglib")) {

			addMessage(
				fileName, "Do not use 'default' version for '" + name + "'",
				"gradle_versioning.markdown", getLineCount(content, pos));
		}
	}

	private final Pattern _versionPattern = Pattern.compile(
		"\n\t*(.* name: \"(.*?)\", version: \"(.*?)\")");

}