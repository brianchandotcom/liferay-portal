/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.ext.Provider;

/**
 * Converts any {@code FileMimeTypeException} to a {@code 400} error.
 *
 * @author Alicia García
 */
@Provider
public class FileMimeTypeExceptionMapper
	extends BaseExceptionMapper<FileMimeTypeException> {

	@Override
	protected Problem getProblem(FileMimeTypeException fileMimeTypeException) {
		return new Problem(fileMimeTypeException);
	}

}