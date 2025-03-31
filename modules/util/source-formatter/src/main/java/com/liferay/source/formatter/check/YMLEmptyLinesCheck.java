/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class YMLEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String trimmedContent = content.trim();

		if (trimmedContent.startsWith("---")) {
			return trimmedContent.substring(3);
		}

		if (trimmedContent.endsWith("---")) {
			return trimmedContent.substring(0, trimmedContent.length() - 3);
		}

		trimmedContent = trimmedContent.replaceAll("\n\n---", "\n---");

		return trimmedContent.replaceAll("---\n\n", "---\n");
	}

}