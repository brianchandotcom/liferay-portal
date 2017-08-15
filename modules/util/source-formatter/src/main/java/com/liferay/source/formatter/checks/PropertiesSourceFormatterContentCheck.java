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

/**
 * @author Peter Shin
 */
public class PropertiesSourceFormatterContentCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_projectPathPrefix = getProjectPathPrefix();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/source-formatter.properties")) {
			return content;
		}

		if (isPortalSource() &&
			isModulesApp(absolutePath, _projectPathPrefix, true)) {

			return content;
		}

		return _checkSourceFormatterContent(content);
	}

	private String _checkSourceFormatterContent(String content)
		throws Exception {

		for (String[] array : _CONVERTED_KEYS) {
			content = StringUtil.replace(content, array[0], array[1]);
		}

		return content;
	}

	private static final String[][] _CONVERTED_KEYS = {
		new String[] {
			"blob/master/portal-impl/src/source-formatter.properties",
			"blob/master/source-formatter.properties"
		}
	};

	private String _projectPathPrefix;

}