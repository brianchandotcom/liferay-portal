/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.address.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.DuplicateRegionExternalReferenceCodeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Balazs Breier
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Address)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Address.DuplicateRegionExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateRegionExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper<DuplicateRegionExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		DuplicateRegionExternalReferenceCodeException
			duplicateRegionExternalReferenceCodeException) {

		return new Problem(
			Response.Status.CONFLICT,
			"A region already exists with the same external reference code");
	}

}