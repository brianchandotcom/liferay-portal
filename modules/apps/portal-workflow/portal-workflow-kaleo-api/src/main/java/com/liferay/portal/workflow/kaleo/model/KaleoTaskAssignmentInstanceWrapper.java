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

/**
 * <p>
 * This class is a wrapper for {@link KaleoTaskAssignmentInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentInstance
 * @generated
 */
@ProviderType
public class KaleoTaskAssignmentInstanceWrapper extends BaseModelWrapper<KaleoTaskAssignmentInstance>
	implements KaleoTaskAssignmentInstance,
		ModelWrapper<KaleoTaskAssignmentInstance> {
	public KaleoTaskAssignmentInstanceWrapper(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {
		super(kaleoTaskAssignmentInstance);
	}

	/**
	* Returns the assignee class name of this kaleo task assignment instance.
	*
	* @return the assignee class name of this kaleo task assignment instance
	*/
	@Override
	public String getAssigneeClassName() {
		return model.getAssigneeClassName();
	}

	/**
	* Returns the assignee class pk of this kaleo task assignment instance.
	*
	* @return the assignee class pk of this kaleo task assignment instance
	*/
	@Override
	public long getAssigneeClassPK() {
		return model.getAssigneeClassPK();
	}

	/**
	* Returns the company ID of this kaleo task assignment instance.
	*
	* @return the company ID of this kaleo task assignment instance
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the completed of this kaleo task assignment instance.
	*
	* @return the completed of this kaleo task assignment instance
	*/
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	* Returns the completion date of this kaleo task assignment instance.
	*
	* @return the completion date of this kaleo task assignment instance
	*/
	@Override
	public Date getCompletionDate() {
		return model.getCompletionDate();
	}

	/**
	* Returns the create date of this kaleo task assignment instance.
	*
	* @return the create date of this kaleo task assignment instance
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this kaleo task assignment instance.
	*
	* @return the group ID of this kaleo task assignment instance
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo task assignment instance.
	*
	* @return the kaleo definition version ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo task assignment instance.
	*
	* @return the kaleo instance ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	/**
	* Returns the kaleo instance token ID of this kaleo task assignment instance.
	*
	* @return the kaleo instance token ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoInstanceTokenId() {
		return model.getKaleoInstanceTokenId();
	}

	/**
	* Returns the kaleo task assignment instance ID of this kaleo task assignment instance.
	*
	* @return the kaleo task assignment instance ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoTaskAssignmentInstanceId() {
		return model.getKaleoTaskAssignmentInstanceId();
	}

	/**
	* Returns the kaleo task ID of this kaleo task assignment instance.
	*
	* @return the kaleo task ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoTaskId() {
		return model.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo task assignment instance.
	*
	* @return the kaleo task instance token ID of this kaleo task assignment instance
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return model.getKaleoTaskInstanceTokenId();
	}

	/**
	* Returns the kaleo task name of this kaleo task assignment instance.
	*
	* @return the kaleo task name of this kaleo task assignment instance
	*/
	@Override
	public String getKaleoTaskName() {
		return model.getKaleoTaskName();
	}

	/**
	* Returns the modified date of this kaleo task assignment instance.
	*
	* @return the modified date of this kaleo task assignment instance
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the primary key of this kaleo task assignment instance.
	*
	* @return the primary key of this kaleo task assignment instance
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo task assignment instance.
	*
	* @return the user ID of this kaleo task assignment instance
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo task assignment instance.
	*
	* @return the user name of this kaleo task assignment instance
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task assignment instance.
	*
	* @return the user uuid of this kaleo task assignment instance
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns <code>true</code> if this kaleo task assignment instance is completed.
	*
	* @return <code>true</code> if this kaleo task assignment instance is completed; <code>false</code> otherwise
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
	* Sets the assignee class name of this kaleo task assignment instance.
	*
	* @param assigneeClassName the assignee class name of this kaleo task assignment instance
	*/
	@Override
	public void setAssigneeClassName(String assigneeClassName) {
		model.setAssigneeClassName(assigneeClassName);
	}

	/**
	* Sets the assignee class pk of this kaleo task assignment instance.
	*
	* @param assigneeClassPK the assignee class pk of this kaleo task assignment instance
	*/
	@Override
	public void setAssigneeClassPK(long assigneeClassPK) {
		model.setAssigneeClassPK(assigneeClassPK);
	}

	/**
	* Sets the company ID of this kaleo task assignment instance.
	*
	* @param companyId the company ID of this kaleo task assignment instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets whether this kaleo task assignment instance is completed.
	*
	* @param completed the completed of this kaleo task assignment instance
	*/
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	* Sets the completion date of this kaleo task assignment instance.
	*
	* @param completionDate the completion date of this kaleo task assignment instance
	*/
	@Override
	public void setCompletionDate(Date completionDate) {
		model.setCompletionDate(completionDate);
	}

	/**
	* Sets the create date of this kaleo task assignment instance.
	*
	* @param createDate the create date of this kaleo task assignment instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this kaleo task assignment instance.
	*
	* @param groupId the group ID of this kaleo task assignment instance
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo task assignment instance.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo task assignment instance.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo instance token ID of this kaleo task assignment instance.
	*
	* @param kaleoInstanceTokenId the kaleo instance token ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		model.setKaleoInstanceTokenId(kaleoInstanceTokenId);
	}

	/**
	* Sets the kaleo task assignment instance ID of this kaleo task assignment instance.
	*
	* @param kaleoTaskAssignmentInstanceId the kaleo task assignment instance ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoTaskAssignmentInstanceId(
		long kaleoTaskAssignmentInstanceId) {
		model.setKaleoTaskAssignmentInstanceId(kaleoTaskAssignmentInstanceId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task assignment instance.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		model.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo task assignment instance.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		model.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the kaleo task name of this kaleo task assignment instance.
	*
	* @param kaleoTaskName the kaleo task name of this kaleo task assignment instance
	*/
	@Override
	public void setKaleoTaskName(String kaleoTaskName) {
		model.setKaleoTaskName(kaleoTaskName);
	}

	/**
	* Sets the modified date of this kaleo task assignment instance.
	*
	* @param modifiedDate the modified date of this kaleo task assignment instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this kaleo task assignment instance.
	*
	* @param primaryKey the primary key of this kaleo task assignment instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo task assignment instance.
	*
	* @param userId the user ID of this kaleo task assignment instance
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task assignment instance.
	*
	* @param userName the user name of this kaleo task assignment instance
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task assignment instance.
	*
	* @param userUuid the user uuid of this kaleo task assignment instance
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected KaleoTaskAssignmentInstanceWrapper wrap(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {
		return new KaleoTaskAssignmentInstanceWrapper(kaleoTaskAssignmentInstance);
	}
}