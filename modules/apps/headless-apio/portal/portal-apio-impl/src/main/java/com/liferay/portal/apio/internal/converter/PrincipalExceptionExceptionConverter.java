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

package com.liferay.portal.apio.internal.converter;

import com.liferay.apio.architect.converter.ExceptionMapper;
import com.liferay.apio.architect.error.APIError;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import javax.ws.rs.core.Response.Status;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code PrincipalException} to its {@link APIError} representation.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PrincipalExceptionExceptionConverter
	implements ExceptionMapper<PrincipalException> {

	@Override
	public APIError map(PrincipalException pe) {
		return new APIError(
			pe, "Resource not found", "not-found",
			Status.NOT_FOUND.getStatusCode());
	}

}