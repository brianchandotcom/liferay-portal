/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherUtil {

	public static String[] getInstalledPatches() {
		return _INSTALLED_PATCH_NAMES;
	}

	private static Properties _getProperties(String fileName) {
		if (Validator.isNull(fileName)) {
			fileName = _PATCHER_PROPERTIES;
		}

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		if (Objects.equals(fileName, _PATCHER_SERVICE_PROPERTIES)) {
			clazz = clazz.getInterfaces()[0];
		}

		ClassLoader classLoader = clazz.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				fileName)) {

			if (inputStream == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load " + fileName);
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

		return properties;
	}

	private static final String[] _INSTALLED_PATCH_NAMES;

	private static final String _PATCHER_PROPERTIES = "patcher.properties";

	private static final String _PATCHER_SERVICE_PROPERTIES =
		"patcher-service.properties";

	private static final Properties _PROPERTIES;

	private static final String _PROPERTY_INSTALLED_PATCHES =
		"installed.patches";

	private static final Log _log = LogFactoryUtil.getLog(PatcherUtil.class);

	static {
		_PROPERTIES = _getProperties(_PATCHER_PROPERTIES);

		_INSTALLED_PATCH_NAMES = StringUtil.split(
			_PROPERTIES.getProperty(_PROPERTY_INSTALLED_PATCHES));
	}

}