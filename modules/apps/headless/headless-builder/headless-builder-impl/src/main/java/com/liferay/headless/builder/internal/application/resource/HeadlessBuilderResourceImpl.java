/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.builder.internal.application.resource;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.headless.builder.internal.util.PathUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

import javax.ws.rs.core.Response;

/**
 * @author Luis Miguel Barcos
 */
public class HeadlessBuilderResourceImpl
	extends BaseHeadlessBuilderResourceImpl {

	public HeadlessBuilderResourceImpl(ObjectEntryHelper objectEntryHelper) {
		_objectEntryHelper = objectEntryHelper;
	}

	@Override
	public Response get(Pagination pagination) throws Exception {
		String endpointPath = StringUtil.removeSubstring(
			PathUtil.sanitize(contextHttpServletRequest.getRequestURI()),
			contextAPIApplication.getBaseURL());

		for (APIApplication.Endpoint endpoint :
				contextAPIApplication.getEndpoints()) {

			if (Objects.equals(endpoint.getPath(), endpointPath)) {
				return Response.ok(
					_objectEntryHelper.getResponseEntityMaps(
						contextCompany.getCompanyId(), endpoint)
				).build();
			}
		}

		throw new NoSuchModelException(
			String.format(
				"Endpoint %s does not exist for %s", endpointPath,
				contextAPIApplication.getTitle()));
	}

	private final ObjectEntryHelper _objectEntryHelper;

}