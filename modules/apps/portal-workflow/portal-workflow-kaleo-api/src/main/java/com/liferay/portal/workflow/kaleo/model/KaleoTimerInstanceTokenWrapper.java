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
 * This class is a wrapper for {@link KaleoTimerInstanceToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimerInstanceToken
 * @generated
 */
@ProviderType
public class KaleoTimerInstanceTokenWrapper extends BaseModelWrapper<KaleoTimerInstanceToken>
	implements KaleoTimerInstanceToken, ModelWrapper<KaleoTimerInstanceToken> {
	public KaleoTimerInstanceTokenWrapper(
		KaleoTimerInstanceToken kaleoTimerInstanceToken) {
		super(kaleoTimerInstanceToken);
	}

	/**
	* Returns the blocking of this kaleo timer instance token.
	*
	* @return the blocking of this kaleo timer instance token
	*/
	@Override
	public boolean getBlocking() {
		return model.getBlocking();
	}

	/**
	* Returns the company ID of this kaleo timer instance token.
	*
	* @return the company ID of this kaleo timer instance token
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the completed of this kaleo timer instance token.
	*
	* @return the completed of this kaleo timer instance token
	*/
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	* Returns the completion date of this kaleo timer instance token.
	*
	* @return the completion date of this kaleo timer instance token
	*/
	@Override
	public Date getCompletionDate() {
		return model.getCompletionDate();
	}

	/**
	* Returns the completion user ID of this kaleo timer instance token.
	*
	* @return the completion user ID of this kaleo timer instance token
	*/
	@Override
	public long getCompletionUserId() {
		return model.getCompletionUserId();
	}

	/**
	* Returns the completion user uuid of this kaleo timer instance token.
	*
	* @return the completion user uuid of this kaleo timer instance token
	*/
	@Override
	public String getCompletionUserUuid() {
		return model.getCompletionUserUuid();
	}

	/**
	* Returns the create date of this kaleo timer instance token.
	*
	* @return the create date of this kaleo timer instance token
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this kaleo timer instance token.
	*
	* @return the group ID of this kaleo timer instance token
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo class name of this kaleo timer instance token.
	*
	* @return the kaleo class name of this kaleo timer instance token
	*/
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	* Returns the kaleo class pk of this kaleo timer instance token.
	*
	* @return the kaleo class pk of this kaleo timer instance token
	*/
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo timer instance token.
	*
	* @return the kaleo definition version ID of this kaleo timer instance token
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo timer instance token.
	*
	* @return the kaleo instance ID of this kaleo timer instance token
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
	* Returns the kaleo instance token ID of this kaleo timer instance token.
	*
	* @return the kaleo instance token ID of this kaleo timer instance token
	*/
	@Override
	public long getKaleoInstanceTokenId() {
		return model.getKaleoInstanceTokenId();
	}

	@Override
	public KaleoTaskInstanceToken getKaleoTaskInstanceToken() {
		return model.getKaleoTaskInstanceToken();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo timer instance token.
	*
	* @return the kaleo task instance token ID of this kaleo timer instance token
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return model.getKaleoTaskInstanceTokenId();
	}

	@Override
	public KaleoTimer getKaleoTimer()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getKaleoTimer();
	}

	/**
	* Returns the kaleo timer ID of this kaleo timer instance token.
	*
	* @return the kaleo timer ID of this kaleo timer instance token
	*/
	@Override
	public long getKaleoTimerId() {
		return model.getKaleoTimerId();
	}

	/**
	* Returns the kaleo timer instance token ID of this kaleo timer instance token.
	*
	* @return the kaleo timer instance token ID of this kaleo timer instance token
	*/
	@Override
	public long getKaleoTimerInstanceTokenId() {
		return model.getKaleoTimerInstanceTokenId();
	}

	/**
	* Returns the kaleo timer name of this kaleo timer instance token.
	*
	* @return the kaleo timer name of this kaleo timer instance token
	*/
	@Override
	public String getKaleoTimerName() {
		return model.getKaleoTimerName();
	}

	/**
	* Returns the modified date of this kaleo timer instance token.
	*
	* @return the modified date of this kaleo timer instance token
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the primary key of this kaleo timer instance token.
	*
	* @return the primary key of this kaleo timer instance token
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo timer instance token.
	*
	* @return the user ID of this kaleo timer instance token
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo timer instance token.
	*
	* @return the user name of this kaleo timer instance token
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo timer instance token.
	*
	* @return the user uuid of this kaleo timer instance token
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the workflow context of this kaleo timer instance token.
	*
	* @return the workflow context of this kaleo timer instance token
	*/
	@Override
	public String getWorkflowContext() {
		return model.getWorkflowContext();
	}

	/**
	* Returns <code>true</code> if this kaleo timer instance token is blocking.
	*
	* @return <code>true</code> if this kaleo timer instance token is blocking; <code>false</code> otherwise
	*/
	@Override
	public boolean isBlocking() {
		return model.isBlocking();
	}

	/**
	* Returns <code>true</code> if this kaleo timer instance token is completed.
	*
	* @return <code>true</code> if this kaleo timer instance token is completed; <code>false</code> otherwise
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
	* Sets whether this kaleo timer instance token is blocking.
	*
	* @param blocking the blocking of this kaleo timer instance token
	*/
	@Override
	public void setBlocking(boolean blocking) {
		model.setBlocking(blocking);
	}

	/**
	* Sets the company ID of this kaleo timer instance token.
	*
	* @param companyId the company ID of this kaleo timer instance token
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets whether this kaleo timer instance token is completed.
	*
	* @param completed the completed of this kaleo timer instance token
	*/
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	* Sets the completion date of this kaleo timer instance token.
	*
	* @param completionDate the completion date of this kaleo timer instance token
	*/
	@Override
	public void setCompletionDate(Date completionDate) {
		model.setCompletionDate(completionDate);
	}

	/**
	* Sets the completion user ID of this kaleo timer instance token.
	*
	* @param completionUserId the completion user ID of this kaleo timer instance token
	*/
	@Override
	public void setCompletionUserId(long completionUserId) {
		model.setCompletionUserId(completionUserId);
	}

	/**
	* Sets the completion user uuid of this kaleo timer instance token.
	*
	* @param completionUserUuid the completion user uuid of this kaleo timer instance token
	*/
	@Override
	public void setCompletionUserUuid(String completionUserUuid) {
		model.setCompletionUserUuid(completionUserUuid);
	}

	/**
	* Sets the create date of this kaleo timer instance token.
	*
	* @param createDate the create date of this kaleo timer instance token
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this kaleo timer instance token.
	*
	* @param groupId the group ID of this kaleo timer instance token
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo class name of this kaleo timer instance token.
	*
	* @param kaleoClassName the kaleo class name of this kaleo timer instance token
	*/
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	* Sets the kaleo class pk of this kaleo timer instance token.
	*
	* @param kaleoClassPK the kaleo class pk of this kaleo timer instance token
	*/
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo timer instance token.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo timer instance token.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo instance token ID of this kaleo timer instance token.
	*
	* @param kaleoInstanceTokenId the kaleo instance token ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		model.setKaleoInstanceTokenId(kaleoInstanceTokenId);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo timer instance token.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		model.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the kaleo timer ID of this kaleo timer instance token.
	*
	* @param kaleoTimerId the kaleo timer ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoTimerId(long kaleoTimerId) {
		model.setKaleoTimerId(kaleoTimerId);
	}

	/**
	* Sets the kaleo timer instance token ID of this kaleo timer instance token.
	*
	* @param kaleoTimerInstanceTokenId the kaleo timer instance token ID of this kaleo timer instance token
	*/
	@Override
	public void setKaleoTimerInstanceTokenId(long kaleoTimerInstanceTokenId) {
		model.setKaleoTimerInstanceTokenId(kaleoTimerInstanceTokenId);
	}

	/**
	* Sets the kaleo timer name of this kaleo timer instance token.
	*
	* @param kaleoTimerName the kaleo timer name of this kaleo timer instance token
	*/
	@Override
	public void setKaleoTimerName(String kaleoTimerName) {
		model.setKaleoTimerName(kaleoTimerName);
	}

	/**
	* Sets the modified date of this kaleo timer instance token.
	*
	* @param modifiedDate the modified date of this kaleo timer instance token
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this kaleo timer instance token.
	*
	* @param primaryKey the primary key of this kaleo timer instance token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo timer instance token.
	*
	* @param userId the user ID of this kaleo timer instance token
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo timer instance token.
	*
	* @param userName the user name of this kaleo timer instance token
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo timer instance token.
	*
	* @param userUuid the user uuid of this kaleo timer instance token
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the workflow context of this kaleo timer instance token.
	*
	* @param workflowContext the workflow context of this kaleo timer instance token
	*/
	@Override
	public void setWorkflowContext(String workflowContext) {
		model.setWorkflowContext(workflowContext);
	}

	@Override
	protected KaleoTimerInstanceTokenWrapper wrap(
		KaleoTimerInstanceToken kaleoTimerInstanceToken) {
		return new KaleoTimerInstanceTokenWrapper(kaleoTimerInstanceToken);
	}
}