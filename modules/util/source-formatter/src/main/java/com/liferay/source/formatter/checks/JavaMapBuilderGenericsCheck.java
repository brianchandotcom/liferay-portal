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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.lang.reflect.Modifier;

import java.util.Objects;
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

		return _formatGenerics(javaTerm, fileContent);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD, JAVA_VARIABLE};
	}

	private String _formatGenerics(JavaTerm javaTerm, String fileContent) {
		String content = javaTerm.getContent();

		Matcher matcher = _mapBuilderPattern.matcher(content);

		while (matcher.find()) {
			String[] genericTypesArray = null;

			String genericTypes = matcher.group(6);

			if (genericTypes != null) {
				genericTypesArray = _getGenericTypesArray(genericTypes);
			}
			else {
				genericTypesArray = _getGenericTypesArray(
					javaTerm, fileContent, matcher);
			}

			if (genericTypesArray == null) {
				continue;
			}

			boolean requiresGenerics = false;

			String keyTypeName = genericTypesArray[0];
			String valueTypeName = genericTypesArray[1];

			if (keyTypeName.contains("<") || valueTypeName.contains("<")) {
				requiresGenerics = true;
			}
			else {
				Class<?> keyClazz = _getClass(keyTypeName, javaTerm);
				Class<?> valueClazz = _getClass(valueTypeName, javaTerm);

				if (_requiresGenerics(keyClazz) ||
					_requiresGenerics(valueClazz)) {

					requiresGenerics = true;
				}
				else if ((keyClazz == null) || (valueClazz == null)) {
					continue;
				}
			}

			if (!requiresGenerics && (genericTypes != null)) {
				return StringUtil.replaceFirst(
					content, matcher.group(5), StringPool.BLANK,
					matcher.start());
			}

			if (requiresGenerics && (genericTypes == null)) {
				return StringUtil.replaceFirst(
					content, "put(",
					StringBundler.concat(
						"<", keyTypeName, ", ", valueTypeName, ">put("),
					matcher.end(4));
			}
		}

		return content;
	}

	private Class<?> _getClass(String typeName, JavaTerm javaTerm) {
		typeName = StringUtil.removeChars(typeName, '[', ']');

		JavaClass javaClass = javaTerm.getParentJavaClass();

		while (javaClass.getParentJavaClass() != null) {
			javaClass = javaClass.getParentJavaClass();
		}

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

	private String[] _getGenericTypesArray(
		JavaTerm javaTerm, String fileContent, Matcher matcher) {

		if (matcher.group(1) == null) {
			return null;
		}

		String mapTypeName = null;

		if (Objects.equals(matcher.group(2), "return")) {
			JavaSignature javaSignature = javaTerm.getSignature();

			mapTypeName = javaSignature.getReturnType();
		}
		else {
			mapTypeName = getVariableTypeName(
				javaTerm.getContent(), fileContent, matcher.group(3), true);
		}

		if (mapTypeName == null) {
			return null;
		}

		int x = mapTypeName.indexOf("<");

		if (x == -1) {
			return null;
		}

		return _getGenericTypesArray(
			mapTypeName.substring(x + 1, mapTypeName.length() - 1));
	}

	private String[] _getGenericTypesArray(String genericTypes) {
		int x = -1;

		while (true) {
			x = genericTypes.indexOf(",", x + 1);

			if (x == -1) {
				return null;
			}

			if (getLevel(genericTypes.substring(0, x), "<", ">") != 0) {
				continue;
			}

			return new String[] {
				StringUtil.trim(genericTypes.substring(0, x)),
				StringUtil.trim(genericTypes.substring(x + 1))
			};
		}
	}

	private boolean _requiresGenerics(Class<?> clazz) {
		if ((clazz == null) ||
			(Modifier.isFinal(clazz.getModifiers()) &&
			 ArrayUtil.isEmpty(clazz.getTypeParameters()))) {

			return false;
		}

		return true;
	}

	private static final Pattern _mapBuilderPattern = Pattern.compile(
		"((return|(\\w+) =)\\s*)?\\s(ConcurrentHash|Hash|LinkedHash|Tree)" +
			"MapBuilder\\.\\s*(<([<>\\[\\],\\s\\.\\w\\?]+)>)?\\s*put\\(");

}