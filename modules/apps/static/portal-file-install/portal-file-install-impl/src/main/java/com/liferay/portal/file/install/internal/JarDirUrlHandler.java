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

package com.liferay.portal.file.install.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.osgi.service.url.AbstractURLStreamHandlerService;

/**
 * @author Matthew Tambara
 */
public class JarDirUrlHandler extends AbstractURLStreamHandlerService {

	public static final String PROTOCOL = "jardir";

	@Override
	public URLConnection openConnection(URL url) throws IOException {
		String path = url.getPath();

		if (path == null) {
			throw new MalformedURLException(
				"Path can not be null. Syntax: " + _SYNTAX);
		}

		path = path.trim();

		if (path.length() == 0) {
			throw new MalformedURLException(
				"Path can not be empty. Syntax: " + _SYNTAX);
		}

		return new Connection(url);
	}

	public class Connection extends URLConnection {

		public Connection(URL url) {
			super(url);
		}

		@Override
		public void connect() throws IOException {
		}

		@Override
		public InputStream getInputStream() throws IOException {
			try {
				PipedOutputStream pipedOutputStream = new PipedOutputStream();

				InputStream inputStream = new PipedInputStream(
					pipedOutputStream);

				Thread thread = new Thread() {

					@Override
					public void run() {
						URL url = getURL();

						try {
							Util.jarDir(
								new File(url.getPath()), pipedOutputStream);
						}
						catch (IOException ioException1) {
							try {
								pipedOutputStream.close();
							}
							catch (IOException ioException2) {
							}
						}
					}

				};

				thread.start();

				return inputStream;
			}
			catch (Exception exception) {
				throw new IOException(
					"Error opening spring xml url", exception);
			}
		}

	}

	private static final String _SYNTAX = PROTOCOL + ": file";

}