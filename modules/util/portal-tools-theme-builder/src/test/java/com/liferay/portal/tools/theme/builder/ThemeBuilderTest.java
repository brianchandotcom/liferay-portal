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
	public void testThemeBuilderStyled() throws Exception {
		buildTheme(
			_diffsDir, _NAME, temporaryFolder.getRoot(), _styledJarFile,
			ThemeBuilder.STYLED, "ftl", _unstyledJarFile);

		_assertEquals("css/custom.scss", ".text { color: black; }");
		_assertNotEquals("css/_portal.scss", "");
		_assertExists("images/thumbnail.png");
		_assertExists("templates/init.ftl");
		_assertNotExists("templates/init.vm");
		_assertExists("WEB-INF/liferay-look-and-feel.xml");
	}

	@Test
	public void testThemeBuilderUnstyled() throws Exception {
		buildTheme(
			_diffsDir, _NAME, temporaryFolder.getRoot(), null,
			ThemeBuilder.UNSTYLED, "vm", _unstyledJarFile);

		_assertEquals("css/custom.scss", ".text { color: black; }");
		_assertEquals("css/_portal.scss", "");
		_assertNotExists("images/thumbnail.png");
		_assertNotExists("templates/init.ftl");
		_assertExists("templates/init.vm");
		_assertExists("WEB-INF/liferay-look-and-feel.xml");
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected void buildTheme(
			File diffsDir, String name, File outputDir, File parentDir,
			String parentName, String templateExtension, File unstyledDir)
		throws Exception {

		ThemeBuilder themeBuilder = new ThemeBuilder(
			diffsDir, name, outputDir, parentDir, parentName, templateExtension,
			unstyledDir);

		themeBuilder.build();
	}

	private void _assertEquals(String fileName, String expected)
		throws IOException {

		String content = _read(fileName);

		Assert.assertEquals(expected, content);
	}

	private File _assertExists(String fileName) {
		File file = new File(temporaryFolder.getRoot(), fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private void _assertNotEquals(String fileName, String expected)
		throws IOException {

		String content = _read(fileName);

		Assert.assertNotEquals(expected, content);
	}

	private File _assertNotExists(String fileName) {
		File file = new File(temporaryFolder.getRoot(), fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private String _read(String fileName) throws IOException {
		File file = _assertExists(fileName);

		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private static final String _NAME = "Test Theme";

	private static File _diffsDir;
	private static File _styledJarFile;
	private static File _unstyledJarFile;

}