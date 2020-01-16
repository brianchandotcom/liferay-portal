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

package com.liferay.portal.upgrade.internal.release;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = ReleasePublisher.class)
public final class ReleasePublisher {

	public void publish(Release release) {
		ServiceRegistration<Release> oldServiceRegistration =
			_serviceConfiguratorRegistrations.get(
				release.getServletContextName());

		if (oldServiceRegistration != null) {
			oldServiceRegistration.unregister();
		}

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"release.bundle.symbolic.name", release.getBundleSymbolicName());
		properties.put("release.state", release.getState());

		try {
			if (Validator.isNotNull(release.getSchemaVersion())) {
				Version version = new Version(release.getSchemaVersion());

				properties.put("release.schema.version", version);
			}
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid schema version for release: " + release,
					illegalArgumentException);
			}
		}

		ServiceRegistration<Release> newServiceRegistration =
			_bundleContext.registerService(Release.class, release, properties);

		_serviceConfiguratorRegistrations.put(
			release.getServletContextName(), newServiceRegistration);
	}

	public void publishInProgress(Release release) {
		release.setState(_STATE_IN_PROGRESS);

		publish(release);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		List<Release> releases = _releaseLocalService.getReleases(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Release release : releases) {
			publish(release);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Release> serviceRegistration :
				_serviceConfiguratorRegistrations.values()) {

			serviceRegistration.unregister();
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	private static final int _STATE_IN_PROGRESS = -1;

	private static final Log _log = LogFactoryUtil.getLog(
		ReleasePublisher.class);

	private BundleContext _bundleContext;
	private ReleaseLocalService _releaseLocalService;
	private final Map<String, ServiceRegistration<Release>>
		_serviceConfiguratorRegistrations = new HashMap<>();

}