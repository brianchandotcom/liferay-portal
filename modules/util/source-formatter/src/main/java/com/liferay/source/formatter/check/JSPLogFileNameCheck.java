/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLogFileNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!isModulesFile(absolutePath) &&
			!absolutePath.contains("/portal-web/")) {

			content = _formatLogFileName(absolutePath, content);
		}

		return content;
	}

	private String _formatLogFileName(String absolutePath, String content) {
		Matcher matcher = _logPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String logFileName = StringUtil.replace(
			absolutePath, CharPool.PERIOD, CharPool.UNDERLINE);

		logFileName = StringUtil.replace(
			logFileName, CharPool.SLASH, CharPool.PERIOD);

		logFileName = StringUtil.replace(
			logFileName, CharPool.DASH, CharPool.UNDERLINE);

		int x = logFileName.lastIndexOf(".portal_web.");

		if (x != -1) {
			logFileName = logFileName.substring(x + 1);
		}
		else {
			x = logFileName.lastIndexOf(".docroot.");

			if (x == -1) {
				x = Math.max(
					logFileName.lastIndexOf(
						".src.main.resources.META_INF.resources."),
					logFileName.lastIndexOf(".src.META_INF.resources."));
			}

			if (x == -1) {
				return content;
			}

			x = logFileName.lastIndexOf(StringPool.PERIOD, x - 1);

			logFileName = "com_liferay_" + logFileName.substring(x + 1);

			logFileName = StringUtil.replace(
				logFileName,
				new String[] {
					".docroot.", ".src.main.resources.META_INF.resources.",
					".src.META_INF.resources."
				},
				new String[] {
					StringPool.PERIOD, StringPool.PERIOD, StringPool.PERIOD
				});
		}

		return StringUtil.replace(
			content, matcher.group(),
			"Log _log = LogFactoryUtil.getLog(\"" + logFileName + "\")");
	}

	private static final Pattern _logPattern = Pattern.compile(
		"Log _log = LogFactoryUtil\\.getLog\\(\"(.*?)\"\\)");

}