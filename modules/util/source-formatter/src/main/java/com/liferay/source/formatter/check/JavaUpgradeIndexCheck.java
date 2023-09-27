/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaUpgradeIndexCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.contains("BaseUpgradePortletPreferences") ||
			extendedClassNames.contains("UpgradeProcess")) {

			Matcher matcher = _pattern.matcher(fileContent);

			while (matcher.find()) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Do not execute '", matcher.group(),
						"' in upgrade classes"),
					getLineNumber(fileContent, matcher.start()));
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private static final Pattern _pattern = Pattern.compile(
		"(create|drop) (unique )?index IX_");

}