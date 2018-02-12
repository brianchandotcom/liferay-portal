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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PackageinfoBNDExportPackageCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("/src/main/resources/") &&
			!_hasBNDExportPackage(fileName)) {

			return null;
		}

		return content;
	}

	private List<String> _getBNDExportPackages(String fileName)
		throws Exception {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return Collections.emptyList();
		}

		Matcher matcher = _exportsPattern.matcher(bndSettings.getContent());

		if (!matcher.find()) {
			return Collections.emptyList();
		}

		List<String> exportPackages = new ArrayList<>();

		for (String line : StringUtil.splitLines(matcher.group(3))) {
			line = StringUtil.trim(line);

			if (Validator.isNull(line) || line.equals("\\")) {
				continue;
			}

			line = StringUtil.removeSubstring(line, ",\\");

			if (line.indexOf(StringPool.SEMICOLON) != -1) {
				line = line.substring(0, line.indexOf(StringPool.SEMICOLON));
			}

			exportPackages.add(line.replace(CharPool.PERIOD, CharPool.SLASH));
		}

		return exportPackages;
	}

	private boolean _hasBNDExportPackage(String fileName) throws Exception {
		List<String> bndExportPackages = _getBNDExportPackages(fileName);

		for (String bndExportPackage : bndExportPackages) {
			String suffix =
				"/src/main/resources/" + bndExportPackage + "/packageinfo";

			if (fileName.endsWith(suffix)) {
				return true;
			}
		}

		return false;
	}

	private final Pattern _exportsPattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);

}