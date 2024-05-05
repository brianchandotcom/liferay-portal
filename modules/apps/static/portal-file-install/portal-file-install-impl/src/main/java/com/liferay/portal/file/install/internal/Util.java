/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;

/**
 * // TODO Temporary class needs to be removed once the refactor is complete
 *
 * @author Matthew Tambara
 */
public class Util {

	public static String getFilePath(String dir) {
		File file = new File(dir);

		try {
			file = file.getCanonicalFile();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		URI uri = file.toURI();

		uri = uri.normalize();

		return uri.getPath();
	}

	private static final Log _log = LogFactoryUtil.getLog(Util.class);

}