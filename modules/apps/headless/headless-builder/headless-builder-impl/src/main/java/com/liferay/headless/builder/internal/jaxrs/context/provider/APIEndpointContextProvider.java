/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.util.PathUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
public class APIEndpointContextProvider
	implements ContextProvider<APIApplication.Endpoint> {

	public APIEndpointContextProvider(APIApplication apiApplication) {
		_apiApplication = apiApplication;
	}

	@Override
	public APIApplication.Endpoint createContext(Message message) {
		HttpServletRequest httpServletRequest = _getHttpServletRequest(message);

		String endpointPath = StringUtil.removeSubstring(
			PathUtil.removeBasePath(httpServletRequest.getRequestURI()),
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

	private HttpServletRequest _getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	private final APIApplication _apiApplication;

}