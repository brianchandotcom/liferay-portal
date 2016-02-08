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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class WorkflowDefinitionLinkSoap implements Serializable {
	public static WorkflowDefinitionLinkSoap toSoapModel(
		WorkflowDefinitionLink model) {
		WorkflowDefinitionLinkSoap soapModel = new WorkflowDefinitionLinkSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setWorkflowDefinitionLinkId(model.getWorkflowDefinitionLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTypePK(model.getTypePK());
		soapModel.setWorkflowDefinitionName(model.getWorkflowDefinitionName());
		soapModel.setWorkflowDefinitionVersion(model.getWorkflowDefinitionVersion());

		return soapModel;
	}

	public static WorkflowDefinitionLinkSoap[] toSoapModels(
		WorkflowDefinitionLink[] models) {
		WorkflowDefinitionLinkSoap[] soapModels = new WorkflowDefinitionLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WorkflowDefinitionLinkSoap[][] toSoapModels(
		WorkflowDefinitionLink[][] models) {
		WorkflowDefinitionLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WorkflowDefinitionLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WorkflowDefinitionLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WorkflowDefinitionLinkSoap[] toSoapModels(
		List<WorkflowDefinitionLink> models) {
		List<WorkflowDefinitionLinkSoap> soapModels = new ArrayList<WorkflowDefinitionLinkSoap>(models.size());

		for (WorkflowDefinitionLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WorkflowDefinitionLinkSoap[soapModels.size()]);
	}

	public WorkflowDefinitionLinkSoap() {
	}

	public long getPrimaryKey() {
		return _workflowDefinitionLinkId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowDefinitionLinkId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLinkId;
	}

	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLinkId = workflowDefinitionLinkId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getTypePK() {
		return _typePK;
	}

	public void setTypePK(long typePK) {
		_typePK = typePK;
	}

	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	private long _mvccVersion;
	private long _workflowDefinitionLinkId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _typePK;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
}