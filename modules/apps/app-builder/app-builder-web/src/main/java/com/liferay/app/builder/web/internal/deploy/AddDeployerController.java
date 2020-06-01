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
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

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
public class AddDeployerController {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, AppDeployer.class,
			new AppDeployerServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
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
		}

		private AppDeployerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;
		private final Log _log = LogFactoryUtil.getLog(
			AppDeployerServiceTrackerCustomizer.class.getName());

	}

}