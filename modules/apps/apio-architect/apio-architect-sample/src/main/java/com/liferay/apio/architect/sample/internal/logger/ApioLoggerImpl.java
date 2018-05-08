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

package com.liferay.apio.architect.sample.internal.logger;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.logger.ApioLogger;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ApioLoggerImpl implements ApioLogger {

	@Override
	public void error(APIError apiError) {
		if (_logger.isDebugEnabled()) {
			_logger.debug(
				_getExceptionMessage(apiError), apiError.getException());
		}
		else {
			_logger.error(_getExceptionMessage(apiError));
		}
	}

	@Override
	public void warning(String message) {
		if (_logger.isDebugEnabled()) {
			_logger.debug(message);
		}
		else {
			_logger.error(message);
		}
	}

	private String _getExceptionMessage(APIError apiError) {
		Optional<String> optional = apiError.getDescription();

		return optional.orElseGet(
			() -> {
				Exception exception = apiError.getException();

				String message = exception.getMessage();

				if ((message != null) && !message.isEmpty()) {
					return message;
				}

				return apiError.toString();
			});
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		ApioLoggerImpl.class);

}