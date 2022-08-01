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

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(
			new String[] {"**/docroot/dtd/**", "**/lportal.properties"},
			getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		if (isPortalSource() || isSubrepository()) {
			return _INCLUDES;
		}

		return new String[] {
			"**/liferay-plugin-package.properties", "**/portal.properties",
			"**/portal-ext.properties", "**/portlet.properties",
			"**/poshi.properties", "**/service.properties",
			"**/source-formatter.properties", "**/test.properties"
		};
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith(".properties") && SourceUtil.isXML(content)) {
			return file;
		}

		return super.format(file, fileName, absolutePath, content);
	}

	private static final String[] _INCLUDES = {
		"**/*.properties", "**/.eslintignore", "**/.prettierignore"
	};

}