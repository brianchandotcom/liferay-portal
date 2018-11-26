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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CompatClassImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (isPortalSource() || isSubrepository() ||
			absolutePath.contains("/ext-") ||
			absolutePath.contains("/portal-compat-shared/")) {

			return content;
		}

		return _fixCompatClassImports(content);
	}

	private String _fixCompatClassImports(String content) throws IOException {
		Map<String, String> compatClassNamesMap = _getCompatClassNamesMap();

		for (Map.Entry<String, String> entry : compatClassNamesMap.entrySet()) {
			String compatClassName = entry.getKey();

			String extendedClassName = entry.getValue();

			Pattern pattern = Pattern.compile(extendedClassName + "\\W");

			while (true) {
				Matcher matcher = pattern.matcher(content);

				if (!matcher.find()) {
					break;
				}

				content =
					content.substring(0, matcher.start()) + compatClassName +
						content.substring(matcher.end() - 1);
			}
		}

		return content;
	}

	private synchronized Map<String, String> _getCompatClassNamesMap()
		throws IOException {

		if (_compatClassNamesMap != null) {
			return _compatClassNamesMap;
		}

		_compatClassNamesMap = new HashMap<>();

		String[] includes = {
			"**/portal-compat-shared/src/com/liferay/compat/**/*.java"
		};

		String baseDirName = getBaseDirName();

		List<String> fileNames = new ArrayList<>();

		for (int i = 0; i < ToolsUtil.PLUGINS_MAX_DIR_LEVEL; i++) {
			File sharedDir = new File(baseDirName + "shared");

			if (sharedDir.exists()) {
				fileNames = getFileNames(baseDirName, new String[0], includes);

				break;
			}

			baseDirName = baseDirName + "../";
		}

		for (String fileName : fileNames) {
			File file = new File(fileName);

			String content = FileUtil.read(file);

			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			fileName = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.PERIOD);

			int pos = fileName.indexOf("com.");

			String compatClassName = fileName.substring(pos);

			compatClassName = compatClassName.substring(
				0, compatClassName.length() - 5);

			String extendedClassName = StringUtil.replace(
				compatClassName, "compat.", StringPool.BLANK);

			if (content.contains("extends " + extendedClassName)) {
				_compatClassNamesMap.put(compatClassName, extendedClassName);
			}
		}

		return _compatClassNamesMap;
	}

	private Map<String, String> _compatClassNamesMap;

}