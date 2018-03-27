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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Shin
 */
public class AlloyMVCCheckstyleUtil {

	public static void cleanUpSuppressionsFiles(List<File> suppressionsFiles)
		throws Exception {

		if (suppressionsFiles == null) {
			return;
		}

		for (File suppressionsFile : suppressionsFiles) {
			String path = SourceUtil.getAbsolutePath(suppressionsFile);

			if (path.contains(_TMP_DIR)) {
				Files.deleteIfExists(suppressionsFile.toPath());
			}
		}
	}

	public static File getJavaFile(String absolutePath, String content)
		throws IOException {

		if (!absolutePath.contains(_SRC_DIR)) {
			return null;
		}

		if (!absolutePath.endsWith(".jspf")) {
			return null;
		}

		if (!content.matches("(?s)<%--.*--%>(\\s*<%@[^\\n]*)*\\s*<%!\\s.*")) {
			return null;
		}

		if (StringUtil.count(content, "<%!") != 1) {
			return null;
		}

		if (!content.endsWith("\n%>")) {
			return null;
		}

		File javaFile = new File(_getJavaFileName(absolutePath));

		String javaContent = StringUtil.replace(
			content, new String[] {"<%--", "--%>", "<%@", "<%!"},
			new String[] {"//<%--", "//--%>", "//<%@", "//<%!"});

		javaContent = StringUtil.replaceLast(javaContent, "\n%>", "");

		FileUtil.write(javaFile, javaContent);

		return javaFile;
	}

	public static String getSourceFileName(String fileName) {
		if (!fileName.contains(_TMP_DIR)) {
			return fileName;
		}

		String s = fileName.replace(_TMP_DIR, _SRC_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".jspf";
	}

	public static List<File> getSuppressionsFiles(List<File> suppressionsFiles)
		throws Exception {

		List<File> tempSuppressionsFiles = new ArrayList<>();

		for (File suppressionsFile : suppressionsFiles) {
			String content = FileUtil.read(suppressionsFile);

			String[] lines = StringUtil.splitLines(content);

			StringBundler sb = new StringBundler(lines.length * 2);

			for (String line : lines) {
				String trimmedLine = StringUtil.trim(line);

				if (trimmedLine.startsWith("<suppress") &&
					line.contains("\"src/")) {

					String s = StringUtil.replace(
						line, new String[] {"\"src/", ".jspf"},
						new String[] {"\"tmp/", ".java"});

					sb.append(s);
				}
				else if (trimmedLine.startsWith("<suppress") &&
						 line.contains("/src/")) {

					String s = StringUtil.replace(
						line, new String[] {"/src/", ".jspf"},
						new String[] {"/tmp/", ".java"});

					sb.append(s);
				}
				else {
					sb.append(line);
				}

				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			if (!content.equals(sb.toString())) {
				File tempSuppressionsFile = new File(
					suppressionsFile.getParentFile() + _TMP_DIR +
						suppressionsFile.getName());

				FileUtil.write(tempSuppressionsFile, sb.toString());

				tempSuppressionsFiles.add(tempSuppressionsFile);
			}
		}

		return tempSuppressionsFiles;
	}

	private static String _getJavaFileName(String fileName) {
		if (!fileName.contains(_SRC_DIR)) {
			return fileName;
		}

		String s = fileName.replace(_SRC_DIR, _TMP_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".java";
	}

	private static final String _SRC_DIR = "/src/main/resources/alloy_mvc/jsp/";

	private static final String _TMP_DIR = "/tmp/main/resources/alloy_mvc/jsp/";

}