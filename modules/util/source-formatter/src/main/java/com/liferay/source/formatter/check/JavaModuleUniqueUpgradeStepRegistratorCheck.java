/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaModuleUniqueUpgradeStepRegistratorCheck
	extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException, ParseException {

		if (!fileName.endsWith("UpgradeStepRegistrator.java") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (!implementedClassNames.contains("UpgradeStepRegistrator")) {
			return javaTerm.getContent();
		}

		int x = absolutePath.indexOf("/src/");

		if (x == -1) {
			return javaTerm.getContent();
		}

		List<String> upgradeFileNames = SourceFormatterUtil.scanForFileNames(
			absolutePath.substring(0, x + 5),
			new String[] {"**/upgrade/registry/*UpgradeStepRegistrator.java"});

		for (String upgradeFileName : upgradeFileNames) {
			if (upgradeFileName.equals(absolutePath)) {
				continue;
			}

			File file = new File(fileName);

			if (!file.exists()) {
				continue;
			}

			javaClass = JavaClassParser.parseJavaClass(
				fileName, FileUtil.read(file));

			implementedClassNames = javaClass.getImplementedClassNames();

			if (implementedClassNames.contains("UpgradeStepRegistrator")) {
				addMessage(
					fileName,
					"A module can not have more than 1 upgrade step " +
						"registrator class (class implements " +
							"UpgradeStepRegistrator)");

				return javaTerm.getContent();
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}