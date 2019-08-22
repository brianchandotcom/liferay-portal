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

package com.liferay.app.builder.web.internal.instance.lifecycle;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class DeployAppBuilderAppsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<AppBuilderApp> appBuilderApps =
			_appBuilderAppLocalService.getAppBuilderApps(
				company.getCompanyId(), AppBuilderAppConstants.STATUS_DEPLOYED);

		for (AppBuilderApp appBuilderApp : appBuilderApps) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				appBuilderApp.getSettings());

			JSONArray jsonArray = jsonObject.getJSONArray("deploymentTypes");

			for (int i = 0; i < jsonArray.length(); i++) {
				AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
					jsonArray.getString(i));

				if (appDeployer != null) {
					appDeployer.deploy(appBuilderApp.getAppBuilderAppId());
				}
			}
		}
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppDeployerTracker _appDeployerTracker;

}