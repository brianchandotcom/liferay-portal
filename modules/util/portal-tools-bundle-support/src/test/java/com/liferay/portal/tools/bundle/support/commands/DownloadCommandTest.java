/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.commands;

import com.sun.net.httpserver.Authenticator;
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

import java.util.Date;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.DateUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Seiphon Wang
 */
public class DownloadCommandTest extends HttpProxyMockServerSupport {

	@BeforeClass
	public static void setUpClass() throws Exception {
		authenticatedHttpProxyServer = startHttpProxyServer(
			AUTHENTICATED_HTTP_PROXY_SERVER_PORT, true,
			authenticatedHttpProxyHit);

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
	public void testDownloadAttachmentFile() throws Exception {
		String fileName = "test.zip";

		File temp = temporaryFolder.newFolder("cacheDir");

		DownloadCommand downloadCommand = new DownloadCommand();

		downloadCommand.setCacheDir(temp);
		downloadCommand.setPassword(HTTP_SERVER_PASSWORD);
		downloadCommand.setToken(false);
		downloadCommand.setUrl(getHttpServerUrl("/" + fileName));
		downloadCommand.setUserName(HTTP_SERVER_USER_NAME);
		downloadCommand.setQuiet(true);

		downloadCommand.execute();

		_assertExists(temp, fileName);
	}

	private static HttpContext _createHttpContext(
		HttpServer httpServer, final String contextPath,
		final String contentType, Authenticator authenticator) {

		HttpHandler httpHandler = new HttpHandler() {

			@Override
			public void handle(HttpExchange httpExchange) throws IOException {
				Headers headers = httpExchange.getResponseHeaders();

				headers.add(HttpHeaders.CONTENT_TYPE, contentType);

				headers.add(
					"Content-Disposition", "attachment; filename=test.zip");

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

}