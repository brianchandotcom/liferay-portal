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

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationContextProvider
	implements ContextProvider<APIApplication> {

	public APIApplicationContextProvider(
		APIApplicationProvider apiApplicationProvider, Portal portal) {

		_apiApplicationProvider = apiApplicationProvider;
		_portal = portal;
	}

	@Override
	public APIApplication createContext(Message message) {
		try {
			return _fetchApiApplication(
				(HttpServletRequest)message.getContextualProperty(
					"HTTP.REQUEST"));
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new NotFoundException(exception.getMessage());
		}
	}

	private APIApplication _fetchApiApplication(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String contextPath = httpServletRequest.getContextPath();

		if (contextPath.startsWith("/o/")) {
			contextPath = contextPath.substring(3);
		}

		return _apiApplicationProvider.fetchAPIApplication(
			contextPath, _portal.getCompanyId(httpServletRequest));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		APIApplicationContextProvider.class);

	private final APIApplicationProvider _apiApplicationProvider;
	private final Portal _portal;

}