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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAnnotationsCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		return formatAnnotations(
			fileName, absolutePath, (JavaClass)javaTerm, fileContent);
	}

	protected String formatAnnotation(
		String fileName, String absolutePath, JavaClass javaClass,
		String fileContent, String annotation, String indent) {

		if (!annotation.contains(StringPool.OPEN_PARENTHESIS)) {
			return annotation;
		}

		annotation = _fixAnnotationLineBreaks(annotation);
		annotation = _fixSingleValueArray(annotation);
		annotation = _fixWhitespaceAroundPipe(annotation);

		return annotation;
	}

	protected String formatAnnotations(
			String fileName, String absolutePath, JavaClass javaClass,
			String fileContent)
		throws IOException {

		String content = javaClass.getContent();

		if (javaClass.getParentJavaClass() != null) {
			return content;
		}

		List<String> annotationsBlocks = SourceUtil.getAnnotationsBlocks(
			content);

		for (String annotationsBlock : annotationsBlocks) {
			String indent = SourceUtil.getIndent(annotationsBlock);

			String newAnnotationsBlock = _formatAnnotations(
				fileName, absolutePath, javaClass, fileContent,
				annotationsBlock, indent);

			content = StringUtil.replace(
				content, "\n" + annotationsBlock, "\n" + newAnnotationsBlock);
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _fixAnnotationLineBreaks(String annotation) {
		Matcher matcher = _annotationLineBreakPattern1.matcher(annotation);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				annotation, matcher.group(1), StringPool.BLANK,
				matcher.start());
		}

		return annotation;
	}

	private String _fixSingleValueArray(String annotation) {
		Matcher matcher = _arrayPattern.matcher(annotation);

		outerLoop:
		while (matcher.find()) {
			int x = matcher.start();

			if (ToolsUtil.isInsideQuotes(annotation, x)) {
				continue;
			}

			String arrayString = null;

			int y = x;

			while (true) {
				y = annotation.indexOf("}", y + 1);

				if (y == -1) {
					return annotation;
				}

				if (!ToolsUtil.isInsideQuotes(annotation, y)) {
					arrayString = annotation.substring(
						matcher.end() - 1, y + 1);

					if (getLevel(arrayString, "{", "}") == 0) {
						break;
					}
				}
			}

			y = -1;

			while (true) {
				y = arrayString.indexOf(",", y + 1);

				if (y == -1) {
					break;
				}

				if (!ToolsUtil.isInsideQuotes(arrayString, y)) {
					continue outerLoop;
				}
			}

			String replacement = StringUtil.trim(
				arrayString.substring(1, arrayString.length() - 1));

			if (Validator.isNotNull(replacement)) {
				return StringUtil.replace(annotation, arrayString, replacement);
			}
		}

		return annotation;
	}

	private String _fixWhitespaceAroundPipe(String annotation) {
		Matcher matcher = _pipePattern.matcher(annotation);

		return matcher.replaceFirst("$1|");
	}

	private String _formatAnnotations(
			String fileName, String absolutePath, JavaClass javaClass,
			String fileContent, String annotationsBlock, String indent)
		throws IOException {

		List<String> annotations = SourceUtil.splitAnnotations(
			annotationsBlock, indent);

		for (String annotation : annotations) {
			String newAnnotation = formatAnnotation(
				fileName, absolutePath, javaClass, fileContent, annotation,
				indent);

			if (newAnnotation.contains(StringPool.OPEN_PARENTHESIS)) {
				newAnnotation = _formatAnnotations(
					fileName, absolutePath, javaClass, fileContent,
					newAnnotation, indent + "\t\t");
			}

			annotationsBlock = StringUtil.replace(
				annotationsBlock, annotation, newAnnotation);
		}

		return annotationsBlock;
	}

	private static final Pattern _annotationLineBreakPattern1 = Pattern.compile(
		"[{=]\n.*(\" \\+\n\t*\")");
	private static final Pattern _arrayPattern = Pattern.compile("=\\s+\\{");
	private static final Pattern _pipePattern = Pattern.compile(
		"(= \".*)( \\| | \\||\\| )");

}