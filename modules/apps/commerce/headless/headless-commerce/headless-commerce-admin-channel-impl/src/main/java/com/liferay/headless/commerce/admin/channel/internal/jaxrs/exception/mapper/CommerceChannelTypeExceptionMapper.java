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

package com.liferay.headless.commerce.admin.channel.internal.jaxrs.exception.mapper;

import com.liferay.commerce.product.exception.CommerceChannelTypeException;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Channel)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Channel.CommerceChannelTypeExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class CommerceChannelTypeExceptionMapper
	extends BaseExceptionMapper<CommerceChannelTypeException> {

	@Override
	public String getErrorDescription() {
		return "Invalid type";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.BAD_REQUEST;
	}

}