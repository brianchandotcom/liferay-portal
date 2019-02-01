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

package com.liferay.portal.workflow.kaleo.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KaleoTaskInstanceToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceToken
 * @generated
 */
@ProviderType
public class KaleoTaskInstanceTokenWrapper extends BaseModelWrapper<KaleoTaskInstanceToken>
	implements KaleoTaskInstanceToken, ModelWrapper<KaleoTaskInstanceToken> {
	public KaleoTaskInstanceTokenWrapper(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		super(kaleoTaskInstanceToken);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("kaleoTaskInstanceTokenId", getKaleoTaskInstanceTokenId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put("kaleoInstanceTokenId", getKaleoInstanceTokenId());
		attributes.put("kaleoTaskId", getKaleoTaskId());
		attributes.put("kaleoTaskName", getKaleoTaskName());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());
		attributes.put("completionUserId", getCompletionUserId());
		attributes.put("completed", isCompleted());
		attributes.put("completionDate", getCompletionDate());
		attributes.put("dueDate", getDueDate());
		attributes.put("workflowContext", getWorkflowContext());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long kaleoTaskInstanceTokenId = (Long)attributes.get(
				"kaleoTaskInstanceTokenId");

		if (kaleoTaskInstanceTokenId != null) {
			setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
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

		Long kaleoDefinitionVersionId = (Long)attributes.get(
				"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		Long kaleoInstanceId = (Long)attributes.get("kaleoInstanceId");

		if (kaleoInstanceId != null) {
			setKaleoInstanceId(kaleoInstanceId);
		}

		Long kaleoInstanceTokenId = (Long)attributes.get("kaleoInstanceTokenId");

		if (kaleoInstanceTokenId != null) {
			setKaleoInstanceTokenId(kaleoInstanceTokenId);
		}

		Long kaleoTaskId = (Long)attributes.get("kaleoTaskId");

		if (kaleoTaskId != null) {
			setKaleoTaskId(kaleoTaskId);
		}

		String kaleoTaskName = (String)attributes.get("kaleoTaskName");

		if (kaleoTaskName != null) {
			setKaleoTaskName(kaleoTaskName);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long completionUserId = (Long)attributes.get("completionUserId");

		if (completionUserId != null) {
			setCompletionUserId(completionUserId);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Date completionDate = (Date)attributes.get("completionDate");

		if (completionDate != null) {
			setCompletionDate(completionDate);
		}

		Date dueDate = (Date)attributes.get("dueDate");

		if (dueDate != null) {
			setDueDate(dueDate);
		}

		String workflowContext = (String)attributes.get("workflowContext");

		if (workflowContext != null) {
			setWorkflowContext(workflowContext);
		}
	}

	/**
	* Returns the class name of this kaleo task instance token.
	*
	* @return the class name of this kaleo task instance token
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class pk of this kaleo task instance token.
	*
	* @return the class pk of this kaleo task instance token
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this kaleo task instance token.
	*
	* @return the company ID of this kaleo task instance token
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the completed of this kaleo task instance token.
	*
	* @return the completed of this kaleo task instance token
	*/
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	* Returns the completion date of this kaleo task instance token.
	*
	* @return the completion date of this kaleo task instance token
	*/
	@Override
	public Date getCompletionDate() {
		return model.getCompletionDate();
	}

	/**
	* Returns the completion user ID of this kaleo task instance token.
	*
	* @return the completion user ID of this kaleo task instance token
	*/
	@Override
	public long getCompletionUserId() {
		return model.getCompletionUserId();
	}

	/**
	* Returns the completion user uuid of this kaleo task instance token.
	*
	* @return the completion user uuid of this kaleo task instance token
	*/
	@Override
	public String getCompletionUserUuid() {
		return model.getCompletionUserUuid();
	}

	/**
	* Returns the create date of this kaleo task instance token.
	*
	* @return the create date of this kaleo task instance token
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the due date of this kaleo task instance token.
	*
	* @return the due date of this kaleo task instance token
	*/
	@Override
	public Date getDueDate() {
		return model.getDueDate();
	}

	@Override
	public KaleoTaskAssignmentInstance getFirstKaleoTaskAssignmentInstance() {
		return model.getFirstKaleoTaskAssignmentInstance();
	}

	/**
	* Returns the group ID of this kaleo task instance token.
	*
	* @return the group ID of this kaleo task instance token
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo task instance token.
	*
	* @return the kaleo definition version ID of this kaleo task instance token
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo task instance token.
	*
	* @return the kaleo instance ID of this kaleo task instance token
	*/
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	@Override
	public KaleoInstanceToken getKaleoInstanceToken()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getKaleoInstanceToken();
	}

	/**
	* Returns the kaleo instance token ID of this kaleo task instance token.
	*
	* @return the kaleo instance token ID of this kaleo task instance token
	*/
	@Override
	public long getKaleoInstanceTokenId() {
		return model.getKaleoInstanceTokenId();
	}

	@Override
	public KaleoTask getKaleoTask()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getKaleoTask();
	}

	@Override
	public java.util.List<KaleoTaskAssignmentInstance> getKaleoTaskAssignmentInstances() {
		return model.getKaleoTaskAssignmentInstances();
	}

	/**
	* Returns the kaleo task ID of this kaleo task instance token.
	*
	* @return the kaleo task ID of this kaleo task instance token
	*/
	@Override
	public long getKaleoTaskId() {
		return model.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo task instance token.
	*
	* @return the kaleo task instance token ID of this kaleo task instance token
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return model.getKaleoTaskInstanceTokenId();
	}

	/**
	* Returns the kaleo task name of this kaleo task instance token.
	*
	* @return the kaleo task name of this kaleo task instance token
	*/
	@Override
	public String getKaleoTaskName() {
		return model.getKaleoTaskName();
	}

	/**
	* Returns the modified date of this kaleo task instance token.
	*
	* @return the modified date of this kaleo task instance token
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this kaleo task instance token.
	*
	* @return the mvcc version of this kaleo task instance token
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this kaleo task instance token.
	*
	* @return the primary key of this kaleo task instance token
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo task instance token.
	*
	* @return the user ID of this kaleo task instance token
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo task instance token.
	*
	* @return the user name of this kaleo task instance token
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task instance token.
	*
	* @return the user uuid of this kaleo task instance token
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the workflow context of this kaleo task instance token.
	*
	* @return the workflow context of this kaleo task instance token
	*/
	@Override
	public String getWorkflowContext() {
		return model.getWorkflowContext();
	}

	/**
	* Returns <code>true</code> if this kaleo task instance token is completed.
	*
	* @return <code>true</code> if this kaleo task instance token is completed; <code>false</code> otherwise
	*/
	@Override
	public boolean isCompleted() {
		return model.isCompleted();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the class name of this kaleo task instance token.
	*
	* @param className the class name of this kaleo task instance token
	*/
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class pk of this kaleo task instance token.
	*
	* @param classPK the class pk of this kaleo task instance token
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this kaleo task instance token.
	*
	* @param companyId the company ID of this kaleo task instance token
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets whether this kaleo task instance token is completed.
	*
	* @param completed the completed of this kaleo task instance token
	*/
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	* Sets the completion date of this kaleo task instance token.
	*
	* @param completionDate the completion date of this kaleo task instance token
	*/
	@Override
	public void setCompletionDate(Date completionDate) {
		model.setCompletionDate(completionDate);
	}

	/**
	* Sets the completion user ID of this kaleo task instance token.
	*
	* @param completionUserId the completion user ID of this kaleo task instance token
	*/
	@Override
	public void setCompletionUserId(long completionUserId) {
		model.setCompletionUserId(completionUserId);
	}

	/**
	* Sets the completion user uuid of this kaleo task instance token.
	*
	* @param completionUserUuid the completion user uuid of this kaleo task instance token
	*/
	@Override
	public void setCompletionUserUuid(String completionUserUuid) {
		model.setCompletionUserUuid(completionUserUuid);
	}

	/**
	* Sets the create date of this kaleo task instance token.
	*
	* @param createDate the create date of this kaleo task instance token
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the due date of this kaleo task instance token.
	*
	* @param dueDate the due date of this kaleo task instance token
	*/
	@Override
	public void setDueDate(Date dueDate) {
		model.setDueDate(dueDate);
	}

	/**
	* Sets the group ID of this kaleo task instance token.
	*
	* @param groupId the group ID of this kaleo task instance token
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo task instance token.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo task instance token
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo task instance token.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo task instance token
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo instance token ID of this kaleo task instance token.
	*
	* @param kaleoInstanceTokenId the kaleo instance token ID of this kaleo task instance token
	*/
	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		model.setKaleoInstanceTokenId(kaleoInstanceTokenId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task instance token.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task instance token
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		model.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo task instance token.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo task instance token
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		model.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the kaleo task name of this kaleo task instance token.
	*
	* @param kaleoTaskName the kaleo task name of this kaleo task instance token
	*/
	@Override
	public void setKaleoTaskName(String kaleoTaskName) {
		model.setKaleoTaskName(kaleoTaskName);
	}

	/**
	* Sets the modified date of this kaleo task instance token.
	*
	* @param modifiedDate the modified date of this kaleo task instance token
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this kaleo task instance token.
	*
	* @param mvccVersion the mvcc version of this kaleo task instance token
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this kaleo task instance token.
	*
	* @param primaryKey the primary key of this kaleo task instance token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo task instance token.
	*
	* @param userId the user ID of this kaleo task instance token
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task instance token.
	*
	* @param userName the user name of this kaleo task instance token
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task instance token.
	*
	* @param userUuid the user uuid of this kaleo task instance token
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the workflow context of this kaleo task instance token.
	*
	* @param workflowContext the workflow context of this kaleo task instance token
	*/
	@Override
	public void setWorkflowContext(String workflowContext) {
		model.setWorkflowContext(workflowContext);
	}

	@Override
	protected KaleoTaskInstanceTokenWrapper wrap(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		return new KaleoTaskInstanceTokenWrapper(kaleoTaskInstanceToken);
	}
}