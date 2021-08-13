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

import com.sun.net.httpserver.HttpServer;

import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.io.File;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.URL;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.ProxyAuthenticator;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * @author Seiphon Wang
 */
public class HttpProxyMockServerSupport {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected static URL getHttpServerUrl(String contextPath) throws Exception {
		return new URL(
			"http", "localhost.localdomain", HTTP_SERVER_PORT, contextPath);
	}

	protected static HttpProxyServer startHttpProxyServer(
		int port, boolean authenticate, final AtomicBoolean hit) {

		HttpProxyServerBootstrap httpProxyServerBootstrap =
			DefaultHttpProxyServer.bootstrap();

		httpProxyServerBootstrap.withFiltersSource(
			new HttpFiltersSourceAdapter() {

				@Override
				public HttpFilters filterRequest(HttpRequest httpRequest) {
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

						if (HTTP_PROXY_SERVER_USER_NAME.equals(userName) &&
							HTTP_PROXY_SERVER_PASSWORD.equals(password)) {

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

	protected void clean(String fileName, File liferayHomeDir)
		throws Exception {

		CleanCommand cleanCommand = new CleanCommand();

		cleanCommand.setFileName(fileName);
		cleanCommand.setLiferayHomeDir(liferayHomeDir);

		cleanCommand.execute();
	}

	protected void copyConfigs(
			List<File> configsDirs, String environment, File liferayHomeDir)
		throws Exception {

		CopyConfigsCommand copyConfigsCommand = new CopyConfigsCommand();

		copyConfigsCommand.setConfigsDirs(configsDirs);
		copyConfigsCommand.setEnvironment(environment);
		copyConfigsCommand.setLiferayHomeDir(liferayHomeDir);

		copyConfigsCommand.execute();
	}

	protected void createToken(
			String emailAddress, boolean force, String password,
			File passwordFile, File tokenFile, URL tokenURL)
		throws Exception {

		CreateTokenCommand createTokenCommand = new CreateTokenCommand();

		createTokenCommand.setEmailAddress(emailAddress);
		createTokenCommand.setForce(force);
		createTokenCommand.setPassword(password);
		createTokenCommand.setPasswordFile(passwordFile);
		createTokenCommand.setTokenFile(tokenFile);
		createTokenCommand.setTokenUrl(tokenURL);

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

	protected static final int AUTHENTICATED_HTTP_PROXY_SERVER_PORT;

	protected static final String CONTEXT_PATH_ZIP = "/test.zip";

	protected static final String HTTP_PROXY_SERVER_PASSWORD = "proxyTest";

	protected static final int HTTP_PROXY_SERVER_PORT;

	protected static final String HTTP_PROXY_SERVER_USER_NAME = "proxyTest";

	protected static final String HTTP_SERVER_PASSWORD = "test";

	protected static final int HTTP_SERVER_PORT;

	protected static final String HTTP_SERVER_REALM = "test";

	protected static final String HTTP_SERVER_USER_NAME = "test";

	protected static final AtomicBoolean authenticatedHttpProxyHit =
		new AtomicBoolean();
	protected static HttpProxyServer authenticatedHttpProxyServer;
	protected static final AtomicBoolean httpProxyHit = new AtomicBoolean();
	protected static HttpProxyServer httpProxyServer;
	protected static HttpServer httpServer;

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

	private static final String _HTTP_PROXY_SERVER_REALM = "proxyTest";

	private static final int _TEST_PORT_RETRIES = 20;

	static {
		try {
			AUTHENTICATED_HTTP_PROXY_SERVER_PORT = _getTestPort();

			HTTP_PROXY_SERVER_PORT = _getTestPort(
				AUTHENTICATED_HTTP_PROXY_SERVER_PORT);

			HTTP_SERVER_PORT = _getTestPort(
				AUTHENTICATED_HTTP_PROXY_SERVER_PORT, HTTP_PROXY_SERVER_PORT);
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}
	}

}