/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CSSImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			String imports = StringUtil.trim(matcher.group());

			String[] lines = StringUtil.splitLines(imports);

			Arrays.sort(lines);

			StringBundler sb = new StringBundler(lines.length * 2);

			for (String line : lines) {
				sb.append(line);
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			String newImports = sb.toString();

			if (!imports.equals(newImports)) {
				return StringUtil.replaceFirst(
					content, imports, newImports, matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"(@import \".*\";(\n|\\Z))+", Pattern.MULTILINE);

}