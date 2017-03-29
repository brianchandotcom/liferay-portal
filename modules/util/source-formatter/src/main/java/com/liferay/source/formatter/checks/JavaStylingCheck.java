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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("JavaStylingCheck.java")) {
			return content;
		}

		if (content.contains("$\n */")) {
			content = StringUtil.replace(content, "$\n */", "$\n *\n */");
		}

		if (content.contains(" * @author Raymond Aug") &&
			!content.contains(" * @author Raymond Aug\u00e9")) {

			content = content.replaceFirst(
				"Raymond Aug.++", "Raymond Aug\u00e9");
		}

		content = StringUtil.replace(
			content, " final static ", " static final ");

		content = StringUtil.replace(
			content,
			new String[] {";\n/**", "\t/*\n\t *", ";;\n", "\n/**\n *\n *"},
			new String[] {";\n\n/**", "\t/**\n\t *", ";\n", "\n/**\n *"});

		content = StringUtil.replace(
			content,
			new String[] {"!Validator.isNotNull(", "!Validator.isNull("},
			new String[] {"Validator.isNull(", "Validator.isNotNull("});

		content = StringUtil.replace(
			content, StringPool.TAB + "for (;;) {",
			StringPool.TAB + "while (true) {");

		Matcher matcher = _incorrectSynchronizedPattern.matcher(content);

		content = matcher.replaceAll("$1$3 $2");

		return content;
	}

	private final Pattern _incorrectSynchronizedPattern = Pattern.compile(
		"([\n\t])(synchronized) (private|public|protected)");

}