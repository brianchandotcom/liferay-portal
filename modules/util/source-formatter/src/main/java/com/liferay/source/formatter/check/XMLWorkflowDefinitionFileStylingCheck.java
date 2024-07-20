/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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

	private static final Pattern _labelTagPattern = Pattern.compile(
		"\t<label [^>]*?>\n\t*(.+)\n\t*</label>");

}