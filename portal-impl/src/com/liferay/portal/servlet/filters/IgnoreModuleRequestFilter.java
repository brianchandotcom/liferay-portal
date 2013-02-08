/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters;

import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This filter checks if the request is targeted toward a module, and if so it
 * ignores the request. This is because the module itself may later invoke the
 * filter. A use case includes when the filter must operate on resources of
 * the target module context, these will be unreachable from the portal's
 * context.
 *
 * @author Raymond Augé
 */
public abstract class IgnoreModuleRequestFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (isModuleRequest(request)) {
			return false;
		}

		return super.isFilterEnabled(request, response);
	}

	protected boolean isModuleRequest(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String resourcePath = requestURI;

		int pos = requestURI.indexOf(contextPath);

		if (pos == 0) {
			resourcePath = resourcePath.substring(contextPath.length());
		}

		if (resourcePath.startsWith(_MODULE_PATH)) {
			return true;
		}

		return false;
	}

	private static final String _MODULE_PATH =
		PortalUtil.getPathContext().concat(Portal.PATH_MODULE);

}