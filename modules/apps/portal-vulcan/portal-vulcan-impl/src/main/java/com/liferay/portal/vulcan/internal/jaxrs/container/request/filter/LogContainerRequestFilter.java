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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * @author Ivica Cardic
 */
@Provider
public class LogContainerRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("{headers: ");
			sb.append(MapUtil.toString(containerRequestContext.getHeaders()));
			sb.append(", method: ");
			sb.append(containerRequestContext.getMethod());
			sb.append(", uri: ");

			UriInfo uriInfo = containerRequestContext.getUriInfo();

			sb.append(uriInfo.getRequestUri());

			sb.append("}");

			_log.debug(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LogContainerRequestFilter.class);

}