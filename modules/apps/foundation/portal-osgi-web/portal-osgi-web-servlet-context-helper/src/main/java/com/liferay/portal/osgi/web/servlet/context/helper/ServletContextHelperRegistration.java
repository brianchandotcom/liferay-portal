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

package com.liferay.portal.osgi.web.servlet.context.helper;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;

import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public interface ServletContextHelperRegistration {

	public void close();

	public ServletContext getServletContext();

	public ServiceReference<ServletContextHelper>
		getServletContextHelperSeviceReference();

	public ServiceReference<ServletContextListener>
		getServletContextListenerSeviceReference();

	public void initDefaults();

	public void setProperties(Map<String, String> contextParameters);

}