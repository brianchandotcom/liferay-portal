/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectFieldReadOnlyException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.ext.Provider;

/**
 * @author Yuri Monteiro
 */
@Provider
public class ObjectFieldReadOnlyExceptionMapper
	extends BaseExceptionMapper<ObjectFieldReadOnlyException> {

	@Override
	protected Problem getProblem(
		ObjectFieldReadOnlyException objectFieldReadOnlyException) {

		return new Problem(objectFieldReadOnlyException);
	}

}