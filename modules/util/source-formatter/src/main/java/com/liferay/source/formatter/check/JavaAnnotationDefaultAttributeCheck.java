/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

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
		throws Exception {

		return formatAnnotations(
			fileName, absolutePath, (JavaClass)javaTerm, fileContent);
	}

	@Override
	protected String formatAnnotation(
		String fileName, String absolutePath, JavaClass javaClass,
		String fileContent, String annotation, String indent) {

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

		List<String> imports = javaClass.getImportNames();

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