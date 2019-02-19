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

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Converts any {@code UnrecognizedPropertyException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class UnrecognizedPropertyExceptionMapper
	implements ExceptionMapper<UnrecognizedPropertyException> {

	@Override
	public Response toResponse(UnrecognizedPropertyException upe) {
		List<JsonMappingException.Reference> references = upe.getPath();

		Stream<JsonMappingException.Reference> stream = references.stream();

		String path = stream.map(
			JsonMappingException.Reference::getFieldName
		).collect(
			Collectors.joining(".")
		);

		return Response.status(
			Response.Status.BAD_REQUEST
		).entity(
			"Unrecognized field {" + path + "}"
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

}