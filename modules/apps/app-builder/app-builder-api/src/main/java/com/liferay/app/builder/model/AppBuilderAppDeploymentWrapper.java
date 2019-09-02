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

package com.liferay.app.builder.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderAppDeployment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeployment
 * @generated
 */
@ProviderType
public class AppBuilderAppDeploymentWrapper
	extends BaseModelWrapper<AppBuilderAppDeployment>
	implements AppBuilderAppDeployment, ModelWrapper<AppBuilderAppDeployment> {

	public AppBuilderAppDeploymentWrapper(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		super(appBuilderAppDeployment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"appBuilderAppDeploymentId", getAppBuilderAppDeploymentId());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("deploymentType", getDeploymentType());
		attributes.put("settings", getSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long appBuilderAppDeploymentId = (Long)attributes.get(
			"appBuilderAppDeploymentId");

		if (appBuilderAppDeploymentId != null) {
			setAppBuilderAppDeploymentId(appBuilderAppDeploymentId);
		}

		Long appBuilderAppId = (Long)attributes.get("appBuilderAppId");

		if (appBuilderAppId != null) {
			setAppBuilderAppId(appBuilderAppId);
		}

		String deploymentType = (String)attributes.get("deploymentType");

		if (deploymentType != null) {
			setDeploymentType(deploymentType);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}
	}

	/**
	 * Returns the app builder app deployment ID of this app builder app deployment.
	 *
	 * @return the app builder app deployment ID of this app builder app deployment
	 */
	@Override
	public long getAppBuilderAppDeploymentId() {
		return model.getAppBuilderAppDeploymentId();
	}

	/**
	 * Returns the app builder app ID of this app builder app deployment.
	 *
	 * @return the app builder app ID of this app builder app deployment
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	/**
	 * Returns the deployment type of this app builder app deployment.
	 *
	 * @return the deployment type of this app builder app deployment
	 */
	@Override
	public String getDeploymentType() {
		return model.getDeploymentType();
	}

	/**
	 * Returns the primary key of this app builder app deployment.
	 *
	 * @return the primary key of this app builder app deployment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings of this app builder app deployment.
	 *
	 * @return the settings of this app builder app deployment
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app builder app deployment ID of this app builder app deployment.
	 *
	 * @param appBuilderAppDeploymentId the app builder app deployment ID of this app builder app deployment
	 */
	@Override
	public void setAppBuilderAppDeploymentId(long appBuilderAppDeploymentId) {
		model.setAppBuilderAppDeploymentId(appBuilderAppDeploymentId);
	}

	/**
	 * Sets the app builder app ID of this app builder app deployment.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app deployment
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the deployment type of this app builder app deployment.
	 *
	 * @param deploymentType the deployment type of this app builder app deployment
	 */
	@Override
	public void setDeploymentType(String deploymentType) {
		model.setDeploymentType(deploymentType);
	}

	/**
	 * Sets the primary key of this app builder app deployment.
	 *
	 * @param primaryKey the primary key of this app builder app deployment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this app builder app deployment.
	 *
	 * @param settings the settings of this app builder app deployment
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	@Override
	protected AppBuilderAppDeploymentWrapper wrap(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		return new AppBuilderAppDeploymentWrapper(appBuilderAppDeployment);
	}

}