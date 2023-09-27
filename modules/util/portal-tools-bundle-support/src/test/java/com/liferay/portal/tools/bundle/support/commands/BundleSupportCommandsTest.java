/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Arrays;
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

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupportCommandsTest extends HttpProxyMockServerSupport {

	@BeforeClass
	public static void setUpClass() throws Exception {
		authenticatedHttpProxyServer = startHttpProxyServer(
			AUTHENTICATED_HTTP_PROXY_SERVER_PORT, true,
			authenticatedHttpProxyHit);

		URL url = BundleSupportCommandsTest.class.getResource(
			"dependencies" + CONTEXT_PATH_ZIP);

		_bundleZipFile = new File(url.toURI());

		httpProxyServer = startHttpProxyServer(
			HTTP_PROXY_SERVER_PORT, false, httpProxyHit);

		httpServer = _startHttpServer();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (authenticatedHttpProxyServer != null) {
			authenticatedHttpProxyServer.stop();
		}

		if (httpProxyServer != null) {
			httpProxyServer.stop();
		}

		if (httpServer != null) {
			httpServer.stop(0);
		}
	}

	@Before
	public void setUp() throws Exception {
		authenticatedHttpProxyHit.set(false);
		httpProxyHit.set(false);
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
	public void testCopyConfigs() throws Exception {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File configsDir = temporaryFolder.newFolder("configs");

		File commonConfigsDir = _createDirectory(configsDir, "common");

		_createFile(commonConfigsDir, "common-configs.properties");

		File prodConfigsDir = _createDirectory(configsDir, "prod");

		_createFile(prodConfigsDir, "prod-configs.properties");

		File devOpsDir = temporaryFolder.newFolder("devops");

		File commonDevOpsDir = _createDirectory(devOpsDir, "common");

		_createFile(commonDevOpsDir, "common-devops.properties");

		File prodDevOpsDir = _createDirectory(devOpsDir, "prod");

		_createFile(prodDevOpsDir, "prod-devops.properties");

		copyConfigs(
			Arrays.asList(configsDir, devOpsDir), "prod", liferayHomeDir);

		File deployedCommonConfigsFile = new File(
			liferayHomeDir, "common-configs.properties");

		Assert.assertTrue(deployedCommonConfigsFile.exists());

		File deployedProdConfigsFile = new File(
			liferayHomeDir, "prod-configs.properties");

		Assert.assertTrue(deployedProdConfigsFile.exists());

		File deployedCommonDevOpsFile = new File(
			liferayHomeDir, "common-devops.properties");

		Assert.assertTrue(deployedCommonDevOpsFile.exists());

		File deployedProdDevOpsFile = new File(
			liferayHomeDir, "prod-devops.properties");

		Assert.assertTrue(deployedProdDevOpsFile.exists());
	}

	@Test
	public void testCreateToken() throws Exception {
		_testCreateToken(_CONTEXT_PATH_TOKEN);
	}

	@Test
	public void testCreateTokenForce() throws Exception {
		File tokenFile = temporaryFolder.newFile();

		_testCreateToken(_CONTEXT_PATH_TOKEN, true, null, tokenFile);
	}

	@Test
	public void testCreateTokenInNonexistentDirectory() throws Exception {
		File tokenFile = new File(
			temporaryFolder.getRoot(), "nonexistent/directory/token");

		_testCreateToken(_CONTEXT_PATH_TOKEN, false, null, tokenFile);
	}

	@Test
	public void testCreateTokenPasswordFile() throws Exception {
		File passwordFile = temporaryFolder.newFile();
		File tokenFile = temporaryFolder.newFile();

		Files.write(
			passwordFile.toPath(),
			HTTP_SERVER_PASSWORD.getBytes(StandardCharsets.UTF_8));

		_testCreateToken(_CONTEXT_PATH_TOKEN, true, passwordFile, tokenFile);
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
	public void testDistBundle7z() throws Exception {
		_testDistBundle("7z");
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
	public void testDownloadCommandQuiet() throws Exception {
		DownloadCommand downloadCommand = new DownloadCommand();

		downloadCommand.setCacheDir(temporaryFolder.newFolder("cacheDir"));
		downloadCommand.setPassword(HTTP_SERVER_PASSWORD);
		downloadCommand.setQuiet(true);
		downloadCommand.setUrl(getHttpServerUrl(CONTEXT_PATH_ZIP));
		downloadCommand.setUserName(HTTP_SERVER_USER_NAME);

		PrintStream printStream = System.out;

		try {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			System.setOut(new PrintStream(byteArrayOutputStream));

			downloadCommand.execute();

			String output = new String(byteArrayOutputStream.toByteArray());

			Assert.assertTrue(output, output.isEmpty());
		}
		finally {
			System.setOut(printStream);
		}
	}

	@Test
	public void testInitBundle7z() throws Exception {
		_testInitBundle(
			getHttpServerUrl(_CONTEXT_PATH_7Z), HTTP_SERVER_PASSWORD,
			HTTP_SERVER_USER_NAME);
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
			"localhost", HTTP_PROXY_SERVER_PORT, null, null, null, httpProxyHit,
			Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxyAuthenticated() throws Exception {
		_testInitBundleTar(
			"localhost", AUTHENTICATED_HTTP_PROXY_SERVER_PORT,
			HTTP_PROXY_SERVER_USER_NAME, HTTP_PROXY_SERVER_PASSWORD, null,
			authenticatedHttpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxyNonproxyHosts() throws Exception {
		_testInitBundleTar(
			"localhost", HTTP_PROXY_SERVER_PORT, null, null,
			"localhost2.localdomain", httpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleTarProxySkip() throws Exception {
		_testInitBundleTar(
			"localhost", HTTP_PROXY_SERVER_PORT, null, null,
			"localhost.localdomain", httpProxyHit, Boolean.FALSE);
	}

	@Test
	public void testInitBundleTarProxyUnauthorized() throws Exception {
		expectedException.expectMessage("Proxy Authentication Required");

		_testInitBundleTar(
			"localhost", AUTHENTICATED_HTTP_PROXY_SERVER_PORT, null, null, null,
			authenticatedHttpProxyHit, Boolean.TRUE);
	}

	@Test
	public void testInitBundleZip() throws Exception {
		_testInitBundle(
			getHttpServerUrl(CONTEXT_PATH_ZIP), HTTP_SERVER_PASSWORD,
			HTTP_SERVER_USER_NAME);
	}

	@Test
	public void testInitBundleZipFile() throws Exception {
		URI uri = _bundleZipFile.toURI();

		_testInitBundle(uri.toURL(), null, null);
	}

	@Test
	public void testInitBundleZipUnauthorized() throws Exception {
		expectedException.expectMessage("Unauthorized");

		_testInitBundle(getHttpServerUrl(CONTEXT_PATH_ZIP), null, null);
	}

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

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

	private static HttpServer _startHttpServer() throws Exception {
		HttpServer httpServer = HttpServer.create(
			new InetSocketAddress(HTTP_SERVER_PORT), 0);

		Authenticator authenticator = new BasicAuthenticator(
			HTTP_SERVER_REALM) {

			@Override
			public boolean checkCredentials(String userName, String password) {
				if (userName.equals(HTTP_SERVER_USER_NAME) &&
					password.equals(HTTP_SERVER_PASSWORD)) {

					return true;
				}

				return false;
			}

		};

		_createHttpContext(
			httpServer, _CONTEXT_PATH_7Z, "application/x-7z-compressed", null);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_TAR, "application/tar+gzip", null);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_TOKEN, "application/json", authenticator);
		_createHttpContext(
			httpServer, _CONTEXT_PATH_TOKEN_UNFORMATTED, "application/json",
			authenticator);
		_createHttpContext(
			httpServer, CONTEXT_PATH_ZIP, "application/zip", authenticator);

		httpServer.setExecutor(null);

		httpServer.start();

		return httpServer;
	}

	private File _assertExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue(file.exists());

		return file;
	}

	private void _assertNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse(file.exists());
	}

	private void _assertPosixFilePermissions(
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

	private File _createDirectory(File parentDir, String dirName) {
		File dir = new File(parentDir, dirName);

		Assert.assertTrue(dir.mkdirs());

		return dir;
	}

	private File _createFile(File dir, String fileName) throws Exception {
		File file = new File(dir, fileName);

		Assert.assertTrue(file.createNewFile());

		return file;
	}

	private void _initBundle(
			File configsDir, File liferayHomeDir, String password,
			int stripComponent, URL url, String userName)
		throws Exception {

		File cacheDir = temporaryFolder.newFolder();

		initBundle(
			cacheDir, configsDir, _INIT_BUNDLE_ENVIRONMENT, liferayHomeDir,
			password, stripComponent, url, userName);
	}

	private void _initBundle(
			File configsDir, File liferayHomeDir, String password, URL url,
			String userName)
		throws Exception {

		_initBundle(
			configsDir, liferayHomeDir, password, _INIT_BUNDLE_STRIP_COMPONENTS,
			url, userName);
	}

	private void _testCreateToken(String contextPath) throws Exception {
		File tokenFile = new File(temporaryFolder.getRoot(), "token");

		_testCreateToken(contextPath, false, null, tokenFile);
	}

	private void _testCreateToken(
			String contextPath, boolean force, File passwordFile,
			File tokenFile)
		throws Exception {

		String password = null;

		if (passwordFile == null) {
			password = HTTP_SERVER_PASSWORD;
		}

		URL tokenURL = getHttpServerUrl(contextPath);

		createToken(
			HTTP_SERVER_USER_NAME, force, password, passwordFile, tokenFile,
			tokenURL);

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

	private void _testInitBundle(URL url, String password, String userName)
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

		_initBundle(configsDir, liferayHomeDir, password, url, userName);

		_assertExists(liferayHomeDir, "README.markdown");
		_assertExists(liferayHomeDir, "empty-folder");
		_assertExists(liferayHomeDir, localPropertiesFile.getName());
		_assertNotExists(liferayHomeDir, prodPropertiesFile.getName());
		_assertPosixFilePermissions(
			liferayHomeDir, "bin/hello.sh", _expectedPosixFilePermissions);

		_initBundle(configsDir, liferayHomeDir, password, 1, url, userName);

		_assertExists(liferayHomeDir, "README.markdown");
		_assertExists(liferayHomeDir, localPropertiesFile.getName());
		_assertNotExists(liferayHomeDir, "empty-folder");
		_assertNotExists(liferayHomeDir, prodPropertiesFile.getName());
		_assertPosixFilePermissions(
			liferayHomeDir, "hello.sh", _expectedPosixFilePermissions);
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

			URL url = getHttpServerUrl(_CONTEXT_PATH_TAR);

			_initBundle(null, liferayHomeDir, null, url, null);

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

	private static final String _CONTEXT_PATH_7Z = "/test.7z";

	private static final String _CONTEXT_PATH_TAR = "/test.tar.gz";

	private static final String _CONTEXT_PATH_TOKEN = "/token.json";

	private static final String _CONTEXT_PATH_TOKEN_UNFORMATTED =
		"/token_unformatted";

	private static final String _INIT_BUNDLE_ENVIRONMENT = "local";

	private static final int _INIT_BUNDLE_STRIP_COMPONENTS = 0;

	private static File _bundleZipFile;
	private static final Set<PosixFilePermission>
		_expectedPosixFilePermissions = PosixFilePermissions.fromString(
			"rwxr-x---");

}