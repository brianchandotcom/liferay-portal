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

package org.gradle.internal.resource.transport.http;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Shin
 */
public class LiferaySslContextFactory extends DefaultSslContextFactory {

	@Override
	public SSLContext createSslContext() {
		if (!Boolean.getBoolean("liferay.ssl.context.enabled")) {
			return super.createSslContext();
		}

		int count = 0;

		while (count < _MAX_RETRY_COUNT) {

			// https://github.com/gradle/gradle/issues/7842

			synchronized (System.getProperties()) {
				String javaHome = System.getProperty("java.home");

				if (Files.exists(Paths.get(javaHome, "lib", "security"))) {
					return super.createSslContext();
				}
			}

			if (_logger.isWarnEnabled()) {
				_logger.warn("Mutated java.home system property");
			}

			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException ie) {
				if (_logger.isWarnEnabled()) {
					_logger.warn("Interrupted while sleeping", ie);
				}
			}

			count = count + 1;
		}

		throw new SecurityException("Cannot locate security policy files");
	}

	private static final int _MAX_RETRY_COUNT = 5;

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySslContextFactory.class);

}