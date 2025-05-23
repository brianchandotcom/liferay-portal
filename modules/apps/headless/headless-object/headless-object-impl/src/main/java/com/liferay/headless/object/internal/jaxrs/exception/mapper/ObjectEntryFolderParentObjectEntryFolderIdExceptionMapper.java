/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectEntryFolderParentObjectEntryFolderIdException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Object)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Object.ObjectEntryFolderParentObjectEntryFolderIdExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ObjectEntryFolderParentObjectEntryFolderIdExceptionMapper
	extends BaseExceptionMapper
		<ObjectEntryFolderParentObjectEntryFolderIdException> {

	@Override
	protected Problem getProblem(
		ObjectEntryFolderParentObjectEntryFolderIdException
			objectEntryFolderParentObjectEntryFolderIdException) {

		return new Problem(objectEntryFolderParentObjectEntryFolderIdException);
	}

}