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

package com.liferay.apio.architect.wiring.osgi.manager;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides methods to get the {@link ErrorMessageMapper} that corresponds to
 * the {@link APIError} and {@code HttpHeaders}.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface ErrorMessageMapperManager {

	/**
	 * Returns the {@code ErrorMessageMapper} that corresponds to the {@code
	 * APIError} and {@code HttpHeaders}. If no acceptable media type is found
	 * in the current request, or no mapper is found for that request's {@code
	 * Accept} type, this method returns a mapper for the {@code
	 * application/problem+json} media type.
	 *
	 * @param  apiError the {@code APIError}
	 * @param  httpHeaders the current request's HTTP headers
	 * @return the {@code ErrorMessageMapper} that corresponds to the {@code
	 *         APIError} and {@code HttpHeaders}
	 */
	public ErrorMessageMapper getErrorMessageMapper(
		APIError apiError, HttpHeaders httpHeaders);

}