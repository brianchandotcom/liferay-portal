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

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = {})
public class AppDeployerActivator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, AppDeployer.class,
			new AppDeployerServiceTrackerCustomizer(
				_appBuilderAppLocalService, bundleContext));
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private ServiceTracker<AppDeployer, AppDeployer> _serviceTracker;

	private class AppDeployerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AppDeployer, AppDeployer> {

		@Override
		public AppDeployer addingService(
			ServiceReference<AppDeployer> serviceReference) {

			List<Long> appBuilderAppIds =
				_appBuilderAppLocalService.getAppBuilderAppIds(
					AppBuilderAppConstants.Status.DEPLOYED.getLabel(),
					(String)serviceReference.getProperty(
						"com.app.builder.deploy.type"));

			AppDeployer appDeployer = _bundleContext.getService(
				serviceReference);

			for (Long appBuilderAppId : appBuilderAppIds) {
				try {
					appDeployer.deploy(appBuilderAppId);
				}
				catch (Exception e) {
					_logger.log(
						Level.SEVERE, "Unable to deploy App " + appBuilderAppId,
						e);
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
		}

		private AppDeployerServiceTrackerCustomizer(
			AppBuilderAppLocalService appBuilderAppLocalService,
			BundleContext bundleContext) {

			_appBuilderAppLocalService = appBuilderAppLocalService;
			_bundleContext = bundleContext;
		}

		private final AppBuilderAppLocalService _appBuilderAppLocalService;
		private final BundleContext _bundleContext;
		private final Logger _logger = Logger.getLogger(
			AppDeployerServiceTrackerCustomizer.class.getName());

	}

}