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

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.output.stream.container.constants.OutputStreamContainerConstants;
import com.liferay.portal.upgrade.internal.executor.SwappedLogExecutor;
import com.liferay.portal.upgrade.internal.executor.UpgradeExecutor;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.util.PropsValues;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
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
@Component(immediate = true, service = {})
public class UpgradeStepRegistratorTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, UpgradeStepRegistrator.class,
			new UpgradeStepRegistratorServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStepRegistratorTracker.class);

	private BundleContext _bundleContext;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	private ServiceTracker<UpgradeStepRegistrator, SafeCloseable>
		_serviceTracker;

	@Reference
	private SwappedLogExecutor _swappedLogExecutor;

	@Reference
	private UpgradeExecutor _upgradeExecutor;

	private class InitialReleaseServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Release, Void> {

		@Override
		public Void addingService(ServiceReference<Release> serviceReference) {
			DBProcessContext dbProcessContext = new DBProcessContext() {

				@Override
				public DBContext getDBContext() {
					return new DBContext();
				}

				@Override
				public OutputStream getOutputStream() {
					return null;
				}

			};

			for (UpgradeStep upgradeStep : _initialUpgradeSteps) {
				try {
					upgradeStep.upgrade(dbProcessContext);
				}
				catch (UpgradeException upgradeException) {
					_log.error(upgradeException);
				}
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<Release> serviceReference, Void v) {
		}

		@Override
		public void removedService(
			ServiceReference<Release> serviceReference, Void v) {
		}

		private InitialReleaseServiceTrackerCustomizer(
			List<UpgradeStep> initialUpgradeSteps) {

			_initialUpgradeSteps = initialUpgradeSteps;
		}

		private final List<UpgradeStep> _initialUpgradeSteps;

	}

	private class UpgradeStepRegistratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<UpgradeStepRegistrator, SafeCloseable> {

		@Override
		public SafeCloseable addingService(
			ServiceReference<UpgradeStepRegistrator> serviceReference) {

			UpgradeStepRegistrator upgradeStepRegistrator =
				_bundleContext.getService(serviceReference);

			if (upgradeStepRegistrator == null) {
				return null;
			}

			Class<? extends UpgradeStepRegistrator> clazz =
				upgradeStepRegistrator.getClass();

			Bundle bundle = FrameworkUtil.getBundle(clazz);

			String bundleSymbolicName = bundle.getSymbolicName();

			int buildNumber = 0;

			ClassLoader classLoader = clazz.getClassLoader();

			if (classLoader.getResource("service.properties") != null) {
				Configuration configuration =
					ConfigurationFactoryUtil.getConfiguration(
						classLoader, "service");

				Properties properties = configuration.getProperties();

				buildNumber = GetterUtil.getInteger(
					properties.getProperty("build.number"));
			}

			UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(
				buildNumber);

			upgradeStepRegistrator.register(upgradeStepRegistry);

			List<UpgradeStep> initialUpgradeSteps =
				upgradeStepRegistry.getInitialUpgradeSteps();

			ServiceTracker<Release, Void> releaseServiceTracker;

			if (initialUpgradeSteps.isEmpty()) {
				releaseServiceTracker = null;
			}
			else {
				releaseServiceTracker = new ServiceTracker<>(
					_bundleContext, _createFilter(bundleSymbolicName),
					new InitialReleaseServiceTrackerCustomizer(
						initialUpgradeSteps));

				releaseServiceTracker.open();
			}

			List<UpgradeInfo> upgradeInfos =
				upgradeStepRegistry.getUpgradeInfos();

			if (PropsValues.UPGRADE_DATABASE_AUTO_RUN ||
				(_releaseLocalService.fetchRelease(bundleSymbolicName) ==
					null)) {

				try {
					_upgradeExecutor.execute(
						bundleSymbolicName, upgradeInfos,
						OutputStreamContainerConstants.FACTORY_NAME_DUMMY);
				}
				catch (Throwable throwable) {
					_swappedLogExecutor.execute(
						bundleSymbolicName,
						() -> _log.error(
							"Failed upgrade process for module ".concat(
								bundleSymbolicName),
							throwable),
						null);
				}
			}

			List<ServiceRegistration<UpgradeStep>> serviceRegistrations =
				new ArrayList<>(upgradeInfos.size());

			try (SafeCloseable safeCloseable =
					UpgradeStepRegistratorThreadLocal.setEnabled(false)) {

				for (UpgradeInfo upgradeInfo : upgradeInfos) {
					ServiceRegistration<UpgradeStep> serviceRegistration =
						_bundleContext.registerService(
							UpgradeStep.class, upgradeInfo.getUpgradeStep(),
							HashMapDictionaryBuilder.<String, Object>put(
								"build.number", upgradeInfo.getBuildNumber()
							).put(
								"upgrade.bundle.symbolic.name",
								bundleSymbolicName
							).put(
								"upgrade.from.schema.version",
								upgradeInfo.getFromSchemaVersionString()
							).put(
								"upgrade.to.schema.version",
								upgradeInfo.getToSchemaVersionString()
							).build());

					serviceRegistrations.add(serviceRegistration);
				}
			}

			return () -> {
				for (ServiceRegistration<UpgradeStep> serviceRegistration :
						serviceRegistrations) {

					serviceRegistration.unregister();
				}

				if (releaseServiceTracker != null) {
					releaseServiceTracker.close();
				}
			};
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			SafeCloseable safeCloseable) {
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			SafeCloseable safeCloseable) {

			safeCloseable.close();
		}

		private Filter _createFilter(String bundleSymbolicName) {
			try {
				return _bundleContext.createFilter(
					StringBundler.concat(
						"(&(objectClass=", Release.class.getName(),
						")(release.bundle.symbolic.name=", bundleSymbolicName,
						")(release.initial=true))"));
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				return ReflectionUtil.throwException(invalidSyntaxException);
			}
		}

	}

}