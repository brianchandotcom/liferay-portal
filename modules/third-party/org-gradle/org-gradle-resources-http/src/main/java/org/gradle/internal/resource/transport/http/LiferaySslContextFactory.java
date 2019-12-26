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

import javax.net.ssl.SSLContext;

import org.apache.http.ssl.SSLContexts;

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

		if (_logger.isInfoEnabled()) {
			_logger.info("Creating default SSL context from system properties");
		}

		return SSLContexts.createSystemDefault();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySslContextFactory.class);

}