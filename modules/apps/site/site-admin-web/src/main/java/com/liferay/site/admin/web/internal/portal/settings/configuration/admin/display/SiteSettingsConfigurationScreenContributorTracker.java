/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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