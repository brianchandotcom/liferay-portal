/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;

import java.io.File;

/**
 * @author Alan Huang
 */
public class TSConfigFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/modules/test/playwright/tests/") ||
			!fileName.endsWith("/config.ts")) {

			return content;
		}

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		String playwrightTestDirLocation = absolutePath.substring(0, x);

		File file = new File(playwrightTestDirLocation + "/test.properties");

		if (!file.exists()) {
			addMessage(
				fileName,
				"Missing test.properties in " + playwrightTestDirLocation);
		}

		return content;
	}

}