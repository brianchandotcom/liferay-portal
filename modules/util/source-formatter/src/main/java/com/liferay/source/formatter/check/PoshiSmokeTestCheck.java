/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiSmokeTestCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".testcase") || SourceUtil.isXML(content)) {
			return content;
		}

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		if (!shortFileName.contains("Smoke") &&
			!shortFileName.contains("smoke")) {

			return content;
		}

		Matcher matcher = _testMethodPattern.matcher(content);

		if (matcher.find()) {
			String s = content.substring(0, matcher.start());

			if (!s.contains("property ci.retries.disabled = \"true\";")) {
				addMessage(
					fileName,
					"Missing property ci.retries.disabled = \"true\" in " +
						"definition for smoke test");

				return content;
			}

			StringBundler sb = new StringBundler(s);

			s = content.substring(matcher.start());

			for (String line : s.split("\n")) {
				if (Validator.isNull(line)) {
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				String trimmedLine = line.trim();

				if (trimmedLine.equals(
						"property ci.retries.disabled = \"true\";")) {

					continue;
				}

				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}

			if (sb.index() > 1) {
				sb.setIndex(sb.index() - 1);
			}

			return sb.toString();
		}

		return content;
	}

	private static final Pattern _testMethodPattern = Pattern.compile(
		"\n\t+test \\w+ \\{");

}