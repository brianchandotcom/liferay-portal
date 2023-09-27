/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class JavaImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		String packageName = JavaSourceUtil.getPackageName(content);

		content = importsFormatter.format(
			content, packageName, JavaSourceUtil.getClassName(fileName));

		return StringUtil.replace(content, ";\n/**", ";\n\n/**");
	}

}