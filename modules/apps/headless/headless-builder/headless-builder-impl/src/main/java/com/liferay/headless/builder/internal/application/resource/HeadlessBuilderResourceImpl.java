/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			PathUtil.removeBasePath(contextHttpServletRequest.getRequestURI()),
			contextAPIApplication.getBaseURL());

		for (APIApplication.Endpoint endpoint :
				contextAPIApplication.getEndpoints()) {

			if (Objects.equals(endpoint.getPath(), endpointPath)) {
				if (endpoint.getResponseSchema() == null) {
					return Response.noContent(
					).build();
				}

				return Response.ok(
					_objectEntryHelper.
						getAPIApplicationSchemaPropertyValueMapPage(
							contextCompany.getCompanyId(), endpoint, pagination)
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