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
public class DeviceProfileRuleSoap implements Serializable {
	public static DeviceProfileRuleSoap toSoapModel(DeviceProfileRule model) {
		DeviceProfileRuleSoap soapModel = new DeviceProfileRuleSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setDeviceProfileRuleId(model.getDeviceProfileRuleId());
		soapModel.setDeviceProfileId(model.getDeviceProfileId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setRuleType(model.getRuleType());
		soapModel.setRuleTypeSettings(model.getRuleTypeSettings());

		return soapModel;
	}

	public static DeviceProfileRuleSoap[] toSoapModels(
		DeviceProfileRule[] models) {
		DeviceProfileRuleSoap[] soapModels = new DeviceProfileRuleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DeviceProfileRuleSoap[][] toSoapModels(
		DeviceProfileRule[][] models) {
		DeviceProfileRuleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DeviceProfileRuleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DeviceProfileRuleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DeviceProfileRuleSoap[] toSoapModels(
		List<DeviceProfileRule> models) {
		List<DeviceProfileRuleSoap> soapModels = new ArrayList<DeviceProfileRuleSoap>(models.size());

		for (DeviceProfileRule model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DeviceProfileRuleSoap[soapModels.size()]);
	}

	public DeviceProfileRuleSoap() {
	}

	public long getPrimaryKey() {
		return _deviceProfileRuleId;
	}

	public void setPrimaryKey(long pk) {
		setDeviceProfileRuleId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDeviceProfileRuleId() {
		return _deviceProfileRuleId;
	}

	public void setDeviceProfileRuleId(long deviceProfileRuleId) {
		_deviceProfileRuleId = deviceProfileRuleId;
	}

	public long getDeviceProfileId() {
		return _deviceProfileId;
	}

	public void setDeviceProfileId(long deviceProfileId) {
		_deviceProfileId = deviceProfileId;
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

	public String getRuleType() {
		return _ruleType;
	}

	public void setRuleType(String ruleType) {
		_ruleType = ruleType;
	}

	public String getRuleTypeSettings() {
		return _ruleTypeSettings;
	}

	public void setRuleTypeSettings(String ruleTypeSettings) {
		_ruleTypeSettings = ruleTypeSettings;
	}

	private String _uuid;
	private long _deviceProfileRuleId;
	private long _deviceProfileId;
	private String _name;
	private String _description;
	private String _ruleType;
	private String _ruleTypeSettings;
}