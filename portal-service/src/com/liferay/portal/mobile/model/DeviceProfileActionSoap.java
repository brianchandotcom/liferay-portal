/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Edward C. Han
 * @generated
 */
public class DeviceProfileActionSoap implements Serializable {
	public static DeviceProfileActionSoap toSoapModel(DeviceProfileAction model) {
		DeviceProfileActionSoap soapModel = new DeviceProfileActionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setDeviceProfileActionId(model.getDeviceProfileActionId());
		soapModel.setDeviceProfileId(model.getDeviceProfileId());
		soapModel.setDeviceProfileRuleId(model.getDeviceProfileRuleId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static DeviceProfileActionSoap[] toSoapModels(
		DeviceProfileAction[] models) {
		DeviceProfileActionSoap[] soapModels = new DeviceProfileActionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DeviceProfileActionSoap[][] toSoapModels(
		DeviceProfileAction[][] models) {
		DeviceProfileActionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DeviceProfileActionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DeviceProfileActionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DeviceProfileActionSoap[] toSoapModels(
		List<DeviceProfileAction> models) {
		List<DeviceProfileActionSoap> soapModels = new ArrayList<DeviceProfileActionSoap>(models.size());

		for (DeviceProfileAction model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DeviceProfileActionSoap[soapModels.size()]);
	}

	public DeviceProfileActionSoap() {
	}

	public long getPrimaryKey() {
		return _deviceProfileActionId;
	}

	public void setPrimaryKey(long pk) {
		setDeviceProfileActionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDeviceProfileActionId() {
		return _deviceProfileActionId;
	}

	public void setDeviceProfileActionId(long deviceProfileActionId) {
		_deviceProfileActionId = deviceProfileActionId;
	}

	public long getDeviceProfileId() {
		return _deviceProfileId;
	}

	public void setDeviceProfileId(long deviceProfileId) {
		_deviceProfileId = deviceProfileId;
	}

	public long getDeviceProfileRuleId() {
		return _deviceProfileRuleId;
	}

	public void setDeviceProfileRuleId(long deviceProfileRuleId) {
		_deviceProfileRuleId = deviceProfileRuleId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private String _uuid;
	private long _deviceProfileActionId;
	private long _deviceProfileId;
	private long _deviceProfileRuleId;
	private String _name;
	private String _description;
	private String _type;
	private String _typeSettings;
}