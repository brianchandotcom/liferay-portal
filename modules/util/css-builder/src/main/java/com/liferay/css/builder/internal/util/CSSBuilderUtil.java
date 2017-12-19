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

package com.liferay.css.builder.internal.util;

import java.io.File;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class CSSBuilderUtil {

	public static File getOutputFile(String fileName, String outputDirName) {
		return getOutputFile(fileName, outputDirName, "");
	}

	public static File getOutputFile(
		String fileName, String outputDirName, String suffix) {

		return new File(getOutputFileName(fileName, outputDirName, suffix));
	}

	public static String getOutputFileName(
		String fileName, String outputDirName, String suffix) {

		String cacheFileName = fileName.replace('\\', '/');

		int x = cacheFileName.lastIndexOf('/');
		int y = cacheFileName.lastIndexOf('.');

		if (cacheFileName.endsWith(".scss")) {
			cacheFileName = cacheFileName.substring(0, y + 1) + "css";
		}

		return cacheFileName.substring(0, x + 1) + outputDirName +
			cacheFileName.substring(x + 1, y) + suffix +
				cacheFileName.substring(y);
	}

	public static String getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf('.');

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
	}

	public static String parseCSSImports(String content) {
		StringBuffer sb = new StringBuffer();

		Matcher matcher = _cssImportPattern.matcher(content);

		Date date = new Date();

		while (matcher.find()) {
			String cssImport = matcher.group();

			matcher.appendReplacement(sb, cssImport + "?t=" + date.getTime());
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static final Pattern _cssImportPattern = Pattern.compile(
		"@import\\s+url\\s*\\(\\s*['\"]?(.+\\.css)");

}