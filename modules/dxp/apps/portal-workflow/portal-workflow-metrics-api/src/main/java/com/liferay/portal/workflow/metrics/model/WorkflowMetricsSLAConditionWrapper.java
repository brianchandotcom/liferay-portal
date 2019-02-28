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
 * This class is a wrapper for {@link WorkflowMetricsSLACondition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACondition
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionWrapper
	extends BaseModelWrapper<WorkflowMetricsSLACondition>
	implements WorkflowMetricsSLACondition,
			   ModelWrapper<WorkflowMetricsSLACondition> {

	public WorkflowMetricsSLAConditionWrapper(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		super(workflowMetricsSLACondition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"workflowMetricsSLAConditionId",
			getWorkflowMetricsSLAConditionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"workflowMetricsSLADefinitionId",
			getWorkflowMetricsSLADefinitionId());

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

		Long workflowMetricsSLAConditionId = (Long)attributes.get(
			"workflowMetricsSLAConditionId");

		if (workflowMetricsSLAConditionId != null) {
			setWorkflowMetricsSLAConditionId(workflowMetricsSLAConditionId);
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

		Long workflowMetricsSLADefinitionId = (Long)attributes.get(
			"workflowMetricsSLADefinitionId");

		if (workflowMetricsSLADefinitionId != null) {
			setWorkflowMetricsSLADefinitionId(workflowMetricsSLADefinitionId);
		}
	}

	/**
	 * Returns the company ID of this workflow metrics sla condition.
	 *
	 * @return the company ID of this workflow metrics sla condition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this workflow metrics sla condition.
	 *
	 * @return the create date of this workflow metrics sla condition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this workflow metrics sla condition.
	 *
	 * @return the group ID of this workflow metrics sla condition
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this workflow metrics sla condition.
	 *
	 * @return the modified date of this workflow metrics sla condition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this workflow metrics sla condition.
	 *
	 * @return the mvcc version of this workflow metrics sla condition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this workflow metrics sla condition.
	 *
	 * @return the primary key of this workflow metrics sla condition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this workflow metrics sla condition.
	 *
	 * @return the user ID of this workflow metrics sla condition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this workflow metrics sla condition.
	 *
	 * @return the user name of this workflow metrics sla condition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this workflow metrics sla condition.
	 *
	 * @return the user uuid of this workflow metrics sla condition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this workflow metrics sla condition.
	 *
	 * @return the uuid of this workflow metrics sla condition
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the workflow metrics sla condition ID of this workflow metrics sla condition.
	 *
	 * @return the workflow metrics sla condition ID of this workflow metrics sla condition
	 */
	@Override
	public long getWorkflowMetricsSLAConditionId() {
		return model.getWorkflowMetricsSLAConditionId();
	}

	/**
	 * Returns the workflow metrics sla definition ID of this workflow metrics sla condition.
	 *
	 * @return the workflow metrics sla definition ID of this workflow metrics sla condition
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
	 * Sets the company ID of this workflow metrics sla condition.
	 *
	 * @param companyId the company ID of this workflow metrics sla condition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this workflow metrics sla condition.
	 *
	 * @param createDate the create date of this workflow metrics sla condition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this workflow metrics sla condition.
	 *
	 * @param groupId the group ID of this workflow metrics sla condition
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this workflow metrics sla condition.
	 *
	 * @param modifiedDate the modified date of this workflow metrics sla condition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this workflow metrics sla condition.
	 *
	 * @param mvccVersion the mvcc version of this workflow metrics sla condition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this workflow metrics sla condition.
	 *
	 * @param primaryKey the primary key of this workflow metrics sla condition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this workflow metrics sla condition.
	 *
	 * @param userId the user ID of this workflow metrics sla condition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this workflow metrics sla condition.
	 *
	 * @param userName the user name of this workflow metrics sla condition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this workflow metrics sla condition.
	 *
	 * @param userUuid the user uuid of this workflow metrics sla condition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this workflow metrics sla condition.
	 *
	 * @param uuid the uuid of this workflow metrics sla condition
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the workflow metrics sla condition ID of this workflow metrics sla condition.
	 *
	 * @param workflowMetricsSLAConditionId the workflow metrics sla condition ID of this workflow metrics sla condition
	 */
	@Override
	public void setWorkflowMetricsSLAConditionId(
		long workflowMetricsSLAConditionId) {

		model.setWorkflowMetricsSLAConditionId(workflowMetricsSLAConditionId);
	}

	/**
	 * Sets the workflow metrics sla definition ID of this workflow metrics sla condition.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID of this workflow metrics sla condition
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
	protected WorkflowMetricsSLAConditionWrapper wrap(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return new WorkflowMetricsSLAConditionWrapper(
			workflowMetricsSLACondition);
	}

}