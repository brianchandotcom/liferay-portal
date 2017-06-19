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

package com.liferay.wsrp.internal.servlet.filter;

import com.liferay.portal.kernel.servlet.PortalClassLoaderFilter;
import com.liferay.wsrp.internal.axis.WSRPHTTPSender;
import com.liferay.wsrp.util.WSRPConsumerManagerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(osgi.http.whiteboard.context.name=wsrp-service)",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX + "filter-class=com.liferay.portal.servlet.filters.secure.SecureFilter",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX + "portal_property_prefix=proxy.servlet.",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME + "=com.liferay.wsrp.servlet.filters.WSRPProxyFilter",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "=/proxy/*"
	},
	service = Filter.class
)
public class WSRPProxyFilter extends PortalClassLoaderFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;

		HttpSession session = request.getSession();

		WSRPConsumerManagerFactory.setSession(session);

		WSRPHTTPSender.setCurrentRequest((HttpServletRequest)servletRequest);

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

}