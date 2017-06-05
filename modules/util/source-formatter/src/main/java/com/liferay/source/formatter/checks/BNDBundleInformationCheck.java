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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

/**
 * @author Hugo Huijser
 */
public class BNDBundleInformationCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/bnd.bnd") &&
			!absolutePath.contains("/testIntegration/") &&
			!absolutePath.contains("/third-party/")) {

			_checkBundleName(fileName, absolutePath, content);

			String bundleVersion = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-Version");

			if (bundleVersion == null) {
				addMessage(
					fileName, "Missing Bundle-Version",
					"bnd_bundle_information.markdown");
			}
		}

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

			strippedBundleName = strippedBundleName.replaceAll(
				"Implementation$", "Impl");
			strippedBundleName = strippedBundleName.replaceAll(
				"Utilities$", "Util");

			String expectedBundleName =
				"liferay" + StringUtil.removeChars(moduleName, CharPool.DASH);

			if (!StringUtil.equalsIgnoreCase(
					strippedBundleName, expectedBundleName)) {

				addMessage(
					fileName, "Incorrect Bundle-Name '" + bundleName + "'",
					"bnd_bundle_information.markdown");
			}
		}
		else {
			addMessage(
				fileName, "Missing Bundle-Name",
				"bnd_bundle_information.markdown");
		}

		if (moduleName.contains("-import-") ||
			moduleName.contains("-private-")) {

			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-SymbolicName");

		if (bundleSymbolicName != null) {
			String expectedBundleSymbolicName =
				"com.liferay." +
					StringUtil.replace(
						moduleName, CharPool.DASH, CharPool.PERIOD);

			if (!bundleSymbolicName.equals(expectedBundleSymbolicName)) {
				addMessage(
					fileName,
					"Incorrect Bundle-SymbolicName '" + bundleSymbolicName +
						"'",
					"bnd_bundle_information.markdown");
			}
		}
		else {
			addMessage(
				fileName, "Missing Bundle-SymbolicName",
				"bnd_bundle_information.markdown");
		}
	}

}