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
		catch (IOException ioe) {
			ioe.printStackTrace();
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