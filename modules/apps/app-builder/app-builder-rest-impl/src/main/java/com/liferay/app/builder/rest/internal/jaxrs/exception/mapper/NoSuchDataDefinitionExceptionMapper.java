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

package com.liferay.app.builder.rest.internal.jaxrs.exception.mapper;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.portal.kernel.util.StringUtil;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.App.Builder.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.App.Builder.REST.NoSuchDataDefinitionExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class NoSuchDataDefinitionExceptionMapper
	implements ExceptionMapper<NoSuchStructureException> {

	@Override
	public Response toResponse(
		NoSuchStructureException noSuchStructureException) {

		return Response.status(
			Response.Status.NOT_FOUND
		).entity(
			StringUtil.replace(
				noSuchStructureException.getMessage(), "DDMStructure",
				"Data definition")
		).build();
	}

}