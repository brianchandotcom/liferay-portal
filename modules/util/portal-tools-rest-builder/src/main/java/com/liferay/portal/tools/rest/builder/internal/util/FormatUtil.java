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

package com.liferay.portal.tools.rest.builder.internal.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class FormatUtil {

	public static String fixWhitespace(File file, String content) {
		Matcher matcher = _multiNewLinePattern.matcher(content);

		String newContent = matcher.replaceAll("\n");

		newContent = newContent.trim();

		if (!StringUtil.endsWith(file.getName(), ".java")) {
			return newContent;
		}

		int index = newContent.indexOf("\npublic ");

		if (index == -1) {
			return newContent;
		}

		String oldSub = newContent.substring(0, index);

		matcher = _componentPropertyPattern.matcher(oldSub);

		String newSub = matcher.replaceAll("\t\t$0");

		newContent = newContent.replace(oldSub, newSub);

		index = newContent.indexOf("\npublic ");

		oldSub = newContent.substring(
			newContent.indexOf("\n", index + 2), newContent.lastIndexOf("}"));

		matcher = _methodHeaderPattern.matcher(oldSub);

		newSub = matcher.replaceAll("\t$1");

		matcher = _methodClosingBracePattern.matcher(newSub);

		newSub = matcher.replaceAll("\t}");

		return newContent.replace(oldSub, newSub);
	}

	public static String format(File file) throws Exception {
		if (StringUtil.endsWith(file.getName(), ".java")) {
			JavaParser.parse(file, 80);
		}

		SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

		sourceFormatterArgs.setFileNames(
			Collections.singletonList(file.getCanonicalPath()));
		sourceFormatterArgs.setIncludeGeneratedFiles(true);
		sourceFormatterArgs.setPrintErrors(false);
		sourceFormatterArgs.setSkipCheckNames(
			Collections.singletonList("JavaOSGiReferenceCheck"));

		SourceFormatter sourceFormatter = new SourceFormatter(
			sourceFormatterArgs);

		sourceFormatter.format();

		return FileUtil.read(file);
	}

	private static final Pattern _componentPropertyPattern = Pattern.compile(
		"^\".+\",$", Pattern.MULTILINE);
	private static final Pattern _methodClosingBracePattern = Pattern.compile(
		"^\t*}$", Pattern.MULTILINE);
	private static final Pattern _methodHeaderPattern = Pattern.compile(
		"^\t*(@|private|protected|public)", Pattern.MULTILINE);
	private static final Pattern _multiNewLinePattern = Pattern.compile(
		"^\n+", Pattern.MULTILINE);

}