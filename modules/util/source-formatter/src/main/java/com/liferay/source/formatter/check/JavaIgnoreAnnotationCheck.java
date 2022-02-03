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

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaIgnoreAnnotationCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (!javaTerm.hasAnnotation("Ignore") ||
			!javaTerm.hasAnnotation("Test")) {

			return javaTerm.getContent();
		}

		List<String> classNames = getAttributeValues(
			_CLASS_NAMES_BLACKLIST_KEY, absolutePath);

		JavaClass javaClass = javaTerm.getParentJavaClass();

		if (classNames.contains(javaClass.getName(true))) {
			addMessage(
				fileName, "Do not ignore test in '" + javaClass.getName(),
				javaTerm.getLineNumber());
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private static final String _CLASS_NAMES_BLACKLIST_KEY =
		"classNamesBlacklist";

}