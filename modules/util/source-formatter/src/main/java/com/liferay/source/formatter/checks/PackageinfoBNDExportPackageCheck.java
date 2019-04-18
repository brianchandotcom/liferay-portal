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
import com.liferay.source.formatter.BNDSettings;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public class PackageinfoBNDExportPackageCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
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
				exportPackageName.replace(CharPool.PERIOD, CharPool.SLASH),
				"/packageinfo");

			if (fileName.endsWith(suffix)) {
				return true;
			}
		}

		return false;
	}

}