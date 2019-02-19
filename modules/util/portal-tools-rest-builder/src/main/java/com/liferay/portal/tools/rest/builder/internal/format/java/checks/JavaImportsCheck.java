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

package com.liferay.portal.tools.rest.builder.internal.format.java.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

/**
 * @author Hugo Huijser
 */
public class JavaImportsCheck {

	public static String format(String content, String fileName)
		throws Exception {

		String oldContent = content;

		while (true) {
			String newContent = _format(oldContent, fileName);

			if (oldContent.equals(newContent)) {
				return newContent;
			}

			oldContent = newContent;
		}
	}

	private static String _format(String content, String fileName)
		throws Exception {

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		String className = JavaSourceUtil.getClassName(fileName);
		String packageName = JavaSourceUtil.getPackageName(content);

		content = importsFormatter.format(content, packageName, className);

		return StringUtil.replace(content, ";\n/**", ";\n\n/**");
	}

}