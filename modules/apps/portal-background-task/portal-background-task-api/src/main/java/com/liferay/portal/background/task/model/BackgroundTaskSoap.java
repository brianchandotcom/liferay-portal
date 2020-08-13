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

package com.liferay.portal.background.task.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.background.task.service.http.BackgroundTaskServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BackgroundTaskSoap implements Serializable {

	public static BackgroundTaskSoap toSoapModel(BackgroundTask model) {
		BackgroundTaskSoap soapModel = new BackgroundTaskSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setBackgroundTaskId(model.getBackgroundTaskId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setServletContextNames(model.getServletContextNames());
		soapModel.setTaskExecutorClassName(model.getTaskExecutorClassName());
		soapModel.setTaskContextMap(model.getTaskContextMap());
		soapModel.setCompleted(model.isCompleted());
		soapModel.setCompletionDate(model.getCompletionDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusMessage(model.getStatusMessage());

		return soapModel;
	}

	public static BackgroundTaskSoap[] toSoapModels(BackgroundTask[] models) {
		BackgroundTaskSoap[] soapModels = new BackgroundTaskSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BackgroundTaskSoap[][] toSoapModels(
		BackgroundTask[][] models) {

		BackgroundTaskSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BackgroundTaskSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BackgroundTaskSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BackgroundTaskSoap[] toSoapModels(
		List<BackgroundTask> models) {

		List<BackgroundTaskSoap> soapModels = new ArrayList<BackgroundTaskSoap>(
			models.size());

		for (BackgroundTask model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BackgroundTaskSoap[soapModels.size()]);
	}

	public BackgroundTaskSoap() {
	}

	public long getPrimaryKey() {
		return _backgroundTaskId;
	}

	public void setPrimaryKey(long pk) {
		setBackgroundTaskId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTaskId = backgroundTaskId;
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

	public Map<String, Serializable> getTaskContextMap() {
		return _taskContextMap;
	}

	public void setTaskContextMap(Map<String, Serializable> taskContextMap) {
		_taskContextMap = taskContextMap;
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

	public String getStatusMessage() {
		return _statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;
	}

	private long _mvccVersion;
	private long _backgroundTaskId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _servletContextNames;
	private String _taskExecutorClassName;
	private Map<String, Serializable> _taskContextMap;
	private boolean _completed;
	private Date _completionDate;
	private int _status;
	private String _statusMessage;

}