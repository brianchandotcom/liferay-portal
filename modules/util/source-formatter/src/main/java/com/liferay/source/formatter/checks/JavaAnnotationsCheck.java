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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAnnotationsCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatAnnotations(content);

		return new Tuple(content, Collections.emptySet());
	}

	private String _fixAnnotationLineBreaks(String annotation) {
		Matcher matcher = _annotationLineBreakPattern1.matcher(annotation);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				annotation, matcher.group(1), StringPool.BLANK,
				matcher.start());
		}

		matcher = _annotationLineBreakPattern2.matcher(annotation);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				annotation, matcher.group(1), StringPool.SPACE,
				matcher.start());
		}

		matcher = _annotationLineBreakPattern3.matcher(annotation);

		if (!matcher.find()) {
			return annotation;
		}

		String match = matcher.group();

		if ((getLevel(match) == 0) && !match.endsWith("\n)\n") &&
			!match.endsWith("\t)\n")) {

			String tabs = matcher.group(1);

			String replacement = StringUtil.replaceLast(
				match, ")", "\n" + tabs + ")");

			return StringUtil.replace(annotation, match, replacement);
		}

		return annotation;
	}

	private String _fixAnnotationMetaTypeProperties(String annotation) {
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

	private String _formatAnnotations(String content) throws Exception {
		List<String> annotationsBlocks = _getAnnotationsBlocks(content);

		for (String annotationsBlock : annotationsBlocks) {
			String indent = _getIndent(annotationsBlock);

			String newAnnotationsBlock = _formatAnnotations(
				annotationsBlock, indent, true);

			content = StringUtil.replace(
				content, "\n" + annotationsBlock, "\n" + newAnnotationsBlock);
		}

		return content;
	}

	private String _formatAnnotations(
			String annotationsBlock, String indent, boolean sortAnnotations)
		throws Exception {

		List<String> annotations = _splitAnnotations(annotationsBlock, indent);

		String previousAnnotation = null;

		for (String annotation : annotations) {
			String newAnnotation = annotation;

			if (newAnnotation.contains(StringPool.OPEN_PARENTHESIS)) {
				newAnnotation = _fixAnnotationLineBreaks(newAnnotation);
				newAnnotation = _fixAnnotationMetaTypeProperties(newAnnotation);
				newAnnotation = _sortAnnotationParameterProperties(
					newAnnotation);

				newAnnotation = _formatAnnotations(
					newAnnotation, indent + "\t\t", false);

				annotationsBlock = StringUtil.replace(
					annotationsBlock, annotation, newAnnotation);
			}

			if (!sortAnnotations) {
				continue;
			}

			if (Validator.isNotNull(previousAnnotation) &&
				(previousAnnotation.compareToIgnoreCase(newAnnotation) > 0)) {

				annotationsBlock = StringUtil.replaceFirst(
					annotationsBlock, previousAnnotation, newAnnotation);
				annotationsBlock = StringUtil.replaceLast(
					annotationsBlock, newAnnotation, previousAnnotation);
			}

			previousAnnotation = newAnnotation;
		}

		return annotationsBlock;
	}

	private List<String> _getAnnotationsBlocks(String content) {
		List<String> annotationsBlocks = new ArrayList<>();

		Matcher matcher = _modifierPattern.matcher(content);

		while (matcher.find()) {
			int lineCount = getLineCount(content, matcher.end());

			String annotationsBlock = StringPool.BLANK;

			for (int i = lineCount - 1;; i--) {
				String line = getLine(content, i);

				if (Validator.isNull(line) ||
					line.matches("\t*(private|public|protected| \\*/).*")) {

					if (Validator.isNotNull(annotationsBlock)) {
						annotationsBlocks.add(annotationsBlock);
					}

					break;
				}

				annotationsBlock = line + "\n" + annotationsBlock;
			}
		}

		return annotationsBlocks;
	}

	private String _getIndent(String s) {
		StringBundler sb = new StringBundler();

		for (char c : s.toCharArray()) {
			if (c != CharPool.TAB) {
				break;
			}

			sb.append(c);
		}

		return sb.toString();
	}

	private String _sortAnnotationParameterProperties(String annotation) {
		int x = annotation.indexOf("property = {");

		if (x == -1) {
			return annotation;
		}

		int y = x;

		while (true) {
			y = annotation.indexOf(CharPool.CLOSE_CURLY_BRACE, y + 1);

			if (!ToolsUtil.isInsideQuotes(annotation, y)) {
				break;
			}
		}

		String parameterProperties = annotation.substring(x + 12, y);

		parameterProperties = StringUtil.replace(
			parameterProperties, StringPool.NEW_LINE, StringPool.SPACE);

		String[] parameterPropertiesArray = StringUtil.split(
			parameterProperties, StringPool.COMMA_AND_SPACE);

		String previousPropertyName = null;
		String previousPropertyNameAndValue = null;

		for (String parameterProperty : parameterPropertiesArray) {
			x = parameterProperty.indexOf(CharPool.QUOTE);

			y = parameterProperty.indexOf(CharPool.EQUAL, x);

			int z = x;

			while (true) {
				z = parameterProperty.indexOf(CharPool.QUOTE, z + 1);

				if ((z == -1) ||
					!ToolsUtil.isInsideQuotes(parameterProperty, z)) {

					break;
				}
			}

			if ((x == -1) || (y == -1) || (z == -1)) {
				return annotation;
			}

			String propertyName = parameterProperty.substring(x + 1, y);
			String propertyNameAndValue = parameterProperty.substring(x + 1, z);

			if (Validator.isNotNull(previousPropertyName) &&
				(previousPropertyName.compareToIgnoreCase(propertyName) > 0)) {

				annotation = StringUtil.replaceFirst(
					annotation, previousPropertyNameAndValue,
					propertyNameAndValue);
				annotation = StringUtil.replaceLast(
					annotation, propertyNameAndValue,
					previousPropertyNameAndValue);

				return annotation;
			}

			previousPropertyName = propertyName;
			previousPropertyNameAndValue = propertyNameAndValue;
		}

		return annotation;
	}

	private List<String> _splitAnnotations(
			String annotationsBlock, String indent)
		throws Exception {

		List<String> annotations = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new UnsyncStringReader(annotationsBlock))) {

			String annotation = null;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (annotation == null) {
					if (line.startsWith(indent + StringPool.AT)) {
						annotation = line + "\n";
					}

					continue;
				}

				String lineIndent = _getIndent(line);

				if (lineIndent.length() < indent.length()) {
					annotations.add(annotation);

					annotation = null;
				}
				else if (line.startsWith(indent + StringPool.AT)) {
					annotations.add(annotation);

					annotation = line + "\n";
				}
				else {
					annotation += line + "\n";
				}
			}

			if (Validator.isNotNull(annotation)) {
				annotations.add(annotation);
			}
		}

		return annotations;
	}

	private final Pattern _annotationLineBreakPattern1 = Pattern.compile(
		"[{=]\n.*(\" \\+\n\t*\")");
	private final Pattern _annotationLineBreakPattern2 = Pattern.compile(
		"=(\n\t*)\"");
	private final Pattern _annotationLineBreakPattern3 = Pattern.compile(
		"(\t*)@(.+)\\(\n([\\s\\S]*?)\\)\n");
	private final Pattern _annotationMetaTypePattern = Pattern.compile(
		"[\\s\\(](name|description) = \"%");
	private final Pattern _modifierPattern = Pattern.compile(
		"[^\n]\n(\t*)(public|protected|private)");

}