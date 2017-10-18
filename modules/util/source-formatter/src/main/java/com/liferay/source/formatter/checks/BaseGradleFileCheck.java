/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

import com.liferay.source.formatter.parser.GradleFile;

/**
 * @author Peter Shin
 */
public abstract class BaseGradleFileCheck
	extends BaseSourceCheck implements GradleFileCheck {

	@Override
	public String process(
			String fileName, String absolutePath, GradleFile gradleFile,
			String content)
		throws Exception {

		clearSourceFormatterMessages(fileName);

		return doProcess(fileName, absolutePath, gradleFile, content);
	}

	protected abstract String doProcess(
			String filename, String absolutePath, GradleFile gradleFile,
			String fileContent)
		throws Exception;

}