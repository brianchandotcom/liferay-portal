/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;

/**
 * @author Hugo Huijser
 */
public class Java2HTMLCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String className = JavaSourceUtil.getClassName(fileName);

		if (content.contains(className + ".java.html")) {
			addMessage(fileName, "Java2HTML");
		}

		return content;
	}

}