/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseSoyCommandTestCase {

	@Before
	public void setUp() throws Exception {
		File dir = temporaryFolder.getRoot();

		Path dirPath = dir.toPath();

		ClassLoader classLoader = BaseSoyCommandTestCase.class.getClassLoader();
		String dirName = getTestDirName();

		for (String fileName : getTestFileNames()) {
			try (InputStream inputStream = classLoader.getResourceAsStream(
					dirName + fileName)) {

				Files.copy(inputStream, dirPath.resolve(fileName));
			}
		}
	}

	@Test
	public void testSoy() throws Exception {
		File dir = temporaryFolder.getRoot();

		testSoy(dir);

		Path dirPath = dir.toPath();

		ClassLoader classLoader = BaseSoyCommandTestCase.class.getClassLoader();
		String dirName = getTestDirName();

		for (String fileName : getTestExpectedFileNames()) {
			String content = FileTestUtil.read(dirPath.resolve(fileName));
			String expectedContent = FileTestUtil.read(
				classLoader, dirName + "expected/" + fileName);

			Assert.assertEquals(
				fixTestContent(expectedContent), fixTestContent(content));
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected String fixTestContent(String content) {
		return content.trim();
	}

	protected abstract String getTestDirName();

	protected String[] getTestExpectedFileNames() {
		return getTestFileNames();
	}

	protected abstract String[] getTestFileNames();

	protected abstract void testSoy(File dir) throws Exception;

}