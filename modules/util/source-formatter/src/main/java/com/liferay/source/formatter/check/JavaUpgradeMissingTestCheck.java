/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.io.File;

/**
 * @author Alan Huang
 */
public class JavaUpgradeMissingTestCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!isModulesFile(absolutePath) && !isPortalSource()) {
			return content;
		}

		String className = JavaSourceUtil.getClassName(fileName);

		if (!absolutePath.contains("/upgrade/") ||
			absolutePath.contains("-test/") || className.startsWith("Base") ||
			!isUpgradeProcess(absolutePath, content)) {

			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		for (String currentBranchRenamedFileName :
				sourceFormatterArgs.getCurrentBranchRenamedFileNames()) {

			if (absolutePath.endsWith(currentBranchRenamedFileName)) {
				return content;
			}
		}

		for (String currentBranchAddedFileNames :
				sourceFormatterArgs.getCurrentBranchAddedFileNames()) {

			if (absolutePath.endsWith(currentBranchAddedFileNames)) {
				_checkMissingTestFile(
					fileName, absolutePath, content, className);

				return content;
			}
		}

		return content;
	}

	private void _checkMissingTestFile(
		String fileName, String absolutePath, String content,
		String className) {

		String expectedTestClassName = StringBundler.concat(
			JavaSourceUtil.getPackageName(content), ".test.", className,
			"Test");

		File file = JavaSourceUtil.getJavaFile(
			expectedTestClassName, SourceUtil.getRootDirName(absolutePath),
			getBundleSymbolicNamesMap(absolutePath));

		if ((file == null) || !file.exists()) {
			addMessage(
				fileName,
				"Test class \"" + expectedTestClassName + "\" does not exist");
		}
	}

}