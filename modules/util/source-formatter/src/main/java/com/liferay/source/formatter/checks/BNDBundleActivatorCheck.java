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
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

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