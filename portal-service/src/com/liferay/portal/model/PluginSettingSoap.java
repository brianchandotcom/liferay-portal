/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.PluginSettingServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.PluginSettingServiceSoap
 * @generated
 */
public class PluginSettingSoap implements Serializable {
	public static PluginSettingSoap toSoapModel(PluginSetting model) {
		PluginSettingSoap soapModel = new PluginSettingSoap();

		soapModel.setPluginSettingId(model.getPluginSettingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setPluginId(model.getPluginId());
		soapModel.setPluginType(model.getPluginType());
		soapModel.setRoles(model.getRoles());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static PluginSettingSoap[] toSoapModels(PluginSetting[] models) {
		PluginSettingSoap[] soapModels = new PluginSettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PluginSettingSoap[][] toSoapModels(PluginSetting[][] models) {
		PluginSettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PluginSettingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PluginSettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PluginSettingSoap[] toSoapModels(List<PluginSetting> models) {
		List<PluginSettingSoap> soapModels = new ArrayList<PluginSettingSoap>(models.size());

		for (PluginSetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PluginSettingSoap[soapModels.size()]);
	}

	public PluginSettingSoap() {
	}

	public long getPrimaryKey() {
		return _pluginSettingId;
	}

	public void setPrimaryKey(long pk) {
		setPluginSettingId(pk);
	}

	public long getPluginSettingId() {
		return _pluginSettingId;
	}

	public void setPluginSettingId(long pluginSettingId) {
		_pluginSettingId = pluginSettingId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getPluginId() {
		return _pluginId;
	}

	public void setPluginId(String pluginId) {
		_pluginId = pluginId;
	}

	public String getPluginType() {
		return _pluginType;
	}

	public void setPluginType(String pluginType) {
		_pluginType = pluginType;
	}

	public String getRoles() {
		return _roles;
	}

	public void setRoles(String roles) {
		_roles = roles;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _pluginSettingId;
	private long _companyId;
	private String _pluginId;
	private String _pluginType;
	private String _roles;
	private boolean _active;
}