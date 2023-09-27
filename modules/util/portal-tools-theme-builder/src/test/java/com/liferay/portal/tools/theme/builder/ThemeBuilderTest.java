/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.theme.builder;

import java.io.BufferedReader;
import java.io.File;

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
				File file = new File(line);

				Assert.assertTrue(file.isFile());

				if (line.contains("classic-theme-")) {
					_classicWarFile = file;
				}
				else if (line.contains("com.liferay.frontend.theme.styled-")) {
					_styledJarFile = file;
				}
				else if (line.contains(
							"com.liferay.frontend.theme.unstyled-")) {

					_unstyledJarFile = file;
				}
			}
		}

		Assert.assertNotNull(_classicWarFile);
		Assert.assertTrue(_classicWarFile.isFile());

		Assert.assertNotNull(_styledJarFile);
		Assert.assertTrue(_styledJarFile.isFile());

		Assert.assertNotNull(_unstyledJarFile);
		Assert.assertTrue(_unstyledJarFile.isFile());
	}

	@Test
	public void testThemeBuilderClassic() throws Exception {
		buildTheme(
			_diffsDir, _NAME, temporaryFolder.getRoot(), _classicWarFile,
			"classic", "ftl", _unstyledJarFile);

		_assertEquals("css/_custom.scss", ".text { color: black; }");
		_assertNotExists("css/main.css");
		_assertNotEquals("css/_portal.scss", "");
		_assertExists("images/thumbnail.png");
		_assertExists("templates/init.ftl");
		_assertNotExists("templates/init.vm");
		_assertExists("WEB-INF/liferay-look-and-feel.xml");
	}

	@Test
	public void testThemeBuilderStyled() throws Exception {
		buildTheme(
			_diffsDir, _NAME, temporaryFolder.getRoot(), _styledJarFile,
			ThemeBuilder.STYLED, "ftl", _unstyledJarFile);

		_assertEquals("css/_custom.scss", ".text { color: black; }");
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
			ThemeBuilder.UNSTYLED, "ftl", _unstyledJarFile);

		_assertEquals("css/_custom.scss", ".text { color: black; }");
		_assertEquals("css/_portal.scss", "");
		_assertNotExists("images/thumbnail.png");
		_assertExists("templates/init.ftl");
		_assertNotExists("templates/init.vm");
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
			null, null, unstyledDir);

		themeBuilder.build();
	}

	private void _assertEquals(String fileName, String expected)
		throws Exception {

		String content = _read(fileName);

		Assert.assertEquals(expected, content);
	}

	private File _assertExists(String fileName) {
		File file = new File(temporaryFolder.getRoot(), fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private void _assertNotEquals(String fileName, String expected)
		throws Exception {

		String content = _read(fileName);

		Assert.assertNotEquals(expected, content);
	}

	private File _assertNotExists(String fileName) {
		File file = new File(temporaryFolder.getRoot(), fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private String _read(String fileName) throws Exception {
		File file = _assertExists(fileName);

		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private static final String _NAME = "Test Theme";

	private static File _classicWarFile;
	private static File _diffsDir;
	private static File _styledJarFile;
	private static File _unstyledJarFile;

}