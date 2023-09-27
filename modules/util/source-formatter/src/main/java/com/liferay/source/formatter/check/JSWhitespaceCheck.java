/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class JSWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = StringUtil.replace(
			content,
			new String[] {
				StringPool.TAB + "else{", StringPool.TAB + "for(",
				StringPool.TAB + "if(", StringPool.TAB + "while(",
				" function (", "){\n", "= new Array();", "= new Object();"
			},
			new String[] {
				StringPool.TAB + "else {", StringPool.TAB + "for (",
				StringPool.TAB + "if (", StringPool.TAB + "while (",
				" function(", ") {\n", "= [];", "= {};"
			});

		return super.doProcess(fileName, absolutePath, content);
	}

}