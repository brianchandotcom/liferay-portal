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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.lang.reflect.Modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaMapBuilderGenericsCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		String content = javaTerm.getContent();

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.getParentJavaClass() != null) {
			return content;
		}

		return _formatGenerics(content, javaClass);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _formatGenerics(String content, JavaClass javaClass) {
		Matcher matcher = _mapBuilderPattern.matcher(content);

		while (matcher.find()) {
			String genericTypes = matcher.group(3);

			if (genericTypes == null) {
				continue;
			}

			int x = -1;

			while (true) {
				x = genericTypes.indexOf(",", x + 1);

				if (x == -1) {
					break;
				}

				if (getLevel(genericTypes.substring(0, x), "<", ">") != 0) {
					continue;
				}

				String keyTypeName = StringUtil.trim(
					genericTypes.substring(0, x));

				Class<?> keyClazz = _getClass(keyTypeName, javaClass);

				if (keyClazz == null) {
					break;
				}

				String valueTypeName = StringUtil.trim(
					genericTypes.substring(x + 1));

				Class<?> valueClazz = _getClass(valueTypeName, javaClass);

				if (valueClazz == null) {
					break;
				}

				if (Modifier.isFinal(keyClazz.getModifiers()) &&
					ArrayUtil.isEmpty(keyClazz.getTypeParameters()) &&
					Modifier.isFinal(valueClazz.getModifiers()) &&
					ArrayUtil.isEmpty(valueClazz.getTypeParameters())) {

					return StringUtil.replaceFirst(
						content, matcher.group(2), StringPool.BLANK,
						matcher.start());
				}
			}
		}

		return content;
	}

	private Class<?> _getClass(String typeName, JavaClass javaClass) {
		typeName = StringUtil.removeChars(typeName, '[', ']');

		for (String importName : javaClass.getImports()) {
			if (!importName.endsWith("." + typeName)) {
				continue;
			}

			try {
				return Class.forName(importName);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		try {
			return Class.forName(typeName);
		}
		catch (ClassNotFoundException cnfe) {
		}

		try {
			return Class.forName("java.lang." + typeName);
		}
		catch (ClassNotFoundException cnfe) {
		}

		try {
			return Class.forName(javaClass.getPackageName() + "." + typeName);
		}
		catch (ClassNotFoundException cnfe) {
		}

		return null;
	}

	private static final Pattern _mapBuilderPattern = Pattern.compile(
		"\\W(ConcurrentHash|Hash|LinkedHash|Tree)MapBuilder\\.\\s*" +
			"(<([<>\\[\\],\\s\\.\\w\\?]+)>)?\\s*put\\(");

}