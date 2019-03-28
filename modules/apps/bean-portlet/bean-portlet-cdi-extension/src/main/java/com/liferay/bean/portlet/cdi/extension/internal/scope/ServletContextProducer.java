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

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import javax.annotation.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import javax.interceptor.Interceptor;

import javax.servlet.ServletContext;

/**
 * @author Neil Griffin
 */
@Alternative
@ApplicationScoped
@Priority(Interceptor.Priority.APPLICATION + 10)
public class ServletContextProducer {

	public void applicationScopedInitialized(
		@Initialized(ApplicationScoped.class) @Observes
			ServletContext servletContext) {

		_servletContext = servletContext;
	}

	@Produces
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private static ServletContext _servletContext;

}