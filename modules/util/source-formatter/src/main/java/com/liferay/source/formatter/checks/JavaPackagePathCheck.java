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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaPackagePathCheck extends BaseFileCheck {

	public void setAllowedInternalPackageDirNames(
		String allowedInternalPackageDirNames) {

		Collections.addAll(
			_allowedInternalPackageDirNames,
			StringUtil.split(allowedInternalPackageDirNames));
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String packageName = JavaSourceUtil.getPackageName(content);

		if (Validator.isNull(packageName)) {
			addMessage(fileName, "Missing package");

			return content;
		}

		_checkPackageName(fileName, absolutePath, packageName);

		if (isModulesFile(absolutePath) && !isModulesApp(absolutePath, true)) {
			_checkModulePackageName(fileName, packageName);
		}

		return content;
	}

	private void _checkModulePackageName(String fileName, String packageName)
		throws Exception {

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
					bndSettings.getFileName(),
				"package.markdown");
		}
	}

	private void _checkPackageName(
		String fileName, String absolutePath, String packageName) {

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String filePath = StringUtil.replace(
			fileName.substring(0, pos), CharPool.SLASH, CharPool.PERIOD);

		if (!filePath.endsWith(packageName)) {
			addMessage(
				fileName,
				"The declared package '" + packageName +
					"' does not match the expected package",
				"package.markdown");

			return;
		}

		if (packageName.matches(".*\\.internal\\.([\\w.]+\\.)?impl")) {
			addMessage(
				fileName, "Do not use 'impl' inside 'internal'",
				"package.markdown");
		}

		for (String allowedInternalPackageDirName :
				_allowedInternalPackageDirNames) {

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
						"' package in API module",
					"package.markdown");
			}
		}
	}

	private final List<String> _allowedInternalPackageDirNames =
		new ArrayList<>();
	private final Pattern _internalPackagePattern = Pattern.compile(
		"\\.(impl|internal)(\\.|\\Z)");

}