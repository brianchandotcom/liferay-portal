/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class TLDStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkMissingCDATA(fileName, content);

		return _removeUnnecessaryCDATA(content);
	}

	private void _checkMissingCDATA(String fileName, String content) {
		Matcher matcher = _descriptionPattern.matcher(content);

		while (matcher.find()) {
			String description = matcher.group(1);

			int x = description.indexOf("replaced by ");

			if (x != -1) {
				x = description.indexOf("<![CDATA[", x + 12);

				if (x == -1) {
					addMessage(
						fileName,
						"Missing CDATA after 'replaced by' in the description",
						SourceUtil.getLineNumber(content, matcher.start(1)));
				}
			}

			x = description.indexOf("<code>");

			while (true) {
				if (x == -1) {
					break;
				}

				if (!StringUtil.endsWith(
						description.substring(0, x), "<![CDATA[")) {

					addMessage(
						fileName,
						"Use CDATA to warp each '<code>' in the description",
						SourceUtil.getLineNumber(content, matcher.start(1)));

					break;
				}

				x = description.indexOf("<code>", x + 6);
			}
		}
	}

	private String _removeUnnecessaryCDATA(String content) {
		Matcher matcher = _descriptionPattern.matcher(content);

		while (matcher.find()) {
			String description = matcher.group(1);

			int x = description.indexOf("<![CDATA[");

			if (x == -1) {
				continue;
			}

			int y = description.indexOf("]]>", x + 9);

			if (y == -1) {
				continue;
			}

			String cdata = description.substring(x + 9, y);

			if (Validator.isNull(cdata) ||
				(!cdata.contains(StringPool.AMPERSAND) &&
				 !cdata.contains(StringPool.APOSTROPHE) &&
				 !cdata.contains(StringPool.GREATER_THAN) &&
				 !cdata.contains(StringPool.LESS_THAN) &&
				 !cdata.contains(StringPool.QUOTE))) {

				return StringUtil.replaceFirst(
					content, description,
					description.substring(0, x) +
						description.substring(x + 9, y) +
							description.substring(y + 3),
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _descriptionPattern = Pattern.compile(
		"\n\t*<description>(.*)?</description>");

}