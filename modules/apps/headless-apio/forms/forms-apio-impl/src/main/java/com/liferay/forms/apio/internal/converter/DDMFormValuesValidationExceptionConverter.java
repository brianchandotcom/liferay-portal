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

package com.liferay.forms.apio.internal.converter;

import com.liferay.apio.architect.converter.ExceptionMapper;
import com.liferay.apio.architect.error.APIError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;

import javax.ws.rs.core.Response.Status;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code DDMFormValuesValidationException} to its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class DDMFormValuesValidationExceptionConverter
	implements ExceptionMapper<DDMFormValuesValidationException> {

	@Override
	public APIError map(DDMFormValuesValidationException ddmfvve) {
		return new APIError(
			ddmfvve, "Wrong values", ddmfvve.getMessage(), "bad-request",
			Status.BAD_REQUEST.getStatusCode());
	}

}