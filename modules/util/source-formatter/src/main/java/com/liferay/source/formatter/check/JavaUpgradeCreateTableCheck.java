/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaUpgradeCreateTableCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (!implementedClassNames.contains("PortalUpgradeProcessRegistry") &&
			!implementedClassNames.contains("UpgradeStepRegistrator") &&
			!isUpgradeProcess(absolutePath, javaClass.getContent())) {

			return javaClass.getContent();
		}

		int x = -1;

		while (true) {
			x = fileContent.indexOf("create table", x + 1);

			if (x == -1) {
				return javaClass.getContent();
			}

			addMessage(
				fileName, "Do not execute \"create table\" in upgrade classes",
				getLineNumber(fileContent, x));
		}
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}