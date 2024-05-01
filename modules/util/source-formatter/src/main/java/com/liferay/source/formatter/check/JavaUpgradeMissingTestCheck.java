/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			!_isUpgradeProcess(absolutePath, content)) {

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
				"Test class '" + expectedTestClassName + "' does not exist");
		}
	}

	private boolean _isUpgradeProcess(String absolutePath, String content) {
		Pattern pattern = Pattern.compile(
			" class " + JavaSourceUtil.getClassName(absolutePath) +
				"\\s+extends\\s+([\\w.]+) ");

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return false;
		}

		String extendedClassName = matcher.group(1);

		if (extendedClassName.equals("UpgradeProcess")) {
			return true;
		}

		pattern = Pattern.compile("\nimport (.*\\." + extendedClassName + ");");

		matcher = pattern.matcher(content);

		if (matcher.find()) {
			extendedClassName = matcher.group(1);
		}

		if (!extendedClassName.contains(StringPool.PERIOD)) {
			extendedClassName =
				JavaSourceUtil.getPackageName(content) + StringPool.PERIOD +
					extendedClassName;
		}

		if (!extendedClassName.startsWith("com.liferay.")) {
			return false;
		}

		File file = JavaSourceUtil.getJavaFile(
			extendedClassName, SourceUtil.getRootDirName(absolutePath),
			getBundleSymbolicNamesMap(absolutePath));

		if (file == null) {
			return false;
		}

		return _isUpgradeProcess(file.getAbsolutePath(), FileUtil.read(file));
	}

}