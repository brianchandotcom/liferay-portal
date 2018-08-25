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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import com.liferay.portal.kernel.util.ServerDetector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Raymond Augé
 */
public class ServletContextListenerExceptionAdapter
	implements ServletContextListener {

	public ServletContextListenerExceptionAdapter(
		ServletContextListener servletContextListener,
		ServletContext servletContext) {

		_servletContextListener = servletContextListener;
		_servletContext = servletContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if (ServerDetector.isJBoss() || ServerDetector.isWildfly()) {
			ServletContext servletContext =
				servletContextEvent.getServletContext();

			Thread thread = new Thread(
				"Context destroyed thread for ".concat(
					servletContext.getServletContextName())) {

				@Override
				public void run() {
					_destroyContext();
				}

			};

			thread.setDaemon(true);

			thread.start();

			try {
				thread.join();
			}
			catch (Exception e) {
			}
		}
		else {
			_destroyContext();
		}
	}

	@Override
	public void contextInitialized(
		final ServletContextEvent servletContextEvent) {

		if (ServerDetector.isJBoss() || ServerDetector.isWildfly()) {
			ServletContext servletContext =
				servletContextEvent.getServletContext();

			Thread thread = new Thread(
				"Context initialized thread for ".concat(
					servletContext.getServletContextName())) {

				@Override
				public void run() {
					_initializeContext();
				}

			};

			thread.setDaemon(true);

			thread.start();

			try {
				thread.join();
			}
			catch (Exception e) {
			}
		}
		else {
			_initializeContext();
		}
	}

	public Exception getException() {
		return _exception;
	}

	private void _destroyContext() {
		try {
			_servletContextListener.contextDestroyed(
				new ServletContextEvent(_servletContext));
		}
		catch (Exception e) {
			_exception = e;
		}
	}

	private void _initializeContext() {
		try {
			_servletContextListener.contextInitialized(
				new ServletContextEvent(_servletContext));
		}
		catch (Exception e) {
			_exception = e;
		}
	}

	private Exception _exception;
	private final ServletContext _servletContext;
	private final ServletContextListener _servletContextListener;

}