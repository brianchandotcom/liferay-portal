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

package com.liferay.portal.tools.soy.builder.commands;

import com.liferay.portal.tools.soy.builder.util.FileTestUtil;

import java.io.File;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class ReplaceTranslationCommandTest {

	@Parameters(name = "{0}")
	public static String[] getTestDirNames() {
		return new String[] {"metal_cli", "metal_cli_2"};
	}

	public ReplaceTranslationCommandTest(String testDirName) {
		_testDirName = testDirName;
	}

	@Before
	public void setUp() throws Exception {
		File dir = temporaryFolder.getRoot();

		Path dirPath = dir.toPath();

		for (String fileName : _FILE_NAMES) {
			try (InputStream inputStream =
					ReplaceTranslationCommand.class.getResourceAsStream(
						"dependencies/replace_translation/" + _testDirName +
							"/" + fileName)) {

				Files.copy(inputStream, dirPath.resolve(fileName));
			}
		}
	}

	@Test
	public void testReplaceTranslation() throws Exception {
		File dir = temporaryFolder.getRoot();

		replaceTranslation(dir);

		Path dirPath = dir.toPath();

		for (String fileName : _FILE_NAMES) {
			String content = FileTestUtil.read(dirPath.resolve(fileName));
			String expectedContent = FileTestUtil.read(
				ReplaceTranslationCommandTest.class,
				"dependencies/replace_translation/" + _testDirName +
					"/expected/" + fileName);

			Assert.assertEquals(expectedContent, content);
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected void replaceTranslation(File dir) throws Exception {
		ReplaceTranslationCommand replaceTranslationCommand =
			new ReplaceTranslationCommand();

		replaceTranslationCommand.setDir(dir);

		replaceTranslationCommand.execute();
	}

	private static final String[] _FILE_NAMES = {
		"footer.soy.js", "header.soy.js", "navigation.soy.js", "view.soy.js"
	};

	private final String _testDirName;

}