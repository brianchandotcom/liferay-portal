/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;
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
				_getCurrentBranchRenamedFileNames(sourceFormatterArgs)) {

			if (absolutePath.endsWith(currentBranchRenamedFileName)) {
				return content;
			}
		}

		for (String currentBranchAddedFileNames :
				_getCurrentBranchAddedFileName(sourceFormatterArgs)) {

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

		String expectedTestClassName = null;
		File file = null;

		if (isModulesFile(absolutePath)) {
			expectedTestClassName = StringBundler.concat(
				JavaSourceUtil.getPackageName(content), ".test.", className,
				"Test");

			file = JavaSourceUtil.getJavaFile(
				expectedTestClassName, SourceUtil.getRootDirName(absolutePath),
				getBundleSymbolicNamesMap(absolutePath));
		}
		else if (absolutePath.contains("/portal-impl/") ||
				 absolutePath.contains("/portal-kernel/")) {

			expectedTestClassName = StringUtil.replaceFirst(
				absolutePath,
				new String[] {"/portal-impl/src/", "/portal-kernel/src/"},
				new String[] {
					"/portal-impl/test/unit/", "/portal-kernel/test/unit/"
				});

			expectedTestClassName = StringUtil.insert(
				expectedTestClassName, "Test",
				expectedTestClassName.length() - 5);

			file = new File(expectedTestClassName);
		}

		if ((file == null) || !file.exists()) {
			addMessage(
				fileName,
				"Test class '" + expectedTestClassName + "' does not exist");
		}
	}

	private synchronized List<String> _getCurrentBranchAddedFileName(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchAddedFileNames != null) {
			return _currentBranchAddedFileNames;
		}

		_currentBranchAddedFileNames = GitUtil.getCurrentBranchAddedFileNames(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchAddedFileNames;
	}

	private synchronized List<String> _getCurrentBranchRenamedFileNames(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchRenamedFileNames != null) {
			return _currentBranchRenamedFileNames;
		}

		_currentBranchRenamedFileNames =
			GitUtil.getCurrentBranchRenamedFileNames(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchRenamedFileNames;
	}

	private boolean _isUpgradeProcess(String absolutePath, String content) {
		String className = JavaSourceUtil.getClassName(absolutePath);

		Pattern pattern = Pattern.compile(
			" class " + className + "\\s+extends\\s+([\\w.]+) ");

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

	private List<String> _currentBranchAddedFileNames;
	private List<String> _currentBranchRenamedFileNames;

}