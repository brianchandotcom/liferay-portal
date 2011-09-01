/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class FileTimeStampUtil {

	public static long getTimeStamp(
		ServletContext servletContext, String path) {

		return getTimeStamp(servletContext, path, 0);
	}

	public static long getTimeStamp(
		ServletContext servletContext, String path, long defaultTimeStamp) {

		if (Validator.isNull(path)) {
			return defaultTimeStamp;
		}

		if (path.charAt(0) != CharPool.SLASH) {
			return defaultTimeStamp;
		}

		Long timeStamp = _timeStamps.get(path);

		if (timeStamp == null) {
			timeStamp = defaultTimeStamp;

			String uriRealPath = ServletContextUtil.getRealPath(
				servletContext, path);

			if (uriRealPath != null) {
				File uriFile = new File(uriRealPath);

				if (uriFile.exists()) {
					timeStamp = uriFile.lastModified();
				}
			}

			_timeStamps.put(path, timeStamp);
		}

		return timeStamp;
	}

	public static void reset() {
		_timeStamps.clear();
	}

	private static Map<String, Long> _timeStamps =
		new ConcurrentHashMap<String, Long>();

}