/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.properties;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Charlotte Wong
 */
public class TestPropertiesPQLValidationTest {

	@Test
	public void testValidatePQL() throws Exception {
		File portalDir = JenkinsResultsParserUtil.getCanonicalFile(
			new File("."));

		while (portalDir != null) {
			File buildTestXMLFile = new File(portalDir, "build-test.xml");

			if (buildTestXMLFile.exists()) {
				break;
			}

			portalDir = portalDir.getParentFile();
		}

		if (portalDir == null) {
			throw new RuntimeException("Unable to find portal directory");
		}

		List<String> errorMessages = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path path, BasicFileAttributes basicFileAttributes) {

					File dir = path.toFile();

					String dirName = dir.getName();

					if (dirName.equals("build") || dirName.equals("bundles") ||
						dirName.equals("classes") ||
						dirName.equals("node_modules") ||
						dirName.equals("src") ||
						dirName.equals("test-classes") ||
						dirName.startsWith(".")) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					File file = path.toFile();

					String fileName = file.getName();

					if (!fileName.equals("test.properties")) {
						return FileVisitResult.CONTINUE;
					}

					Properties properties = new Properties();

					try (InputStream inputStream = new FileInputStream(file)) {
						properties.load(inputStream);
					}

					for (String propertyName :
							properties.stringPropertyNames()) {

						if (!propertyName.startsWith(
								"test.batch.run.property.query")) {

							continue;
						}

						String propertyValue = properties.getProperty(
							propertyName);

						String pql = propertyValue.trim();

						pql = pql.replaceAll(
							"\\$\\{[^}]+\\}", "(portal.acceptance == true)");

						try {
							JenkinsResultsParserUtil.validatePQL(pql, file);
						}
						catch (RuntimeException runtimeException) {
							errorMessages.add(
								"Invalid PQL in property " + propertyName +
									": " + runtimeException.getMessage());
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
					Path path, IOException ioException) {

					return FileVisitResult.CONTINUE;
				}

			});

		Collections.sort(errorMessages);

		for (String errorMessage : errorMessages) {
			System.out.println(errorMessage);
		}

		Assert.assertTrue(errorMessages.isEmpty());
	}

}