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

package com.liferay.app.builder.service.impl;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.base.AppBuilderAppDeploymentLocalServiceBaseImpl;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderAppDeployment",
	service = AopService.class
)
public class AppBuilderAppDeploymentLocalServiceImpl
	extends AppBuilderAppDeploymentLocalServiceBaseImpl {

	@Override
	public AppBuilderAppDeployment addAppBuilderAppDeployment(
		long appBuilderAppId, String settings, String type) {

		AppBuilderAppDeployment appBuilderAppDeployment =
			appBuilderAppDeploymentPersistence.create(
				counterLocalService.increment());

		appBuilderAppDeployment.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppDeployment.setSettings(settings);
		appBuilderAppDeployment.setType(type);

		return appBuilderAppDeploymentPersistence.update(
			appBuilderAppDeployment);
	}

	@Override
	public AppBuilderAppDeployment deleteAppBuilderAppDeployment(
			long appBuilderAppDeploymentId)
		throws PortalException {

		AppBuilderAppDeployment appBuilderAppDeployment =
			getAppBuilderAppDeployment(appBuilderAppDeploymentId);

		AppDeployer appDeployer = _serviceTrackerMap.getService(
			appBuilderAppDeployment.getType());

		try {
			if (appDeployer != null) {
				appDeployer.undeploy(
					appBuilderAppDeployment.getAppBuilderAppId());
			}
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		return super.deleteAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	@Override
	public AppBuilderAppDeployment getAppBuilderAppDeployment(
			long appBuilderAppId, String type)
		throws PortalException {

		return appBuilderAppDeploymentPersistence.findByA_T(
			appBuilderAppId, type);
	}

	@Override
	public List<AppBuilderAppDeployment> getAppBuilderAppDeployments(
		long appBuilderAppId) {

		return appBuilderAppDeploymentPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AppDeployer.class, "app.builder.deploy.type");
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AppDeployer> _serviceTrackerMap;

}