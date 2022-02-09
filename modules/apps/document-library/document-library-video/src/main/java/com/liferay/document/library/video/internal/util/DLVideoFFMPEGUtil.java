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

package com.liferay.document.library.video.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Alejandro Tardín
 */
public class DLVideoFFMPEGUtil {

	public static boolean isFFMPEGInstalled() {
		Runtime runtime = Runtime.getRuntime();

		try {
			Process process = runtime.exec("ffmpeg -version");

			if (process.waitFor() != 0) {
				return false;
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLVideoFFMPEGUtil.class);

}