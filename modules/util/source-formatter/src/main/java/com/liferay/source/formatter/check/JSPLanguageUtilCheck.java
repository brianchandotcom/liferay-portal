/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLanguageUtilCheck extends BaseTagAttributesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _languageUtilPattern1.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start(), true)) {
				return StringUtil.replaceFirst(
					content, "LanguageUtil.get(locale,",
					"LanguageUtil.get(request,");
			}
		}

		matcher = _languageUtilPattern2.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				addMessage(
					fileName,
					"Use <liferay-ui:message> tag instead of LanguageUtil.get",
					getLineNumber(content, matcher.start()));
			}
		}

		return _simplyUnnecessaryReference(content);
	}

	private String _simplyUnnecessaryReference(String content) {
		Matcher matcher = _clayAlertTagPattern.matcher(content);

		while (matcher.find()) {
			String tagString = getTag(content, matcher.start());

			if (tagString == null) {
				continue;
			}

			Tag tag = parseTag(tagString, false);

			if (tag == null) {
				continue;
			}

			Map<String, String> attributesMap = tag.getAttributesMap();

			String message = attributesMap.get("message");

			if (message == null) {
				continue;
			}

			if (message.matches("<%= LanguageUtil.get\\(request, .+\\) %>")) {
				tag.putAttribute(
					"message",
					message.replaceFirst(
						"<%= LanguageUtil.get\\(request, \"(.+)\"\\) %>",
						"$1"));

				String newTag = tag.toString();

				if (!StringUtil.equals(tagString, newTag)) {
					return StringUtil.replaceFirst(
						content, tagString, newTag, matcher.start());
				}
			}
		}

		return content;
	}

	private static final Pattern _clayAlertTagPattern = Pattern.compile(
		"\t*<clay:alert");
	private static final Pattern _languageUtilPattern1 = Pattern.compile(
		"LanguageUtil\\.get\\(locale,");
	private static final Pattern _languageUtilPattern2 = Pattern.compile(
		"<%= LanguageUtil\\.get\\(");

}