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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Peter Shin
 */
public class AlloyMVCCheckstyleUtil {

	public static File getJavaFile(String absolutePath, String content)
		throws IOException {

		String javaFileName = getJavaFileName(absolutePath);

		if (javaFileName == null) {
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

		File javaFile = new File(javaFileName);

		String javaContent = StringUtil.replace(
			content, new String[] {"<%--", "--%>", "<%@", "<%!"},
			new String[] {"//<%--", "//--%>", "//<%@", "//<%!"});

		javaContent = StringUtil.replaceLast(javaContent, "\n%>", "");

		FileUtil.write(javaFile, javaContent);

		return javaFile;
	}

	public static String getJavaFileName(String fileName) {
		if ((!fileName.startsWith(_SRC_DIR) &&
			 !fileName.contains("/" + _SRC_DIR)) ||
			!fileName.endsWith(".jspf")) {

			return null;
		}

		String s = fileName.replace(_SRC_DIR, _TMP_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".java";
	}

	public static String getSourceFileName(String fileName) {
		if (!fileName.contains(_TMP_DIR)) {
			return fileName;
		}

		String s = fileName.replace(_TMP_DIR, _SRC_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".jspf";
	}

	private static final String _SRC_DIR = "src/main/resources/alloy_mvc/jsp/";

	private static final String _TMP_DIR = "tmp/main/resources/alloy_mvc/jsp/";

}