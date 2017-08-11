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

package com.liferay.portal.dao.jdbc.functions;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.sql.Connection;

import java.util.Properties;
import java.util.function.Function;

/**
 * @author Manuel de la Peña
 */
public class RetryJDBCConnectionFunction
	implements Function<Function<Properties, Connection>, Connection> {

	public RetryJDBCConnectionFunction(Properties properties) {
		this(properties, 0, 0);
	}

	public RetryJDBCConnectionFunction(
		Properties properties, int delaySeconds, int times) {

		_properties = properties;
		_delaySeconds = delaySeconds;
		_times = times;
	}

	@Override
	public Connection apply(Function<Properties, Connection> function) {
		int retryCount = _times;

		while (retryCount-- > 0) {
			try {
				return function.apply(_properties);
			}
			catch (RuntimeException re) {
				String message = re.getMessage();

				if (message.equals("No JDBC connection found")) {
					try {
						if (_log.isWarnEnabled()) {
							int current = _times - retryCount;

							_log.warn(
								"Retrying JDBC connection in " + _delaySeconds +
									" seconds. (Currently " + current + ")");
						}

						if (_delaySeconds > 0) {
							Thread.sleep(_delaySeconds * 1000);
						}

						continue;
					}
					catch (InterruptedException ie) {
						throw new RuntimeException(
							"JDBC connection could not be retried", ie);
					}
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RetryJDBCConnectionFunction.class);

	private final int _delaySeconds;
	private final Properties _properties;
	private final int _times;

}