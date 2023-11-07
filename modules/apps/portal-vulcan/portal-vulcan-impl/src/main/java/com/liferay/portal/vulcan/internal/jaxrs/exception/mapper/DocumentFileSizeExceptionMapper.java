/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

/**
 * Converts any {@code FileSizeException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @author Carlos Correa
 */
public class DocumentFileSizeExceptionMapper
	extends BaseExceptionMapper<FileSizeException> {

	@Override
	protected Problem getProblem(FileSizeException fileSizeException) {
		return new Problem(fileSizeException);
	}

}