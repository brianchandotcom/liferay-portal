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
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaInternalPackageCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.contains("/modules/apps/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return content;
		}

		String packageName = JavaSourceUtil.getPackageName(content);

		if (packageName.contains(".internal.") ||
			packageName.endsWith(".internal")) {

			return content;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return content;
		}

		List<String> exportPackageNames = bndSettings.getExportPackageNames();

		if (!exportPackageNames.contains(packageName)) {
			addMessage(
				fileName,
				"Classes that are not exported should be in 'internal' " +
					"package");
		}

		return content;
	}

}