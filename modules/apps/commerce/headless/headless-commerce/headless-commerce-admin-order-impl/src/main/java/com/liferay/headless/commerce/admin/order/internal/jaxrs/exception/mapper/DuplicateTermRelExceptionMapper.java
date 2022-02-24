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

package com.liferay.headless.commerce.admin.order.internal.jaxrs.exception.mapper;

import com.liferay.commerce.term.exception.DuplicateCommerceTermEntryRelException;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Order)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Order.DuplicateTermRelExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class DuplicateTermRelExceptionMapper
	extends BaseExceptionMapper<DuplicateCommerceTermEntryRelException> {

	@Override
	public String getErrorDescription() {
		return "Duplicate term relation";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.CONFLICT;
	}

	@Override
	protected String toJSON(
		DuplicateCommerceTermEntryRelException
			duplicateCommerceTermEntryRelException,
		int status) {

		return super.toJSON("the-qualifier-is-already-linked", status);
	}

}