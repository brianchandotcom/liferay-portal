/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.jaxrs.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(
	enabled = false,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Search.Experiences.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Search.Experiences.REST.UnrecognizedPropertyExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class UnrecognizedPropertyExceptionMapper
	extends BaseExceptionMapper<UnrecognizedPropertyException> {

	@Override
	public Response toResponse(
		UnrecognizedPropertyException unrecognizedPropertyException) {

		Problem problem = getProblem(unrecognizedPropertyException);

		return Response.status(
			problem.getStatus()
		).entity(
			problem
		).type(
			getMediaType()
		).build();
	}

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