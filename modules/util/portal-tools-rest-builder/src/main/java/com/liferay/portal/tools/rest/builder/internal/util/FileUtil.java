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
import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;

/**
 * @author Peter Shin
 */
public class FileUtil {

	public static String format(File file, String content) throws Exception {
		if (!file.exists()) {
			Path path = file.toPath();

			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}

			Files.createFile(file.toPath());
		}

		String newContent = _fixWhitespace(file, content);

		Files.write(file.toPath(), newContent.getBytes(StandardCharsets.UTF_8));

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

		return read(file);
	}

	public static String read(File file) throws IOException {
		if (!file.exists()) {
			return "";
		}

		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return s.replace("\r\n", "\n");
	}

	public static void write(File file, String content) throws Exception {
		String oldContent = read(file);

		if (!oldContent.equals(format(file, content))) {
			System.out.println("Writing " + file.getCanonicalPath());
		}
	}

	public static void write(String fileName, String content) throws Exception {
		write(new File(fileName), content);
	}

	private static String _fixWhitespace(File file, String content) {
		String newContent = content.replaceAll("(?m)^\n+", "\n");

		newContent = newContent.trim();

		if (!StringUtil.endsWith(file.getName(), ".java")) {
			return newContent;
		}

		int index = newContent.indexOf("\npublic ");

		if (index == -1) {
			return newContent;
		}

		String oldSub = newContent.substring(0, index);

		String newSub = oldSub.replaceAll(
			"(?m)^\t*(\"osgi.jaxrs.extension.select=)", "\t\t$1");

		newSub = newSub.replaceAll(",\n\n", ",\n");

		newContent = newContent.replace(oldSub, newSub);

		int x = newContent.indexOf("\n", index + 2);
		int y = newContent.lastIndexOf("}");

		oldSub = newContent.substring(x, y);

		newSub = oldSub.replaceAll(
			"(?m)^\t*(@|public|protected|private)", "\t$1");

		newSub = newSub.replaceAll("(?m)^\t*}$", "\t}");

		return newContent.replace(oldSub, newSub);
	}

}