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

package com.liferay.headless.admin.address.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.DuplicateRegionException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Address)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Address.DuplicateRegionExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateRegionExceptionMapper
	extends BaseExceptionMapper<DuplicateRegionException> {

	@Override
	protected Problem getProblem(
		DuplicateRegionException duplicateRegionException) {

		return new Problem(
			null, Response.Status.BAD_REQUEST,
			duplicateRegionException.getMessage(),
			DuplicateRegionException.class.getSimpleName());
	}

}