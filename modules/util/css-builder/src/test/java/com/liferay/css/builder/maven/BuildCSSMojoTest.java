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

package com.liferay.css.builder.maven;

import com.liferay.maven.executor.MavenExecutor;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Christopher Bryan Boyd
 * @author Gregory Amerson
 */
public class BuildCSSMojoTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Before
	public void setUp() throws Exception {
		File testCss = testCaseTemporaryFolder.newFolder("css");
		File testPom = testCaseTemporaryFolder.newFile("pom.xml");

		File testScssFile = new File(testCss, "test.scss");

		Path dependenciesFolder = Paths.get(
			"src/test/resources/com/liferay/css/builder/maven/dependencies");

		Files.copy(
			dependenciesFolder.resolve("css/test.scss"), testScssFile.toPath(),
			StandardCopyOption.COPY_ATTRIBUTES,
			StandardCopyOption.REPLACE_EXISTING);

		_preparePomXml(dependenciesFolder, testPom.toPath());
	}

	@Test
	public void testNoCssFilesAreGeneratedInSrcFolder() throws Exception {
		File testFolder = testCaseTemporaryFolder.getRoot();

		_executeMaven(testFolder.toPath());

		Assert.assertFalse(_hasCssFilesInPath(testFolder.toPath()));
	}

	@Rule
	public final TemporaryFolder testCaseTemporaryFolder =
		new TemporaryFolder();

	private static final void _executeMaven(final Path projectDir)
		throws Exception {

		Path currentPath = Paths.get(".").toAbsolutePath();

		Path repoPath = currentPath.resolve("../../../.m2").normalize();

		final String mavenRepoPath = repoPath.toString();

		final String mavenRepoArgument = String.format(
			"-Dmaven.repo.local=%s", mavenRepoPath);

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir.toFile(), mavenRepoArgument, "css-builder:build");

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static final boolean _hasCssFilesInPath(final Path dirPath)
		throws IOException {

		final boolean[] foundCssFile = new boolean[1];

		SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(
					Path path, BasicFileAttributes attrs)
				throws IOException {

				File dir = path.toFile();

				String dirName = dir.getName();

				if (dirName.equals(".sass-cache")) {
					return FileVisitResult.SKIP_SUBTREE;
				}

				return super.preVisitDirectory(path, attrs);
			}

			@Override
			public FileVisitResult visitFile(
					Path path, BasicFileAttributes attrs)
				throws IOException {

				File file = path.toFile();

				String fileName = file.getName();

				if (fileName.endsWith(".css")) {
					foundCssFile[0] = true;

					return FileVisitResult.TERMINATE;
				}

				return super.visitFile(path, attrs);
			}

		};

		Files.walkFileTree(dirPath, visitor);

		return foundCssFile[0];
	}

	private static void _preparePomXml(
			Path dependenciesFolder, Path testPomPath)
		throws IOException {

		Path pomTemplatePath = Paths.get(
			dependenciesFolder.toString(), "pom_xml.tmpl");

		String content = new String(Files.readAllBytes(pomTemplatePath));

		content = _replace(
			content, "[$CSS_BUILDER_VERSION$]", _CSS_BUILDER_VERSION);

		Files.write(testPomPath, content.getBytes(StandardCharsets.UTF_8));
	}

	private static String _replace(String s, String key, String value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value);
	}

	private static final String _CSS_BUILDER_VERSION = System.getProperty(
		"css.builder.version");

}
