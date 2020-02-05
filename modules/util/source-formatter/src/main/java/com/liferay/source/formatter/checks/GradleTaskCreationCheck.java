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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.GradleFile;

/**
 * @author Peter Shin
 */
public class GradleTaskCreationCheck extends BaseGradleFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, GradleFile gradleFile,
		String content) {

		if (absolutePath.contains("/project-templates-")) {
			return content;
		}

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (line.matches("^task\\s+.*$") && line.contains("{")) {
				addMessage(
					fileName,
					"The task should be declared in a separate line before " +
						"the closure",
					getLineNumber(content, content.indexOf(line)));
			}
		}

		return content;
	}

}