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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceFormatterFileCheck extends BaseFileCheck {

	public PropertiesSourceFormatterFileCheck(boolean hasPrivateAppsDir)
		throws Exception {

		_hasPrivateAppsDir = hasPrivateAppsDir;

		_projectPathPrefix = getProjectPathPrefix();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/source-formatter.properties")) {
			_checkSourceFormatterProperties(fileName);
		}

		return content;
	}

	private void _checkSourceFormatterProperties(String fileName)
		throws Exception {

		int level = ToolsUtil.PLUGINS_MAX_DIR_LEVEL;

		if (isPortalSource()) {
			level = ToolsUtil.PORTAL_MAX_DIR_LEVEL;
		}

		Properties properties = new Properties();

		InputStream inputStream = new FileInputStream(fileName);

		properties.load(inputStream);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (!key.endsWith("excludes")) {
				continue;
			}

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			List<String> propertyFileNames = ListUtil.fromString(
				value, StringPool.COMMA);

			for (String propertyFileName : propertyFileNames) {
				if (propertyFileName.contains(StringPool.STAR) ||
					propertyFileName.endsWith("-ext.properties") ||
					(isPortalSource() && !_hasPrivateAppsDir &&
					 isModulesApp(
						 propertyFileName, _projectPathPrefix, true))) {

					continue;
				}

				int pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				File file = getFile(propertyFileName, level);

				if (file == null) {
					addMessage(
						fileName,
						"Property value '" + propertyFileName +
							"' points to file that does not exist");
				}
			}
		}
	}

	private final boolean _hasPrivateAppsDir;
	private final String _projectPathPrefix;

}