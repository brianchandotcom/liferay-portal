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

package com.liferay.maven.executor;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Andrea Di Giorgi
 */
public class MavenExecutorTest {

	@BeforeClass
	public static void setUpClass() {
		_mavenDistributionFileName = System.getProperty(
			"maven.distribution.file.name");

		Assert.assertNotNull(_mavenDistributionFileName);
	}

	@Before
	public void setUp() {
		_mavenExecutor = new MavenExecutor();
	}

	@After
	public void tearDown() {
		_mavenExecutor.after();
	}

	@Test
	public void testBefore() throws Throwable {
		_mavenExecutor.before();

		Path mavenHomeDirPath = _mavenExecutor.getMavenHomeDirPath();

		Assert.assertTrue(
			Files.isExecutable(mavenHomeDirPath.resolve("bin/mvn")));
		Assert.assertTrue(
			Files.isRegularFile(mavenHomeDirPath.resolve("bin/mvn.cmd")));
	}

	@Test
	public void testExecuteGoal() throws Throwable {
		File projectDir = temporaryFolder.getRoot();

		Path projectDirPath = projectDir.toPath();

		Files.copy(
			MavenExecutorTest.class.getResourceAsStream(
				"dependencies/echo.xml"),
			projectDirPath.resolve("pom.xml"));

		testBefore();

		MavenExecutor.Result result = _mavenExecutor.execute(
			projectDir, "initialize");

		Assert.assertEquals(0, result.exitCode);

		Assert.assertTrue(
			result.output.contains(
				"This is the text which will be printed out."));
	}

	@Test
	public void testExecuteVersion() throws Throwable {
		testBefore();

		MavenExecutor.Result result = _mavenExecutor.execute(
			temporaryFolder.getRoot(), "--version");

		Assert.assertEquals(0, result.exitCode);
		Assert.assertTrue(result.output.startsWith("Apache Maven "));
	}

	@Test(expected = IllegalStateException.class)
	public void testExecuteWithoutBefore() throws Exception {
		_mavenExecutor.execute(temporaryFolder.getRoot(), "--version");
	}

	@Test(expected = IllegalStateException.class)
	public void testGetMavenHomeDirPathWithoutBefore() {
		_mavenExecutor.getMavenHomeDirPath();
	}

	@Test
	public void testProxy() throws Throwable {
		File projectDir = temporaryFolder.getRoot();

		Path projectDirPath = projectDir.toPath();

		Files.copy(
			MavenExecutorTest.class.getResourceAsStream(
				"dependencies/wrong-plugin.xml"),
			projectDirPath.resolve("pom.xml"));

		String proxyHost = _setSystemProperty("http.proxyHost", "127.0.0.1");
		String proxyPort = _setSystemProperty("http.proxyPort", "7777");
		String repositoryUrl = _setSystemProperty(
			"repository.url", "http://127.0.0.1:8888");

		try {
			testBefore();

			MavenExecutor.Result result = _mavenExecutor.execute(
				projectDir, "initialize");

			Assert.assertEquals(1, result.exitCode);
			Assert.assertTrue(
				result.output.contains("Connect to 127.0.0.1:7777"));
		}
		finally {
			_setSystemProperty("http.proxyHost", proxyHost);
			_setSystemProperty("http.proxyPort", proxyPort);
			_setSystemProperty("repository.url", repositoryUrl);
		}
	}

	@Test
	public void testRepositoryUrl() throws Throwable {
		File projectDir = temporaryFolder.getRoot();

		Path projectDirPath = projectDir.toPath();

		Files.copy(
			MavenExecutorTest.class.getResourceAsStream(
				"dependencies/wrong-plugin.xml"),
			projectDirPath.resolve("pom.xml"));

		String repositoryUrl = _setSystemProperty(
			"repository.url", "http://127.0.0.1:8888");

		try {
			testBefore();

			MavenExecutor.Result result = _mavenExecutor.execute(
				projectDir, "initialize");

			Assert.assertEquals(1, result.exitCode);
			Assert.assertTrue(
				result.output.contains("Connect to 127.0.0.1:8888"));
		}
		finally {
			_setSystemProperty("repository.url", repositoryUrl);
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static String _setSystemProperty(String key, String value) {
		String oldValue = System.getProperty(key);

		if (value == null) {
			Properties properties = System.getProperties();

			properties.remove(key);
		}
		else {
			System.setProperty(key, value);
		}

		return oldValue;
	}

	private static String _mavenDistributionFileName;

	private MavenExecutor _mavenExecutor;

}