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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Peter Shin
 */
public class PropertiesSourceFormatterContentCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/source-formatter.properties")) {
			content = _checkConvertedKeys(content);
			content = _checkGitLiferayPortalBranch(content);
		}

		return content;
	}

	private String _checkConvertedKeys(String content) {
		for (String[] array : _CONVERTED_KEYS) {
			content = StringUtil.replace(content, array[0], array[1]);
		}

		return content;
	}

	private String _checkGitLiferayPortalBranch(String content) {
		if (!content.matches("(?s).*[^#]git\\.liferay\\.portal\\.branch=.*")) {
			return content;
		}

		String gitLiferayPortalBranch = StringPool.BLANK;
		String previousLine = StringPool.BLANK;

		String[] lines = StringUtil.splitLines(content);

		StringBundler sb = new StringBundler(lines.length * 2);

		for (String line : lines) {
			String trimmedLine = StringUtil.trim(line);

			if (trimmedLine.startsWith("git.liferay.portal.branch=")) {
				gitLiferayPortalBranch = trimmedLine.substring(
					trimmedLine.indexOf(CharPool.EQUAL) + 1);
			}

			String s = StringUtil.trim(previousLine);

			if (s.equals("git.liferay.portal.branch=\\")) {
				gitLiferayPortalBranch = StringUtil.trim(line);
			}
			else if (!trimmedLine.startsWith("git.liferay.portal.branch=")) {
				sb.append(line);
				sb.append("\n");
			}

			previousLine = line;
		}

		String exclusionsComments = StringBundler.concat(
			"##\n## Exclusions\n##\n## See ", _SOURCE_FORMATTER_PROPERTIES_URL,
			"\n## for available properties.\n##");
		String gitComments = "##\n## Git\n##";

		String s = StringUtil.replace(
			sb.toString(), new String[] {exclusionsComments, gitComments},
			new String[] {StringPool.BLANK, StringPool.BLANK});

		s = StringUtil.trim(s);

		if (Validator.isNull(s)) {
			return StringBundler.concat(
				exclusionsComments, "\n\n", gitComments, "\n\n",
				StringPool.FOUR_SPACES, "git.liferay.portal.branch=",
				gitLiferayPortalBranch);
		}

		return StringBundler.concat(
			exclusionsComments, "\n\n", StringPool.FOUR_SPACES, s, "\n\n",
			gitComments, "\n\n", StringPool.FOUR_SPACES,
			"git.liferay.portal.branch=", gitLiferayPortalBranch);
	}

	private static final String[][] _CONVERTED_KEYS = {
		{
			"blob/master/portal-impl/src/source-formatter.properties",
			"blob/master/source-formatter.properties"
		}
	};

	private static final String _SOURCE_FORMATTER_PROPERTIES_URL =
		"https://github.com/liferay/liferay-portal/blob/master" +
			"/source-formatter.properties";

}