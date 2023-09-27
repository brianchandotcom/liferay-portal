/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

/**
 * Converts any {@code InvalidFilterException} to a {@code 400} error.
 *
 * @author Javier Gamarra
 * @review
 */
public class InvalidFilterExceptionMapper
	extends BaseExceptionMapper<InvalidFilterException> {

	@Override
	protected Problem getProblem(
		InvalidFilterException invalidFilterException) {

		return new Problem(invalidFilterException);
	}

}