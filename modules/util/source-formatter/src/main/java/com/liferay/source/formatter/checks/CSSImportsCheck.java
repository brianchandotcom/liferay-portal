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

import com.liferay.portal.kernel.util.StringBundler;
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
			String fileName, String absolutePath, String content)
		throws Exception {

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

	private final Pattern _importsPattern = Pattern.compile(
		"(@import \".*\";(\n|\\Z))+", Pattern.MULTILINE);

}