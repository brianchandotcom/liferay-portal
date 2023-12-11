/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.io.IOException;

/**
 * @author Nícolas Moura
 */
public class UpgradeCatchAllJavaImportsCheck extends JavaImportsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("UpgradeCatchAllCheck.java")) {
			return content;
		}

		return super.doProcess(fileName, absolutePath, content);
	}

}