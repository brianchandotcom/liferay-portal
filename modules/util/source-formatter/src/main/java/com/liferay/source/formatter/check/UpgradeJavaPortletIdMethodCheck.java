/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaPortletIdMethodCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		StringBuilder sb = new StringBuilder();

		sb.append("PortletProviderUtil.getPortletId(");
		sb.append(matcher.group(1));
		sb.append("Field.ENTRY_CLASS_NAME), ");
		sb.append("PortletProvider.Action.VIEW");

		return StringUtil.replace(newContent, matcher.group(), sb.toString());
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.portlet.PortletProvider",
			"com.liferay.portal.kernel.portlet.PortletProviderUtil"
		};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(\\w+\\.get\\()\\s*(Field.PORTLET_ID)");
	}

}