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

package com.liferay.portal.apio.internal.architect.logger;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ApioLoggerImpl implements ApioLogger {

	@Override
	public void error(APIError apiError) {
		if (_log.isDebugEnabled()) {
			_log.debug(_getExceptionMessage(apiError), apiError.getException());
		}
		else {
			_log.error(_getExceptionMessage(apiError));
		}
	}

	@Override
	public void warning(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(message);
		}
		else {
			_log.warn(message);
		}
	}

	private String _getExceptionMessage(APIError apiError) {
		Optional<String> optional = apiError.getDescription();

		return optional.orElseGet(
			() -> {
				Exception exception = apiError.getException();

				String message = exception.getMessage();

				if (Validator.isNotNull(message)) {
					return message;
				}

				return apiError.toString();
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(ApioLoggerImpl.class);

}