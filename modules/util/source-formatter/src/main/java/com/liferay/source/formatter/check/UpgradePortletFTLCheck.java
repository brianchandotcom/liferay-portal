/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradePortletFTLCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		Matcher portletTopperMatcher = _portletTopperPattern.matcher(content);

		while (portletTopperMatcher.find()) {
			newContent = StringUtil.replace(
				newContent, portletTopperMatcher.group(1),
				"\"cadmin portlet-topper\"");
		}

		Matcher liferayPortletMatcher = _liferayPortletPattern.matcher(content);

		while (liferayPortletMatcher.find()) {
			newContent = StringUtil.replace(
				newContent, liferayPortletMatcher.group(),
				"<@liferay_frontend[\"icon-options\"] direction=" +
					"\"right cadmin\" " + liferayPortletMatcher.group(1));
		}

		Matcher portletTitleMenuMatcher = _portletTitleMenuPattern.matcher(
			content);

		while (portletTitleMenuMatcher.find()) {
			String portletTitleMenuCall = portletTitleMenuMatcher.group();

			if (portletTitleMenuCall.contains(
					"portletTitleMenu.setDirection")) {

				return newContent;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(JavaSourceUtil.getIndent(portletTitleMenuCall));
			sb.append("${portletTitleMenu.setDirection(\"right cadmin\")}");
			sb.append(StringPool.NEW_LINE);
			sb.append(portletTitleMenuCall);

			newContent = StringUtil.replace(
				newContent, portletTitleMenuCall, sb.toString());
		}

		return newContent;
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"ftl"};
	}

	private static final Pattern _liferayPortletPattern = Pattern.compile(
		"<@liferay_portlet\\[\"icon-options\"\\]\\s*(\\w+[^;]\\w+\\s*\\/>)");
	private static final Pattern _portletTitleMenuPattern = Pattern.compile(
		"\\t*<@liferay_ui[^;]+\\s*\\w+\\=\\s*portletTitleMenu\\s*\\/>" +
			"|portletTitleMenu\\.setDirection");
	private static final Pattern _portletTopperPattern = Pattern.compile(
		"\\w+[^;](\"portlet-topper\")");

}