/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Response;

/**
 * Converts any {@code NoSuchModelException} to a {@code 404} error.
 *
 *
 * @author Alejandro Hernández
 * @review
 */
public class NoSuchModelExceptionMapper
	extends BaseExceptionMapper<NoSuchModelException> {

	@Override
	protected Problem getProblem(NoSuchModelException noSuchModelException) {
		return new Problem(
			Response.Status.NOT_FOUND, noSuchModelException.getMessage());
	}

}