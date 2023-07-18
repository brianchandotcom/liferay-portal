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

package com.liferay.portal.remote.jaxrs.whiteboard.lifecycle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Stian Sigvartsen
 */
@Component(service = JAXRSLifecycle.class)
public class JAXRSLifecycle {

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Signaling JAX-RS whiteboard ready to initialize");
		}

		_serviceRegistration = bundleContext.registerService(
			Object.class, new Object(),
			MapUtil.singletonDictionary(
				"liferay.jaxrs.whiteboard.ready", true));

		_bundleContext = bundleContext;
	}

	@Deactivate
	protected synchronized void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JAXRSLifecycle.class);

	private BundleContext _bundleContext;
	private ServiceRegistration<?> _serviceRegistration;

}