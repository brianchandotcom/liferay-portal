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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaPackagePathCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.isAnonymous()) {
			return javaTerm.getContent();
		}

		String packageName = javaClass.getPackageName();

		if (Validator.isNull(packageName)) {
			addMessage(fileName, "Missing package");

			return javaTerm.getContent();
		}

		_checkPackageName(
			fileName, absolutePath, packageName, javaClass.getName());

		if (isModulesFile(absolutePath) && !isModulesApp(absolutePath, true)) {
			_checkModulePackageName(fileName, packageName);
		}

		String className = javaClass.getName();

		if (className.startsWith("Base")) {
			return javaTerm.getContent();
		}

		List<String> expectedInternalImplementsDataEntries = getAttributeValues(
			_EXPECTED_INTERNAL_IMPLEMENTS_DATA_KEY, absolutePath);

		for (String expectedInternalImplementsDataEntry :
				expectedInternalImplementsDataEntries) {

			String[] array = StringUtil.split(
				expectedInternalImplementsDataEntry, CharPool.COLON);

			if (array.length == 2) {
				_checkPackageName(
					fileName, javaClass.getImplementedClassNames(), array[0],
					packageName, array[1], true);
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkModulePackageName(String fileName, String packageName)
		throws IOException {

		if (!packageName.startsWith("com.liferay")) {
			return;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");

		if (!bundleSymbolicName.startsWith("com.liferay")) {
			return;
		}

		bundleSymbolicName = bundleSymbolicName.replaceAll(
			"\\.(api|service|test)$", StringPool.BLANK);

		if (packageName.contains(bundleSymbolicName)) {
			return;
		}

		bundleSymbolicName = bundleSymbolicName.replaceAll(
			"\\.impl$", ".internal");

		if (!packageName.contains(bundleSymbolicName)) {
			addMessage(
				fileName,
				"Package should follow Bundle-SymbolicName specified in " +
					bndSettings.getFileName());
		}
	}

	private void _checkPackageName(
			String fileName, List<String> implementedClassNames,
			String implementedClassName, String packageName,
			String expectedPackageName, boolean internal)
		throws IOException {

		if (!implementedClassNames.contains(implementedClassName)) {
			return;
		}

		if (internal && !packageName.contains(".internal.") &&
			!packageName.endsWith(".internal")) {

			addMessage(
				fileName,
				StringBundler.concat(
					"Class implementing '", implementedClassName,
					"' should be in 'internal' package"));
		}

		if (packageName.endsWith(expectedPackageName)) {
			return;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");

		Matcher matcher = _apiOrServiceBundleSymbolicNamePattern.matcher(
			bundleSymbolicName);

		bundleSymbolicName = matcher.replaceAll(StringPool.BLANK);

		if (bundleSymbolicName.endsWith(expectedPackageName)) {
			return;
		}

		int x = -1;

		while (true) {
			x = expectedPackageName.indexOf(".", x + 1);

			if (x == -1) {
				break;
			}

			if (bundleSymbolicName.endsWith(
					expectedPackageName.substring(0, x))) {

				expectedPackageName = expectedPackageName.substring(x + 1);

				break;
			}
		}

		if (!packageName.endsWith(expectedPackageName)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Package for class implementing '", implementedClassName,
					"' should end with '", expectedPackageName, "'"));
		}
	}

	private void _checkPackageName(
		String fileName, String absolutePath, String packageName,
		String className) {

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String filePath = StringUtil.replace(
			fileName.substring(0, pos), CharPool.SLASH, CharPool.PERIOD);

		if (!filePath.endsWith(packageName)) {
			addMessage(
				fileName,
				"The declared package '" + packageName +
					"' does not match the expected package");

			return;
		}

		if (packageName.matches(".*\\.impl\\.([\\w.]+\\.)?internal") ||
			packageName.matches(".*\\.internal\\.([\\w.]+\\.)?impl")) {

			addMessage(
				fileName,
				"Do not use both 'impl' and 'internal' in the package");
		}

		List<String> allowedInternalPackageDirNames = getAttributeValues(
			_ALLOWED_INTERNAL_PACKAGE_DIR_NAMES_KEY, absolutePath);

		for (String allowedInternalPackageDirName :
				allowedInternalPackageDirNames) {

			if (absolutePath.contains(allowedInternalPackageDirName)) {
				return;
			}
		}

		if (absolutePath.contains("-api/src/")) {
			Matcher matcher = _internalPackagePattern.matcher(packageName);

			if (matcher.find()) {
				addMessage(
					fileName,
					"Do not use '" + matcher.group(1) +
						"' package in API module");
			}

			if (packageName.contains(".api.") || packageName.endsWith(".api")) {
				addMessage(
					fileName,
					"Do not use 'api' in the package for classes in the API " +
						"module");
			}
		}

		if (className.endsWith("OSGiCommands") &&
			!packageName.endsWith(".osgi.commands")) {

			addMessage(
				fileName,
				"Class '" + className +
					"' should be in package ending with '.osgi.commands'");
		}

		if (className.matches(".*(?<!Display)Context") &&
			packageName.endsWith(".display.context")) {

			addMessage(
				fileName,
				"The name of Class '" + className +
					"' should be ending with 'DisplayContext'");
		}

		if (isModulesFile(absolutePath) &&
			className.equals("ServletContextUtil") &&
			!packageName.contains(".internal")) {

			addMessage(
				fileName,
				"Class '" + className + "' should be in 'internal' package");
		}
	}

	private static final String _ALLOWED_INTERNAL_PACKAGE_DIR_NAMES_KEY =
		"allowedInternalPackageDirNames";

	private static final String _EXPECTED_INTERNAL_IMPLEMENTS_DATA_KEY =
		"expectedInternalImplementsData";

	private static final Pattern _apiOrServiceBundleSymbolicNamePattern =
		Pattern.compile("\\.(api|service)$");
	private static final Pattern _internalPackagePattern = Pattern.compile(
		"\\.(impl|internal)(\\.|\\Z)");

}