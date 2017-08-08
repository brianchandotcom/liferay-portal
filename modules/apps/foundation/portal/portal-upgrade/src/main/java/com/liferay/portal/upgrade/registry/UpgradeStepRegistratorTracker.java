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

package com.liferay.portal.upgrade.registry;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

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
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class UpgradeStepRegistratorTracker {

	public static List<ServiceRegistration<UpgradeStep>> register(
		BundleContext bundleContext, String bundleSymbolicName,
		String fromSchemaVersionString, String toSchemaVersionString,
		Dictionary<String, Object> properties, UpgradeStep... upgradeSteps) {

		List<ServiceRegistration<UpgradeStep>> serviceRegistrations =
			new ArrayList<>();

		List<UpgradeInfo> upgradeInfos = createUpgradeInfos(
			fromSchemaVersionString, toSchemaVersionString,
			GetterUtil.getInteger(properties.get("build.number")),
			upgradeSteps);

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			ServiceRegistration<UpgradeStep> serviceRegistration = _register(
				bundleContext, bundleSymbolicName, upgradeInfo, properties);

			serviceRegistrations.add(serviceRegistration);
		}

		return serviceRegistrations;
	}

	public static List<ServiceRegistration<UpgradeStep>> register(
		BundleContext bundleContext, String bundleSymbolicName,
		String fromSchemaVersionString, String toSchemaVersionString,
		UpgradeStep... upgradeSteps) {

		return register(
			bundleContext, bundleSymbolicName, fromSchemaVersionString,
			toSchemaVersionString, new Hashtable<String, Object>(),
			upgradeSteps);
	}

	protected static List<UpgradeInfo> createUpgradeInfos(
		String fromSchemaVersionString, String toSchemaVersionString,
		int buildNumber, UpgradeStep... upgradeSteps) {

		if (ArrayUtil.isEmpty(upgradeSteps)) {
			return Collections.emptyList();
		}

		List<UpgradeInfo> upgradeInfos = new ArrayList<>();

		String upgradeInfoFromSchemaVersionString = fromSchemaVersionString;

		for (int i = 0; i < upgradeSteps.length - 1; i++) {
			UpgradeStep upgradeStep = upgradeSteps[i];

			String upgradeInfoToSchemaVersionString =
				toSchemaVersionString + "-step" + (i - upgradeSteps.length + 1);

			UpgradeInfo upgradeInfo = new UpgradeInfo(
				upgradeInfoFromSchemaVersionString,
				upgradeInfoToSchemaVersionString, buildNumber, upgradeStep);

			upgradeInfos.add(upgradeInfo);

			upgradeInfoFromSchemaVersionString =
				upgradeInfoToSchemaVersionString;
		}

		UpgradeInfo upgradeInfo = new UpgradeInfo(
			upgradeInfoFromSchemaVersionString, toSchemaVersionString,
			buildNumber, upgradeSteps[upgradeSteps.length - 1]);

		upgradeInfos.add(upgradeInfo);

		return upgradeInfos;
	}

	/**
	 * @deprecated As of 2.8.0, replaced by {@link #createUpgradeInfos(String,
	 *             String, int, UpgradeStep...)}
	 */
	@Deprecated
	protected static List<UpgradeInfo> createUpgradeInfos(
		String fromSchemaVersionString, String toSchemaVersionString,
		UpgradeStep... upgradeSteps) {

		return createUpgradeInfos(
			fromSchemaVersionString, toSchemaVersionString, 0, upgradeSteps);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, UpgradeStepRegistrator.class,
			new UpgradeStepRegistratorServiceTrackerCustomizer());
	}

	/**
	 * @deprecated As of 2.8.0, with no direct replacement
	 */
	@Deprecated
	protected List<UpgradeInfo> createUpgradeInfos(
		String fromSchemaVersionString, String toSchemaVersionString,
		Collection<UpgradeStep> upgradeSteps) {

		return createUpgradeInfos(
			fromSchemaVersionString, toSchemaVersionString, 0,
			upgradeSteps.toArray(new UpgradeStep[upgradeSteps.size()]));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference(target = ModuleServiceLifecycle.DATABASE_INITIALIZED)
	protected ModuleServiceLifecycle moduleServiceLifecycle;

	private static ServiceRegistration<UpgradeStep> _register(
		BundleContext bundleContext, String bundleSymbolicName,
		UpgradeInfo upgradeInfo, Dictionary<String, Object> properties) {

		properties.put("build.number", upgradeInfo.getBuildNumber());
		properties.put("upgrade.bundle.symbolic.name", bundleSymbolicName);
		properties.put("upgrade.db.type", "any");
		properties.put(
			"upgrade.from.schema.version",
			upgradeInfo.getFromSchemaVersionString());
		properties.put(
			"upgrade.to.schema.version",
			upgradeInfo.getToSchemaVersionString());

		return bundleContext.registerService(
			UpgradeStep.class, upgradeInfo.getUpgradeStep(), properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStepRegistratorTracker.class);

	private BundleContext _bundleContext;
	private ServiceTracker
		<UpgradeStepRegistrator, Collection<ServiceRegistration<UpgradeStep>>>
			_serviceTracker;

	private class UpgradeStepRegistratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<UpgradeStepRegistrator,
				Collection<ServiceRegistration<UpgradeStep>>> {

		@Override
		public Collection<ServiceRegistration<UpgradeStep>> addingService(
			ServiceReference<UpgradeStepRegistrator> serviceReference) {

			UpgradeStepRegistrator upgradeStepRegistrator =
				_bundleContext.getService(serviceReference);

			if (upgradeStepRegistrator == null) {
				return null;
			}

			Collection<ServiceRegistration<UpgradeStep>> serviceRegistrations =
				new ArrayList<>();

			upgradeStepRegistrator.register(
				new UpgradeStepRegistry(
					upgradeStepRegistrator, serviceRegistrations));

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
				UpgradeStepRegistrator upgradeStepRegistrator,
				Collection<ServiceRegistration<UpgradeStep>>
					serviceRegistrations) {

				_upgradeStepRegistrator = upgradeStepRegistrator;
				_serviceRegistrations = serviceRegistrations;
			}

			@Override
			public void register(
				final String bundleSymbolicName, String fromSchemaVersionString,
				String toSchemaVersionString, UpgradeStep... upgradeSteps) {

				int buildNumber = 0;

				try {
					if (ArrayUtil.isNotEmpty(upgradeSteps)) {
						Class<? extends UpgradeStepRegistrator> clazz =
							_upgradeStepRegistrator.getClass();

						Configuration configuration =
							ConfigurationFactoryUtil.getConfiguration(
								clazz.getClassLoader(), "service");

						Properties properties = configuration.getProperties();

						buildNumber = GetterUtil.getInteger(
							properties.getProperty("build.number"));
					}
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to read service.properties for " +
								bundleSymbolicName);
					}
				}

				List<UpgradeInfo> upgradeInfos = createUpgradeInfos(
					fromSchemaVersionString, toSchemaVersionString, buildNumber,
					upgradeSteps);

				for (UpgradeInfo upgradeInfo : upgradeInfos) {
					ServiceRegistration<UpgradeStep> serviceRegistration =
						_register(
							_bundleContext, bundleSymbolicName, upgradeInfo,
							new Hashtable<String, Object>());

					_serviceRegistrations.add(serviceRegistration);
				}
			}

			private final Collection<ServiceRegistration<UpgradeStep>>
				_serviceRegistrations;
			private final UpgradeStepRegistrator _upgradeStepRegistrator;

		}

	}

}