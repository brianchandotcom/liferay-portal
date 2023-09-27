/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

/**
 * @author Hugo Huijser
 */
public class BNDBundleInformationCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/bnd.bnd") ||
			absolutePath.contains("/testIntegration/") ||
			absolutePath.contains("/third-party/")) {

			return content;
		}

		_checkBundleName(fileName, absolutePath, content);
		_checkBundleVersion(fileName, absolutePath, content);

		return content;
	}

	private void _checkBundleName(
		String fileName, String absolutePath, String content) {

		String moduleName = BNDSourceUtil.getModuleName(absolutePath);

		String bundleName = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-Name");

		if (bundleName != null) {
			String strippedBundleName = StringUtil.removeChars(
				bundleName, CharPool.DASH, CharPool.SPACE);

			String expectedBundleName = "liferay-" + moduleName;

			expectedBundleName = expectedBundleName.replaceAll(
				"(?<!portal)-impl($|-)", "implementation");
			expectedBundleName = expectedBundleName.replaceAll(
				"(?<!portal)-util($|-)", "utilities");

			expectedBundleName = StringUtil.removeChars(
				expectedBundleName, CharPool.DASH);

			if (!StringUtil.equalsIgnoreCase(
					strippedBundleName, expectedBundleName)) {

				addMessage(
					fileName, "Incorrect Bundle-Name '" + bundleName + "'");
			}
		}
		else {
			addMessage(fileName, "Missing Bundle-Name");
		}

		if (moduleName.endsWith("-import") || moduleName.contains("-import-") ||
			moduleName.contains("-private-")) {

			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-SymbolicName");

		if (bundleSymbolicName != null) {
			moduleName = StringUtil.replace(
				moduleName, CharPool.DASH, CharPool.PERIOD);

			String expectedBundleSymbolicName = "com.liferay." + moduleName;

			if (!bundleSymbolicName.equals(expectedBundleSymbolicName)) {
				addMessage(
					fileName,
					"Incorrect Bundle-SymbolicName '" + bundleSymbolicName +
						"'");
			}
		}
		else {
			addMessage(fileName, "Missing Bundle-SymbolicName");
		}
	}

	private void _checkBundleVersion(
		String fileName, String absolutePath, String content) {

		String bundleVersion = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-Version");

		if (bundleVersion == null) {
			addMessage(fileName, "Missing Bundle-Version");
		}
		else if (!bundleVersion.matches("^\\d+\\.\\d+\\.\\d+$")) {
			addMessage(fileName, "Invalid Bundle-Version: " + bundleVersion);
		}
		else if (absolutePath.endsWith("-test/bnd.bnd") &&
				 !bundleVersion.equals("1.0.0")) {

			addMessage(
				fileName,
				"'Bundle-Version' for *-test modules should always be " +
					"'1.0.0', since we do not publish these");
		}
	}

}