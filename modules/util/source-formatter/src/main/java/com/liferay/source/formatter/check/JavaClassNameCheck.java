/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaClassNameCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.isAnonymous() || javaClass.hasAnnotation("Deprecated")) {
			return javaTerm.getContent();
		}

		String packageName = javaClass.getPackageName();

		if (Validator.isNull(packageName)) {
			return javaTerm.getContent();
		}

		String className = javaClass.getName();

		_checkTypo(fileName, className, packageName, 1);

		List<String> expectedPackagePathDataEntries = getAttributeValues(
			_EXPECTED_PACKAGE_PATH_DATA_KEY, absolutePath);

		for (String expectedPackagePathDataEntry :
				expectedPackagePathDataEntries) {

			String[] array = StringUtil.split(
				expectedPackagePathDataEntry, CharPool.COLON);

			if (array.length != 2) {
				continue;
			}

			String expectedClassNameEnding = array[1];

			if (packageName.endsWith("." + array[0]) &&
				!className.endsWith(expectedClassNameEnding)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Name of class in package '", packageName,
						"' should end with '", expectedClassNameEnding, "'"));
			}
		}

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (implementedClassNames.isEmpty()) {
			return javaTerm.getContent();
		}

		List<String> enforceImplementedClassNames = getAttributeValues(
			_ENFORCE_IMPLEMENTED_CLASS_NAMES_KEY, absolutePath);

		outerLoop:
		for (String implementedClassName : enforceImplementedClassNames) {
			if (!implementedClassNames.contains(implementedClassName)) {
				continue;
			}

			for (String extendedClassName : javaClass.getExtendedClassNames()) {
				if (extendedClassName.startsWith("Base") &&
					!extendedClassName.endsWith(implementedClassName)) {

					continue outerLoop;
				}
			}

			if (!className.endsWith(implementedClassName) &&
				((implementedClassNames.size() == 1) ||
				 implementedClassName.equals("ScreenNavigationCategory"))) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Name of class implementing '", implementedClassName,
						"' should end with '", implementedClassName, "'"));
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkTypo(
		String fileName, String className, String packageName, int level) {

		StringBundler sb = new StringBundler(level + 2);

		sb.append("(.*[a-z0-9]|\\A)");

		for (int i = 0; i < level; i++) {
			sb.append("([A-Z][a-z0-9]+)");
		}

		sb.append("$");

		Pattern pattern = Pattern.compile(sb.toString());

		Matcher matcher = pattern.matcher(className);

		if (!matcher.find()) {
			return;
		}

		String classNamePart = StringUtil.toLowerCase(matcher.group(2));

		String[] packageNameParts = StringUtil.split(
			packageName, CharPool.PERIOD);

		String packageNamePart =
			packageNameParts[packageNameParts.length - level];

		if (classNamePart.equals(packageNamePart)) {
			if (packageNameParts.length == 1) {
				return;
			}

			_checkTypo(fileName, className, packageName, level + 1);
		}
		else if (SourceUtil.hasTypo(classNamePart, packageNamePart)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Typo in either class name '", className, "' or package '",
					packageName, "'"));
		}
	}

	private static final String _ENFORCE_IMPLEMENTED_CLASS_NAMES_KEY =
		"enforceImplementedClassNames";

	private static final String _EXPECTED_PACKAGE_PATH_DATA_KEY =
		"expectedPackagePathData";

}