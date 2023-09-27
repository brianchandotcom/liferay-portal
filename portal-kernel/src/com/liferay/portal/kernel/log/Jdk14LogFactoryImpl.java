/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.log;

import java.io.IOException;
import java.io.InputStream;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Brian Wing Shun Chan
 */
public class Jdk14LogFactoryImpl implements LogFactory {

	public Jdk14LogFactoryImpl() {
		if (System.getProperty("java.util.logging.config.file") != null) {
			return;
		}

		try (InputStream inputStream =
				Jdk14LogFactoryImpl.class.getResourceAsStream(
					"/logging.properties")) {

			if (inputStream != null) {
				LogManager logManager = LogManager.getLogManager();

				logManager.readConfiguration(inputStream);
			}
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@Override
	public Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	@Override
	public Log getLog(String name) {
		return new Jdk14LogImpl(Logger.getLogger(name));
	}

}