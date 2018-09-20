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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPXSSVulnerabilitiesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixXSSVulnerability(content);

		return content;
	}

	private String _fixXSSVulnerability(String content) {
		Matcher matcher1 = _xssPattern.matcher(content);

		String jspVariable = null;
		int vulnerabilityPos = -1;

		while (matcher1.find()) {
			jspVariable = matcher1.group(1);

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";
			String inputVulnerability = " value=\"<%= " + jspVariable + " %>";

			vulnerabilityPos = Math.max(
				_getTaglibXSSVulnerabilityPos(content, anchorVulnerability),
				_getTaglibXSSVulnerabilityPos(content, inputVulnerability));

			if (vulnerabilityPos != -1) {
				break;
			}

			Pattern pattern = Pattern.compile(
				"('|\\(\"| \"|\\.)<%= " + jspVariable + " %>");

			Matcher matcher2 = pattern.matcher(content);

			if (matcher2.find()) {
				vulnerabilityPos = matcher2.start();

				break;
			}
		}

		if (vulnerabilityPos != -1) {
			return StringUtil.replaceFirst(
				content, "<%= " + jspVariable + " %>",
				"<%= HtmlUtil.escape(" + jspVariable + ") %>",
				vulnerabilityPos);
		}

		return content;
	}

	private int _getTaglibXSSVulnerabilityPos(
		String content, String vulnerability) {

		int x = -1;

		while (true) {
			x = content.indexOf(vulnerability, x + 1);

			if (x == -1) {
				return x;
			}

			String tagContent = null;

			int y = x;

			while (true) {
				y = content.lastIndexOf(CharPool.LESS_THAN, y - 1);

				if (y == -1) {
					return -1;
				}

				if (content.charAt(y + 1) == CharPool.PERCENT) {
					continue;
				}

				tagContent = content.substring(y, x);

				if (getLevel(tagContent, "<", ">") == 1) {
					break;
				}
			}

			if (!tagContent.startsWith("<aui:") &&
				!tagContent.startsWith("<liferay-portlet:") &&
				!tagContent.startsWith("<liferay-util:") &&
				!tagContent.startsWith("<portlet:")) {

				return x;
			}
		}
	}

	private static final Pattern _xssPattern = Pattern.compile(
		"\\s+([^\\s]+)\\s*=\\s*(Bean)?ParamUtil\\.getString\\(");

}