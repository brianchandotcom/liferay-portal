/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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

		return _fixXSSVulnerability(content);
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