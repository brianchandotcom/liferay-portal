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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;

/**
 * @author Alan Huang
 */
public class TLDStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

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
				(!cdata.contains("<") && !cdata.contains(">"))) {

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