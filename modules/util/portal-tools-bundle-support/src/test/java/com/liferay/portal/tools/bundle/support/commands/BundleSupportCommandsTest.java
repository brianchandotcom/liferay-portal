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

package com.liferay.portal.tools.bundle.support.commands;

import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.URL;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupportCommandsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_httpServer = _startHttpServer();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_httpServer != null) {
			_httpServer.stop(0);
		}
	}

	@Test
	public void testClean() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File osgiModulesDir = _createDirectory(liferayHomeDir, "osgi/modules");

		File jarFile = _createFile(osgiModulesDir, "test.jar");

		File osgiWarDir = _createDirectory(liferayHomeDir, "osgi/war");

		File warFile = _createFile(osgiWarDir, "test.war");

		clean(warFile.getName(), liferayHomeDir);

		Assert.assertFalse(warFile.exists());

		Assert.assertTrue(jarFile.exists());
	}

	@Test
	public void testDeployJar() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File file = temporaryFolder.newFile("test-1.0.0.jar");
		File deployedFile = new File(liferayHomeDir, "osgi/modules/test.jar");

		deploy(file, liferayHomeDir, deployedFile.getName());

		Assert.assertTrue(deployedFile.exists());
	}

	@Test
	public void testDeployWar() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File file = temporaryFolder.newFile("test-1.0.0.war");
		File deployedFile = new File(liferayHomeDir, "osgi/war/test.war");

		deploy(file, liferayHomeDir, deployedFile.getName());

		Assert.assertTrue(deployedFile.exists());
	}

	@Test
	public void testDistBundleTar() throws Exception {
		_testDistBundle("tar.gz");
	}

	@Test
	public void testDistBundleZip() throws Exception {
		_testDistBundle("zip");
	}

	@Test
	public void testInitBundleTar() throws Exception {
		_testInitBundleTar(null, null, null, null, null, null, null);
	}

	@Test
	public void testInitBundleZip() throws Exception {
		_testInitBundleZip(_HTTP_SERVER_PASSWORD, _HTTP_SERVER_USER_NAME);
	}

	@Test
	public void testInitBundleZipUnauthorized() throws Exception {
		expectedException.expectMessage("Unauthorized");

		_testInitBundleZip(null, null);
	}

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected void clean(String fileName, File liferayHomeDir)
		throws Exception {

		CleanCommand cleanCommand = new CleanCommand();

		cleanCommand.setFileName(fileName);
		cleanCommand.setLiferayHomeDir(liferayHomeDir);

		cleanCommand.execute();
	}

	protected void deploy(File file, File liferayHomeDir, String outputFileName)
		throws Exception {

		DeployCommand deployCommand = new DeployCommand();

		deployCommand.setFile(file);
		deployCommand.setLiferayHomeDir(liferayHomeDir);
		deployCommand.setOutputFileName(outputFileName);

		deployCommand.execute();
	}

	protected void distBundle(
			String format, File liferayHomeDir, File outputFile)
		throws Exception {

		DistBundleCommand distBundleCommand = new DistBundleCommand();

		distBundleCommand.setFormat(format);
		distBundleCommand.setIncludeFolder(true);
		distBundleCommand.setLiferayHomeDir(liferayHomeDir);
		distBundleCommand.setOutputFile(outputFile);

		distBundleCommand.execute();
	}

	protected void initBundle(
			File cacheDir, File configsDir, File liferayHomeDir,
			String password, URL url, String userName)
		throws Exception {

		InitBundleCommand initBundleCommand = new InitBundleCommand();

		initBundleCommand.setCacheDir(cacheDir);
		initBundleCommand.setConfigsDir(configsDir);
		initBundleCommand.setEnvironment("local");
		initBundleCommand.setLiferayHomeDir(liferayHomeDir);
		initBundleCommand.setPassword(password);
		initBundleCommand.setStripComponents(0);
		initBundleCommand.setUrl(url);
		initBundleCommand.setUserName(userName);

		initBundleCommand.execute();
	}

	private static void _assertExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue(file.exists());
	}

	private static void _assertNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse(file.exists());
	}

	private static File _createDirectory(File parentDir, String dirName) {
		File dir = new File(parentDir, dirName);

		Assert.assertTrue(dir.mkdirs());

		return dir;
	}

	private static File _createFile(File dir, String fileName)
		throws IOException {

		File file = new File(dir, fileName);

		Assert.assertTrue(file.createNewFile());

		return file;
	}

	private static HttpContext _createHttpContext(
		HttpServer httpServer, final String contextPath,
		final String contentType) {

		HttpHandler httpHandler = new HttpHandler() {

			@Override
			public void handle(HttpExchange httpExchange) throws IOException {
				Headers headers = httpExchange.getResponseHeaders();

				headers.add(HttpHeaders.CONTENT_TYPE, contentType);

				URL url = BundleSupportCommandsTest.class.getResource(
					"dependencies" + contextPath);

				File file = new File(url.getFile());

				try (BufferedInputStream bufferedInputStream =
						new BufferedInputStream(new FileInputStream(file))) {

					int length = (int)file.length();

					byte[] byteArray = new byte[length];

					bufferedInputStream.read(byteArray, 0, length);

					httpExchange.sendResponseHeaders(HttpStatus.SC_OK, length);

					OutputStream outputStream = httpExchange.getResponseBody();

					outputStream.write(byteArray, 0, length);

					outputStream.close();
				}
			}

		};

		return httpServer.createContext(contextPath, httpHandler);
	}

	private static HttpServer _startHttpServer() throws IOException {
		HttpServer httpServer = HttpServer.create(
			new InetSocketAddress(_HTTP_SERVER_PORT), 0);

		HttpContext httpContext = _createHttpContext(
			httpServer, _CONTEXT_PATH_ZIP, "application/zip");

		httpContext.setAuthenticator(
			new BasicAuthenticator(_HTTP_SERVER_REALM) {

				@Override
				public boolean checkCredentials(String user, String pwd) {
					if (user.equals(_HTTP_SERVER_USER_NAME) &&
						pwd.equals(_HTTP_SERVER_PASSWORD)) {

						return true;
					}

					return false;
				}

			});

		_createHttpContext(
			httpServer, _CONTEXT_PATH_TAR, "application/tar+gzip");

		httpServer.setExecutor(null);

		httpServer.start();

		return httpServer;
	}

	private void _initBundle(
			File configsDir, String contextPath, File liferayHomeDir,
			String password, String userName)
		throws Exception {

		File cacheDir = temporaryFolder.newFolder();
		URL url = new URL(
			"http", "localhost.localdomain", _HTTP_SERVER_PORT, contextPath);

		initBundle(
			cacheDir, configsDir, liferayHomeDir, password, url, userName);
	}

	private void _testDistBundle(String format) throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File osgiWarDir = _createDirectory(liferayHomeDir, "osgi/war");

		_createFile(osgiWarDir, "test.war");

		File outputFile = new File(temporaryFolder.getRoot(), "out." + format);

		Assert.assertFalse(outputFile.exists());

		distBundle(format, liferayHomeDir, outputFile);

		Assert.assertTrue(outputFile.exists());
	}

	private void _testInitBundleTar(
			String proxyHost, Integer proxyPort, String proxyUser,
			String proxyPassword, String nonProxyHosts,
			AtomicInteger proxyCounter, Integer expectedCounter)
		throws Exception {

		if (proxyCounter != null) {
			Assert.assertEquals(0, proxyCounter.get());
		}

		proxyHost = BundleSupportUtil.setSystemProperty(
			"http.proxyHost", proxyHost);
		proxyPort = BundleSupportUtil.setSystemProperty(
			"http.proxyPort", proxyPort);
		proxyUser = BundleSupportUtil.setSystemProperty(
			"http.proxyUser", proxyUser);
		proxyPassword = BundleSupportUtil.setSystemProperty(
			"http.proxyPassword", proxyPassword);
		nonProxyHosts = BundleSupportUtil.setSystemProperty(
			"http.nonProxyHosts", nonProxyHosts);

		try {
			File liferayHomeDir = temporaryFolder.newFolder("bundles");

			_initBundle(null, _CONTEXT_PATH_TAR, liferayHomeDir, null, null);

			if (proxyCounter != null) {
				Assert.assertEquals(
					expectedCounter.intValue(), proxyCounter.intValue());
			}

			_assertExists(liferayHomeDir, "README.markdown");
		}
		finally {
			BundleSupportUtil.setSystemProperty("http.proxyHost", proxyHost);
			BundleSupportUtil.setSystemProperty("http.proxyPort", proxyPort);
			BundleSupportUtil.setSystemProperty("http.proxyUser", proxyUser);
			BundleSupportUtil.setSystemProperty(
				"http.proxyPassword", proxyPassword);
			BundleSupportUtil.setSystemProperty(
				"http.nonProxyHosts", nonProxyHosts);
		}
	}

	private void _testInitBundleZip(String password, String userName)
		throws Exception {

		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File configsDir = temporaryFolder.newFolder("configs");

		File configsLocalDir = _createDirectory(configsDir, "local");

		File localPropertiesFile = _createFile(
			configsLocalDir, "portal-ext.properties");

		File configsProdDir = _createDirectory(configsDir, "prod");

		File prodPropertiesFile = _createFile(
			configsProdDir, "portal-prod.properties");

		_initBundle(
			configsDir, _CONTEXT_PATH_ZIP, liferayHomeDir, password, userName);

		_assertExists(liferayHomeDir, "README.markdown");
		_assertExists(liferayHomeDir, localPropertiesFile.getName());
		_assertNotExists(liferayHomeDir, prodPropertiesFile.getName());
	}

	private static final String _CONTEXT_PATH_TAR = "/test.tar.gz";

	private static final String _CONTEXT_PATH_ZIP = "/test.zip";

	private static final String _HTTP_SERVER_PASSWORD = "test";

	private static final int _HTTP_SERVER_PORT = 8888;

	private static final String _HTTP_SERVER_REALM = "test";

	private static final String _HTTP_SERVER_USER_NAME = "test";

	private static HttpServer _httpServer;

}