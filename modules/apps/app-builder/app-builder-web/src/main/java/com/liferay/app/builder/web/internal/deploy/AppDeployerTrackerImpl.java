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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jeyvison Nascimento
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = AppDeployerTracker.class)
public class AppDeployerTrackerImpl implements AppDeployerTracker {

	@Override
	public AppDeployer getAppDeployer(String type) {
		return _serviceTrackerMap.getService(type);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AppDeployer.class, "app.builder.deploy.type",
			new AppDeployerServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppDeployerTrackerImpl.class);

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private ServiceTrackerMap<String, AppDeployer> _serviceTrackerMap;

	private class AppDeployerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AppDeployer, AppDeployer> {

		@Override
		public AppDeployer addingService(
			ServiceReference<AppDeployer> serviceReference) {

			List<Long> appBuilderAppIds =
				_appBuilderAppLocalService.getAppBuilderAppIds(
					true,
					(String)serviceReference.getProperty(
						"app.builder.deploy.type"));

			AppDeployer appDeployer = _bundleContext.getService(
				serviceReference);

			for (Long appBuilderAppId : appBuilderAppIds) {
				try {
					appDeployer.deploy(appBuilderAppId);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to deploy app " + appBuilderAppId, exception);
				}
			}

			return appDeployer;
		}

		@Override
		public void modifiedService(
			ServiceReference<AppDeployer> serviceReference,
			AppDeployer appDeployer) {
		}

		@Override
		public void removedService(
			ServiceReference<AppDeployer> serviceReference,
			AppDeployer appDeployer) {

			_bundleContext.ungetService(serviceReference);
		}

		private AppDeployerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}