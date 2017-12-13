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

package com.liferay.apio.architect.error.internal.converter;

import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import com.liferay.apio.architect.converter.ExceptionConverter;
import com.liferay.apio.architect.error.APIError;

import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code ServiceUnavailableException} to its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ServiceUnavailableExceptionConverter
	extends WebApplicationExceptionConverter
	implements ExceptionConverter<ServiceUnavailableException> {

	@Override
	public APIError convert(ServiceUnavailableException exception) {
		return super.convert(exception);
	}

	@Override
	protected Response.StatusType getStatusType() {
		return SERVICE_UNAVAILABLE;
	}

	@Override
	protected String getTitle() {
		return "Server is temporarily unavailable or busy";
	}

	@Override
	protected String getType() {
		return "unavailable";
	}

}