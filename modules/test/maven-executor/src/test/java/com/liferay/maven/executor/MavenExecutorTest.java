/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.maven.executor;

import java.io.File;
import java.io.IOException;

import java.net.ServerSocket;

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

		Assert.assertEquals(result.output, 0, result.exitCode);

		Assert.assertTrue(
			result.output,
			result.output.contains(
				"This is the text which will be printed out."));
	}

	@Test
	public void testExecuteVersion() throws Throwable {
		testBefore();

		MavenExecutor.Result result = _mavenExecutor.execute(
			temporaryFolder.getRoot(), "--version");

		Assert.assertEquals(result.output, 0, result.exitCode);
		Assert.assertTrue(
			result.output, result.output.startsWith("Apache Maven "));
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

		int fakeProxyPort = _getFakePort(0);

		int fakeRepositoryPort = _getFakePort(fakeProxyPort);

		String proxyHost = _setSystemProperty("http.proxyHost", "127.0.0.1");
		String proxyPort = _setSystemProperty(
			"http.proxyPort", String.valueOf(fakeProxyPort));
		String repositoryUrl = _setSystemProperty(
			"repository.url", "http://127.0.0.1:" + fakeRepositoryPort);

		try {
			testBefore();

			MavenExecutor.Result result = _mavenExecutor.execute(
				projectDir, "initialize");

			Assert.assertNotEquals(result.output, 0, result.exitCode);
			Assert.assertTrue(
				result.output,
				result.output.contains("127.0.0.1:" + fakeProxyPort));
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

		int fakeRepositoryPort = _getFakePort(0);

		String repositoryUrl = _setSystemProperty(
			"repository.url", "http://127.0.0.1:" + fakeRepositoryPort);

		try {
			testBefore();

			MavenExecutor.Result result = _mavenExecutor.execute(
				projectDir, "initialize");

			Assert.assertNotEquals(result.output, 0, result.exitCode);
			Assert.assertTrue(
				result.output,
				result.output.contains("127.0.0.1:" + fakeRepositoryPort));
		}
		finally {
			_setSystemProperty("repository.url", repositoryUrl);
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private int _getFakePort(int excludedPort) throws IOException {
		for (int i = 0; i < _FAKE_PORT_RETRIES; i++) {
			try (ServerSocket serverSocket = new ServerSocket(0)) {
				int port = serverSocket.getLocalPort();

				if (excludedPort != port) {
					return port;
				}
			}
		}

		throw new IOException("Unable to find a random available port");
	}

	private String _setSystemProperty(String key, String value) {
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

	private static final int _FAKE_PORT_RETRIES = 20;

	private static String _mavenDistributionFileName;

	private MavenExecutor _mavenExecutor;

}