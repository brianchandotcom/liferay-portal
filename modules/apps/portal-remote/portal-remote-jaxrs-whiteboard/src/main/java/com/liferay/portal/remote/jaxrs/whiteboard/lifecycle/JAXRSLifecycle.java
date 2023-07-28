/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	}

	@Deactivate
	protected synchronized void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JAXRSLifecycle.class);

	private ServiceRegistration<?> _serviceRegistration;

}