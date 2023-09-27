/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

/**
 * @author Alan Huang
 */
public class BNDBundleActivatorCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/bnd.bnd") &&
			!absolutePath.contains("/testIntegration/") &&
			!absolutePath.contains("/third-party/")) {

			_checkBundleActivator(fileName, content);
		}

		return content;
	}

	private void _checkBundleActivator(String fileName, String content) {
		String bundleActivator = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-Activator");

		if (bundleActivator == null) {
			return;
		}

		if (!bundleActivator.endsWith("BundleActivator")) {
			addMessage(
				fileName,
				"Incorrect Bundle-Activator, it should end with " +
					"'BundleActivator'");

			return;
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-SymbolicName");

		if (bundleSymbolicName == null) {
			return;
		}

		int x = bundleActivator.lastIndexOf(StringPool.PERIOD);
		int y = bundleActivator.lastIndexOf("BundleActivator");

		String strippedBundleActivator = bundleActivator.substring(x + 1, y);

		String strippedBundleSymbolicName = StringUtil.removeChar(
			bundleSymbolicName, CharPool.PERIOD);

		if (!StringUtil.endsWith(
				strippedBundleSymbolicName, strippedBundleActivator)) {

			addMessage(
				fileName,
				"Incorrect Bundle-Activator, it should match " +
					"'Bundle-SymbolicName'");
		}
	}

}