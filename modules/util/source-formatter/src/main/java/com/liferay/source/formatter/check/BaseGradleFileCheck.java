/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.GradleFile;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public abstract class BaseGradleFileCheck
	extends BaseSourceCheck implements GradleFileCheck {

	@Override
	public String process(
			String fileName, String absolutePath, GradleFile gradleFile,
			String content)
		throws IOException {

		clearSourceFormatterMessages(fileName);

		return doProcess(fileName, absolutePath, gradleFile, content);
	}

	protected abstract String doProcess(
			String fileName, String absolutePath, GradleFile gradleFile,
			String fileContent)
		throws IOException;

}