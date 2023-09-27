/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.ext.Provider;

/**
 * @author Marco Leo
 */
@Provider
public class RequiredObjectRelationshipExceptionMapper
	extends BaseExceptionMapper<RequiredObjectRelationshipException> {

	@Override
	protected Problem getProblem(
		RequiredObjectRelationshipException
			requiredObjectRelationshipException) {

		return new Problem(requiredObjectRelationshipException);
	}

}