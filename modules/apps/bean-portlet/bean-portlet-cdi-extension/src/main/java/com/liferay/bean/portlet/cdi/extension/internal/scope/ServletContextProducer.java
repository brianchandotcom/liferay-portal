/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		@Initialized(ApplicationScoped.class) @Observes ServletContext
			servletContext) {

		_servletContext = servletContext;
	}

	@Produces
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private static ServletContext _servletContext;

}