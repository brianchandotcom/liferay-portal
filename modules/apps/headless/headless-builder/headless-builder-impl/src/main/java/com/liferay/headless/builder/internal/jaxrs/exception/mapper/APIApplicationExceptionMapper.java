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

package com.liferay.headless.builder.internal.jaxrs.exception.mapper;

import com.liferay.headless.builder.exception.InvalidBaseURLException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

/**
 * @author Sergio Jiménez del Coso
 */
public class APIApplicationExceptionMapper
	extends BaseExceptionMapper<InvalidBaseURLException> {

	@Override
	protected Problem getProblem(
		InvalidBaseURLException invalidBaseURLException) {

		return new Problem(invalidBaseURLException);
	}

}