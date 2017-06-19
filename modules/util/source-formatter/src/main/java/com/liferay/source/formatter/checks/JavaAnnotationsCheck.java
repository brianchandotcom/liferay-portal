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
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAnnotationsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatAnnotations(content);

		return content;
	}

	private String _fixAnnotationLineBreaks(String annotation, String indent) {
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

		if (annotation.matches(".*\\(\n[\\S\\s]*[^\t\n]\\)\n")) {
			return StringUtil.replaceLast(annotation, ")", "\n" + indent + ")");
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
				newAnnotation = _fixAnnotationLineBreaks(newAnnotation, indent);
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
		if (!annotation.contains("@Component(")) {
			return annotation;
		}

		Matcher matcher = _annotationParameterPropertyPattern.matcher(
			annotation);

		while (matcher.find()) {
			int x = matcher.end();

			while (true) {
				x = annotation.indexOf(CharPool.CLOSE_CURLY_BRACE, x + 1);

				if (!ToolsUtil.isInsideQuotes(annotation, x)) {
					break;
				}
			}

			String parameterProperties = annotation.substring(matcher.end(), x);

			parameterProperties = StringUtil.removeChar(
				parameterProperties, CharPool.TAB);

			parameterProperties = StringUtil.trim(
				StringUtil.replace(
					parameterProperties, StringPool.NEW_LINE,
					StringPool.SPACE));

			if (parameterProperties.startsWith(StringPool.AT)) {
				continue;
			}

			String[] parameterPropertiesArray = StringUtil.split(
				parameterProperties, StringPool.COMMA_AND_SPACE);

			AnnotationParameterPropertyComparator comparator =
				new AnnotationParameterPropertyComparator(matcher.group(1));

			for (int i = 1; i < parameterPropertiesArray.length; i++) {
				String parameterProperty = parameterPropertiesArray[i];
				String previousParameterProperty =
					parameterPropertiesArray[i - 1];

				if (comparator.compare(
						previousParameterProperty, parameterProperty) > 0) {

					annotation = StringUtil.replaceFirst(
						annotation, previousParameterProperty,
						parameterProperty);
					annotation = StringUtil.replaceLast(
						annotation, parameterProperty,
						previousParameterProperty);

					return annotation;
				}
			}
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
	private final Pattern _annotationMetaTypePattern = Pattern.compile(
		"[\\s\\(](name|description) = \"%");
	private final Pattern _annotationParameterPropertyPattern = Pattern.compile(
		"\t(\\w+) = \\{");
	private final Pattern _modifierPattern = Pattern.compile(
		"[^\n]\n(\t*)(public|protected|private)");

	private class AnnotationParameterPropertyComparator
		extends NaturalOrderStringComparator {

		public AnnotationParameterPropertyComparator(String parameterName) {
			_parameterName = parameterName;
		}

		public int compare(String property1, String property2) {
			if (!_parameterName.equals("property")) {
				return super.compare(property1, property2);
			}

			String propertyName1 = _getPropertyName(property1);
			String propertyName2 = _getPropertyName(property2);

			if (propertyName1.equals(propertyName2)) {
				return super.compare(property1, property2);
			}

			int value = super.compare(propertyName1, propertyName2);

			if (propertyName1.startsWith(StringPool.QUOTE) ^
				propertyName2.startsWith(StringPool.QUOTE)) {

				return -value;
			}

			return value;
		}

		private String _getPropertyName(String property) {
			int x = property.indexOf(StringPool.EQUAL);

			return property.substring(0, x);
		}

		private final String _parameterName;

	}

}