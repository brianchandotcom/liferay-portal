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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public abstract class PortalClassLoaderServletContextListener
	extends BasePortalLifecycle implements ServletContextListener {

	public PortalClassLoaderServletContextListener() {
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		_servletContextEvent = servletContextEvent;

		ServletContext servletContext = servletContextEvent.getServletContext();

		_servletContextName = servletContext.getServletContextName();

		Thread currentThread = Thread.currentThread();

		_portletClassLoader = currentThread.getContextClassLoader();

		registerPortalLifecycle();
	}

	@Override
	protected void doPortalDestroy() {
		Thread currentThread = Thread.currentThread();

		PortletClassLoaderUtil.setClassLoader(
			currentThread.getId(), _portletClassLoader);
		PortletClassLoaderUtil.setServletContextName(_servletContextName);

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener.contextDestroyed(_servletContextEvent);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			PortletClassLoaderUtil.setClassLoader(currentThread.getId(), null);
			PortletClassLoaderUtil.setServletContextName(null);
		}
	}

	@Override
	protected void doPortalInit() throws Exception {
		Thread currentThread = Thread.currentThread();

		PortletClassLoaderUtil.setClassLoader(
			currentThread.getId(), _portletClassLoader);
		PortletClassLoaderUtil.setServletContextName(_servletContextName);

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_servletContextListener = getInstance();

			_servletContextListener.contextInitialized(_servletContextEvent);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected abstract ServletContextListener getInstance() throws Exception;

	private ServletContextEvent _servletContextEvent;
	private ServletContextListener _servletContextListener;
	private ClassLoader _portletClassLoader;
	private String _servletContextName;

}