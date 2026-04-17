/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.jaxrs.exception.mapper;

import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Fragment)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Fragment.DuplicateFragmentCollectionKeyExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateFragmentCollectionKeyExceptionMapper
	extends BaseExceptionMapper<DuplicateFragmentCollectionKeyException> {

	@Override
	protected Problem getProblem(
		DuplicateFragmentCollectionKeyException
			duplicateFragmentCollectionKeyException) {

		return new Problem(
			Response.Status.CONFLICT,
			"A fragment set with the same key already exists");
	}

}