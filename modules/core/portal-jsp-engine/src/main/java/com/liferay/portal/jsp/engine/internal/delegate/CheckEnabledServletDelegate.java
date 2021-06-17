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

package com.liferay.portal.jsp.engine.internal.delegate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.jasper.servlet.JspServlet;

/**
 * @author Shuyang Zhou
 */
public class CheckEnabledServletDelegate {

	public CheckEnabledServletDelegate(
		JspServlet jspServlet, ServletContext servletContext,
		long checkInterval) {

		_jspServlet = jspServlet;
		_servletContext = servletContext;
		_checkInterval = checkInterval;
	}

	public void destroy() {
		_scheduledExecutorService.shutdownNow();
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		_jspServlet.init(servletConfig);

		_scheduledExecutorService = new ScheduledThreadPoolExecutor(
			1,
			runnable -> {
				Thread thread = new Thread(
					runnable,
					"Portal Jasper Servlet Background Compiler Thread");

				thread.setContextClassLoader(_servletContext.getClassLoader());
				thread.setDaemon(true);

				return thread;
			});

		_scheduledExecutorService.scheduleWithFixedDelay(
			_jspServlet::periodicEvent, _checkInterval, _checkInterval,
			TimeUnit.SECONDS);
	}

	private final long _checkInterval;
	private final JspServlet _jspServlet;
	private ScheduledExecutorService _scheduledExecutorService;
	private final ServletContext _servletContext;

}