/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaInternalPackageCheck extends BaseJavaTermCheck {

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

		if (javaClass.hasAnnotation("Deprecated")) {
			return javaClass.getContent();
		}

		String packageName = javaClass.getPackageName();

		if (packageName == null) {
			return javaClass.getContent();
		}

		if (packageName.contains(".impl.") || packageName.endsWith(".impl")) {
			_checkImplPackageName(fileName, packageName);

			return javaClass.getContent();
		}

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			packageName.contains(".internal.") ||
			packageName.endsWith(".internal")) {

			return javaClass.getContent();
		}

		if (absolutePath.contains("-service/src/")) {
			_checkServiceClasses(fileName, packageName);
		}
		else if (absolutePath.contains("-web/src/")) {
			_checkExportedClasses(fileName, packageName);
		}

		return javaClass.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkExportedClasses(String fileName, String packageName)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		List<String> exportPackageNames = bndSettings.getExportPackageNames();

		if (!exportPackageNames.contains(packageName)) {
			addMessage(
				fileName,
				"Classes that are not exported should be in 'internal' " +
					"package");
		}
	}

	private void _checkImplPackageName(String fileName, String packageName)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");

		if (bundleSymbolicName.endsWith(".impl") &&
			packageName.contains(bundleSymbolicName)) {

			addMessage(
				fileName,
				"Use 'internal' instead of 'impl' in package '" + packageName +
					"'");
		}
	}

	private void _checkServiceClasses(String fileName, String packageName)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if ((bndSettings == null) ||
			!GetterUtil.getBoolean(
				BNDSourceUtil.getDefinitionValue(
					bndSettings.getContent(), "Liferay-Service"))) {

			return;
		}

		if (!packageName.matches(
				".*\\.(model|service)\\.(impl|persistence).*")) {

			addMessage(
				fileName,
				"Classes in service modules should be in 'internal' package");
		}
	}

}