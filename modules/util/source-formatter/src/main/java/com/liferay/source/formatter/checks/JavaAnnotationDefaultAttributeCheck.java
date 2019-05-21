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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 * @author Hugo Huijser
 */
public class JavaAnnotationDefaultAttributeCheck extends JavaAnnotationsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		return formatAnnotations(fileName, absolutePath, (JavaClass)javaTerm);
	}

	@Override
	protected String formatAnnotation(
		String fileName, String absolutePath, JavaClass javaClass,
		String annotation, String indent) {

		annotation = _formatDefaultAttribute(annotation);

		annotation = _formatDefaultValue(
			annotation, javaClass, "aQute.bnd.annotation.metatype", "Meta.AD",
			"cardinality", "0");
		annotation = _formatDefaultValue(
			annotation, javaClass, "aQute.bnd.annotation.metatype", "Meta.AD",
			"required", "true");
		annotation = _formatDefaultValue(
			annotation, javaClass, "aQute.bnd.annotation.metatype", "Meta.OCD",
			"factory", "false");

		annotation = _formatDefaultValue(
			annotation, javaClass, "org.springframework.web.bind.annotation",
			"DeleteMapping");
		annotation = _formatDefaultValue(
			annotation, javaClass, "org.springframework.web.bind.annotation",
			"GetMapping");
		annotation = _formatDefaultValue(
			annotation, javaClass, "org.springframework.web.bind.annotation",
			"PatchMapping");
		annotation = _formatDefaultValue(
			annotation, javaClass, "org.springframework.web.bind.annotation",
			"PostMapping");
		annotation = _formatDefaultValue(
			annotation, javaClass, "org.springframework.web.bind.annotation",
			"PutMapping");

		return annotation;
	}

	private String _formatDefaultAttribute(String annotation) {
		Matcher matcher = _valueAttributePattern.matcher(annotation);

		if (!matcher.find()) {
			return annotation;
		}

		int x = matcher.end();

		while (true) {
			x = annotation.indexOf(CharPool.COMMA, x + 1);

			if (x == -1) {
				break;
			}

			if (!ToolsUtil.isInsideQuotes(annotation, x) &&
				(getLevel(annotation.substring(matcher.end(), x), "{", "}") ==
					0)) {

				return annotation;
			}
		}

		return matcher.replaceFirst("$1");
	}

	private String _formatDefaultValue(
		String annotation, JavaClass javaClass, String packageName,
		String annotationName) {

		return _formatDefaultValue(
			annotation, javaClass, packageName, annotationName, null, "\"\"");
	}

	private String _formatDefaultValue(
		String annotation, JavaClass javaClass, String packageName,
		String annotationName, String attributeName,
		String defaultAttributeValue) {

		List<String> imports = javaClass.getImports();

		String importName = null;

		int pos = annotationName.indexOf(CharPool.PERIOD);

		if (pos == -1) {
			importName = packageName + StringPool.PERIOD + annotationName;
		}
		else {
			importName =
				packageName + StringPool.PERIOD +
					annotationName.substring(0, pos);
		}

		if (!imports.contains(importName)) {
			return annotation;
		}

		if (attributeName == null) {
			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"(@", annotationName, ")\\(\\s*", defaultAttributeValue,
					"\\s*\\)"));

			Matcher matcher = pattern.matcher(annotation);

			if (matcher.find()) {
				return matcher.replaceFirst("$1");
			}

			return annotation;
		}

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(\\(|,)\\s*", attributeName, " = ", defaultAttributeValue,
				"\\s*(\\)|,)"));

		Matcher matcher = pattern.matcher(annotation);

		if (!matcher.find()) {
			return annotation;
		}

		if (Objects.equals(matcher.group(1), StringPool.COMMA)) {
			return matcher.replaceFirst("$2");
		}

		if (Objects.equals(matcher.group(2), StringPool.COMMA)) {
			return matcher.replaceFirst("$1");
		}

		return matcher.replaceFirst("");
	}

	private static final Pattern _valueAttributePattern = Pattern.compile(
		"^(\\s*@[\\w.]+\\(\\s*)(value = )");

}