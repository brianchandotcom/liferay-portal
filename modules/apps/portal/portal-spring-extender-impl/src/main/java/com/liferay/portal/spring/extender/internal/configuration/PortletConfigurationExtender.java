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

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.module.util.BundleUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
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
@Component(service = {})
public class PortletConfigurationExtender
	implements BundleTrackerCustomizer<Configuration> {

	@Override
	public Configuration addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		if (!BundleUtil.isLiferayServiceBundle(bundle)) {
			return null;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		Configuration portletConfiguration =
			ConfigurationFactoryUtil.getConfiguration(classLoader, "portlet");

		if (portletConfiguration == null) {
			return null;
		}

		try {
			_resourceActions.populateModelResources(
				classLoader,
				StringUtil.split(
					portletConfiguration.get(
						PropsKeys.RESOURCE_ACTIONS_CONFIGS)));

			if (!PropsValues.RESOURCE_ACTIONS_STRICT_MODE_ENABLED) {
				_resourceActions.populatePortletResources(
					classLoader,
					StringUtil.split(
						portletConfiguration.get(
							PropsKeys.RESOURCE_ACTIONS_CONFIGS)));
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to read resource actions config in " +
					PropsKeys.RESOURCE_ACTIONS_CONFIGS,
				exception);
		}

		return portletConfiguration;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		Configuration portletConfiguration) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		Configuration portletConfiguration) {
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