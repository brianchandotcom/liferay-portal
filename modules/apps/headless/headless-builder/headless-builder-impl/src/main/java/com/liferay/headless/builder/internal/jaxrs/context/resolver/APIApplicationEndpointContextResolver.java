/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.resolver;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.util.PathUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationEndpointContextResolver
	implements ContextResolver<APIApplication.Endpoint> {

	public APIApplicationEndpointContextResolver(
		APIApplication apiApplication) {

		_apiApplication = apiApplication;
	}

	@Override
	public APIApplication.Endpoint getContext(Class<?> aClass) {
		String endpointPath = StringUtil.removeSubstring(
			PathUtil.removeBasePath(_httpServletRequest.getRequestURI()),
			_apiApplication.getBaseURL());

		for (APIApplication.Endpoint endpoint :
				_apiApplication.getEndpoints()) {

			if (Objects.equals(endpoint.getPath(), endpointPath)) {
				return endpoint;
			}
		}

		throw new NotFoundException(
			String.format(
				"Endpoint %s does not exist for %s", endpointPath,
				_apiApplication.getTitle()));
	}

	private final APIApplication _apiApplication;

	@Context
	private HttpServletRequest _httpServletRequest;

}