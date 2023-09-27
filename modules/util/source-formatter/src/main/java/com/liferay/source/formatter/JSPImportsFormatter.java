/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class JSPImportsFormatter extends BaseImportsFormatter {

	public static List<String> getImportNames(String content) {
		List<String> importNames = new ArrayList<>();

		Matcher matcher = _jspImportPattern.matcher(content);

		while (matcher.find()) {
			importNames.add(matcher.group(1));
		}

		return importNames;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _jspImportPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		matcher = _jspTaglibPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		return null;
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

		content = StringUtil.replaceFirst(content, imports, newImports + "\n");

		return StringUtil.trimTrailing(content);
	}

	protected String getImports(String content, Pattern importPattern) {
		Matcher matcher = importPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private static final Pattern _jspImportPattern = Pattern.compile(
		"import=\"([^\\s\"]+)\"");
	private static final Pattern _jspTaglibPattern = Pattern.compile(
		"uri=\"http://([^\\s\"]+)\"");

}