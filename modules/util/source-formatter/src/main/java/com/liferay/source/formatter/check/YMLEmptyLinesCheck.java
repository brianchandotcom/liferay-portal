/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class YMLEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = content.replaceAll("(?<!\n)(\n---\n)", "\n$1");
		content = content.replaceAll("(\n---\n)(?!\n)", "$1\n");
		content = content.replaceAll("(?<!---)\n\n(?!---)", "\n");

		return content;
	}

}