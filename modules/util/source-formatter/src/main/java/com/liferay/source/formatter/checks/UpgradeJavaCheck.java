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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class UpgradeJavaCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		return _fixImports(javaClass, content);
	}

	private String _fixImports(JavaClass javaClass, String content)
		throws Exception {

		Map<String, String> importsMap = _getImportsMap();

		for (String importName : javaClass.getImportNames()) {
			String newImportName = importsMap.get(importName);

			if (newImportName != null) {
				return StringUtil.replace(
					content, StringBundler.concat("import ", importName, ";"),
					StringBundler.concat("import ", newImportName, ";"));
			}
		}

		return content;
	}

	private synchronized Map<String, String> _getImportsMap() throws Exception {
		if (_importsMap == null) {
			_importsMap = _getMap("/java/imports.txt");
		}

		return _importsMap;
	}

	private Map<String, String> _getMap(String fileName) throws Exception {
		Map<String, String> map = new HashMap<>();

		File importsFile = SourceFormatterUtil.getFile(
			getBaseDirName(),
			SourceFormatterUtil.UPGRADE_INPUT_DATA_DIRECTORY_NAME + fileName,
			getMaxDirLevel());

		if (importsFile == null) {
			return map;
		}

		String[] lines = StringUtil.splitLines(FileUtil.read(importsFile));

		String oldValue = null;

		for (String line : lines) {
			if (line.matches("\\d+\\.old:.+")) {
				oldValue = line.substring(line.indexOf(":") + 1);
			}
			else if (line.matches("\\d+\\.new:.+") && (oldValue != null)) {
				map.put(oldValue, line.substring(line.indexOf(":") + 1));

				oldValue = null;
			}
		}

		return map;
	}

	private Map<String, String> _importsMap;

}