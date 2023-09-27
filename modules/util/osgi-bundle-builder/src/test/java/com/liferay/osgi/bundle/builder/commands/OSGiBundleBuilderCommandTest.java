/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder.commands;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
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
 * @author Andrea Di Giorgi
 */
public class OSGiBundleBuilderCommandTest {

	@Before
	public void setUp() throws Exception {
		_expectedDir = _unzip("dependencies/expected.jar", "expected");
		_projectDir = _unzip("dependencies/project.zip", "project");
	}

	@Test
	public void testExplodedJarCommand() throws Exception {
		ExplodedJarCommand explodedJarCommand = new ExplodedJarCommand();

		File actualDir = new File(
			_projectDir, "build/com.liferay.blade.authenticator.shiro");

		explodedJarCommand.build(_getOSGiBundleBuilderArgs(actualDir));

		Assert.assertTrue(actualDir.isDirectory());

		_compareJarDirs(_expectedDir, actualDir);
	}

	@Test
	public void testJarCommand() throws Exception {
		JarCommand jarCommand = new JarCommand();

		File jarFile = new File(
			_projectDir, "build/com.liferay.blade.authenticator.shiro.jar");

		jarCommand.build(_getOSGiBundleBuilderArgs(jarFile));

		Assert.assertTrue(jarFile.exists());

		File actualDir = temporaryFolder.newFolder("actual");

		_unzip(jarFile, actualDir);

		_compareJarDirs(_expectedDir, actualDir);
	}

	@Test
	public void testManifestCommand() throws Exception {
		ManifestCommand manifestCommand = new ManifestCommand();

		File actualFile = new File(_projectDir, "build/MANIFEST.MF");

		manifestCommand.build(_getOSGiBundleBuilderArgs(actualFile));

		Assert.assertTrue(actualFile.exists());

		_compareManifestFiles(
			new File(_expectedDir, "META-INF/MANIFEST.MF"), actualFile);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _compareJarDirs(File expectedDir, File actualDir)
		throws Exception {

		final Path expectedDirPath = expectedDir.toPath();
		final Path actualDirPath = actualDir.toPath();

		Files.walkFileTree(
			expectedDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (_ignoredJarDirNames.contains(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					Path relatedPath = expectedDirPath.relativize(path);

					Path actualPath = actualDirPath.resolve(relatedPath);

					Assert.assertTrue(
						"File " + relatedPath + " does not exist",
						Files.exists(actualPath));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _compareManifestFiles(File expectedFile, File actualFile)
		throws Exception {

		Attributes expectedAttributes = _getManifestAttributes(expectedFile);
		Attributes actualAttributes = _getManifestAttributes(actualFile);

		for (Object name : expectedAttributes.keySet()) {
			if (_ignoredManifestAttributeNames.contains(String.valueOf(name))) {
				continue;
			}

			Assert.assertEquals(
				"Value of attribute \"" + name + "\" does not match",
				expectedAttributes.get(name), actualAttributes.get(name));
		}
	}

	private Attributes _getManifestAttributes(File file) throws Exception {
		try (InputStream inputStream = new FileInputStream(file)) {
			Manifest manifest = new Manifest(inputStream);

			return manifest.getMainAttributes();
		}
	}

	private OSGiBundleBuilderArgs _getOSGiBundleBuilderArgs(File outputFile) {
		OSGiBundleBuilderArgs osgiBundleBuilderArgs =
			new OSGiBundleBuilderArgs();

		osgiBundleBuilderArgs.setBaseDir(_projectDir);
		osgiBundleBuilderArgs.setBndFile(new File(_projectDir, "bnd.bnd"));
		osgiBundleBuilderArgs.setClassesDir(new File(_projectDir, "classes"));
		osgiBundleBuilderArgs.setClasspath(
			Arrays.asList(
				new File(_projectDir, "com.liferay.portal.kernel-2.0.0.jar"),
				new File(
					_projectDir,
					"org.osgi.service.component.annotations-1.3.0.jar"),
				new File(_projectDir, "shiro-core-1.1.0.jar")));
		osgiBundleBuilderArgs.setOutputFile(outputFile);
		osgiBundleBuilderArgs.setResourcesDir(
			new File(_projectDir, "resources"));

		return osgiBundleBuilderArgs;
	}

	private void _unzip(File file, File outputDir) throws Exception {
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

	private File _unzip(String resourceName, String outputDirName)
		throws Exception {

		URL url = OSGiBundleBuilderCommandTest.class.getResource(resourceName);

		File file = new File(url.getFile());

		File outputDir = temporaryFolder.newFolder(outputDirName);

		_unzip(file, outputDir);

		return outputDir;
	}

	private static final Set<String> _ignoredJarDirNames = new HashSet<>(
		Arrays.asList("OSGI-INF", "OSGI-OPT"));
	private static final Set<String> _ignoredManifestAttributeNames =
		new HashSet<>(
			Arrays.asList(
				"Bnd-LastModified", "Created-By", "Javac-Debug",
				"Javac-Deprecation", "Javac-Encoding"));

	private File _expectedDir;
	private File _projectDir;

}