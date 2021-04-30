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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaMetaAnnotationsCheck extends JavaAnnotationsCheck {

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

		if (!annotation.contains("@Meta.")) {
			return annotation;
		}

		String content = javaClass.getContent();

		_checkDelimeters(fileName, content, annotation);

		if (isAttributeValue(_CHECK_CONFIGURATION_NAME_KEY, absolutePath)) {
			_checkConfigurationNameValue(fileName, content, annotation);
		}

		annotation = _fixOCDId(
			fileName, annotation, javaClass.getPackageName());
		annotation = _fixTypeProperties(annotation);

		return annotation;
	}

	private void _checkConfigurationNameValue(
		String fileName, String content, String annotation) {

		if (!annotation.contains("@Meta.OCD")) {
			return;
		}

		Matcher matcher = _annotationNameValueKeyPattern.matcher(annotation);

		if (matcher.find()) {
			String nameValue = matcher.group(1);

			if (!nameValue.endsWith("-configuration-name")) {
				addMessage(
					fileName,
					"Value for 'name' should end with '-configuration-name'",
					getLineNumber(content, content.indexOf(matcher.group())));
			}
		}
	}

	private void _checkDelimeter(
		String fileName, String content, Matcher matcher, String key,
		String correctDelimeter, String incorrectDelimeter) {

		if (!key.equals(matcher.group(1))) {
			return;
		}

		String value = matcher.group(2);

		if (!value.contains(incorrectDelimeter)) {
			return;
		}

		StringBundler sb = new StringBundler(7);

		sb.append("Value '");
		sb.append(value);
		sb.append("' for key '");
		sb.append(key);
		sb.append("' should use '");
		sb.append(correctDelimeter);
		sb.append("' as delimeter");

		addMessage(
			fileName, sb.toString(),
			getLineNumber(content, content.indexOf(matcher.group())));
	}

	private void _checkDelimeters(
		String fileName, String content, String annotation) {

		Matcher matcher = _annotationMetaValueKeyPattern.matcher(annotation);

		while (matcher.find()) {
			_checkDelimeter(
				fileName, content, matcher, "description", StringPool.DASH,
				StringPool.PERIOD);
			_checkDelimeter(
				fileName, content, matcher, "id", StringPool.PERIOD,
				StringPool.DASH);
			_checkDelimeter(
				fileName, content, matcher, "name", StringPool.DASH,
				StringPool.PERIOD);
		}
	}

	private String _fixOCDId(
		String fileName, String annotation, String packageName) {

		return annotation.replaceFirst(
			"(@Meta\\.OCD\\([^\\{]+id = )\".+?\"",
			StringBundler.concat(
				"$1\"", packageName, StringPool.PERIOD,
				JavaSourceUtil.getClassName(fileName), StringPool.QUOTE));
	}

	private String _fixTypeProperties(String annotation) {
		if (!annotation.contains("@Meta.")) {
			return annotation;
		}

		Matcher matcher = _annotationMetaTypePattern.matcher(annotation);

		if (!matcher.find()) {
			return annotation;
		}

		return StringUtil.replaceFirst(
			annotation, StringPool.PERCENT, StringPool.BLANK, matcher.start());
	}

	private static final String _CHECK_CONFIGURATION_NAME_KEY =
		"checkConfigurationName";

	private static final Pattern _annotationMetaTypePattern = Pattern.compile(
		"[\\s\\(](name|description) = \"%");
	private static final Pattern _annotationMetaValueKeyPattern =
		Pattern.compile("\\s(\\w+) = \"([\\w\\.\\-]+?)\"");
	private static final Pattern _annotationNameValueKeyPattern =
		Pattern.compile("\\sname = \"([\\w\\.\\-]+?)\"");

}