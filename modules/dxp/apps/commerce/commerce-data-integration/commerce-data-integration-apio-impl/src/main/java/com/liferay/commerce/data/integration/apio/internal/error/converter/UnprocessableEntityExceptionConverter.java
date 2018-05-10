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

package com.liferay.commerce.data.integration.apio.internal.error.converter;

import com.liferay.apio.architect.converter.ExceptionConverter;
import com.liferay.apio.architect.error.APIError;
import com.liferay.commerce.data.integration.apio.internal.exceptions.UnprocessableEntityException;

import org.apache.http.HttpStatus;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code NotAcceptableException} to its {@link APIError}
 * representation.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class UnprocessableEntityExceptionConverter
	implements ExceptionConverter<UnprocessableEntityException> {

	@Override
	public APIError convert(UnprocessableEntityException uee) {
		return new APIError(
			uee, "Unable to process the contained instructions in the request",
			"unprocessable-entity", HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

}