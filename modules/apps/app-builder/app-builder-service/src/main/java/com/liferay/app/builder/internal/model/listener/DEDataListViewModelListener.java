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

package com.liferay.app.builder.internal.model.listener;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(immediate = true, service = ModelListener.class)
public class DEDataListViewModelListener
	extends BaseModelListener<DEDataListView> {

	@Override
	public void onAfterRemove(DEDataListView deDataListView)
		throws ModelListenerException {

		List<AppBuilderApp> appBuilderApps =
			_appBuilderAppLocalService.getAppBuilderAppsByDEDataListViewId(
				deDataListView.getDeDataListViewId());

		for (AppBuilderApp appBuilderApp : appBuilderApps) {
			List<AppBuilderAppDeployment> appBuilderAppDeployments =
				_appBuilderAppDeploymentLocalService.
					getAppBuilderAppDeployments(
						appBuilderApp.getAppBuilderAppId());

			for (AppBuilderAppDeployment appBuilderAppDeployment :
					appBuilderAppDeployments) {

				AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
					appBuilderAppDeployment.getType());

				try {
					if (appDeployer != null) {
						appDeployer.undeploy(
							appBuilderApp.getAppBuilderAppId());
					}
				}
				catch (Exception exception) {
					throw new ModelListenerException(exception);
				}
			}
		}
	}

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppDeployerTracker _appDeployerTracker;

}