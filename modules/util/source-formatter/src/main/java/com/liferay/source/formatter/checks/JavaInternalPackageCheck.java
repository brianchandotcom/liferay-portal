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

import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
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

		if (!absolutePath.contains("-web/src/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return javaTerm.getContent();
		}

		if (packageName.contains(".internal.") ||
			packageName.endsWith(".internal")) {

			return javaClass.getContent();
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return javaClass.getContent();
		}

		List<String> exportPackageNames = bndSettings.getExportPackageNames();

		if (!exportPackageNames.contains(packageName)) {
			addMessage(
				fileName,
				"Classes that are not exported should be in 'internal' " +
					"package");
		}

		return javaClass.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
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

}