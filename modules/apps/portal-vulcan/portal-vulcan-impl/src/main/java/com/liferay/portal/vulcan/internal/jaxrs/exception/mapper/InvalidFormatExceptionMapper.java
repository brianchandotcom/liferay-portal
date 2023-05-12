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
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * Converts any {@code InvalidFormatException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class InvalidFormatExceptionMapper
	extends BaseExceptionMapper<InvalidFormatException> {

	@Override
	protected Problem getProblem(
		InvalidFormatException invalidFormatException) {

		List<JsonMappingException.Reference> references =
			invalidFormatException.getPath();

		StringBundler sb = new StringBundler(references.size() * 2);

		for (JsonMappingException.Reference reference : references) {
			sb.append(reference.getFieldName());
			sb.append(".");
		}

		sb.setIndex(sb.index() - 1);

		Class<?> clazz = invalidFormatException.getTargetType();

		return new Problem(
			invalidFormatException.getLocalizedMessage(),
			Response.Status.BAD_REQUEST,
			StringBundler.concat(
				"Unable to map JSON path \"", sb, "\" with value \"",
				invalidFormatException.getValue(), "\" to class \"",
				clazz.getSimpleName(), "\""),
			InvalidFormatException.class.getName());
	}

}