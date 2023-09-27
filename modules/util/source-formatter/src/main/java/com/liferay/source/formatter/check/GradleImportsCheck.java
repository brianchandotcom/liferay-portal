/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.GradleImportsFormatter;
import com.liferay.source.formatter.parser.GradleFile;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class GradleImportsCheck extends BaseGradleFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, GradleFile gradleFile,
			String content)
		throws IOException {

		String importsBlock = gradleFile.getImportsBlock();

		if (Validator.isNull(importsBlock)) {
			return content;
		}

		ImportsFormatter importsFormatter = new GradleImportsFormatter();

		return StringUtil.replaceFirst(
			content, importsBlock,
			importsFormatter.format(importsBlock, null, null));
	}

}