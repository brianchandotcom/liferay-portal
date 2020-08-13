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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dispatch.service.http.DispatchLogServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DispatchLogSoap implements Serializable {

	public static DispatchLogSoap toSoapModel(DispatchLog model) {
		DispatchLogSoap soapModel = new DispatchLogSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setDispatchLogId(model.getDispatchLogId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDispatchTriggerId(model.getDispatchTriggerId());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setError(model.getError());
		soapModel.setOutput(model.getOutput());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static DispatchLogSoap[] toSoapModels(DispatchLog[] models) {
		DispatchLogSoap[] soapModels = new DispatchLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DispatchLogSoap[][] toSoapModels(DispatchLog[][] models) {
		DispatchLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DispatchLogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DispatchLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DispatchLogSoap[] toSoapModels(List<DispatchLog> models) {
		List<DispatchLogSoap> soapModels = new ArrayList<DispatchLogSoap>(
			models.size());

		for (DispatchLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DispatchLogSoap[soapModels.size()]);
	}

	public DispatchLogSoap() {
	}

	public long getPrimaryKey() {
		return _dispatchLogId;
	}

	public void setPrimaryKey(long pk) {
		setDispatchLogId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getDispatchLogId() {
		return _dispatchLogId;
	}

	public void setDispatchLogId(long dispatchLogId) {
		_dispatchLogId = dispatchLogId;
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

	public long getDispatchTriggerId() {
		return _dispatchTriggerId;
	}

	public void setDispatchTriggerId(long dispatchTriggerId) {
		_dispatchTriggerId = dispatchTriggerId;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
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

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _dispatchLogId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _dispatchTriggerId;
	private Date _endDate;
	private String _error;
	private String _output;
	private Date _startDate;
	private int _status;

}