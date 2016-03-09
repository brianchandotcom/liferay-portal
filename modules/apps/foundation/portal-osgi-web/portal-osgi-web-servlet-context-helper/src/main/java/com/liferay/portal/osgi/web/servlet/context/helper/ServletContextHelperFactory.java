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

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.ServletContextHelperRegistrationServiceFactory;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.runtime.HttpServiceRuntime;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = ServletContextHelperFactory.class)
public class ServletContextHelperFactory {

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws Exception {

		_serviceRegistration = bundleContext.registerService(
			ServletContextHelperRegistration.class.getName(),
			new ServletContextHelperRegistrationServiceFactory(
				_props, properties), null);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();
	}

	@Reference
	private HttpServiceRuntime _httpServiceRuntime;

	@Reference
	private Props _props;

	private ServiceRegistration<?> _serviceRegistration;

}