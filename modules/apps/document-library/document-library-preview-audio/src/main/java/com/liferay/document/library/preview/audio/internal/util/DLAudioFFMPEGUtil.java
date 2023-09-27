/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_log.error(exception);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLAudioFFMPEGUtil.class);

}