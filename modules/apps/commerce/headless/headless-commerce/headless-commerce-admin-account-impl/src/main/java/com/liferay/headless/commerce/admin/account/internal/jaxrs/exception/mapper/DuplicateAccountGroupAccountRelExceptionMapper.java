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

package com.liferay.headless.commerce.admin.account.internal.jaxrs.exception.mapper;

import com.liferay.commerce.account.exception.DuplicateCommerceAccountGroupCommerceAccountRelException;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Account)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Account.DuplicateAccountGroupAccountRelException.java"
	},
	service = ExceptionMapper.class
)
@Provider
public class DuplicateAccountGroupAccountRelExceptionMapper
	extends BaseExceptionMapper
		<DuplicateCommerceAccountGroupCommerceAccountRelException> {

	@Override
	public String getErrorDescription() {
		return "Duplicate account group - account relation";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.CONFLICT;
	}

}