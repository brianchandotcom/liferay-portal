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

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.oauth2.provider.scope.liferay.ApplicationDescriptorLocator;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Tomas Polesovsky
 */
@Component(service = ApplicationDescriptorLocator.class)
public class ApplicationDescriptorLocatorImpl
	implements ApplicationDescriptorLocator {

	@Override
	public ApplicationDescriptor getApplicationDescriptor(
		String applicationName) {

		ApplicationDescriptor applicationDescriptor =
			_serviceTrackerMap.getService(applicationName);

		if (applicationDescriptor == null) {
			return _defaultApplicationDescriptor;
		}

		return applicationDescriptor;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ApplicationDescriptor.class,
			OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL, target = "(default=true)"
	)
	private ApplicationDescriptor _defaultApplicationDescriptor;

	private ServiceTrackerMap<String, ApplicationDescriptor> _serviceTrackerMap;

}