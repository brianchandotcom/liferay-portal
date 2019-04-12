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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.net.URL;

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

		String timestampsCacheKey = FileTimestampUtil.class.getName();

		Map<String, Long> timestamps =
			(Map<String, Long>)servletContext.getAttribute(timestampsCacheKey);

		if (timestamps == null) {
			timestamps = new ConcurrentHashMap<>();

			servletContext.setAttribute(timestampsCacheKey, timestamps);
		}

		Long timestamp = timestamps.get(path);

		if (timestamp != null) {
			return timestamp;
		}

		timestamp = 0L;

		String uriRealPath = servletContext.getRealPath(path);

		if (uriRealPath != null) {
			File uriFile = new File(uriRealPath);

			if (uriFile.exists()) {
				timestamp = uriFile.lastModified();

				timestamps.put(path, timestamp);

				return timestamp;
			}
		}

		try {
			URL url = servletContext.getResource(path);

			if (url == null) {
				_log.error("Resource URL for " + path + " is null");
			}
			else {
				timestamp = URLUtil.getLastModifiedTime(url);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		timestamps.put(path, timestamp);

		return timestamp;
	}

	public static void reset(ServletContext servletContext) {
		String timestampsCacheKey = FileTimestampUtil.class.getName();

		servletContext.removeAttribute(timestampsCacheKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileTimestampUtil.class);

}