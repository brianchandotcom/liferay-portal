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