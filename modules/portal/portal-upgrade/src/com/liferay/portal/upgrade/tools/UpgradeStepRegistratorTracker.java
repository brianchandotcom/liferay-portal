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

package com.liferay.portal.upgrade.tools;

import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.upgrade.internal.UpgradeInfo;
import com.liferay.portal.upgrade.tools.UpgradeStepRegistrator.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class UpgradeStepRegistratorTracker {

	public static List<ServiceRegistration<UpgradeStep>> register(
		BundleContext bundleContext, String upgradeBundleSymbolicName,
		String upgradeFromSchemaVersion, String upgradeToSchemaVersion,
		Collection<UpgradeStep> upgradeSteps) {

		UpgradeStep[] upgradeStepsArray = new UpgradeStep[upgradeSteps.size()];

		return register(
			bundleContext, upgradeBundleSymbolicName, upgradeFromSchemaVersion,
			upgradeToSchemaVersion, upgradeSteps.toArray(upgradeStepsArray));
	}

	public static List<ServiceRegistration<UpgradeStep>> register(
		BundleContext bundleContext, String upgradeBundleSymbolicName,
		String upgradeFromSchemaVersion, String upgradeToSchemaVersion,
		UpgradeStep ... upgradeSteps) {

		List<UpgradeInfo> upgradeInfos = buildUpgradeInfos(
			upgradeFromSchemaVersion, upgradeToSchemaVersion, upgradeSteps);

		List<ServiceRegistration<UpgradeStep>> serviceRegistrations =
			new ArrayList<>();

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			ServiceRegistration<UpgradeStep> upgradeStep = _registerUpgradeStep(
				bundleContext, upgradeBundleSymbolicName, upgradeInfo);

			serviceRegistrations.add(upgradeStep);
		}

		return serviceRegistrations;
	}

	protected static List<UpgradeInfo> buildUpgradeInfos(
		String upgradeFromVersion, String upgradeToVersion,
		UpgradeStep... upgradeSteps) {

		if (ArrayUtil.isEmpty(upgradeSteps)) {
			return Collections.emptyList();
		}

		List<UpgradeInfo> upgradeInfos = new ArrayList<>();

		String from = upgradeFromVersion;

		int length = upgradeSteps.length;

		for (int i = 0; i < length - 1; i++) {
			UpgradeStep upgradeStep = upgradeSteps[i];

			String to = upgradeToVersion + "-step" + (i - length + 1);

			upgradeInfos.add(new UpgradeInfo(from, to, upgradeStep));

			from = to;
		}

		UpgradeInfo upgradeInfo = new UpgradeInfo(
			from, upgradeToVersion, upgradeSteps[length - 1]);

		upgradeInfos.add(upgradeInfo);

		return upgradeInfos;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, UpgradeStepRegistrator.class,
			new UpgradeStepRegistratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected List<UpgradeInfo> buildUpgradeInfos(
		String upgradeFromVersion, String upgradeToVersion,
		Collection<UpgradeStep> upgradeSteps) {

		return buildUpgradeInfos(
			upgradeFromVersion, upgradeToVersion,
			upgradeSteps.toArray(new UpgradeStep[upgradeSteps.size()]));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static ServiceRegistration<UpgradeStep> _registerUpgradeStep(
		BundleContext bundleContext, String upgradeBundleSymbolicName,
		UpgradeInfo upgradeInfo) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("upgrade.db.type", "any");
		properties.put(
			"upgrade.from.version", upgradeInfo.getFromSchemaVersionString());
		properties.put(
			"upgrade.to.version", upgradeInfo.getToSchemaVersionString());
		properties.put(
			"upgrade.bundle.symbolic.name", upgradeBundleSymbolicName);

		return bundleContext.registerService(
			UpgradeStep.class, upgradeInfo.getUpgradeStep(), properties);
	}

	private BundleContext _bundleContext;
	private ServiceTracker<UpgradeStepRegistrator,
		Collection<ServiceRegistration<UpgradeStep>>> _serviceTracker;

	private class UpgradeStepRegistratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<UpgradeStepRegistrator,
				Collection<ServiceRegistration<UpgradeStep>>> {

		@Override
		public Collection<ServiceRegistration<UpgradeStep>> addingService(
			ServiceReference<UpgradeStepRegistrator> serviceReference) {

			UpgradeStepRegistrator upgradeStepRegistrator =
				_bundleContext.getService(serviceReference);

			if (upgradeStepRegistrator == null) {
				return null;
			}

			final Collection<ServiceRegistration<UpgradeStep>>
				serviceRegistrations = new ArrayList<>();

			upgradeStepRegistrator.register(
				new UpgradeStepRegistry(serviceRegistrations));

			return serviceRegistrations;
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			Collection<ServiceRegistration<UpgradeStep>> serviceRegistrations) {

			removedService(serviceReference, serviceRegistrations);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			Collection<ServiceRegistration<UpgradeStep>> serviceRegistrations) {

			for (ServiceRegistration<UpgradeStep> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}

		private class UpgradeStepRegistry implements Registry {

			public UpgradeStepRegistry(
				Collection<ServiceRegistration<UpgradeStep>>
					serviceRegistrations) {

				_serviceRegistrations = serviceRegistrations;
			}

			@Override
			public void register(
				final String bundleSymbolicName, String from, String to,
				Collection<UpgradeStep> upgradeSteps) {

				List<UpgradeInfo> upgradeInfos = buildUpgradeInfos(
					from, to, upgradeSteps);

				for (UpgradeInfo upgradeInfo : upgradeInfos) {
					ServiceRegistration<UpgradeStep> serviceRegistration =
						_registerUpgradeStep(
							_bundleContext, bundleSymbolicName, upgradeInfo);

					_serviceRegistrations.add(serviceRegistration);
				}
			}

			private final Collection<ServiceRegistration<UpgradeStep>>
				_serviceRegistrations;

		}

	}

}