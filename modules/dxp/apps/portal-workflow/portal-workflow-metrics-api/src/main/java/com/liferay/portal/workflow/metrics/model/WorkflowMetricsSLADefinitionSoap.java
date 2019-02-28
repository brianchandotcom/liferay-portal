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
public class WorkflowMetricsSLADefinitionSoap implements Serializable {

	public static WorkflowMetricsSLADefinitionSoap toSoapModel(
		WorkflowMetricsSLADefinition model) {

		WorkflowMetricsSLADefinitionSoap soapModel =
			new WorkflowMetricsSLADefinitionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setWorkflowMetricsSLADefinitionId(
			model.getWorkflowMetricsSLADefinitionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDuration(model.getDuration());
		soapModel.setProcessId(model.getProcessId());
		soapModel.setPauseNodeNames(model.getPauseNodeNames());
		soapModel.setStartNodeNames(model.getStartNodeNames());
		soapModel.setStopNodeNames(model.getStopNodeNames());

		return soapModel;
	}

	public static WorkflowMetricsSLADefinitionSoap[] toSoapModels(
		WorkflowMetricsSLADefinition[] models) {

		WorkflowMetricsSLADefinitionSoap[] soapModels =
			new WorkflowMetricsSLADefinitionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WorkflowMetricsSLADefinitionSoap[][] toSoapModels(
		WorkflowMetricsSLADefinition[][] models) {

		WorkflowMetricsSLADefinitionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WorkflowMetricsSLADefinitionSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new WorkflowMetricsSLADefinitionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WorkflowMetricsSLADefinitionSoap[] toSoapModels(
		List<WorkflowMetricsSLADefinition> models) {

		List<WorkflowMetricsSLADefinitionSoap> soapModels =
			new ArrayList<WorkflowMetricsSLADefinitionSoap>(models.size());

		for (WorkflowMetricsSLADefinition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new WorkflowMetricsSLADefinitionSoap[soapModels.size()]);
	}

	public WorkflowMetricsSLADefinitionSoap() {
	}

	public long getPrimaryKey() {
		return _workflowMetricsSLADefinitionId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowMetricsSLADefinitionId(pk);
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

	public long getWorkflowMetricsSLADefinitionId() {
		return _workflowMetricsSLADefinitionId;
	}

	public void setWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		_workflowMetricsSLADefinitionId = workflowMetricsSLADefinitionId;
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

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getDuration() {
		return _duration;
	}

	public void setDuration(long duration) {
		_duration = duration;
	}

	public long getProcessId() {
		return _processId;
	}

	public void setProcessId(long processId) {
		_processId = processId;
	}

	public String getPauseNodeNames() {
		return _pauseNodeNames;
	}

	public void setPauseNodeNames(String pauseNodeNames) {
		_pauseNodeNames = pauseNodeNames;
	}

	public String getStartNodeNames() {
		return _startNodeNames;
	}

	public void setStartNodeNames(String startNodeNames) {
		_startNodeNames = startNodeNames;
	}

	public String getStopNodeNames() {
		return _stopNodeNames;
	}

	public void setStopNodeNames(String stopNodeNames) {
		_stopNodeNames = stopNodeNames;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _workflowMetricsSLADefinitionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private long _duration;
	private long _processId;
	private String _pauseNodeNames;
	private String _startNodeNames;
	private String _stopNodeNames;

}