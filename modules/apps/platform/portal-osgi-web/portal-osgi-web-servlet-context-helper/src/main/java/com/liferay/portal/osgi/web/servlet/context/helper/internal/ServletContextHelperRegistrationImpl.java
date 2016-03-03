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

package com.liferay.portal.osgi.web.servlet.context.helper.internal;

import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class ServletContextHelperRegistrationImpl
	implements ServletContextHelperRegistration {

	public ServletContextHelperRegistrationImpl(
		ServiceRegistration<ServletContextHelper> serviceRegistration,
		CustomServletContextHelper customServletContextHelper) {

		_serviceRegistration = serviceRegistration;
		_customServletContextHelper = customServletContextHelper;
	}

	@Override
	public ServiceReference<ServletContextHelper> getServiceReference() {
		return _serviceRegistration.getReference();
	}

	@Override
	public ServiceRegistration<ServletContextHelper> getServiceRegistration() {
		return _serviceRegistration;
	}

	@Override
	public ServletContext getServletContext() {
		return _customServletContextHelper.getServletContext();
	}

	private final CustomServletContextHelper _customServletContextHelper;
	private final ServiceRegistration<ServletContextHelper>
		_serviceRegistration;

}