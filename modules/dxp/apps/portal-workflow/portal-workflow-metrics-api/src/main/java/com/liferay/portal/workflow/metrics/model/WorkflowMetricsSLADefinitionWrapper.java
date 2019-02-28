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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WorkflowMetricsSLADefinition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinition
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLADefinitionWrapper
	extends BaseModelWrapper<WorkflowMetricsSLADefinition>
	implements WorkflowMetricsSLADefinition,
			   ModelWrapper<WorkflowMetricsSLADefinition> {

	public WorkflowMetricsSLADefinitionWrapper(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		super(workflowMetricsSLADefinition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"workflowMetricsSLADefinitionId",
			getWorkflowMetricsSLADefinitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("duration", getDuration());
		attributes.put("processId", getProcessId());
		attributes.put("pauseNodeNames", getPauseNodeNames());
		attributes.put("startNodeNames", getStartNodeNames());
		attributes.put("stopNodeNames", getStopNodeNames());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long workflowMetricsSLADefinitionId = (Long)attributes.get(
			"workflowMetricsSLADefinitionId");

		if (workflowMetricsSLADefinitionId != null) {
			setWorkflowMetricsSLADefinitionId(workflowMetricsSLADefinitionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Long processId = (Long)attributes.get("processId");

		if (processId != null) {
			setProcessId(processId);
		}

		String pauseNodeNames = (String)attributes.get("pauseNodeNames");

		if (pauseNodeNames != null) {
			setPauseNodeNames(pauseNodeNames);
		}

		String startNodeNames = (String)attributes.get("startNodeNames");

		if (startNodeNames != null) {
			setStartNodeNames(startNodeNames);
		}

		String stopNodeNames = (String)attributes.get("stopNodeNames");

		if (stopNodeNames != null) {
			setStopNodeNames(stopNodeNames);
		}
	}

	/**
	 * Returns the company ID of this workflow metrics sla definition.
	 *
	 * @return the company ID of this workflow metrics sla definition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this workflow metrics sla definition.
	 *
	 * @return the create date of this workflow metrics sla definition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this workflow metrics sla definition.
	 *
	 * @return the description of this workflow metrics sla definition
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the duration of this workflow metrics sla definition.
	 *
	 * @return the duration of this workflow metrics sla definition
	 */
	@Override
	public long getDuration() {
		return model.getDuration();
	}

	/**
	 * Returns the group ID of this workflow metrics sla definition.
	 *
	 * @return the group ID of this workflow metrics sla definition
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this workflow metrics sla definition.
	 *
	 * @return the modified date of this workflow metrics sla definition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this workflow metrics sla definition.
	 *
	 * @return the mvcc version of this workflow metrics sla definition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this workflow metrics sla definition.
	 *
	 * @return the name of this workflow metrics sla definition
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the pause node names of this workflow metrics sla definition.
	 *
	 * @return the pause node names of this workflow metrics sla definition
	 */
	@Override
	public String getPauseNodeNames() {
		return model.getPauseNodeNames();
	}

	/**
	 * Returns the primary key of this workflow metrics sla definition.
	 *
	 * @return the primary key of this workflow metrics sla definition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the process ID of this workflow metrics sla definition.
	 *
	 * @return the process ID of this workflow metrics sla definition
	 */
	@Override
	public long getProcessId() {
		return model.getProcessId();
	}

	/**
	 * Returns the start node names of this workflow metrics sla definition.
	 *
	 * @return the start node names of this workflow metrics sla definition
	 */
	@Override
	public String getStartNodeNames() {
		return model.getStartNodeNames();
	}

	/**
	 * Returns the stop node names of this workflow metrics sla definition.
	 *
	 * @return the stop node names of this workflow metrics sla definition
	 */
	@Override
	public String getStopNodeNames() {
		return model.getStopNodeNames();
	}

	/**
	 * Returns the user ID of this workflow metrics sla definition.
	 *
	 * @return the user ID of this workflow metrics sla definition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this workflow metrics sla definition.
	 *
	 * @return the user name of this workflow metrics sla definition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this workflow metrics sla definition.
	 *
	 * @return the user uuid of this workflow metrics sla definition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this workflow metrics sla definition.
	 *
	 * @return the uuid of this workflow metrics sla definition
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the workflow metrics sla definition ID of this workflow metrics sla definition.
	 *
	 * @return the workflow metrics sla definition ID of this workflow metrics sla definition
	 */
	@Override
	public long getWorkflowMetricsSLADefinitionId() {
		return model.getWorkflowMetricsSLADefinitionId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this workflow metrics sla definition.
	 *
	 * @param companyId the company ID of this workflow metrics sla definition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this workflow metrics sla definition.
	 *
	 * @param createDate the create date of this workflow metrics sla definition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this workflow metrics sla definition.
	 *
	 * @param description the description of this workflow metrics sla definition
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the duration of this workflow metrics sla definition.
	 *
	 * @param duration the duration of this workflow metrics sla definition
	 */
	@Override
	public void setDuration(long duration) {
		model.setDuration(duration);
	}

	/**
	 * Sets the group ID of this workflow metrics sla definition.
	 *
	 * @param groupId the group ID of this workflow metrics sla definition
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this workflow metrics sla definition.
	 *
	 * @param modifiedDate the modified date of this workflow metrics sla definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this workflow metrics sla definition.
	 *
	 * @param mvccVersion the mvcc version of this workflow metrics sla definition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this workflow metrics sla definition.
	 *
	 * @param name the name of this workflow metrics sla definition
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the pause node names of this workflow metrics sla definition.
	 *
	 * @param pauseNodeNames the pause node names of this workflow metrics sla definition
	 */
	@Override
	public void setPauseNodeNames(String pauseNodeNames) {
		model.setPauseNodeNames(pauseNodeNames);
	}

	/**
	 * Sets the primary key of this workflow metrics sla definition.
	 *
	 * @param primaryKey the primary key of this workflow metrics sla definition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the process ID of this workflow metrics sla definition.
	 *
	 * @param processId the process ID of this workflow metrics sla definition
	 */
	@Override
	public void setProcessId(long processId) {
		model.setProcessId(processId);
	}

	/**
	 * Sets the start node names of this workflow metrics sla definition.
	 *
	 * @param startNodeNames the start node names of this workflow metrics sla definition
	 */
	@Override
	public void setStartNodeNames(String startNodeNames) {
		model.setStartNodeNames(startNodeNames);
	}

	/**
	 * Sets the stop node names of this workflow metrics sla definition.
	 *
	 * @param stopNodeNames the stop node names of this workflow metrics sla definition
	 */
	@Override
	public void setStopNodeNames(String stopNodeNames) {
		model.setStopNodeNames(stopNodeNames);
	}

	/**
	 * Sets the user ID of this workflow metrics sla definition.
	 *
	 * @param userId the user ID of this workflow metrics sla definition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this workflow metrics sla definition.
	 *
	 * @param userName the user name of this workflow metrics sla definition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this workflow metrics sla definition.
	 *
	 * @param userUuid the user uuid of this workflow metrics sla definition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this workflow metrics sla definition.
	 *
	 * @param uuid the uuid of this workflow metrics sla definition
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the workflow metrics sla definition ID of this workflow metrics sla definition.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID of this workflow metrics sla definition
	 */
	@Override
	public void setWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		model.setWorkflowMetricsSLADefinitionId(workflowMetricsSLADefinitionId);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WorkflowMetricsSLADefinitionWrapper wrap(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return new WorkflowMetricsSLADefinitionWrapper(
			workflowMetricsSLADefinition);
	}

}