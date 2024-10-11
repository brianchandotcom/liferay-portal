/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.GradleSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class GradleRestClientDependenciesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> dependenciesBlocks =
			GradleSourceUtil.getDependenciesBlocks(content);

		for (String dependenciesBlock : dependenciesBlocks) {
			int x = dependenciesBlock.indexOf("\n");
			int y = dependenciesBlock.lastIndexOf("\n");

			if (x == y) {
				continue;
			}

			String dependencies = dependenciesBlock.substring(x, y + 1);

			Matcher matcher = _restClientDependencyPattern.matcher(
				dependencies);

			while (matcher.find()) {
				String matched = matcher.group();

				if (!StringUtil.startsWith(
						matched, "testIntegrationImplementation")) {

					addMessage(
						fileName,
						"Project dependencies \".*-rest-client\" can only be " +
							"used for \"testIntegrationImplementation\"",
						SourceUtil.getLineNumber(
							content, content.indexOf(matched)));
				}
			}
		}

		return content;
	}

	private static final Pattern _restClientDependencyPattern = Pattern.compile(
		"\\w+ project\\(\".*-rest-client\"\\)");

}