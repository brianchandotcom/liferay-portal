/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder.internal.util;

import com.liferay.petra.string.StringBundler;

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

		return StringBundler.concat(
			cacheFileName.substring(0, x + 1), outputDirName,
			cacheFileName.substring(x + 1, y), suffix,
			cacheFileName.substring(y));
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