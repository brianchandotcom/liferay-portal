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
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.model.Release;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
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
		Dictionary<String, Object> properties = new Hashtable<>();

		String servletContextName = release.getServletContextName();

		ServiceRegistration<Release> oldServiceRegistration =
			_serviceConfiguratorRegistrations.get(servletContextName);

		if (oldServiceRegistration != null) {
			oldServiceRegistration.unregister();
		}

		properties.put("component.name", servletContextName);
		properties.put("release.version", release.getVersion());

		ServiceRegistration<Release> serviceRegistration =
			_bundleContext.registerService(Release.class, release, properties);

		_serviceConfiguratorRegistrations.put(
			servletContextName, serviceRegistration);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws UpgradeException {

		_bundleContext = bundleContext;

		_log = new Logger(bundleContext);

		List<Release> releases = _releaseLocalService.getReleases(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Release release : releases) {
			publish(release);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (
			ServiceRegistration<Release> serviceRegistration :
				_serviceConfiguratorRegistrations.values()) {

			serviceRegistration.unregister();
		}
	}

	@Reference
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	private BundleContext _bundleContext;
	private Logger _log;
	private ReleaseLocalService _releaseLocalService;
	private final Map<String, ServiceRegistration<Release>>
		_serviceConfiguratorRegistrations = new HashMap<>();

}