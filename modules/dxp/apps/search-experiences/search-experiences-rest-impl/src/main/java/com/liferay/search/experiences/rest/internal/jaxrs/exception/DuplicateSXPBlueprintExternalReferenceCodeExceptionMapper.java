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

package com.liferay.search.experiences.rest.internal.jaxrs.exception;

import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import com.liferay.search.experiences.exception.DuplicateSXPBlueprintExternalReferenceCodeException;

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
		"osgi.jaxrs.name=Liferay.Search.Experiences.REST.DuplicateSXPBlueprintExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class DuplicateSXPBlueprintExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper
		<DuplicateSXPBlueprintExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		DuplicateSXPBlueprintExternalReferenceCodeException
			duplicateSXPBlueprintExternalReferenceCodeException) {

		return new Problem(
			null, Response.Status.BAD_REQUEST,
			duplicateSXPBlueprintExternalReferenceCodeException.getMessage(),
			DuplicateSXPBlueprintExternalReferenceCodeException.class.
				getSimpleName());
	}

	@Override
	protected boolean isSanitize() {
		return false;
	}

}