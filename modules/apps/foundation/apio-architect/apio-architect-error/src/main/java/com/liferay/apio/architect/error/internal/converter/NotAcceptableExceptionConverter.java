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

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

import com.liferay.apio.architect.converter.ExceptionConverter;
import com.liferay.apio.architect.error.APIError;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code NotAcceptableException} to its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class NotAcceptableExceptionConverter
	extends WebApplicationExceptionConverter
	implements ExceptionConverter<NotAcceptableException> {

	@Override
	public APIError convert(NotAcceptableException exception) {
		return super.convert(exception);
	}

	@Override
	protected Response.StatusType getStatusType() {
		return NOT_ACCEPTABLE;
	}

	@Override
	protected String getTitle() {
		return "Client media type requested not supported";
	}

	@Override
	protected String getType() {
		return "not-acceptable";
	}

}