/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.jaxrs.whiteboard.lifecycle;

import com.liferay.petra.concurrent.DCLSingleton;
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

	public void ensureReady() {
		if (_jaxrsReady) {
			return;
		}

		_jaxrsReady = true;

		_serviceRegistrationDCLSingleton.getSingleton(
			() -> _bundleContext.registerService(
				Object.class, new Object(),
				MapUtil.singletonDictionary(
					"liferay.jaxrs.whiteboard.ready", true)));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistrationDCLSingleton.destroy(
			ServiceRegistration::unregister);
	}

	private BundleContext _bundleContext;
	private boolean _jaxrsReady;
	private final DCLSingleton<ServiceRegistration<?>>
		_serviceRegistrationDCLSingleton = new DCLSingleton<>();

}