/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Micaelle Silva
 */
public class UpgradeJavaBasePanelAppExtendedClassesCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (!extendedClassNames.contains("BasePanelApp")) {
			return content;
		}

		Matcher matcher = _setPortletPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replace(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1),
					"\n\t)\n\tprivate Portlet _portlet;\n\n\t@Override\n\t",
					"public Portlet getPortlet() {\n\t\treturn _portlet;"));
		}

		return content;
	}

	private static final Pattern _setPortletPattern = Pattern.compile(
		"@Override\\s*(@Reference\\(\\s*.+\\s*\\)+\")[\\s+\\S+]+" +
			"setPortlet[\\s+\\S+]+;");

}