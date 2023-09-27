/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder.maven;

import com.liferay.css.builder.util.FileTestUtil;
import com.liferay.css.builder.util.StringTestUtil;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Gregory Amerson
 */
public class BuildCSSMojoTestUtil {

	public static void preparePomXml(
			Path baseDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path importDirPath, String outputDirName,
			int precision, String[] rtlExcludedPathRegexps,
			String sassCompilerClassName)
		throws IOException {

		String content = FileTestUtil.read(
			BuildCSSMojoTest.class, "dependencies/pom_xml.tmpl");

		content = _replace(
			content, "[$CSS_BUILDER_VERSION$]", _CSS_BUILDER_VERSION);

		content = _replace(
			content, "[$CSS_BUILDER_BASE_DIR$]",
			String.valueOf(baseDirPath.toAbsolutePath()));
		content = _replace(content, "[$CSS_BUILDER_DIR_NAMES$]", dirName);
		content = _replace(
			content, "[$CSS_BUILDER_EXCLUDES$]",
			StringTestUtil.merge(excludes));
		content = _replace(
			content, "[$CSS_BUILDER_GENERATE_SOURCE_MAP$]",
			String.valueOf(generateSourceMap));
		content = _replace(
			content, "[$CSS_BUILDER_OUTPUT_DIR_NAME$]", outputDirName);
		content = _replace(
			content, "[$CSS_BUILDER_IMPORT_DIR$]",
			String.valueOf(importDirPath.toAbsolutePath()));
		content = _replace(
			content, "[$CSS_BUILDER_PRECISION$]", String.valueOf(precision));
		content = _replace(
			content, "[$CSS_BUILDER_RTL_EXCLUDED_PATH_REGEXPS$]",
			StringTestUtil.merge(rtlExcludedPathRegexps));
		content = _replace(
			content, "[$CSS_BUILDER_SASS_COMPILER_CLASS_NAME$]",
			sassCompilerClassName);

		Path pomXmlPath = baseDirPath.resolve("pom.xml");

		Files.write(pomXmlPath, content.getBytes(StandardCharsets.UTF_8));
	}

	private static String _replace(String s, String key, String value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value);
	}

	private static final String _CSS_BUILDER_VERSION = System.getProperty(
		"css.builder.version");

}