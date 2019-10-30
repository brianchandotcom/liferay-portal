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

package com.liferay.portal.portlet.bridge.soy.internal;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.portlet.bridge.soy.SoyPortletRegister;

import java.util.Dictionary;

import javax.portlet.Portlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = {})
public class SoyPortletRegisterTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker =
			new ServiceTracker
				<SoyPortletRegister, ServiceRegistration<Portlet>>(
					bundleContext, SoyPortletRegister.class,
					new ServiceTrackerCustomizer
						<SoyPortletRegister, ServiceRegistration<Portlet>>() {

						@Override
						public ServiceRegistration<Portlet> addingService(
							ServiceReference<SoyPortletRegister>
								serviceReference) {

							Bundle bundle = serviceReference.getBundle();

							BundleContext originBundleContext =
								bundle.getBundleContext();

							return originBundleContext.registerService(
								Portlet.class,
								new SoyPortlet(
									bundleContext.getService(serviceReference)),
								_getProperties(serviceReference));
						}

						@Override
						public void modifiedService(
							ServiceReference<SoyPortletRegister>
								serviceReference,
							ServiceRegistration<Portlet> serviceRegistration) {

							serviceRegistration.setProperties(
								_getProperties(serviceReference));
						}

						@Override
						public void removedService(
							ServiceReference<SoyPortletRegister>
								serviceReference,
							ServiceRegistration<Portlet> serviceRegistration) {

							serviceRegistration.unregister();

							Bundle bundle = serviceReference.getBundle();

							BundleContext originBundleContext =
								bundle.getBundleContext();

							originBundleContext.ungetService(serviceReference);
						}

					});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private Dictionary<String, Object> _getProperties(
		ServiceReference<?> serviceReference) {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		for (String key : serviceReference.getPropertyKeys()) {
			if (key.equals(Constants.OBJECTCLASS) ||
				key.equals(ComponentConstants.COMPONENT_ID) ||
				key.equals(ComponentConstants.COMPONENT_NAME) ||
				key.equals(Constants.SERVICE_BUNDLEID) ||
				key.equals(Constants.SERVICE_ID) ||
				key.equals(Constants.SERVICE_SCOPE)) {

				continue;
			}

			dictionary.put(key, serviceReference.getProperty(key));
		}

		return dictionary;
	}

	private ServiceTracker<SoyPortletRegister, ServiceRegistration<Portlet>>
		_serviceTracker;

}