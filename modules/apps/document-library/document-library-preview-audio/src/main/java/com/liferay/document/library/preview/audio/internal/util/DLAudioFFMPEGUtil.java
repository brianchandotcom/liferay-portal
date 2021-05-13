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

package com.liferay.document.library.preview.audio.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Adolfo Pérez
 */
public class DLAudioFFMPEGUtil {

	public static boolean isFFMPEGInstalled() {
		try {
			Runtime runtime = Runtime.getRuntime();

			Process process = runtime.exec("ffmpeg -version");

			if (process.waitFor() != 0) {
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLAudioFFMPEGUtil.class);

}