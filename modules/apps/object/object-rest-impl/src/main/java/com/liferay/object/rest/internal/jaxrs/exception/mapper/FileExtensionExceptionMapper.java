/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.ext.Provider;

/**
 * @author Carlos Correa
 */
@Provider
public class FileExtensionExceptionMapper
	extends BaseExceptionMapper<FileExtensionException> {

	@Override
	protected Problem getProblem(
		FileExtensionException fileExtensionException) {

		return new Problem(fileExtensionException);
	}

}