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

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class ThemeBuilderTest {

	@After
	public void tearDown() throws Exception {
		Files.walkFileTree(
			Paths.get("build/theme"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioe)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testThemeBuilderStyled() throws Exception {
		File diffsDir = new File(
			"src/test/resources/com/liferay/portal/tools/theme/builder/diffs");
		String name = "Test Theme";
		File outputDir = new File("build/theme");
		File parentDir = new File(
			"../../apps/foundation/frontend-theme/frontend-theme-styled/src/" +
				"main/resources/META-INF/resources/_styled");
		String parentName = "_styled";
		String templateExtension = "ftl";
		File unstyledDir = new File(
			"../../apps/foundation/frontend-theme/frontend-theme-unstyled/" +
				"src/main/resources/META-INF/resources/_unstyled");

		ThemeBuilder themeBuilder = new ThemeBuilder(
			diffsDir, name, outputDir, parentDir, parentName, templateExtension,
			unstyledDir);

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
	public void testThemeBuilderUnstyled() throws Exception {
		File diffsDir = new File(
			"src/test/resources/com/liferay/portal/tools/theme/builder/diffs");
		String name = "testTheme";
		File outputDir = new File("build/theme");
		File parentDir = null;
		String parentName = "_unstyled";
		String templateExtension = "vm";
		File unstyledDir = new File(
			"../../apps/foundation/frontend-theme/frontend-theme-unstyled/" +
				"src/main/resources/META-INF/resources/_unstyled");

		ThemeBuilder themeBuilder = new ThemeBuilder(
			diffsDir, name, outputDir, parentDir, parentName, templateExtension,
			unstyledDir);

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

	private String _read(Path filePath) throws Exception {
		return new String(Files.readAllBytes(filePath));
	}

}