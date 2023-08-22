/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherValues {

	public static final String[] INSTALLED_PATCH_NAMES;

	private static final Log _log = LogFactoryUtil.getLog(PatcherValues.class);

	static {
		Properties properties = new Properties();

		ClassLoader classLoader = PatcherValues.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"patcher.properties")) {

			if (inputStream == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load patcher.properties");
				}
			}
			else {
				properties.load(inputStream);
			}
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException);
			}
		}

		INSTALLED_PATCH_NAMES = StringUtil.split(
			properties.getProperty("installed.patches"));
	}

}