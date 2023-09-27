/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.DuplicateObjectFieldExternalReferenceCodeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Guilherme Camacho
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Object.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Object.Admin.REST.DuplicateObjectFieldExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateObjectFieldExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper
		<DuplicateObjectFieldExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		DuplicateObjectFieldExternalReferenceCodeException
			duplicateObjectFieldExternalReferenceCodeException) {

		return new Problem(duplicateObjectFieldExternalReferenceCodeException);
	}

}