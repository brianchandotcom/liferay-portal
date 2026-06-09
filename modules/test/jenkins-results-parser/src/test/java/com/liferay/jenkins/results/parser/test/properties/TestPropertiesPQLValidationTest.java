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
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Charlotte Wong
 */
public class TestPropertiesPQLValidationTest {

	@Test
	public void testValidatePQL() throws Exception {
		List<File> testPropertiesFiles = _findTestPropertiesFiles(
			_getPortalDir());

		List<String> errors = Collections.synchronizedList(new ArrayList<>());
		List<Future<?>> futures = new ArrayList<>();

		ThreadPoolExecutor threadPoolExecutor =
			JenkinsResultsParserUtil.getNewThreadPoolExecutor(
				Runtime.getRuntime(
				).availableProcessors(),
				true);

		for (File testPropertiesFile : testPropertiesFiles) {
			Properties properties = new Properties();

			try (InputStream inputStream = new FileInputStream(
					testPropertiesFile)) {

				properties.load(inputStream);
			}

			for (String propertyName : properties.stringPropertyNames()) {
				if (!propertyName.startsWith("test.batch.run.property.query")) {
					continue;
				}

				String value = properties.getProperty(
					propertyName
				).trim();

				if (value.contains("${")) {
					continue;
				}

				futures.add(
					threadPoolExecutor.submit(
						() -> {
							try {
								JenkinsResultsParserUtil.validatePQL(
									value, testPropertiesFile);
							}
							catch (RuntimeException runtimeException) {
								errors.add(
									"Invalid PQL in property `" + propertyName +
										"` in " + testPropertiesFile + ": " +
											runtimeException.getMessage());
							}
						}));
			}
		}

		threadPoolExecutor.shutdown();

		System.out.println(
			"Validating " + futures.size() +
				" test.batch.run.property.query PQL values across " +
					testPropertiesFiles.size() + " test.properties files");

		for (Future<?> future : futures) {
			future.get();
		}

		Assert.assertTrue(
			"Found invalid PQL:\n" + String.join("\n", errors),
			errors.isEmpty());
	}

	private List<File> _findTestPropertiesFiles(File portalDir)
		throws Exception {

		List<File> testPropertiesFiles = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dir, BasicFileAttributes basicFileAttributes) {

					String dirName = dir.getFileName(
					).toString();

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
					Path file, BasicFileAttributes basicFileAttributes) {

					if (Objects.equals(
							file.getFileName(
							).toString(),
							"test.properties")) {

						testPropertiesFiles.add(file.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
					Path file, IOException ioException) {

					return FileVisitResult.CONTINUE;
				}

			});

		return testPropertiesFiles;
	}

	private File _getPortalDir() {
		File dir = JenkinsResultsParserUtil.getCanonicalFile(new File("."));

		while (dir != null) {
			if (Objects.equals(dir.getName(), "liferay-portal")) {
				return dir;
			}

			dir = dir.getParentFile();
		}

		throw new RuntimeException("Unable to find portal directory");
	}

}