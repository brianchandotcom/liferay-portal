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

package com.liferay.portal.vulcan.internal.exception.mapper;

import com.liferay.portal.kernel.exception.PortalException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * Converts any {@code PortalException} to a {@code 500} error.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.extension.select=\\(osgi.jaxrs.name=Liferay.Vulcan.PortalExceptionMapper\\))",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Vulcan.PortalExceptionMapper"
	},
	scope = ServiceScope.PROTOTYPE, service = ExceptionMapper.class
)
public class PortalExceptionMapper implements ExceptionMapper<PortalException> {

	@Override
	public Response toResponse(PortalException pe) {
		throw new InternalServerErrorException(pe);
	}

}