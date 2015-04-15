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

package com.liferay.portal.tools.sourceformatter;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FTLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return trimContent(content, false);
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = new String[] {
			"**\\journal\\dependencies\\template.ftl",
			"**\\service\\builder\\dependencies\\props.ftl"
		};
		String[] includes = new String[] {"**\\*.ftl"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

}