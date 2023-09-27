/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDImportsFormatter extends BaseImportsFormatter {

	public static String getImports(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(3);
		}

		return null;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		if (line.endsWith(StringPool.BACK_SLASH)) {
			line = line.substring(0, line.length() - 1);
		}

		if (line.endsWith(StringPool.COMMA)) {
			line = line.substring(0, line.length() - 1);
		}

		String importString = StringUtil.trim(line);

		if (Validator.isNull(importString)) {
			return null;
		}

		int pos = importString.indexOf(StringPool.SEMICOLON);

		if (pos != -1) {
			importString = importString.substring(0, pos);

			pos = line.indexOf(StringPool.SEMICOLON);

			line =
				line.substring(0, pos + 1) +
					_sortAttributes(line.substring(pos + 1));
		}

		return new BNDImportPackage(importString, line);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packageDir,
			String className)
		throws IOException {

		String imports = getImports(content, importPattern);

		if (Validator.isNull(imports)) {
			return content;
		}

		String newImports = sortAndGroupImports(imports);

		newImports = newImports.substring(0, newImports.length() - 1);

		newImports = StringUtil.replace(
			newImports, new String[] {"\n", "\n,\\"},
			new String[] {",\\\n", "\n\t\\"});

		if (newImports.contains(",\\\n")) {
			newImports = newImports.replaceAll("(?m)^\t*", "\t");
		}

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		return content;
	}

	private String _sortAttributes(String attributes) {
		List<String> attributeList = ListUtil.fromString(
			attributes, StringPool.SEMICOLON);

		Collections.sort(
			attributeList,
			new Comparator<String>() {

				@Override
				public int compare(String attribute1, String attribute2) {
					if (attribute1.startsWith("-") &&
						!attribute2.startsWith("-")) {

						return 1;
					}

					if (!attribute1.startsWith("-") &&
						attribute2.startsWith("-")) {

						return -1;
					}

					String attributeName1 = attribute1.replaceFirst(
						"(.+?):?=.+", "$1");

					String attributeName2 = attribute2.replaceFirst(
						"(.+?):?=.+", "$1");

					return attributeName1.compareTo(attributeName2);
				}

			});

		return ListUtil.toString(
			attributeList, StringPool.BLANK, StringPool.SEMICOLON);
	}

}