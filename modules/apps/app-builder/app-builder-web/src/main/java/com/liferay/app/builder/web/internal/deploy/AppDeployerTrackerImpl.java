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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = AppDeployerTracker.class)
public class AppDeployerTrackerImpl implements AppDeployerTracker {

	@Override
	public AppDeployer getAppDeployer(String deploymentType) {
		return _appDeployersMap.get(deploymentType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addAppDeployer(
		AppDeployer appDeployer, Map<String, Object> properties) {

		_appDeployersMap.put(
			(String)properties.get("com.app.builder.deploy.type"), appDeployer);
	}

	@Deactivate
	protected void deactivate() {
		_appDeployersMap.clear();
	}

	protected void removeAppDeployer(
		AppDeployer appDeployer, Map<String, Object> properties) {

		_appDeployersMap.remove(properties.get("com.app.builder.deploy.type"));
	}

	private final Map<String, AppDeployer> _appDeployersMap =
		new ConcurrentHashMap<>();

}