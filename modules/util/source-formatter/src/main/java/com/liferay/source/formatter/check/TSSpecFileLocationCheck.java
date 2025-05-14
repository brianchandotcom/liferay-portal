/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;

import java.io.File;

/**
 * @author Alan Huang
 */
public class TSSpecFileLocationCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/modules/test/playwright/tests/") ||
			!fileName.endsWith(".spec.ts")) {

			return content;
		}

		String path = absolutePath;

		while (true) {
			int x = path.lastIndexOf(StringPool.SLASH);

			path = absolutePath.substring(0, x);

			if (path.endsWith("/modules/test/playwright/tests")) {
				addMessage(
					fileName,
					"*.spec.ts file should be inside a folder that contains " +
						"config.ts");

				return content;
			}

			File file = new File(path + "/config.ts");

			if (file.exists()) {
				return content;
			}
		}
	}

}