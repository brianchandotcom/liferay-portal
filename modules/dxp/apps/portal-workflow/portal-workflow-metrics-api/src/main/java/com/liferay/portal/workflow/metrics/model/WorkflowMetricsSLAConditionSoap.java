/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.model;

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
public class WorkflowMetricsSLAConditionSoap implements Serializable {

	public static WorkflowMetricsSLAConditionSoap toSoapModel(
		WorkflowMetricsSLACondition model) {

		WorkflowMetricsSLAConditionSoap soapModel =
			new WorkflowMetricsSLAConditionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setWorkflowMetricsSLAConditionId(
			model.getWorkflowMetricsSLAConditionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setWorkflowMetricsSLADefinitionId(
			model.getWorkflowMetricsSLADefinitionId());

		return soapModel;
	}

	public static WorkflowMetricsSLAConditionSoap[] toSoapModels(
		WorkflowMetricsSLACondition[] models) {

		WorkflowMetricsSLAConditionSoap[] soapModels =
			new WorkflowMetricsSLAConditionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WorkflowMetricsSLAConditionSoap[][] toSoapModels(
		WorkflowMetricsSLACondition[][] models) {

		WorkflowMetricsSLAConditionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WorkflowMetricsSLAConditionSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new WorkflowMetricsSLAConditionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WorkflowMetricsSLAConditionSoap[] toSoapModels(
		List<WorkflowMetricsSLACondition> models) {

		List<WorkflowMetricsSLAConditionSoap> soapModels =
			new ArrayList<WorkflowMetricsSLAConditionSoap>(models.size());

		for (WorkflowMetricsSLACondition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new WorkflowMetricsSLAConditionSoap[soapModels.size()]);
	}

	public WorkflowMetricsSLAConditionSoap() {
	}

	public long getPrimaryKey() {
		return _workflowMetricsSLAConditionId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowMetricsSLAConditionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getWorkflowMetricsSLAConditionId() {
		return _workflowMetricsSLAConditionId;
	}

	public void setWorkflowMetricsSLAConditionId(
		long workflowMetricsSLAConditionId) {

		_workflowMetricsSLAConditionId = workflowMetricsSLAConditionId;
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

	public long getWorkflowMetricsSLADefinitionId() {
		return _workflowMetricsSLADefinitionId;
	}

	public void setWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		_workflowMetricsSLADefinitionId = workflowMetricsSLADefinitionId;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _workflowMetricsSLAConditionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _workflowMetricsSLADefinitionId;

}