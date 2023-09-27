/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeDLUtilCheck extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		if (fileName.contains("jsp") &&
			!newContent.contains("com.liferay.portal.kernel.util.PortalUtil")) {

			return StringBundler.concat(
				newContent, "\n\n<%@ page import=\"",
				"com.liferay.portal.kernel.util.PortalUtil\" %>");
		}

		return addNewImports(newContent);
	}

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		return StringUtil.replace(
			newContent, matcher.group(),
			StringBundler.concat(
				"PortalUtil.getCurrentAndAncestorSiteGroupIds(",
				matcher.group(1), ".getScopeGroupId())"));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"com.liferay.portal.kernel.util.PortalUtil"};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("DLUtil.\\s*getGroupIds\\(\\s*(.+)\\s*\\)");
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"java", "jsp"};
	}

}