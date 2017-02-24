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

package com.liferay.portal.tools.theme.builder.maven;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.portal.tools.theme.builder.ThemeBuilderTest;
import com.liferay.portal.tools.theme.builder.internal.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThemeMojoTest extends ThemeBuilderTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Override
	protected void buildTheme(
			File diffsDir, String name, File outputDir, File parentDir,
			String parentName, String templateExtension, File unstyledDir)
		throws Exception {

		File projectDir = temporaryFolder.newFolder("maven");

		_preparePomXml(
			projectDir, diffsDir, name, outputDir, parentDir, parentName,
			templateExtension, unstyledDir);

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir, "theme-builder:build");

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static void _preparePomXml(
			File projectDir, File diffsDir, String name, File outputDir,
			File parentDir, String parentName, String templateExtension,
			File unstyledDir)
		throws IOException {

		String content = FileUtil.read(
			BuildThemeMojoTest.class, "dependencies/pom_xml.tmpl");

		content = _replace(
			content, "[$THEME_BUILDER_VERSION$]", _THEME_BUILDER_VERSION);

		content = _replace(content, "[$THEME_BUILDER_DIFFS_DIR$]", diffsDir);
		content = _replace(content, "[$THEME_BUILDER_NAME$]", name);
		content = _replace(content, "[$THEME_BUILDER_OUTPUT_DIR$]", outputDir);
		content = _replace(content, "[$THEME_BUILDER_PARENT_DIR$]", parentDir);
		content = _replace(
			content, "[$THEME_BUILDER_PARENT_NAME$]", parentName);
		content = _replace(
			content, "[$THEME_BUILDER_TEMPLATE_EXTENSION$]", templateExtension);
		content = _replace(
			content, "[$THEME_BUILDER_UNSTYLED_DIR$]", unstyledDir);

		File pomXmlFile = new File(projectDir, "pom.xml");

		Files.write(
			pomXmlFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static String _replace(String s, String key, File file) {
		String value = null;

		if (file != null) {
			value = file.getAbsolutePath();
		}

		return _replace(s, key, value);
	}

	private static String _replace(String s, String key, String value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value);
	}

	private static final String _THEME_BUILDER_VERSION = System.getProperty(
		"theme.builder.version");

}