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

import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
public class ServletContextHelperRegistrationServiceFactory
	implements ServiceFactory<ServletContextHelperRegistration> {

	public ServletContextHelperRegistrationServiceFactory(
		SAXParserFactory saxParserFactory, Logger logger,
		Map<String, Object> properties) {

		_saxParserFactory = saxParserFactory;
		_logger = logger;
		_properties = properties;
	}

	@Override
	public ServletContextHelperRegistration getService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelperRegistration> registration) {

		return new ServletContextHelperRegistrationImpl(
			bundle, _saxParserFactory, _logger, _properties);
	}

	@Override
	public void ungetService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelperRegistration> registration,
		ServletContextHelperRegistration servletContextHelperRegistration) {

		servletContextHelperRegistration.close();
	}

	private final Logger _logger;
	private final Map<String, Object> _properties;
	private final SAXParserFactory _saxParserFactory;

}