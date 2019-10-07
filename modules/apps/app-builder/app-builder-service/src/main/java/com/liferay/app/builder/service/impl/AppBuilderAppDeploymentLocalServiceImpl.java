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
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.base.AppBuilderAppDeploymentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
			appBuilderAppDeployment.getType());

		try {
			if (appDeployer != null) {
				appDeployer.undeploy(
					appBuilderAppDeployment.getAppBuilderAppId());
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		return super.deleteAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	@Override
	public AppBuilderAppDeployment getAppBuilderAppDeployment(
			long appBuilderAppId, String type)
		throws Exception {

		return appBuilderAppDeploymentPersistence.findByA_T(
			appBuilderAppId, type);
	}

	@Override
	public List<AppBuilderAppDeployment> getAppBuilderAppDeployments(
		long appBuilderAppId) {

		return appBuilderAppDeploymentPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Reference
	private AppDeployerTracker _appDeployerTracker;

}