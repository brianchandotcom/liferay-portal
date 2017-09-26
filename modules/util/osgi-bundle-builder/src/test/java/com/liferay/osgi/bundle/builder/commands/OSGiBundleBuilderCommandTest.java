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

package com.liferay.osgi.bundle.builder.commands;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 */
public class OSGiBundleBuilderCommandTest {

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		URL projectURL = clazz.getResource("dependencies/project.zip");

		File projectFile = new File(projectURL.getFile());

		_unzip(projectFile, temporaryFolder.newFolder("project"));

		URL expectedURL = clazz.getResource("dependencies/expected.jar");

		File expectedFile = new File(expectedURL.getFile());

		_unzip(expectedFile, temporaryFolder.newFolder("expected"));
	}

	@Test
	public void testJarCommand() throws Exception {
		File tempFolder = temporaryFolder.getRoot();

		File baseDir = new File(tempFolder, "project");

		OSGiBundleBuilderArgs osgiBundleBuilderArgs =
			new OSGiBundleBuilderArgs();

		osgiBundleBuilderArgs.setBaseDir(baseDir);
		osgiBundleBuilderArgs.setBndFile(new File(baseDir, "bnd.bnd"));
		osgiBundleBuilderArgs.setClassesDir(new File(baseDir, "classes"));
		osgiBundleBuilderArgs.setClasspath(
			Arrays.asList(
				new File(baseDir, "com.liferay.portal.kernel-2.0.0.jar"),
				new File(
					baseDir,
					"org.osgi.service.component.annotations-1.3.0.jar"),
				new File(baseDir, "shiro-core-1.1.0.jar")));
		osgiBundleBuilderArgs.setOutputDir(new File(baseDir, "build"));
		osgiBundleBuilderArgs.setResourcesDir(new File(baseDir, "resources"));

		JarCommand jarCommand = new JarCommand();

		jarCommand.build(osgiBundleBuilderArgs);

		File jarFile = new File(
			baseDir, "build/com.liferay.blade.authenticator.shiro.jar");

		Assert.assertTrue(jarFile.exists());

		File actualFile = temporaryFolder.newFolder("actual");

		_unzip(jarFile, actualFile);

		File expectedFile = new File(tempFolder, "expected");

		_compareJarFiles(expectedFile.toPath(), actualFile.toPath());
	}

	@Test
	public void testManifestCommand() throws Exception {
		File tempFolder = temporaryFolder.getRoot();

		File baseDir = new File(tempFolder, "project");

		OSGiBundleBuilderArgs osgiBundleBuilderArgs =
			new OSGiBundleBuilderArgs();

		osgiBundleBuilderArgs.setBaseDir(baseDir);
		osgiBundleBuilderArgs.setBndFile(new File(baseDir, "bnd.bnd"));
		osgiBundleBuilderArgs.setClassesDir(new File(baseDir, "classes"));
		osgiBundleBuilderArgs.setClasspath(
			Arrays.asList(
				new File(baseDir, "com.liferay.portal.kernel-2.0.0.jar"),
				new File(
					baseDir,
					"org.osgi.service.component.annotations-1.3.0.jar"),
				new File(baseDir, "shiro-core-1.1.0.jar")));
		osgiBundleBuilderArgs.setOutputDir(new File(baseDir, "build"));
		osgiBundleBuilderArgs.setResourcesDir(new File(baseDir, "resources"));

		ManifestCommand manifestCommand = new ManifestCommand();

		manifestCommand.build(osgiBundleBuilderArgs);

		File actualFile = new File(baseDir, "build/MANIFEST.MF");

		Assert.assertTrue(actualFile.exists());

		_compareManifestFiles(
			new File(tempFolder, "expected/META-INF/MANIFEST.MF"), actualFile);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static void _unzip(File file, File outputDir) throws IOException {
		Path outputDirPath = outputDir.toPath();

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/")) {
					continue;
				}

				Path path = outputDirPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(zipFile.getInputStream(zipEntry), path);
			}
		}
	}

	private void _compareJarFiles(final Path expected, final Path actual)
		throws Exception {

		Files.walkFileTree(
			expected,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dir, BasicFileAttributes attrs)
					throws IOException {

					Path fileName = dir.getFileName();

					if (_ignoredDirectories.contains(fileName.toString())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes attrs)
					throws IOException {

					Path relativize = expected.relativize(file);

					Path fileInActual = actual.resolve(relativize);

					//System.out.println(file.toString());

					Assert.assertTrue(Files.exists(fileInActual));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _compareManifestFiles(File expected, File actual)
		throws Exception {

		Manifest expectedManifest = new Manifest(new FileInputStream(expected));

		Manifest actualManifest = new Manifest(new FileInputStream(actual));

		Attributes expectedAttributes = expectedManifest.getMainAttributes();

		Attributes actualAttributes = actualManifest.getMainAttributes();

		for (Object name : expectedAttributes.keySet()) {
			if (_ignoredAttributes.contains(String.valueOf(name))) {
				continue;
			}

			String expectedValue = (String)expectedAttributes.get(name);

			String actualValue = (String)actualAttributes.get(name);

			Assert.assertEquals(expectedValue, actualValue);
		}
	}

	private static final List<String> _ignoredAttributes = new ArrayList<>(
		Arrays.asList(
			"Bnd-LastModified", "Javac-Debug", "Javac-Deprecation",
			"Javac-Encoding"));
	private static final List<String> _ignoredDirectories = new ArrayList<>(
		Arrays.asList("OSGI-INF", "OSGI-OPT"));

}