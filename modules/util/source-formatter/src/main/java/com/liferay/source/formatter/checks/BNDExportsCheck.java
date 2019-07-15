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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDExportsCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/bnd.bnd") ||
			absolutePath.contains("/third-party/")) {

			return content;
		}

		if (!absolutePath.contains("/testIntegration/")) {
			_checkExports(
				fileName, content, _exportContentsPattern, "-exportcontents");
			_checkExports(fileName, content, _exportsPattern, "Export-Package");
		}

		if (absolutePath.contains("/modules/apps/")) {
			_checkAllowedExportPackages(fileName, absolutePath, content);
		}

		_checkExportPackages(fileName, content);

		return content;
	}

	private void _checkAllowedExportPackages(
		String fileName, String absolutePath, String content) {

		List<String> allowedExportPackageDirNames = getAttributeValues(
			_ALLOWED_EXPORT_PACKAGE_DIR_NAMES_KEY, absolutePath);

		for (String allowedExportPackageDirName :
				allowedExportPackageDirNames) {

			if (absolutePath.contains(allowedExportPackageDirName)) {
				return;
			}
		}

		if (fileName.endsWith("/test-bnd.bnd") ||
			absolutePath.contains("-api/") ||
			absolutePath.contains("-client/") ||
			absolutePath.contains("-spi/") ||
			absolutePath.contains("-taglib/") ||
			absolutePath.contains("-test-util/") ||
			!content.contains("Export-Package")) {

			return;
		}

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		addMessage(
			fileName,
			"Exporting packages not allowed in module '" +
				absolutePath.substring(y + 1, x) + "'",
			"bnd_exports.markdown");
	}

	private void _checkExportPackages(String fileName, String content)
		throws IOException {

		List<String> exportPackages = _getExportPackages(content);

		if (exportPackages.isEmpty()) {
			return;
		}

		int i = fileName.lastIndexOf("/");

		String srcMainDirLocation = fileName.substring(0, i) + "/src/main/";

		File srcMainDir = new File(srcMainDirLocation);

		if (!srcMainDir.exists()) {
			return;
		}

		for (String exportPackage : exportPackages) {
			String exportPackagePath = StringUtil.replace(
				exportPackage, CharPool.PERIOD, CharPool.SLASH);

			File resourcesDir = new File(
				StringBundler.concat(
					srcMainDirLocation, "resources/", exportPackagePath));

			File[] resourcesFiles = resourcesDir.listFiles(
				new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						String fileName = pathname.getName();

						if (fileName.startsWith(".lfrbuild-") ||
							fileName.equals("packageinfo")) {

							return false;
						}

						return pathname.isFile();
					}

				});

			File srcDir = new File(
				StringBundler.concat(
					srcMainDirLocation, "java/", exportPackagePath));

			File[] srcFiles = srcDir.listFiles(
				new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						return pathname.isFile();
					}

				});

			File packageinfoFile = new File(
				StringBundler.concat(
					srcMainDirLocation, "resources/", exportPackagePath,
					"/packageinfo"));

			if (ArrayUtil.isNotEmpty(resourcesFiles) ||
				ArrayUtil.isNotEmpty(srcFiles)) {

				if (!packageinfoFile.exists()) {
					addMessage(
						fileName, "Added packageinfo for " + exportPackage);

					FileUtil.write(packageinfoFile, "version 1.0.0");
				}
			}
			else if (exportPackage.startsWith("com.liferay.")) {
				File importedFilesPropertiesFile = new File(
					fileName.substring(0, i) + "/imported-files.properties");

				if (!importedFilesPropertiesFile.exists()) {
					addMessage(
						fileName,
						"Unneeded/incorrect Export-Package: " + exportPackage);
				}
			}
		}
	}

	private void _checkExports(
		String fileName, String content, Pattern pattern,
		String definitionKey) {

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-SymbolicName");

		if ((bundleSymbolicName == null) ||
			bundleSymbolicName.endsWith(".compat")) {

			return;
		}

		Matcher matcher = _apiOrServiceBundleSymbolicNamePattern.matcher(
			bundleSymbolicName);

		bundleSymbolicName = matcher.replaceAll(StringPool.BLANK);

		matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String[] lines = StringUtil.splitLines(matcher.group(2));

		for (int i = 0; i < lines.length; i++) {
			String line = StringUtil.removeChar(
				StringUtil.trim(lines[i]), CharPool.BACK_SLASH);

			if (Validator.isNull(line) || !line.startsWith("com.liferay.") ||
				line.startsWith(bundleSymbolicName)) {

				continue;
			}

			StringBundler sb = new StringBundler(6);

			sb.append(definitionKey);
			sb.append(" '");
			sb.append(line);
			sb.append("' should match Bundle-SymbolicName '");
			sb.append(bundleSymbolicName);
			sb.append("'");

			addMessage(
				fileName, sb.toString(),
				getLineNumber(content, matcher.start(2)) + i);
		}
	}

	private List<String> _getExportPackages(String content) {
		Matcher matcher = _exportsPattern.matcher(content);

		if (!matcher.find()) {
			return Collections.emptyList();
		}

		List<String> exportPackages = new ArrayList<>();

		for (String line : StringUtil.splitLines(matcher.group(3))) {
			line = StringUtil.trim(line);

			if (Validator.isNull(line) || line.equals("\\")) {
				continue;
			}

			line = StringUtil.removeSubstring(line, ",\\");

			if (line.indexOf(StringPool.SEMICOLON) != -1) {
				line = line.substring(0, line.indexOf(StringPool.SEMICOLON));
			}

			exportPackages.add(line);
		}

		return exportPackages;
	}

	private static final String _ALLOWED_EXPORT_PACKAGE_DIR_NAMES_KEY =
		"allowedExportPackageDirNames";

	private static final Pattern _apiOrServiceBundleSymbolicNamePattern =
		Pattern.compile("\\.(api|service)$");
	private static final Pattern _exportContentsPattern = Pattern.compile(
		"\n-exportcontents:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _exportsPattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);

}