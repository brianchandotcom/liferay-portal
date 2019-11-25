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

package com.liferay.scheduler.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.scheduler.service.http.SchedulerProcessLogServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class SchedulerProcessLogSoap implements Serializable {

	public static SchedulerProcessLogSoap toSoapModel(
		SchedulerProcessLog model) {

		SchedulerProcessLogSoap soapModel = new SchedulerProcessLogSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setSchedulerProcessLogId(model.getSchedulerProcessLogId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSchedulerProcessId(model.getSchedulerProcessId());
		soapModel.setError(model.getError());
		soapModel.setOutput(model.getOutput());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static SchedulerProcessLogSoap[] toSoapModels(
		SchedulerProcessLog[] models) {

		SchedulerProcessLogSoap[] soapModels =
			new SchedulerProcessLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SchedulerProcessLogSoap[][] toSoapModels(
		SchedulerProcessLog[][] models) {

		SchedulerProcessLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SchedulerProcessLogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SchedulerProcessLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SchedulerProcessLogSoap[] toSoapModels(
		List<SchedulerProcessLog> models) {

		List<SchedulerProcessLogSoap> soapModels =
			new ArrayList<SchedulerProcessLogSoap>(models.size());

		for (SchedulerProcessLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SchedulerProcessLogSoap[soapModels.size()]);
	}

	public SchedulerProcessLogSoap() {
	}

	public long getPrimaryKey() {
		return _schedulerProcessLogId;
	}

	public void setPrimaryKey(long pk) {
		setSchedulerProcessLogId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getSchedulerProcessLogId() {
		return _schedulerProcessLogId;
	}

	public void setSchedulerProcessLogId(long schedulerProcessLogId) {
		_schedulerProcessLogId = schedulerProcessLogId;
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

	public long getSchedulerProcessId() {
		return _schedulerProcessId;
	}

	public void setSchedulerProcessId(long schedulerProcessId) {
		_schedulerProcessId = schedulerProcessId;
	}

	public String getError() {
		return _error;
	}

	public void setError(String error) {
		_error = error;
	}

	public String getOutput() {
		return _output;
	}

	public void setOutput(String output) {
		_output = output;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _schedulerProcessLogId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _schedulerProcessId;
	private String _error;
	private String _output;
	private Date _startDate;
	private Date _endDate;
	private int _status;

}