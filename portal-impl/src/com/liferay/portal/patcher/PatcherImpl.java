/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.Patcher;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Igor Beslic
 * @author Zoltán Takács
 */
public class PatcherImpl implements Patcher {

	public PatcherImpl() {
		_properties = _getProperties(PATCHER_PROPERTIES);

		_installedPatchNames = StringUtil.split(
			_properties.getProperty(PROPERTY_INSTALLED_PATCHES));
	}

	@Override
	public String[] getInstalledPatches() {
		return _installedPatchNames;
	}

	private Properties _getProperties(String fileName) {
		if (Validator.isNull(fileName)) {
			fileName = PATCHER_PROPERTIES;
		}

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		if (Objects.equals(fileName, PATCHER_SERVICE_PROPERTIES)) {
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

	private static final Log _log = LogFactoryUtil.getLog(PatcherImpl.class);

	private final String[] _installedPatchNames;
	private final Properties _properties;

}