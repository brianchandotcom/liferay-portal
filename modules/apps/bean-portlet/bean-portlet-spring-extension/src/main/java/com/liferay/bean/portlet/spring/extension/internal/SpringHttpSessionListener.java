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

package com.liferay.bean.portlet.spring.extension.internal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Neil Griffin
 */
public class SpringHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		ServletContext servletContext = httpSession.getServletContext();

		SpringBeanPortletExtension springBeanPortletExtension =
			(SpringBeanPortletExtension)servletContext.getAttribute(
				SpringBeanPortletExtension.class.getName());

		springBeanPortletExtension.step4SessionScopeBeforeDestroyed(
			httpSession);
	}

}