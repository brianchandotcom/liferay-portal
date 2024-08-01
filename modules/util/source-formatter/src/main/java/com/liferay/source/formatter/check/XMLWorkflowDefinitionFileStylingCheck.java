/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class XMLWorkflowDefinitionFileStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("workflow-definition.xml")) {
			return content;
		}

		content = _formatLabelTags(content);
		content = _formatNotificationTags(content);

		return _formatTemplateTags(content);
	}

	private String _formatLabelTags(String content) {
		Matcher matcher = _labelTagPattern.matcher(content);

		while (matcher.find()) {
			String label = matcher.group(1);

			String titleCaseLabel = StringUtil.getTitleCase(label, false);

			if (!label.equals(titleCaseLabel)) {
				return StringUtil.replaceFirst(
					content, label, titleCaseLabel, matcher.start(1));
			}
		}

		return content;
	}

	private String _formatNotificationTags(String content) {
		Matcher matcher = _notificationTagPattern.matcher(content);

		while (matcher.find()) {
			String notification = matcher.group();

			int x = notification.indexOf(matcher.group(1) + "\t<name>");

			if (x == -1) {
				continue;
			}

			int y = notification.indexOf("</name>", x);

			if (y == -1) {
				continue;
			}

			String name = notification.substring(x + 10, y);

			String titleCaseName = StringUtil.getTitleCase(name, false);

			if (!name.equals(titleCaseName)) {
				return StringUtil.replaceFirst(
					content, name, titleCaseName, matcher.start());
			}
		}

		return content;
	}

	private String _formatTemplateTags(String content) {
		Matcher matcher = _templateTagPattern.matcher(content);

		while (matcher.find()) {
			String template = matcher.group(2);

			String trimmedTemplate = template.trim();

			if (!trimmedTemplate.startsWith("<![CDATA[") &&
				!trimmedTemplate.endsWith("]]>")) {

				continue;
			}

			String cdataValue = trimmedTemplate.substring(
				9, trimmedTemplate.length() - 3);

			cdataValue = cdataValue.trim();

			if (cdataValue.equals(StringPool.BLANK)) {
				return content;
			}

			int x = cdataValue.indexOf("\n");

			if (x != -1) {
				return content;
			}

			String indent = matcher.group(1);

			String replacement = StringBundler.concat(
				"\n", indent, "\t<![CDATA[", cdataValue.trim(), "]]>\n",
				indent);

			if (!template.equals(replacement)) {
				return StringUtil.replaceFirst(
					content, template, replacement, matcher.start(2));
			}
		}

		return content;
	}

	private static final Pattern _labelTagPattern = Pattern.compile(
		"\t<label [^>]*?>\n\t*(.+)\n\t*</label>");
	private static final Pattern _notificationTagPattern = Pattern.compile(
		"(\\t+)<notification>[\\s\\S]+?</notification>");
	private static final Pattern _templateTagPattern = Pattern.compile(
		"(\\t+)<template>([\\s\\S]+?)</template>");

}