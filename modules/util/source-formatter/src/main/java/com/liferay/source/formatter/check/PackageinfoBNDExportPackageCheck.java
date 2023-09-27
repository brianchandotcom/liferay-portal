/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public class PackageinfoBNDExportPackageCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.contains("/src/main/resources/") &&
			!_hasBNDExportPackage(fileName)) {

			return null;
		}

		return content;
	}

	private boolean _hasBNDExportPackage(String fileName) throws IOException {
		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return false;
		}

		for (String exportPackageName : bndSettings.getExportPackageNames()) {
			String suffix = StringBundler.concat(
				"/src/main/resources/",
				StringUtil.replace(
					exportPackageName, CharPool.PERIOD, CharPool.SLASH),
				"/packageinfo");

			if (fileName.endsWith(suffix)) {
				return true;
			}
		}

		return false;
	}

}