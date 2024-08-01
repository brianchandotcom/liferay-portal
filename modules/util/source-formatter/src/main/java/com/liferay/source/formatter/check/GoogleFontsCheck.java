/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class GoogleFontsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int x = content.indexOf("fonts.googleapis.com");

		if (x != -1) {
			addMessage(fileName, "Do not use Google fonts");
		}

		return content;
	}

}