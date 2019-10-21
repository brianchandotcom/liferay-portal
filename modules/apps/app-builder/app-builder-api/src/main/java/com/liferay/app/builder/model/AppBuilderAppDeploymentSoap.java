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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderAppDeploymentSoap implements Serializable {

	public static AppBuilderAppDeploymentSoap toSoapModel(
		AppBuilderAppDeployment model) {

		AppBuilderAppDeploymentSoap soapModel =
			new AppBuilderAppDeploymentSoap();

		soapModel.setAppBuilderAppDeploymentId(
			model.getAppBuilderAppDeploymentId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAppBuilderAppId(model.getAppBuilderAppId());
		soapModel.setSettings(model.getSettings());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static AppBuilderAppDeploymentSoap[] toSoapModels(
		AppBuilderAppDeployment[] models) {

		AppBuilderAppDeploymentSoap[] soapModels =
			new AppBuilderAppDeploymentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppDeploymentSoap[][] toSoapModels(
		AppBuilderAppDeployment[][] models) {

		AppBuilderAppDeploymentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AppBuilderAppDeploymentSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new AppBuilderAppDeploymentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppDeploymentSoap[] toSoapModels(
		List<AppBuilderAppDeployment> models) {

		List<AppBuilderAppDeploymentSoap> soapModels =
			new ArrayList<AppBuilderAppDeploymentSoap>(models.size());

		for (AppBuilderAppDeployment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AppBuilderAppDeploymentSoap[soapModels.size()]);
	}

	public AppBuilderAppDeploymentSoap() {
	}

	public long getPrimaryKey() {
		return _appBuilderAppDeploymentId;
	}

	public void setPrimaryKey(long pk) {
		setAppBuilderAppDeploymentId(pk);
	}

	public long getAppBuilderAppDeploymentId() {
		return _appBuilderAppDeploymentId;
	}

	public void setAppBuilderAppDeploymentId(long appBuilderAppDeploymentId) {
		_appBuilderAppDeploymentId = appBuilderAppDeploymentId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	public void setAppBuilderAppId(long appBuilderAppId) {
		_appBuilderAppId = appBuilderAppId;
	}

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _appBuilderAppDeploymentId;
	private long _companyId;
	private long _appBuilderAppId;
	private String _settings;
	private String _type;

}