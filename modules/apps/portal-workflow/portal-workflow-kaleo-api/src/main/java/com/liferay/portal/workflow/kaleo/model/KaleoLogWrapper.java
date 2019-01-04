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
 * This class is a wrapper for {@link KaleoLog}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLog
 * @generated
 */
@ProviderType
public class KaleoLogWrapper extends BaseModelWrapper<KaleoLog>
	implements KaleoLog, ModelWrapper<KaleoLog> {
	public KaleoLogWrapper(KaleoLog kaleoLog) {
		super(kaleoLog);
	}

	/**
	* Returns the comment of this kaleo log.
	*
	* @return the comment of this kaleo log
	*/
	@Override
	public String getComment() {
		return model.getComment();
	}

	/**
	* Returns the company ID of this kaleo log.
	*
	* @return the company ID of this kaleo log
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo log.
	*
	* @return the create date of this kaleo log
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the current assignee class name of this kaleo log.
	*
	* @return the current assignee class name of this kaleo log
	*/
	@Override
	public String getCurrentAssigneeClassName() {
		return model.getCurrentAssigneeClassName();
	}

	/**
	* Returns the current assignee class pk of this kaleo log.
	*
	* @return the current assignee class pk of this kaleo log
	*/
	@Override
	public long getCurrentAssigneeClassPK() {
		return model.getCurrentAssigneeClassPK();
	}

	/**
	* Returns the duration of this kaleo log.
	*
	* @return the duration of this kaleo log
	*/
	@Override
	public long getDuration() {
		return model.getDuration();
	}

	/**
	* Returns the end date of this kaleo log.
	*
	* @return the end date of this kaleo log
	*/
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	* Returns the group ID of this kaleo log.
	*
	* @return the group ID of this kaleo log
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo action description of this kaleo log.
	*
	* @return the kaleo action description of this kaleo log
	*/
	@Override
	public String getKaleoActionDescription() {
		return model.getKaleoActionDescription();
	}

	/**
	* Returns the kaleo action ID of this kaleo log.
	*
	* @return the kaleo action ID of this kaleo log
	*/
	@Override
	public long getKaleoActionId() {
		return model.getKaleoActionId();
	}

	/**
	* Returns the kaleo action name of this kaleo log.
	*
	* @return the kaleo action name of this kaleo log
	*/
	@Override
	public String getKaleoActionName() {
		return model.getKaleoActionName();
	}

	/**
	* Returns the kaleo class name of this kaleo log.
	*
	* @return the kaleo class name of this kaleo log
	*/
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	* Returns the kaleo class pk of this kaleo log.
	*
	* @return the kaleo class pk of this kaleo log
	*/
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo log.
	*
	* @return the kaleo definition version ID of this kaleo log
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo log.
	*
	* @return the kaleo instance ID of this kaleo log
	*/
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	/**
	* Returns the kaleo instance token ID of this kaleo log.
	*
	* @return the kaleo instance token ID of this kaleo log
	*/
	@Override
	public long getKaleoInstanceTokenId() {
		return model.getKaleoInstanceTokenId();
	}

	/**
	* Returns the kaleo log ID of this kaleo log.
	*
	* @return the kaleo log ID of this kaleo log
	*/
	@Override
	public long getKaleoLogId() {
		return model.getKaleoLogId();
	}

	/**
	* Returns the kaleo node name of this kaleo log.
	*
	* @return the kaleo node name of this kaleo log
	*/
	@Override
	public String getKaleoNodeName() {
		return model.getKaleoNodeName();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo log.
	*
	* @return the kaleo task instance token ID of this kaleo log
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return model.getKaleoTaskInstanceTokenId();
	}

	/**
	* Returns the modified date of this kaleo log.
	*
	* @return the modified date of this kaleo log
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the previous assignee class name of this kaleo log.
	*
	* @return the previous assignee class name of this kaleo log
	*/
	@Override
	public String getPreviousAssigneeClassName() {
		return model.getPreviousAssigneeClassName();
	}

	/**
	* Returns the previous assignee class pk of this kaleo log.
	*
	* @return the previous assignee class pk of this kaleo log
	*/
	@Override
	public long getPreviousAssigneeClassPK() {
		return model.getPreviousAssigneeClassPK();
	}

	/**
	* Returns the previous kaleo node ID of this kaleo log.
	*
	* @return the previous kaleo node ID of this kaleo log
	*/
	@Override
	public long getPreviousKaleoNodeId() {
		return model.getPreviousKaleoNodeId();
	}

	/**
	* Returns the previous kaleo node name of this kaleo log.
	*
	* @return the previous kaleo node name of this kaleo log
	*/
	@Override
	public String getPreviousKaleoNodeName() {
		return model.getPreviousKaleoNodeName();
	}

	/**
	* Returns the primary key of this kaleo log.
	*
	* @return the primary key of this kaleo log
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the start date of this kaleo log.
	*
	* @return the start date of this kaleo log
	*/
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	* Returns the terminal kaleo node of this kaleo log.
	*
	* @return the terminal kaleo node of this kaleo log
	*/
	@Override
	public boolean getTerminalKaleoNode() {
		return model.getTerminalKaleoNode();
	}

	/**
	* Returns the type of this kaleo log.
	*
	* @return the type of this kaleo log
	*/
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	* Returns the user ID of this kaleo log.
	*
	* @return the user ID of this kaleo log
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo log.
	*
	* @return the user name of this kaleo log
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo log.
	*
	* @return the user uuid of this kaleo log
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the workflow context of this kaleo log.
	*
	* @return the workflow context of this kaleo log
	*/
	@Override
	public String getWorkflowContext() {
		return model.getWorkflowContext();
	}

	/**
	* Returns <code>true</code> if this kaleo log is terminal kaleo node.
	*
	* @return <code>true</code> if this kaleo log is terminal kaleo node; <code>false</code> otherwise
	*/
	@Override
	public boolean isTerminalKaleoNode() {
		return model.isTerminalKaleoNode();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the comment of this kaleo log.
	*
	* @param comment the comment of this kaleo log
	*/
	@Override
	public void setComment(String comment) {
		model.setComment(comment);
	}

	/**
	* Sets the company ID of this kaleo log.
	*
	* @param companyId the company ID of this kaleo log
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo log.
	*
	* @param createDate the create date of this kaleo log
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the current assignee class name of this kaleo log.
	*
	* @param currentAssigneeClassName the current assignee class name of this kaleo log
	*/
	@Override
	public void setCurrentAssigneeClassName(String currentAssigneeClassName) {
		model.setCurrentAssigneeClassName(currentAssigneeClassName);
	}

	/**
	* Sets the current assignee class pk of this kaleo log.
	*
	* @param currentAssigneeClassPK the current assignee class pk of this kaleo log
	*/
	@Override
	public void setCurrentAssigneeClassPK(long currentAssigneeClassPK) {
		model.setCurrentAssigneeClassPK(currentAssigneeClassPK);
	}

	/**
	* Sets the duration of this kaleo log.
	*
	* @param duration the duration of this kaleo log
	*/
	@Override
	public void setDuration(long duration) {
		model.setDuration(duration);
	}

	/**
	* Sets the end date of this kaleo log.
	*
	* @param endDate the end date of this kaleo log
	*/
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	* Sets the group ID of this kaleo log.
	*
	* @param groupId the group ID of this kaleo log
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo action description of this kaleo log.
	*
	* @param kaleoActionDescription the kaleo action description of this kaleo log
	*/
	@Override
	public void setKaleoActionDescription(String kaleoActionDescription) {
		model.setKaleoActionDescription(kaleoActionDescription);
	}

	/**
	* Sets the kaleo action ID of this kaleo log.
	*
	* @param kaleoActionId the kaleo action ID of this kaleo log
	*/
	@Override
	public void setKaleoActionId(long kaleoActionId) {
		model.setKaleoActionId(kaleoActionId);
	}

	/**
	* Sets the kaleo action name of this kaleo log.
	*
	* @param kaleoActionName the kaleo action name of this kaleo log
	*/
	@Override
	public void setKaleoActionName(String kaleoActionName) {
		model.setKaleoActionName(kaleoActionName);
	}

	/**
	* Sets the kaleo class name of this kaleo log.
	*
	* @param kaleoClassName the kaleo class name of this kaleo log
	*/
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	* Sets the kaleo class pk of this kaleo log.
	*
	* @param kaleoClassPK the kaleo class pk of this kaleo log
	*/
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo log.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo log
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo log.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo log
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo instance token ID of this kaleo log.
	*
	* @param kaleoInstanceTokenId the kaleo instance token ID of this kaleo log
	*/
	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		model.setKaleoInstanceTokenId(kaleoInstanceTokenId);
	}

	/**
	* Sets the kaleo log ID of this kaleo log.
	*
	* @param kaleoLogId the kaleo log ID of this kaleo log
	*/
	@Override
	public void setKaleoLogId(long kaleoLogId) {
		model.setKaleoLogId(kaleoLogId);
	}

	/**
	* Sets the kaleo node name of this kaleo log.
	*
	* @param kaleoNodeName the kaleo node name of this kaleo log
	*/
	@Override
	public void setKaleoNodeName(String kaleoNodeName) {
		model.setKaleoNodeName(kaleoNodeName);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo log.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo log
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		model.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the modified date of this kaleo log.
	*
	* @param modifiedDate the modified date of this kaleo log
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the previous assignee class name of this kaleo log.
	*
	* @param previousAssigneeClassName the previous assignee class name of this kaleo log
	*/
	@Override
	public void setPreviousAssigneeClassName(String previousAssigneeClassName) {
		model.setPreviousAssigneeClassName(previousAssigneeClassName);
	}

	/**
	* Sets the previous assignee class pk of this kaleo log.
	*
	* @param previousAssigneeClassPK the previous assignee class pk of this kaleo log
	*/
	@Override
	public void setPreviousAssigneeClassPK(long previousAssigneeClassPK) {
		model.setPreviousAssigneeClassPK(previousAssigneeClassPK);
	}

	/**
	* Sets the previous kaleo node ID of this kaleo log.
	*
	* @param previousKaleoNodeId the previous kaleo node ID of this kaleo log
	*/
	@Override
	public void setPreviousKaleoNodeId(long previousKaleoNodeId) {
		model.setPreviousKaleoNodeId(previousKaleoNodeId);
	}

	/**
	* Sets the previous kaleo node name of this kaleo log.
	*
	* @param previousKaleoNodeName the previous kaleo node name of this kaleo log
	*/
	@Override
	public void setPreviousKaleoNodeName(String previousKaleoNodeName) {
		model.setPreviousKaleoNodeName(previousKaleoNodeName);
	}

	/**
	* Sets the primary key of this kaleo log.
	*
	* @param primaryKey the primary key of this kaleo log
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the start date of this kaleo log.
	*
	* @param startDate the start date of this kaleo log
	*/
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	* Sets whether this kaleo log is terminal kaleo node.
	*
	* @param terminalKaleoNode the terminal kaleo node of this kaleo log
	*/
	@Override
	public void setTerminalKaleoNode(boolean terminalKaleoNode) {
		model.setTerminalKaleoNode(terminalKaleoNode);
	}

	/**
	* Sets the type of this kaleo log.
	*
	* @param type the type of this kaleo log
	*/
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	* Sets the user ID of this kaleo log.
	*
	* @param userId the user ID of this kaleo log
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo log.
	*
	* @param userName the user name of this kaleo log
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo log.
	*
	* @param userUuid the user uuid of this kaleo log
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the workflow context of this kaleo log.
	*
	* @param workflowContext the workflow context of this kaleo log
	*/
	@Override
	public void setWorkflowContext(String workflowContext) {
		model.setWorkflowContext(workflowContext);
	}

	@Override
	protected KaleoLogWrapper wrap(KaleoLog kaleoLog) {
		return new KaleoLogWrapper(kaleoLog);
	}
}