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
public class JavaBaseUpgradeCallableCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		String packageName = javaClass.getPackageName();

		if (packageName == null) {
			return javaTerm.getContent();
		}

		Matcher packageNameMatcher = _packageNamePattern.matcher(packageName);

		if (!packageNameMatcher.find()) {
			return javaTerm.getContent();
		}

		Matcher runnablematcher = _runnablePattern.matcher(fileContent);

		if (runnablematcher.find()) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Do not use 'java.lang.Runnable' in '",
					packageNameMatcher.group(2),
					"' classes, use 'BaseUpgradeCallable' instead."),
				getLineNumber(fileContent, runnablematcher.start()));
		}

		List<String> importNames = javaClass.getImportNames();

		for (String importName : importNames) {
			if (importName.equals(
					"com.liferay.petra.function.UnsafeRunnable") ||
				importName.equals("java.util.concurrent.Callable")) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Do not use '", importName, "' in '",
						packageNameMatcher.group(2),
						"' classes, use 'BaseUpgradeCallable' instead."),
					getLineNumber(
						fileContent, fileContent.indexOf(importName)));
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private static final Pattern _packageNamePattern = Pattern.compile(
		"\\.internal(\\..+)?\\.(upgrade|verify)(\\.|\\Z)");
	private static final Pattern _runnablePattern = Pattern.compile(
		"\\WRunnable\\W");

}