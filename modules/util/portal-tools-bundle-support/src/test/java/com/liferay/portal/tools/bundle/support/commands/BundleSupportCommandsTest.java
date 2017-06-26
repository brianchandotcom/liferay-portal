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
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.DateUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.ProxyAuthenticator;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupportCommandsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_authenticatedHttpProxyServer = _startHttpProxyServer(
			_AUTHENTICATED_HTTP_PROXY_SERVER_PORT, true,
			_authenticatedHttpProxyHit);

		URL url = BundleSupportCommandsTest.class.getResource(
			"dependencies" + _CONTEXT_PATH_ZIP);

		_bundleZipFile = new File(url.toURI());

		_httpProxyServer = _startHttpProxyServer(
			_HTTP_PROXY_SERVER_PORT, false, _httpProxyHit);

		_httpServer = _startHttpServer();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_authenticatedHttpProxyServer != null) {
			_authenticatedHttpProxyServer.stop();
		}

		if (_httpProxyServer != null) {
			_httpProxyServer.stop();
		}

		if (_httpServer != null) {
			_httpServer.stop(0);
		}
	}

	@Before
	public void setUp() throws Exception {
		_authenticatedHttpProxyHit.set(false);
		_httpProxyHit.set(false);
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
	public void testCreateToken() throws Exception {
		_testCreateToken(_CONTEXT_PATH_TOKEN);
	}

	@Test
	public void testCreateTokenForce() throws Exception {
		File tokenFile = temporaryFolder.newFile();

		_testCreateToken(_CONTEXT_PATH_TOKEN, true, tokenFile);
	}

	@Test
	public void testCreateTokenUnformatted() throws Exception {
		_testCreateToken(_CONTEXT_PATH_TOKEN_UNFORMATTED);
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
	public void testInitBundleTarDifferentLocale() throws Exception {
		Locale locale = Locale.getDefault();

		try {
			Locale.setDefault(Locale.ITALY);

			_testInitBundleTar(null, null, null, null, null, null, null);
		}
		finally {
			Locale.setDefault(locale);
		}
	}

	@Test
	public void testInitBundleTarProxy() throws Exception {
		_testInitBundleTar(
			"localhost", _HTTP_PROXY_SERVER_PORT, null, null, null,
			_httpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxyAuthenticated() throws Exception {
		_testInitBundleTar(
			"localhost", _AUTHENTICATED_HTTP_PROXY_SERVER_PORT,
			_HTTP_PROXY_SERVER_USER_NAME, _HTTP_PROXY_SERVER_PASSWORD, null,
			_authenticatedHttpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxyNonProxyHosts() throws Exception {
		_testInitBundleTar(
			"localhost", _HTTP_PROXY_SERVER_PORT, null, null,
			"localhost2.localdomain", _httpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxySkip() throws Exception {
		_testInitBundleTar(
			"localhost", _HTTP_PROXY_SERVER_PORT, null, null,
			"localhost.localdomain", _httpProxyHit, Boolean.FALSE);
	}

	@Test
	public void testInitBundleTarProxyUnauthorized() throws Exception {
		expectedException.expectMessage("Proxy Authentication Required");

		_testInitBundleTar(
			"localhost", _AUTHENTICATED_HTTP_PROXY_SERVER_PORT, null, null,
			null, _authenticatedHttpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleZip() throws Exception {
		_testInitBundleZip(null, _HTTP_SERVER_PASSWORD, _HTTP_SERVER_USER_NAME);
	}

	@Test
	public void testInitBundleZipFile() throws Exception {
		_testInitBundleZip(_bundleZipFile, null, null);
	}

	@Test
	public void testInitBundleZipUnauthorized() throws Exception {
		expectedException.expectMessage("Unauthorized");

		_testInitBundleZip(null, null, null);
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

	protected void createToken(
			String emailAddress, boolean force, String password, File tokenFile,
			URL tokenUrl)
		throws Exception {

		CreateTokenCommand createTokenCommand = new CreateTokenCommand();

		createTokenCommand.setEmailAddress(emailAddress);
		createTokenCommand.setForce(force);
		createTokenCommand.setPassword(password);
		createTokenCommand.setTokenFile(tokenFile);
		createTokenCommand.setTokenUrl(tokenUrl);

		createTokenCommand.execute();
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
			File cacheDir, File configsDir, String environment,
			File liferayHomeDir, String password, int stripComponents, URL url,
			String userName)
		throws Exception {

		InitBundleCommand initBundleCommand = new InitBundleCommand();

		initBundleCommand.setCacheDir(cacheDir);
		initBundleCommand.setConfigsDir(configsDir);
		initBundleCommand.setEnvironment(environment);
		initBundleCommand.setLiferayHomeDir(liferayHomeDir);
		initBundleCommand.setPassword(password);
		initBundleCommand.setStripComponents(stripComponents);
		initBundleCommand.setUrl(url);
		initBundleCommand.setUserName(userName);

		initBundleCommand.execute();
	}

	private static File _assertExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue(file.exists());

		return file;
	}

	private static void _assertNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse(file.exists());
	}

	private static void _assertPosixFilePermissions(
			File dir, String fileName,
			Set<PosixFilePermission> expectedPosixFilePermissions)
		throws IOException {

		File file = _assertExists(dir, fileName);

		Path path = file.toPath();

		if (!FileUtil.isPosixSupported(path)) {
			return;
		}

		Set<PosixFilePermission> actualPosixFilePermissions =
			Files.getPosixFilePermissions(path);

		Assert.assertEquals(
			expectedPosixFilePermissions, actualPosixFilePermissions);
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
		final String contentType, Authenticator authenticator) {

		HttpHandler httpHandler = new HttpHandler() {

			@Override
			public void handle(HttpExchange httpExchange) throws IOException {
				Headers headers = httpExchange.getResponseHeaders();

				headers.add(HttpHeaders.CONTENT_TYPE, contentType);

				URL url = BundleSupportCommandsTest.class.getResource(
					"dependencies" + contextPath);

				File file = new File(url.getFile());

				Date lastModifiedDate = new Date(file.lastModified());

				headers.add(
					HttpHeaders.LAST_MODIFIED,
					DateUtils.formatDate(lastModifiedDate));

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

		HttpContext httpContext = httpServer.createContext(
			contextPath, httpHandler);

		if (authenticator != null) {
			httpContext.setAuthenticator(authenticator);
		}

		return httpContext;
	}

	private static URL _getHttpServerUrl(String contextPath) throws Exception {
		return new URL(
			"http", "localhost.localdomain", _HTTP_SERVER_PORT, contextPath);
	}

	private static int _getTestPort(int... excludedPorts) throws IOException {
		for (int i = 0; i < _TEST_PORT_RETRIES; i++) {
			try (ServerSocket serverSocket = new ServerSocket(0)) {
				int port = serverSocket.getLocalPort();

				boolean found = false;

				for (int excludedPort : excludedPorts) {
					if (excludedPort == port) {
						found = true;

						break;
					}
				}

				if (!found) {
					return port;
				}
			}
		}

		throw new IOException("Unable to find a test port");
	}

	private static HttpProxyServer _startHttpProxyServer(
		int port, boolean authenticate, final AtomicBoolean hit) {

		HttpProxyServerBootstrap httpProxyServerBootstrap =
			DefaultHttpProxyServer.bootstrap();

		httpProxyServerBootstrap.withFiltersSource(
			new HttpFiltersSourceAdapter() {

				@Override
				public HttpFilters filterRequest(
					final HttpRequest httpRequest) {

					return new HttpFiltersAdapter(httpRequest) {

						@Override
						public HttpResponse clientToProxyRequest(
							HttpObject httpObject) {

							hit.set(true);

							return super.clientToProxyRequest(httpObject);
						}

					};
				}

			});

		httpProxyServerBootstrap.withPort(port);

		if (authenticate) {
			httpProxyServerBootstrap.withProxyAuthenticator(
				new ProxyAuthenticator() {

					@Override
					public boolean authenticate(
						String userName, String password) {

						if (_HTTP_PROXY_SERVER_USER_NAME.equals(userName) &&
							_HTTP_PROXY_SERVER_PASSWORD.equals(password)) {

							return true;
						}

						return false;
					}

					@Override
					public String getRealm() {
						return _HTTP_PROXY_SERVER_REALM;
					}

				});
		}

		return httpProxyServerBootstrap.start();
	}

	private static HttpServer _startHttpServer() throws IOException {
		HttpServer httpServer = HttpServer.create(
			new InetSocketAddress(_HTTP_SERVER_PORT), 0);

		Authenticator authenticator =
			new BasicAuthenticator(_HTTP_SERVER_REALM) {

				@Override
				public boolean checkCredentials(
					String username, String password) {

					if (username.equals(_HTTP_SERVER_USER_NAME) &&
						password.equals(_HTTP_SERVER_PASSWORD)) {

						return true;
					}

					return false;
				}

			};

		_createHttpContext(
			httpServer, _CONTEXT_PATH_TAR, "application/tar+gzip", null);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_TOKEN, "application/json", authenticator);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_TOKEN_UNFORMATTED, "application/json",
			authenticator);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_ZIP, "application/zip", authenticator);

		httpServer.setExecutor(null);

		httpServer.start();

		return httpServer;
	}

	private void _initBundle(
			File configsDir, File file, File liferayHomeDir, String password,
			String userName)
		throws Exception {

		File cacheDir = temporaryFolder.newFolder();
		URI uri = file.toURI();

		initBundle(
			cacheDir, configsDir, _INIT_BUNDLE_ENVIRONMENT, liferayHomeDir,
			password, _INIT_BUNDLE_STRIP_COMPONENTS, uri.toURL(), userName);
	}

	private void _initBundle(
			File configsDir, String contextPath, File liferayHomeDir,
			String password, String userName)
		throws Exception {

		File cacheDir = temporaryFolder.newFolder();
		URL url = _getHttpServerUrl(contextPath);

		initBundle(
			cacheDir, configsDir, _INIT_BUNDLE_ENVIRONMENT, liferayHomeDir,
			password, _INIT_BUNDLE_STRIP_COMPONENTS, url, userName);
	}

	private void _testCreateToken(String contextPath) throws Exception {
		File tokenFile = new File(temporaryFolder.getRoot(), "token");

		_testCreateToken(contextPath, false, tokenFile);
	}

	private void _testCreateToken(
			String contextPath, boolean force, File tokenFile)
		throws Exception {

		URL tokenUrl = _getHttpServerUrl(contextPath);

		createToken(
			_HTTP_SERVER_PASSWORD, force, _HTTP_SERVER_USER_NAME, tokenFile,
			tokenUrl);

		Assert.assertEquals("hello-world", FileUtil.read(tokenFile));
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
			String proxyPassword, String nonProxyHosts, AtomicBoolean proxyHit,
			Boolean expectedProxyHit)
		throws Exception {

		if (proxyHit != null) {
			Assert.assertFalse(proxyHit.get());
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

			if (proxyHit != null) {
				Assert.assertEquals(
					expectedProxyHit.booleanValue(), proxyHit.get());
			}

			_assertExists(liferayHomeDir, "README.markdown");
			_assertPosixFilePermissions(
				liferayHomeDir, "bin/hello.sh", _expectedPosixFilePermissions);
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

	private void _testInitBundleZip(File file, String password, String userName)
		throws Exception {

		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File configsDir = temporaryFolder.newFolder("configs");

		File configsLocalDir = _createDirectory(
			configsDir, _INIT_BUNDLE_ENVIRONMENT);

		File localPropertiesFile = _createFile(
			configsLocalDir, "portal-ext.properties");

		File configsProdDir = _createDirectory(configsDir, "prod");

		File prodPropertiesFile = _createFile(
			configsProdDir, "portal-prod.properties");

		if (file != null) {
			_initBundle(configsDir, file, liferayHomeDir, password, userName);
		}
		else {
			_initBundle(
				configsDir, _CONTEXT_PATH_ZIP, liferayHomeDir, password,
				userName);
		}

		_assertExists(liferayHomeDir, "README.markdown");
		_assertExists(liferayHomeDir, localPropertiesFile.getName());
		_assertNotExists(liferayHomeDir, prodPropertiesFile.getName());
		_assertPosixFilePermissions(
			liferayHomeDir, "bin/hello.sh", _expectedPosixFilePermissions);
	}

	private static final int _AUTHENTICATED_HTTP_PROXY_SERVER_PORT;

	private static final String _CONTEXT_PATH_TAR = "/test.tar.gz";

	private static final String _CONTEXT_PATH_TOKEN = "/token.json";

	private static final String _CONTEXT_PATH_TOKEN_UNFORMATTED =
		"/token_unformatted";

	private static final String _CONTEXT_PATH_ZIP = "/test.zip";

	private static final String _HTTP_PROXY_SERVER_PASSWORD = "proxyTest";

	private static final int _HTTP_PROXY_SERVER_PORT;

	private static final String _HTTP_PROXY_SERVER_REALM = "proxyTest";

	private static final String _HTTP_PROXY_SERVER_USER_NAME = "proxyTest";

	private static final String _HTTP_SERVER_PASSWORD = "test";

	private static final int _HTTP_SERVER_PORT;

	private static final String _HTTP_SERVER_REALM = "test";

	private static final String _HTTP_SERVER_USER_NAME = "test";

	private static final String _INIT_BUNDLE_ENVIRONMENT = "local";

	private static final int _INIT_BUNDLE_STRIP_COMPONENTS = 0;

	private static final int _TEST_PORT_RETRIES = 20;

	private static final AtomicBoolean _authenticatedHttpProxyHit =
		new AtomicBoolean();
	private static HttpProxyServer _authenticatedHttpProxyServer;
	private static File _bundleZipFile;
	private static final Set<PosixFilePermission>
		_expectedPosixFilePermissions = PosixFilePermissions.fromString(
			"rwxr-x---");
	private static final AtomicBoolean _httpProxyHit = new AtomicBoolean();
	private static HttpProxyServer _httpProxyServer;
	private static HttpServer _httpServer;

	static {
		try {
			_AUTHENTICATED_HTTP_PROXY_SERVER_PORT = _getTestPort();

			_HTTP_PROXY_SERVER_PORT = _getTestPort(
				_AUTHENTICATED_HTTP_PROXY_SERVER_PORT);

			_HTTP_SERVER_PORT = _getTestPort(
				_AUTHENTICATED_HTTP_PROXY_SERVER_PORT, _HTTP_PROXY_SERVER_PORT);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}