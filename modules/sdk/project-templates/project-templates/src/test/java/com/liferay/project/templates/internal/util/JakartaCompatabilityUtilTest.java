/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.project.templates.extensions.util.FileUtil;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Brian Greenwald
 */
public class JakartaCompatabilityUtilTest {

	@Before
	public void setUp() throws Exception {
		_sourceTemplatesDir = Paths.get(
			"src/test/resources/com/liferay/project/templates/internal/util" +
				"/templates");
		_tempDir = Files.createTempDirectory("jakarta-test");

		Files.walkFileTree(
			_sourceTemplatesDir,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String updatedName = _sourceTemplatesDir.relativize(
						path
					).toString(
					).replace(
						"_template", StringPool.BLANK
					);

					Path destinationPath = _tempDir.resolve(updatedName);

					Files.copy(
						path, destinationPath,
						StandardCopyOption.REPLACE_EXISTING);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.deleteDir(_tempDir);
	}

	@Test
	public void testUpdateForJakarta() throws Exception {
		JakartaCompatabilityUtil.updateForJakarta(_tempDir.toFile());

		Files.walkFileTree(
			_tempDir,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					try {
						String fileName = path.getFileName(
						).toString();

						String expectedFileName = fileName + "_expected";

						Path expectedFilePathInResources =
							_sourceTemplatesDir.getParent(
							).resolve(
								expectedFileName
							);

						Assert.assertTrue(
							"Expected file does not exist: " +
								expectedFilePathInResources,
							Files.exists(expectedFilePathInResources));

						String processedFileContent = Files.readString(path);
						String expectedFileContent = Files.readString(
							expectedFilePathInResources);

						Assert.assertEquals(
							"File content does not match for " + fileName,
							expectedFileContent, processedFileContent);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private Path _sourceTemplatesDir;
	private Path _tempDir;

}