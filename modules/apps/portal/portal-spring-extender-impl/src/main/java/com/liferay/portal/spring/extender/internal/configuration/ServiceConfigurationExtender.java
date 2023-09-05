/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;

import java.util.Dictionary;

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
	implements BundleTrackerCustomizer<ServiceConfigurationInitializer> {

	@Override
	public ServiceConfigurationInitializer addingBundle(
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

		if (requireSchemaVersion == null) {
			_log.error("Liferay-Require-SchemaVersion is required");

			return null;
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
						"Invalid \"Liferay-Require-SchemaVersion\" header " +
							"for bundle: " + bundle.getBundleId(),
						illegalArgumentException1);
				}
			}
		}

		if (versionRangeFilter == null) {
			return null;
		}

		ServiceConfigurationInitializer serviceConfigurationInitializer =
			new ServiceConfigurationInitializer(
				bundle, classLoader, serviceConfiguration,
				_serviceComponentLocalService);

		ServiceLatch serviceLatch = new ServiceLatch(bundle.getBundleContext());

		serviceLatch.waitFor(
			StringBundler.concat(
				"(&(objectClass=", Release.class.getName(),
				")(release.bundle.symbolic.name=", bundle.getSymbolicName(),
				")", versionRangeFilter, ")"));

		serviceLatch.openOn(serviceConfigurationInitializer::start);

		return serviceConfigurationInitializer;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceConfigurationInitializer serviceConfigurationInitializer) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceConfigurationInitializer serviceConfigurationInitializer) {

		serviceConfigurationInitializer.stop();
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

	private String _getVersionRangerFilter(Version version) {
		return StringBundler.concat(
			"(&(release.schema.version>=", version.getMajor(), ".",
			version.getMinor(), ".0)(!(release.schema.version>=",
			version.getMajor() + 1, ".0.0)))");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfigurationExtender.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

}