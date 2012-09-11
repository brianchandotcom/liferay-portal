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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.spring.context.PortletContextLoaderListener;
import com.liferay.util.log4j.Log4JUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Brian Wing Shun Chan
 */
public class SpringHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error initializing Spring for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error uninitializing Spring for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		ContextLoaderListener contextLoaderListener =
			new PortletContextLoaderListener();

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();
		
		initLogger(contextClassLoader);

		try {
			PACLClassLoaderUtil.setContextClassLoader(
				PACLClassLoaderUtil.getPortalClassLoader());

			contextLoaderListener.contextInitialized(
				new ServletContextEvent(servletContext));
		}
		finally {
			PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}

		_contextLoaderListeners.put(servletContextName, contextLoaderListener);
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		ContextLoaderListener contextLoaderListener =
			_contextLoaderListeners.remove(servletContextName);

		if (contextLoaderListener == null) {
			return;
		}

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		try {
			PACLClassLoaderUtil.setContextClassLoader(
				PACLClassLoaderUtil.getPortalClassLoader());

			contextLoaderListener.contextDestroyed(
				new ServletContextEvent(servletContext));
		}
		finally {
			PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}
	
	protected void initLogger(ClassLoader classLoader) {
		Log4JUtil.configureLog4J(
			classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	private static Map<String, ContextLoaderListener> _contextLoaderListeners =
		new HashMap<String, ContextLoaderListener>();

}