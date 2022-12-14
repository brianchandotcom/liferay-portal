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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * Converts any {@code UnrecognizedPropertyException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class UnrecognizedPropertyExceptionMapper
	extends BaseExceptionMapper<UnrecognizedPropertyException> {

	@Override
	protected Problem getProblem(
		UnrecognizedPropertyException unrecognizedPropertyException) {

		StringBundler sb = new StringBundler();

		List<JsonMappingException.Reference> references =
			unrecognizedPropertyException.getPath();

		for (int i = 0; i < references.size(); i++) {
			JsonMappingException.Reference reference = references.get(i);

			Object object = reference.getFrom();

			Class<?> clazz = object.getClass();

			sb.append(
				StringBundler.concat(
					"The property \"", reference.getFieldName(),
					"\" is not defined in ", clazz.getSimpleName(), "."));

			if ((i + 1) < references.size()) {
				sb.append(" ");
			}
		}

		return new Problem(Response.Status.BAD_REQUEST, sb.toString());
	}

}