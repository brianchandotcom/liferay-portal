/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Hugo Huijser
 */
public class BNDRangeCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int pos = content.indexOf("\"${range");

		if (pos != -1) {
			addMessage(
				fileName, "Do not use range expression",
				getLineNumber(content, pos));
		}

		return content;
	}

}