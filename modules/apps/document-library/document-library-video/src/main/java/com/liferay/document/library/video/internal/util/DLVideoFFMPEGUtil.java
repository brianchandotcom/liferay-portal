/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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