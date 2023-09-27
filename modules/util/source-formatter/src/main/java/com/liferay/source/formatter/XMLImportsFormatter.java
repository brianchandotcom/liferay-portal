/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLImportsFormatter extends BaseImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		return createJavaImportPackage(line);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packagePath,
			String className)
		throws IOException {

		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			String imports = matcher.group(1);

			if (imports.endsWith("\n\n")) {
				imports = imports.substring(0, imports.length() - 1);
			}

			String newImports = sortAndGroupImports(imports);

			if (!imports.equals(newImports)) {
				return StringUtil.replace(content, imports, newImports);
			}
		}

		return content;
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"<\\!\\[CDATA\\[\n((^[ \t]*import\\s+.*;\n+)+)", Pattern.MULTILINE);

}