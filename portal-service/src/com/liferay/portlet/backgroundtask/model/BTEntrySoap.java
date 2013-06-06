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

package com.liferay.portlet.backgroundtask.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BTEntrySoap implements Serializable {
	public static BTEntrySoap toSoapModel(BTEntry model) {
		BTEntrySoap soapModel = new BTEntrySoap();

		soapModel.setBtEntryId(model.getBtEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setServletContextNames(model.getServletContextNames());
		soapModel.setTaskExecutorClassName(model.getTaskExecutorClassName());
		soapModel.setTaskContext(model.getTaskContext());
		soapModel.setCompleted(model.getCompleted());
		soapModel.setCompletionDate(model.getCompletionDate());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static BTEntrySoap[] toSoapModels(BTEntry[] models) {
		BTEntrySoap[] soapModels = new BTEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BTEntrySoap[][] toSoapModels(BTEntry[][] models) {
		BTEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new BTEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new BTEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BTEntrySoap[] toSoapModels(List<BTEntry> models) {
		List<BTEntrySoap> soapModels = new ArrayList<BTEntrySoap>(models.size());

		for (BTEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BTEntrySoap[soapModels.size()]);
	}

	public BTEntrySoap() {
	}

	public long getPrimaryKey() {
		return _btEntryId;
	}

	public void setPrimaryKey(long pk) {
		setBtEntryId(pk);
	}

	public long getBtEntryId() {
		return _btEntryId;
	}

	public void setBtEntryId(long btEntryId) {
		_btEntryId = btEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getServletContextNames() {
		return _servletContextNames;
	}

	public void setServletContextNames(String servletContextNames) {
		_servletContextNames = servletContextNames;
	}

	public String getTaskExecutorClassName() {
		return _taskExecutorClassName;
	}

	public void setTaskExecutorClassName(String taskExecutorClassName) {
		_taskExecutorClassName = taskExecutorClassName;
	}

	public String getTaskContext() {
		return _taskContext;
	}

	public void setTaskContext(String taskContext) {
		_taskContext = taskContext;
	}

	public boolean getCompleted() {
		return _completed;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	public Date getCompletionDate() {
		return _completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _btEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _servletContextNames;
	private String _taskExecutorClassName;
	private String _taskContext;
	private boolean _completed;
	private Date _completionDate;
	private int _status;
}