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

package com.liferay.portal.tools.bundle.support;

import com.liferay.portal.tools.bundle.support.commands.CleanCommand;
import com.liferay.portal.tools.bundle.support.commands.DeployCommand;
import com.liferay.portal.tools.bundle.support.commands.DistBundleCommand;
import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;

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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 */
public class BundleSupportTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_httpServer = HttpServer.create(new InetSocketAddress(8888), 0);

		HttpContext httpContext = _httpServer.createContext(
			"/test.zip",
			new HttpHandler() {

				@Override
				public void handle(HttpExchange httpExchange)
					throws IOException {

					Headers headers = httpExchange.getResponseHeaders();

					headers.add("Content-Type", "application/zip");

					URL url = BundleSupportTest.class.getResource(
						"dependencies/test.zip");

					File file = new File(url.getFile());

					try (BufferedInputStream bufferedInputStream =
							new BufferedInputStream(
								new FileInputStream(file))) {

						int length = (int)file.length();

						byte[] byteArray = new byte[(int)length];

						bufferedInputStream.read(byteArray, 0, length);

						httpExchange.sendResponseHeaders(200, length);

						OutputStream outputStream =
							httpExchange.getResponseBody();

						outputStream.write(byteArray, 0, length);

						outputStream.close();
					}
				}

			});

		httpContext.setAuthenticator(
			new BasicAuthenticator("test") {

				@Override
				public boolean checkCredentials(String user, String pwd) {
					if (user.equals("test") && pwd.equals("test")) {
						return true;
					}

					return false;
				}

			});

		_httpServer.createContext(
			"/test.tar.gz",
			new HttpHandler() {

				@Override
				public void handle(HttpExchange httpExchange)
					throws IOException {

					Headers headers = httpExchange.getResponseHeaders();

					headers.add("Content-Type", "application/tar+gzip");

					URL zipURL = BundleSupportTest.class.getResource(
						"dependencies/test.tar.gz");

					File file = new File(zipURL.getFile());

					long fileSize = file.length();

					byte[] byteArray = new byte[(int)fileSize];

					BufferedInputStream bufferedInputStream =
						new BufferedInputStream(new FileInputStream(file));

					int length = byteArray.length;

					bufferedInputStream.read(byteArray, 0, length);

					httpExchange.sendResponseHeaders(200, fileSize);

					OutputStream os = httpExchange.getResponseBody();

					os.write(byteArray, 0, length);

					os.close();

					bufferedInputStream.close();
				}

			});

		_httpServer.setExecutor(null);

		_httpServer.start();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_httpServer != null) {
			_httpServer.stop(0);
		}
	}

	@Test
	public void testCommandClean() throws Exception {
		File warFolder = temporaryFolder.newFolder("bundles", "osgi", "war");

		File testWar = new File(warFolder, "test.war");

		testWar.createNewFile();

		File jarFolder = temporaryFolder.newFolder(
			"bundles", "osgi", "modules");

		File testJar = new File(jarFolder, "test.jar");

		testJar.createNewFile();

		CleanCommand cleanCommand = new CleanCommand();

		cleanCommand.setFileName("test.war");
		cleanCommand.setLiferayHomeDir(
			new File(temporaryFolder.getRoot(), "bundles"));

		cleanCommand.execute();

		Assert.assertFalse(testWar.exists());

		Assert.assertTrue(testJar.exists());
	}

	@Test
	public void testCommandDeployJar() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		DeployCommand deployCommand = new DeployCommand();

		deployCommand.setFile(temporaryFolder.newFile("test-1.0.0.jar"));
		deployCommand.setLiferayHomeDir(liferayHomeDir);
		deployCommand.setOutputFileName("test.jar");

		deployCommand.execute();

		File deployedJarFile = new File(
			liferayHomeDir, "osgi/modules/test.jar");

		Assert.assertTrue(deployedJarFile.exists());
	}

	@Test
	public void testCommandDeployWar() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		DeployCommand deployCommand = new DeployCommand();

		deployCommand.setFile(temporaryFolder.newFile("test-1.0.0.war"));
		deployCommand.setLiferayHomeDir(liferayHomeDir);
		deployCommand.setOutputFileName("test.war");

		deployCommand.execute();

		File deployedWarFile = new File(liferayHomeDir, "osgi/war/test.war");

		Assert.assertTrue(deployedWarFile.exists());
	}

	@Test
	public void testCommandDistBundleTar() throws Exception {
		File warFolder = temporaryFolder.newFolder("bundles", "osgi", "war");

		File testWar = new File(warFolder, "test.war");

		testWar.createNewFile();

		File outputFile = new File(temporaryFolder.getRoot(), "out.tar.gz");

		Assert.assertFalse(outputFile.exists());

		DistBundleCommand distBundleCommand = new DistBundleCommand();

		distBundleCommand.setFormat("tar.gz");
		distBundleCommand.setIncludeFolder(true);
		distBundleCommand.setLiferayHomeDir(
			new File(temporaryFolder.getRoot(), "bundles"));
		distBundleCommand.setOutputFile(outputFile);

		distBundleCommand.execute();

		Assert.assertTrue(outputFile.exists());
	}

	@Test
	public void testCommandDistBundleZip() throws Exception {
		File warFolder = temporaryFolder.newFolder("bundles", "osgi", "war");

		File testWar = new File(warFolder, "test.war");

		testWar.createNewFile();

		File outputFile = new File(temporaryFolder.getRoot(), "out.zip");

		Assert.assertFalse(outputFile.exists());

		DistBundleCommand distBundleCommand = new DistBundleCommand();

		distBundleCommand.setFormat("zip");
		distBundleCommand.setIncludeFolder(true);
		distBundleCommand.setLiferayHomeDir(
			new File(temporaryFolder.getRoot(), "bundles"));
		distBundleCommand.setOutputFile(outputFile);

		distBundleCommand.execute();

		Assert.assertTrue(outputFile.exists());
	}

	@Test
	public void testCommandInitBundleTar() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		InitBundleCommand initBundleCommand = new InitBundleCommand();

		initBundleCommand.setConfigsDir(null);
		initBundleCommand.setEnvironment("local");
		initBundleCommand.setLiferayHomeDir(liferayHomeDir);
		initBundleCommand.setPassword("test");
		initBundleCommand.setProxyHost(null);
		initBundleCommand.setProxyPassword(null);
		initBundleCommand.setProxyPort(0);
		initBundleCommand.setProxyProtocol(null);
		initBundleCommand.setProxyUsername(null);
		initBundleCommand.setStripComponents(0);
		initBundleCommand.setUrl("http://localhost:8888/test.tar.gz");
		initBundleCommand.setUsername("test");

		initBundleCommand.execute();

		Assert.assertTrue(liferayHomeDir.listFiles().length > 0);

		File readmeFile = new File(liferayHomeDir, "README.markdown");

		Assert.assertTrue(readmeFile.exists());
	}

	@Test
	public void testCommandInitBundleZip() throws Exception {
		File local = temporaryFolder.newFolder("configs", "local");

		File propertiesExtFile = new File(local, "portal-ext.properties");

		propertiesExtFile.createNewFile();

		File prod = temporaryFolder.newFolder("configs", "prod");

		File propertiesProdFile = new File(prod, "portal-prod.properties");

		propertiesProdFile.createNewFile();

		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		InitBundleCommand initBundleCommand = new InitBundleCommand();

		initBundleCommand.setConfigsDir(
			new File(temporaryFolder.getRoot(), "configs"));
		initBundleCommand.setEnvironment("local");
		initBundleCommand.setLiferayHomeDir(liferayHomeDir);
		initBundleCommand.setPassword("test");
		initBundleCommand.setProxyHost(null);
		initBundleCommand.setProxyPassword(null);
		initBundleCommand.setProxyPort(0);
		initBundleCommand.setProxyProtocol(null);
		initBundleCommand.setProxyUsername(null);
		initBundleCommand.setStripComponents(0);
		initBundleCommand.setUrl("http://localhost:8888/test.tar.gz");
		initBundleCommand.setUsername(null);

		initBundleCommand.execute();

		Assert.assertTrue(liferayHomeDir.listFiles().length > 0);

		File readmeFile = new File(liferayHomeDir, "README.markdown");

		Assert.assertTrue(readmeFile.exists());

		File testPropertiesExtFile = new File(
			liferayHomeDir, "portal-ext.properties");

		Assert.assertTrue(testPropertiesExtFile.exists());

		File testPropertiesProdFile = new File(
			liferayHomeDir, "portal-prod.properties");

		Assert.assertFalse(testPropertiesProdFile.exists());
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static HttpServer _httpServer;

}