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

package com.liferay.portal.workflow.kaleo.forms.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.workflow.kaleo.forms.service.http.KaleoProcessServiceSoap}.
 *
 * @author Marcellus Tavares
 * @see com.liferay.portal.workflow.kaleo.forms.service.http.KaleoProcessServiceSoap
 * @generated
 */
@ProviderType
public class KaleoProcessSoap implements Serializable {
	public static KaleoProcessSoap toSoapModel(KaleoProcess model) {
		KaleoProcessSoap soapModel = new KaleoProcessSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setKaleoProcessId(model.getKaleoProcessId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDDLRecordSetId(model.getDDLRecordSetId());
		soapModel.setDDMTemplateId(model.getDDMTemplateId());
		soapModel.setWorkflowDefinitionName(model.getWorkflowDefinitionName());
		soapModel.setWorkflowDefinitionVersion(model.getWorkflowDefinitionVersion());

		return soapModel;
	}

	public static KaleoProcessSoap[] toSoapModels(KaleoProcess[] models) {
		KaleoProcessSoap[] soapModels = new KaleoProcessSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static KaleoProcessSoap[][] toSoapModels(KaleoProcess[][] models) {
		KaleoProcessSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new KaleoProcessSoap[models.length][models[0].length];
		}
		else {
			soapModels = new KaleoProcessSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static KaleoProcessSoap[] toSoapModels(List<KaleoProcess> models) {
		List<KaleoProcessSoap> soapModels = new ArrayList<KaleoProcessSoap>(models.size());

		for (KaleoProcess model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new KaleoProcessSoap[soapModels.size()]);
	}

	public KaleoProcessSoap() {
	}

	public long getPrimaryKey() {
		return _kaleoProcessId;
	}

	public void setPrimaryKey(long pk) {
		setKaleoProcessId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getKaleoProcessId() {
		return _kaleoProcessId;
	}

	public void setKaleoProcessId(long kaleoProcessId) {
		_kaleoProcessId = kaleoProcessId;
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

	public long getDDLRecordSetId() {
		return _DDLRecordSetId;
	}

	public void setDDLRecordSetId(long DDLRecordSetId) {
		_DDLRecordSetId = DDLRecordSetId;
	}

	public long getDDMTemplateId() {
		return _DDMTemplateId;
	}

	public void setDDMTemplateId(long DDMTemplateId) {
		_DDMTemplateId = DDMTemplateId;
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

	private String _uuid;
	private long _kaleoProcessId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _DDLRecordSetId;
	private long _DDMTemplateId;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
}