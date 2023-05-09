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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;

import java.util.Dictionary;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(service = {})
public class ServiceConfigurationExtender
	implements BundleTrackerCustomizer
		<ServiceConfigurationExtender.ServiceConfigurationExtension> {

	@Override
	public ServiceConfigurationExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		Configuration serviceConfiguration =
			ConfigurationFactoryUtil.getConfiguration(classLoader, "service");

		if (serviceConfiguration == null) {
			return null;
		}

		String requireSchemaVersion = headers.get(
			"Liferay-Require-SchemaVersion");

		ServiceConfigurationInitializer serviceConfigurationInitializer =
			new ServiceConfigurationInitializer(
				bundle, classLoader, serviceConfiguration,
				_serviceComponentLocalService);

		ServiceConfigurationExtension serviceConfigurationExtension =
			new ServiceConfigurationExtension(
				bundle, requireSchemaVersion, serviceConfigurationInitializer);

		serviceConfigurationExtension.start();

		return serviceConfigurationExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceConfigurationExtension serviceConfigurationExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceConfigurationExtension serviceConfigurationExtension) {

		serviceConfigurationExtension.destroy();
	}

	public static class ServiceConfigurationExtension {

		public void destroy() {
			_dependencyManager.remove(_component);
		}

		public void start() {
			_dependencyManager.add(_component);
		}

		private ServiceConfigurationExtension(
			Bundle bundle, String requireSchemaVersion,
			ServiceConfigurationInitializer serviceConfigurationInitializer) {

			_dependencyManager = new DependencyManager(
				bundle.getBundleContext());

			_component = _dependencyManager.createComponent();

			_component.setImplementation(serviceConfigurationInitializer);

			if (requireSchemaVersion == null) {
				return;
			}

			String versionRangeFilter = null;

			// See LPS-76926

			try {
				Version version = new Version(requireSchemaVersion);

				versionRangeFilter = _getVersionRangerFilter(version);
			}
			catch (IllegalArgumentException illegalArgumentException1) {
				try {
					VersionRange versionRange = new VersionRange(
						requireSchemaVersion);

					versionRangeFilter = versionRange.toFilterString(
						"release.schema.version");
				}
				catch (IllegalArgumentException illegalArgumentException2) {
					illegalArgumentException1.addSuppressed(
						illegalArgumentException2);

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid \"Liferay-Require-SchemaVersion\" " +
								"header for bundle: " + bundle.getBundleId(),
							illegalArgumentException1);
					}
				}
			}

			if (versionRangeFilter == null) {
				return;
			}

			ServiceDependency serviceDependency =
				_dependencyManager.createServiceDependency();

			serviceDependency.setRequired(true);

			serviceDependency.setService(
				Release.class,
				StringBundler.concat(
					"(&(release.bundle.symbolic.name=",
					bundle.getSymbolicName(), ")", versionRangeFilter, ")"));

			_component.add(serviceDependency);
		}

		private String _getVersionRangerFilter(Version version) {
			return StringBundler.concat(
				"(&(release.schema.version>=", version.getMajor(), ".",
				version.getMinor(), ".0)(!(release.schema.version>=",
				version.getMajor() + 1, ".0.0)))");
		}

		private final org.apache.felix.dm.Component _component;
		private final DependencyManager _dependencyManager;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfigurationExtender.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

}