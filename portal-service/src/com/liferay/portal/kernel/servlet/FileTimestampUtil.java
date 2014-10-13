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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class FileTimestampUtil {

	public static long getTimestamp(
		ServletContext servletContext, String path) {

		if (Validator.isNull(path)) {
			return 0;
		}

		if (path.charAt(0) != CharPool.SLASH) {
			return 0;
		}

		Long timestamp = _timestamps.get(path);

		if (timestamp != null) {
			return timestamp;
		}

		timestamp = 0L;

		String uriRealPath = servletContext.getRealPath(path);

		if (uriRealPath != null) {
			File uriFile = new File(uriRealPath);

			if (uriFile.exists()) {
				timestamp = uriFile.lastModified();

				_timestamps.put(path, timestamp);

				return timestamp;
			}
		}

		try {
			URL url = servletContext.getResource(path);

			if (url == null) {
				_log.error("Resource URL for " + path + " is null");
			}
			else {
				URLConnection urlConnection = null;

				try {
					urlConnection = url.openConnection();

					timestamp = urlConnection.getLastModified();
				}
				finally {
					if (urlConnection != null) {
						try {
							InputStream inputStream =
								urlConnection.getInputStream();

							inputStream.close();
						}
						catch (IOException ioe) {
						}
					}
				}
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		_timestamps.put(path, timestamp);

		return timestamp;
	}

	public static void reset() {
		_timestamps.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileTimestampUtil.class);

	private static final Map<String, Long> _timestamps =
		new ConcurrentHashMap<String, Long>();

}