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

package com.liferay.portal.tools.target.platform.indexer.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class BytesURLSupport {

	public static void init() {
		URL.setURLStreamHandlerFactory(
			new URLStreamHandlerFactory() {

				@Override
				public URLStreamHandler createURLStreamHandler(
					String protocol) {

					if (!protocol.equals("bytes")) {
						return null;
					}

					return new URLStreamHandler() {

						@Override
						protected URLConnection openConnection(URL url) {
							return new URLConnection(url) {

								@Override
								public void connect() {
								}

								@Override
								public InputStream getInputStream()
									throws IOException {

									byte[] bytes = _bytesMap.get(url);

									if (bytes == null) {
										throw new IOException(
											"Unable to get bytes for " + url);
									}

									return new ByteArrayInputStream(bytes);
								}

							};
						}

					};
				}

			});
	}

	public static URL putBytes(String id, byte[] bytes) {
		try {
			URL url = new URL(
				"bytes://localhost/".concat(URLEncoder.encode(id, "UTF-8")));

			_bytesMap.put(url, bytes);

			return url;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] removeBytes(URL url) {
		return _bytesMap.remove(url);
	}

	private static final Map<URL, byte[]> _bytesMap = new ConcurrentHashMap<>();

}