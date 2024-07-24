/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaDisplayPageInfoItemCapabilityCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		Matcher matcher = _pattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String annotationContent = joinLines(
			"@Reference(",
			String.format(
				"\t\ttarget = \"(info.item.capability.key=\" + " +
					"DisplayPageInfoItemCapability.KEY + \")\""),
			"\t)", "\tprivate InfoItemCapability " + matcher.group(1) + ";");

		return matcher.replaceAll(annotationContent);
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.info.item.capability.InfoItemCapability"
		};
	}

	private static final Pattern _pattern = Pattern.compile(
		"@Reference\\s*private DisplayPageInfoItemCapability (\\w+);");

}