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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, SiteSettingsConfigurationScreenContributor.class,
			new ServiceTrackerCustomizer
				<SiteSettingsConfigurationScreenContributor,
				 ConfigurationScreen>() {

				@Override
				public ConfigurationScreen addingService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference) {

					return _registerConfigurationScreen(
						_bundleContext.getService(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference,
					ConfigurationScreen configurationScreen) {
				}

				@Override
				public void removedService(
					ServiceReference<SiteSettingsConfigurationScreenContributor>
						serviceReference,
					ConfigurationScreen configurationScreen) {

					_unregisterConfigurationScreen(
						_bundleContext.getService(serviceReference));
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ConfigurationScreen _registerConfigurationScreen(
		SiteSettingsConfigurationScreenContributor
			siteSettingsConfigurationScreenContributor) {

		SiteSettingsConfigurationScreen configurationScreen =
			new SiteSettingsConfigurationScreen(
				siteSettingsConfigurationScreenContributor, _servletContext);

		_serviceRegistrationMap.put(
			siteSettingsConfigurationScreenContributor.getKey(),
			_bundleContext.registerService(
				ConfigurationScreen.class, configurationScreen,
				new HashMapDictionary<>()));

		return configurationScreen;
	}

	private void _unregisterConfigurationScreen(
		SiteSettingsConfigurationScreenContributor
			siteSettingsConfigurationScreenContributor) {

		ServiceRegistration<ConfigurationScreen> serviceRegistration =
			_serviceRegistrationMap.remove(
				siteSettingsConfigurationScreenContributor.getKey());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;
	private final Map<String, ServiceRegistration<ConfigurationScreen>>
		_serviceRegistrationMap = new ConcurrentHashMap<>();
	private ServiceTracker
		<SiteSettingsConfigurationScreenContributor, ConfigurationScreen>
			_serviceTracker;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}