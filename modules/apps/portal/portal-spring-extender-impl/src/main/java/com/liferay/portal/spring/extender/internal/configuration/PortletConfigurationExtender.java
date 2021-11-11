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

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class PortletConfigurationExtender
	implements BundleTrackerCustomizer
		<PortletConfigurationExtender.PortletConfigurationExtension> {

	@Override
	public PortletConfigurationExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		Configuration portletConfiguration = ConfigurationUtil.getConfiguration(
			classLoader, "portlet");

		if (portletConfiguration == null) {
			return null;
		}

		PortletConfigurationExtension portletConfigurationExtension =
			new PortletConfigurationExtension(
				bundle, classLoader, portletConfiguration);

		portletConfigurationExtension.start();

		return portletConfigurationExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		PortletConfigurationExtension portletConfigurationExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		PortletConfigurationExtension portletConfigurationExtension) {

		portletConfigurationExtension.destroy();
	}

	public class PortletConfigurationExtension {

		public void destroy() {
			if (_configurationServiceRegistration != null) {
				_configurationServiceRegistration.unregister();

				_configurationServiceRegistration = null;
			}
		}

		public void start() {
			try {
				_resourceActions.populateModelResources(
					_classLoader,
					StringUtil.split(
						_portletConfiguration.get(
							PropsKeys.RESOURCE_ACTIONS_CONFIGS)));

				if (!PropsValues.RESOURCE_ACTIONS_STRICT_MODE_ENABLED) {
					_resourceActions.populatePortletResources(
						_classLoader,
						StringUtil.split(
							_portletConfiguration.get(
								PropsKeys.RESOURCE_ACTIONS_CONFIGS)));
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to read resource actions config in " +
						PropsKeys.RESOURCE_ACTIONS_CONFIGS,
					exception);
			}

			BundleContext bundleContext = _bundle.getBundleContext();

			_configurationServiceRegistration = bundleContext.registerService(
				Configuration.class, _portletConfiguration,
				HashMapDictionaryBuilder.<String, Object>put(
					"name", "portlet"
				).put(
					"origin.bundle.symbolic.name", _bundle.getSymbolicName()
				).build());
		}

		private PortletConfigurationExtension(
			Bundle bundle, ClassLoader classLoader,
			Configuration portletConfiguration) {

			_bundle = bundle;
			_classLoader = classLoader;
			_portletConfiguration = portletConfiguration;
		}

		private final Bundle _bundle;
		private final ClassLoader _classLoader;
		private ServiceRegistration<Configuration>
			_configurationServiceRegistration;
		private final Configuration _portletConfiguration;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigurationExtender.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private ResourceActions _resourceActions;

}