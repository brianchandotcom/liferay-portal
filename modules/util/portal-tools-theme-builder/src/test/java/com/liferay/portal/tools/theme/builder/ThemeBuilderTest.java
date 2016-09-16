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

package com.liferay.portal.tools.theme.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class ThemeBuilderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		URL url = ThemeBuilderTest.class.getResource("dependencies/diffs");

		_diffsDir = new File(url.toURI());

		Assert.assertTrue(_diffsDir.isDirectory());

		try (BufferedReader bufferedReader = Files.newBufferedReader(
				Paths.get("build/parent-theme-dependencies.txt"))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				File jarFile = new File(line);

				Assert.assertTrue(jarFile.isFile());

				if (line.contains("com.liferay.frontend.theme.styled-")) {
					_styledJarFile = jarFile;
				}
				else if (line.contains(
							"com.liferay.frontend.theme.unstyled-")) {

					_unstyledJarFile = jarFile;
				}
			}
		}
	}

	@Test
	public void testThemeBuilderStyled() throws IOException {
		String name = "Test Theme";
		File outputDir = temporaryFolder.getRoot();
		String parentName = "_styled";
		String templateExtension = "ftl";

		ThemeBuilder themeBuilder = new ThemeBuilder(
			_diffsDir, name, outputDir, _styledJarFile, parentName,
			templateExtension, _unstyledJarFile);

		themeBuilder.build();

		Assert.assertTrue(outputDir.exists());

		File customScssFile = new File(outputDir, "css/custom.scss");

		Assert.assertTrue(customScssFile.exists());

		String customScssContent = _read(customScssFile.toPath());

		Assert.assertEquals(".text { color: black; }", customScssContent);

		File thumbnailFile = new File(outputDir, "images/thumbnail.png");

		Assert.assertTrue(thumbnailFile.exists());

		File templateFtlFile = new File(outputDir, "templates/init.ftl");

		Assert.assertTrue(templateFtlFile.exists());

		File templateVmFile = new File(outputDir, "templates/init.vm");

		Assert.assertFalse(templateVmFile.exists());

		File lookAndFeelXmlFile = new File(
			outputDir, "WEB-INF/liferay-look-and-feel.xml");

		Assert.assertTrue(lookAndFeelXmlFile.exists());
	}

	@Test
	public void testThemeBuilderUnstyled() throws IOException {
		String name = "testTheme";
		File outputDir = temporaryFolder.getRoot();
		String parentName = "_unstyled";
		String templateExtension = "vm";

		ThemeBuilder themeBuilder = new ThemeBuilder(
			_diffsDir, name, outputDir, null, parentName, templateExtension,
			_unstyledJarFile);

		themeBuilder.build();

		Assert.assertTrue(outputDir.exists());

		File customScssFile = new File(outputDir, "css/custom.scss");

		Assert.assertTrue(customScssFile.exists());

		String customScssContent = _read(customScssFile.toPath());

		Assert.assertEquals(".text { color: black; }", customScssContent);

		File templateFtlFile = new File(outputDir, "templates/init.ftl");

		Assert.assertFalse(templateFtlFile.exists());

		File templateVmFile = new File(outputDir, "templates/init.vm");

		Assert.assertTrue(templateVmFile.exists());

		File lookAndFeelXmlFile = new File(
			outputDir, "WEB-INF/liferay-look-and-feel.xml");

		Assert.assertTrue(lookAndFeelXmlFile.exists());
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static String _read(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	private static File _diffsDir;
	private static File _styledJarFile;
	private static File _unstyledJarFile;

}