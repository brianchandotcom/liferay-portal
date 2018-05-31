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

package com.liferay.commerce.data.integration.apio.internal.error.mapper;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.exception.mapper.ExceptionMapper;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code ConflictException} to its {@link APIError} representation.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ConflictExceptionMapper
	implements ExceptionMapper<ConflictException> {

	@Override
	public APIError map(ConflictException ce) {
		return new APIError(
			ce, "Unable to process the instructions in the request",
			"Conflict: " + ce.getLocalizedMessage(),
			Response.Status.CONFLICT.getStatusCode());
	}

}