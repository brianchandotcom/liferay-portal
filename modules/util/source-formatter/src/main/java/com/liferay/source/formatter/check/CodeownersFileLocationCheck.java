/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Hugo Huijser
 */
public class CodeownersFileLocationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/.github/CODEOWNERS")) {
			addMessage(
				fileName,
				"CODEOWNERS file should be located in directory '.github'");
		}

		return content;
	}

}