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

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class SiteSettingsConfigurationScreenContributorTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, SiteSettingsConfigurationScreenContributor.class,
			new ServiceTrackerCustomizer
				<SiteSettingsConfigurationScreenContributor,
				 ServiceRegistration<?>>() {

				@Override
				public ServiceRegistration<?> addingService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference) {

					return bundleContext.registerService(
						ConfigurationScreen.class,
						new SiteSettingsConfigurationScreen(
							bundleContext.getService(serviceReference),
							_servletContext),
						null);
				}

				@Override
				public void modifiedService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference,
					ServiceRegistration<?> serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference,
					ServiceRegistration<?> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker
		<SiteSettingsConfigurationScreenContributor, ServiceRegistration<?>>
			_serviceTracker;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}