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

package com.liferay.dispatch.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.dispatch.service.http.DispatchTriggerServiceSoap}.
 *
 * @author Matija Petanjek
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DispatchTriggerSoap implements Serializable {

	public static DispatchTriggerSoap toSoapModel(DispatchTrigger model) {
		DispatchTriggerSoap soapModel = new DispatchTriggerSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setDispatchTriggerId(model.getDispatchTriggerId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setActive(model.isActive());
		soapModel.setCronExpression(model.getCronExpression());
		soapModel.setDispatchTaskClusterMode(
			model.getDispatchTaskClusterMode());
		soapModel.setDispatchTaskExecutorType(
			model.getDispatchTaskExecutorType());
		soapModel.setDispatchTaskSettings(model.getDispatchTaskSettings());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setName(model.getName());
		soapModel.setOverlapAllowed(model.isOverlapAllowed());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setSystem(model.isSystem());

		return soapModel;
	}

	public static DispatchTriggerSoap[] toSoapModels(DispatchTrigger[] models) {
		DispatchTriggerSoap[] soapModels =
			new DispatchTriggerSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DispatchTriggerSoap[][] toSoapModels(
		DispatchTrigger[][] models) {

		DispatchTriggerSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DispatchTriggerSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DispatchTriggerSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DispatchTriggerSoap[] toSoapModels(
		List<DispatchTrigger> models) {

		List<DispatchTriggerSoap> soapModels =
			new ArrayList<DispatchTriggerSoap>(models.size());

		for (DispatchTrigger model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DispatchTriggerSoap[soapModels.size()]);
	}

	public DispatchTriggerSoap() {
	}

	public long getPrimaryKey() {
		return _dispatchTriggerId;
	}

	public void setPrimaryKey(long pk) {
		setDispatchTriggerId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getDispatchTriggerId() {
		return _dispatchTriggerId;
	}

	public void setDispatchTriggerId(long dispatchTriggerId) {
		_dispatchTriggerId = dispatchTriggerId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
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

	public String getCronExpression() {
		return _cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		_cronExpression = cronExpression;
	}

	public int getDispatchTaskClusterMode() {
		return _dispatchTaskClusterMode;
	}

	public void setDispatchTaskClusterMode(int dispatchTaskClusterMode) {
		_dispatchTaskClusterMode = dispatchTaskClusterMode;
	}

	public String getDispatchTaskExecutorType() {
		return _dispatchTaskExecutorType;
	}

	public void setDispatchTaskExecutorType(String dispatchTaskExecutorType) {
		_dispatchTaskExecutorType = dispatchTaskExecutorType;
	}

	public String getDispatchTaskSettings() {
		return _dispatchTaskSettings;
	}

	public void setDispatchTaskSettings(String dispatchTaskSettings) {
		_dispatchTaskSettings = dispatchTaskSettings;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean getOverlapAllowed() {
		return _overlapAllowed;
	}

	public boolean isOverlapAllowed() {
		return _overlapAllowed;
	}

	public void setOverlapAllowed(boolean overlapAllowed) {
		_overlapAllowed = overlapAllowed;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	private long _mvccVersion;
	private long _dispatchTriggerId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _active;
	private String _cronExpression;
	private int _dispatchTaskClusterMode;
	private String _dispatchTaskExecutorType;
	private String _dispatchTaskSettings;
	private Date _endDate;
	private String _name;
	private boolean _overlapAllowed;
	private Date _startDate;
	private boolean _system;

}