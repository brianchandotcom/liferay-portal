/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.list.type.internal.jaxrs.exception.mapper;

import com.liferay.list.type.exception.DuplicateListTypeEntryExternalReferenceCodeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Diogo Melo
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.List.Type)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.List.Type.DuplicateListTypeEntryExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateListTypeEntryExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper
		<DuplicateListTypeEntryExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		DuplicateListTypeEntryExternalReferenceCodeException
			duplicateListTypeEntryExternalReferenceCodeException) {

		return new Problem(
			duplicateListTypeEntryExternalReferenceCodeException);
	}

}