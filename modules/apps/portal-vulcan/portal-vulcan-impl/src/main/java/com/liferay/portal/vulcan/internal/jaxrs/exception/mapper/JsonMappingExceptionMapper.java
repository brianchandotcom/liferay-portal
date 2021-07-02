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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Response;

/**
 * Converts any {@code JsonMappingException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class JsonMappingExceptionMapper
	extends BaseExceptionMapper<JsonMappingException> {

	@Override
	protected Problem getProblem(JsonMappingException jsonMappingException) {
		if (_log.isDebugEnabled()) {
			_log.debug(jsonMappingException, jsonMappingException);
		}

		List<JsonMappingException.Reference> references =
			jsonMappingException.getPath();

		Stream<JsonMappingException.Reference> stream = references.stream();

		String path = stream.map(
			JsonMappingException.Reference::getFieldName
		).collect(
			Collectors.joining(".")
		);

		return new Problem(
			jsonMappingException.getLocalizedMessage(),
			Response.Status.BAD_REQUEST, "Unable to map JSON path: " + path,
			"JsonMappingException");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JsonMappingExceptionMapper.class);

}