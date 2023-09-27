/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

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
public class WorkflowMetricsSLADefinitionWrapper
	extends BaseModelWrapper<WorkflowMetricsSLADefinition>
	implements ModelWrapper<WorkflowMetricsSLADefinition>,
			   WorkflowMetricsSLADefinition {

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
		attributes.put("active", isActive());
		attributes.put("calendarKey", getCalendarKey());
		attributes.put("description", getDescription());
		attributes.put("duration", getDuration());
		attributes.put("name", getName());
		attributes.put("pauseNodeKeys", getPauseNodeKeys());
		attributes.put("processId", getProcessId());
		attributes.put("processVersion", getProcessVersion());
		attributes.put("startNodeKeys", getStartNodeKeys());
		attributes.put("stopNodeKeys", getStopNodeKeys());
		attributes.put("version", getVersion());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String calendarKey = (String)attributes.get("calendarKey");

		if (calendarKey != null) {
			setCalendarKey(calendarKey);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String pauseNodeKeys = (String)attributes.get("pauseNodeKeys");

		if (pauseNodeKeys != null) {
			setPauseNodeKeys(pauseNodeKeys);
		}

		Long processId = (Long)attributes.get("processId");

		if (processId != null) {
			setProcessId(processId);
		}

		String processVersion = (String)attributes.get("processVersion");

		if (processVersion != null) {
			setProcessVersion(processVersion);
		}

		String startNodeKeys = (String)attributes.get("startNodeKeys");

		if (startNodeKeys != null) {
			setStartNodeKeys(startNodeKeys);
		}

		String stopNodeKeys = (String)attributes.get("stopNodeKeys");

		if (stopNodeKeys != null) {
			setStopNodeKeys(stopNodeKeys);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public WorkflowMetricsSLADefinition cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this workflow metrics sla definition.
	 *
	 * @return the active of this workflow metrics sla definition
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the calendar key of this workflow metrics sla definition.
	 *
	 * @return the calendar key of this workflow metrics sla definition
	 */
	@Override
	public String getCalendarKey() {
		return model.getCalendarKey();
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
	 * Returns the pause node keys of this workflow metrics sla definition.
	 *
	 * @return the pause node keys of this workflow metrics sla definition
	 */
	@Override
	public String getPauseNodeKeys() {
		return model.getPauseNodeKeys();
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
	 * Returns the process version of this workflow metrics sla definition.
	 *
	 * @return the process version of this workflow metrics sla definition
	 */
	@Override
	public String getProcessVersion() {
		return model.getProcessVersion();
	}

	/**
	 * Returns the start node keys of this workflow metrics sla definition.
	 *
	 * @return the start node keys of this workflow metrics sla definition
	 */
	@Override
	public String getStartNodeKeys() {
		return model.getStartNodeKeys();
	}

	/**
	 * Returns the status of this workflow metrics sla definition.
	 *
	 * @return the status of this workflow metrics sla definition
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this workflow metrics sla definition.
	 *
	 * @return the status by user ID of this workflow metrics sla definition
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this workflow metrics sla definition.
	 *
	 * @return the status by user name of this workflow metrics sla definition
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this workflow metrics sla definition.
	 *
	 * @return the status by user uuid of this workflow metrics sla definition
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this workflow metrics sla definition.
	 *
	 * @return the status date of this workflow metrics sla definition
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the stop node keys of this workflow metrics sla definition.
	 *
	 * @return the stop node keys of this workflow metrics sla definition
	 */
	@Override
	public String getStopNodeKeys() {
		return model.getStopNodeKeys();
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
	 * Returns the version of this workflow metrics sla definition.
	 *
	 * @return the version of this workflow metrics sla definition
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
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

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is active.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is approved.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is denied.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is a draft.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is expired.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is inactive.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is incomplete.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is pending.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this workflow metrics sla definition is scheduled.
	 *
	 * @return <code>true</code> if this workflow metrics sla definition is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this workflow metrics sla definition is active.
	 *
	 * @param active the active of this workflow metrics sla definition
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the calendar key of this workflow metrics sla definition.
	 *
	 * @param calendarKey the calendar key of this workflow metrics sla definition
	 */
	@Override
	public void setCalendarKey(String calendarKey) {
		model.setCalendarKey(calendarKey);
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
	 * Sets the pause node keys of this workflow metrics sla definition.
	 *
	 * @param pauseNodeKeys the pause node keys of this workflow metrics sla definition
	 */
	@Override
	public void setPauseNodeKeys(String pauseNodeKeys) {
		model.setPauseNodeKeys(pauseNodeKeys);
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
	 * Sets the process version of this workflow metrics sla definition.
	 *
	 * @param processVersion the process version of this workflow metrics sla definition
	 */
	@Override
	public void setProcessVersion(String processVersion) {
		model.setProcessVersion(processVersion);
	}

	/**
	 * Sets the start node keys of this workflow metrics sla definition.
	 *
	 * @param startNodeKeys the start node keys of this workflow metrics sla definition
	 */
	@Override
	public void setStartNodeKeys(String startNodeKeys) {
		model.setStartNodeKeys(startNodeKeys);
	}

	/**
	 * Sets the status of this workflow metrics sla definition.
	 *
	 * @param status the status of this workflow metrics sla definition
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this workflow metrics sla definition.
	 *
	 * @param statusByUserId the status by user ID of this workflow metrics sla definition
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this workflow metrics sla definition.
	 *
	 * @param statusByUserName the status by user name of this workflow metrics sla definition
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this workflow metrics sla definition.
	 *
	 * @param statusByUserUuid the status by user uuid of this workflow metrics sla definition
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this workflow metrics sla definition.
	 *
	 * @param statusDate the status date of this workflow metrics sla definition
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the stop node keys of this workflow metrics sla definition.
	 *
	 * @param stopNodeKeys the stop node keys of this workflow metrics sla definition
	 */
	@Override
	public void setStopNodeKeys(String stopNodeKeys) {
		model.setStopNodeKeys(stopNodeKeys);
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
	 * Sets the version of this workflow metrics sla definition.
	 *
	 * @param version the version of this workflow metrics sla definition
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
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
	public String toXmlString() {
		return model.toXmlString();
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