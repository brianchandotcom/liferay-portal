/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.felix.fileinstall.internal;

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
 * A URL handler that can jar a directory on the fly
 */
public class JarDirUrlHandler extends AbstractURLStreamHandlerService {

	public static final String PROTOCOL = "jardir";

	/**
	 * Open the connection for the given URL.
	 *
	 * @param url the url from which to open a connection.
	 * @return a connection on the specified URL.
	 * @throws IOException if an error occurs or if the URL is malformed.
	 */
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

								// Ignore

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
/* @generated */