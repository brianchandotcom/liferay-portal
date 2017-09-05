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
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		String className = JavaSourceUtil.getClassName(fileName);
		String packagePath = JavaSourceUtil.getPackagePath(content);

		content = importsFormatter.format(content, packagePath, className);

		content = StringUtil.replace(content, ";\n/**", ";\n\n/**");

		Matcher matcher = _IMPORT_METHOD_PATTERN.matcher(content);

		while (matcher.find()) {
			StringBundler sb = new StringBundler();

			sb.append("Do not import method '");
			sb.append(matcher.group(1));
			sb.append("', import class '");
			sb.append(matcher.group(2));
			sb.append("' instead");

			addMessage(
				fileName, sb.toString(), getLineCount(content, matcher.end()));
		}

		return content;
	}

	private static final Pattern _IMPORT_METHOD_PATTERN = RegexUtil.getPattern(
		"\nimport static ((.*\\.(Assert|(Power)?Mockito))\\.[a-z]\\w*);");

}