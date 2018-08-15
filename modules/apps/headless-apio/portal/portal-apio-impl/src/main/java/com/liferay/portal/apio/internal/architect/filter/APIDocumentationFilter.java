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

package com.liferay.portal.apio.internal.architect.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Application;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=API Documentation Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class APIDocumentationFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled() {
		if (_applicationPath == null) {
			return false;
		}

		return true;
	}

	@Override
	protected Log getLog() {
		return LogFactoryUtil.getLog(getClass());
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("<");
		stringBuilder.append(_getApiDocumentationUrl(request));
		stringBuilder.append(" ");
		stringBuilder.append(
			"rel=\"http://www.w3.org/ns/hydra/core#apiDocumentation\"");
		stringBuilder.append(">");

		response.addHeader("Link", stringBuilder.toString());

		super.processFilter(request, response, filterChain);
	}

	@Reference(target = "(osgi.jaxrs.name=apio-application)", unbind = "-")
	protected void setApioApplication(
		ServiceReference<Application> serviceReference) {

		_applicationPath = (String)serviceReference.getProperty(
			"osgi.jaxrs.application.base");
	}

	private String _getApiDocumentationUrl(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(_portal.getPortalURL(request));
		stringBuilder.append("/o");
		stringBuilder.append(_applicationPath);
		stringBuilder.append("/doc");

		return stringBuilder.toString();
	}

	private String _applicationPath;

	@Reference
	private Portal _portal;

}