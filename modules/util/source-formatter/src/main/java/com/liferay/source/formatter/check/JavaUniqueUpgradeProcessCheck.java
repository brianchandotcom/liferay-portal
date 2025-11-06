/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaUniqueUpgradeProcessCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (!implementedClassNames.contains("PortalUpgradeProcessRegistry") &&
			!implementedClassNames.contains("UpgradeStepRegistrator")) {

			return javaClass.getContent();
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.hasAnnotation("Override") ||
				!childJavaTerm.isJavaMethod()) {

				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String methodName = javaMethod.getName();

			if (methodName.equals("register")) {
				_checkUniqueUpgradeProcess(
					fileName, absolutePath, javaClass, javaMethod,
					"registry.register", 2);
			}
			else if (methodName.equals("registerUpgradeProcesses")) {
				_checkUniqueUpgradeProcess(
					fileName, absolutePath, javaClass, javaMethod,
					"upgradeVersionTreeMap.put", 1);
			}
		}

		return javaClass.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkUniqueUpgradeProcess(
		String fileName, String absolutePath, JavaClass javaClass,
		JavaMethod javaMethod, String methodName,
		int upgradeStepsParameterIndex) {

		String content = javaMethod.getContent();

		int x = -1;

		while (true) {
			x = content.indexOf(methodName + "(", x + 1);

			if (x == -1) {
				return;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(x));

			List<String> upgradeSteps = parameterList.subList(
				upgradeStepsParameterIndex, parameterList.size());

			if (upgradeSteps.size() < 2) {
				continue;
			}

			int upgradeProcessCount = _getUpgradeProcessCount(
				absolutePath, javaClass.getImportNames(),
				javaClass.getPackageName(), upgradeSteps);

			if (upgradeProcessCount == 0) {
				continue;
			}

			if (upgradeProcessCount > 1) {
				addMessage(
					fileName,
					"Only one UpgradeProcess can be added in \"" + methodName +
						"\", see LPD-44331",
					javaMethod.getLineNumber(x));
			}

			if (!_containsTableCreateOrUpgradeFactoryCalls(upgradeSteps)) {
				continue;
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Do not combine an UpgradeModulesFactory, ",
					"UpgradeProcessFactory or UpgradeTableBuilder upgrade ",
					"process with a standard UpgradeProcess under the same ",
					"schema version, see LPD-44331"),
				javaMethod.getLineNumber(x));
		}
	}

	private boolean _containsTableCreateOrUpgradeFactoryCalls(
		List<String> upgradeSteps) {

		for (String upgradeStep : upgradeSteps) {
			if (upgradeStep.matches("\\w+Table\\.create\\(\\)") ||
				upgradeStep.startsWith("UpgradeModulesFactory.") ||
				upgradeStep.startsWith("UpgradeProcessFactory.")) {

				return true;
			}
		}

		return false;
	}

	private int _getUpgradeProcessCount(
		String absolutePath, List<String> imports, String packageName,
		List<String> upgradeSteps) {

		int upgradeProcessCount = 0;

		for (String upgradeStep : upgradeSteps) {
			Matcher matcher = _classNamePattern.matcher(upgradeStep);

			if (!matcher.find()) {
				continue;
			}

			String className = StringUtil.removeChars(
				matcher.group(1), CharPool.NEW_LINE, CharPool.SPACE,
				CharPool.TAB);

			if (className.equals("UpgradeProcess")) {
				upgradeProcessCount++;

				continue;
			}

			if (!className.contains(StringPool.PERIOD)) {
				for (String importName : imports) {
					if (importName.endsWith(StringPool.PERIOD + className)) {
						className = importName;

						break;
					}
				}
			}

			if (!className.contains(StringPool.PERIOD)) {
				className = StringBundler.concat(
					packageName, StringPool.PERIOD, className);
			}

			File file = JavaSourceUtil.getJavaFile(
				className, SourceUtil.getRootDirName(absolutePath),
				getBundleSymbolicNamesMap(absolutePath));

			if ((file == null) || !file.exists() ||
				!isUpgradeProcess(
					file.getAbsolutePath(), FileUtil.read(file))) {

				continue;
			}

			upgradeProcessCount++;
		}

		return upgradeProcessCount;
	}

	private static final Pattern _classNamePattern = Pattern.compile(
		"^new ([\\s\\w.]+)\\(");

}