/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link KaleoLog}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLog
 * @generated
 */
public class KaleoLogWrapper
	extends BaseModelWrapper<KaleoLog>
	implements KaleoLog, ModelWrapper<KaleoLog> {

	public KaleoLogWrapper(KaleoLog kaleoLog) {
		super(kaleoLog);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoLogId", getKaleoLogId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoClassName", getKaleoClassName());
		attributes.put("kaleoClassPK", getKaleoClassPK());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put("kaleoInstanceTokenId", getKaleoInstanceTokenId());
		attributes.put(
			"kaleoTaskInstanceTokenId", getKaleoTaskInstanceTokenId());
		attributes.put("kaleoNodeName", getKaleoNodeName());
		attributes.put("terminalKaleoNode", isTerminalKaleoNode());
		attributes.put("kaleoActionId", getKaleoActionId());
		attributes.put("kaleoActionName", getKaleoActionName());
		attributes.put("kaleoActionDescription", getKaleoActionDescription());
		attributes.put("previousKaleoNodeId", getPreviousKaleoNodeId());
		attributes.put("previousKaleoNodeName", getPreviousKaleoNodeName());
		attributes.put(
			"previousAssigneeClassName", getPreviousAssigneeClassName());
		attributes.put("previousAssigneeClassPK", getPreviousAssigneeClassPK());
		attributes.put(
			"currentAssigneeClassName", getCurrentAssigneeClassName());
		attributes.put("currentAssigneeClassPK", getCurrentAssigneeClassPK());
		attributes.put("type", getType());
		attributes.put("comment", getComment());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("duration", getDuration());
		attributes.put("workflowContext", getWorkflowContext());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long kaleoLogId = (Long)attributes.get("kaleoLogId");

		if (kaleoLogId != null) {
			setKaleoLogId(kaleoLogId);
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

		String kaleoClassName = (String)attributes.get("kaleoClassName");

		if (kaleoClassName != null) {
			setKaleoClassName(kaleoClassName);
		}

		Long kaleoClassPK = (Long)attributes.get("kaleoClassPK");

		if (kaleoClassPK != null) {
			setKaleoClassPK(kaleoClassPK);
		}

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
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

		Long kaleoInstanceTokenId = (Long)attributes.get(
			"kaleoInstanceTokenId");

		if (kaleoInstanceTokenId != null) {
			setKaleoInstanceTokenId(kaleoInstanceTokenId);
		}

		Long kaleoTaskInstanceTokenId = (Long)attributes.get(
			"kaleoTaskInstanceTokenId");

		if (kaleoTaskInstanceTokenId != null) {
			setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
		}

		String kaleoNodeName = (String)attributes.get("kaleoNodeName");

		if (kaleoNodeName != null) {
			setKaleoNodeName(kaleoNodeName);
		}

		Boolean terminalKaleoNode = (Boolean)attributes.get(
			"terminalKaleoNode");

		if (terminalKaleoNode != null) {
			setTerminalKaleoNode(terminalKaleoNode);
		}

		Long kaleoActionId = (Long)attributes.get("kaleoActionId");

		if (kaleoActionId != null) {
			setKaleoActionId(kaleoActionId);
		}

		String kaleoActionName = (String)attributes.get("kaleoActionName");

		if (kaleoActionName != null) {
			setKaleoActionName(kaleoActionName);
		}

		String kaleoActionDescription = (String)attributes.get(
			"kaleoActionDescription");

		if (kaleoActionDescription != null) {
			setKaleoActionDescription(kaleoActionDescription);
		}

		Long previousKaleoNodeId = (Long)attributes.get("previousKaleoNodeId");

		if (previousKaleoNodeId != null) {
			setPreviousKaleoNodeId(previousKaleoNodeId);
		}

		String previousKaleoNodeName = (String)attributes.get(
			"previousKaleoNodeName");

		if (previousKaleoNodeName != null) {
			setPreviousKaleoNodeName(previousKaleoNodeName);
		}

		String previousAssigneeClassName = (String)attributes.get(
			"previousAssigneeClassName");

		if (previousAssigneeClassName != null) {
			setPreviousAssigneeClassName(previousAssigneeClassName);
		}

		Long previousAssigneeClassPK = (Long)attributes.get(
			"previousAssigneeClassPK");

		if (previousAssigneeClassPK != null) {
			setPreviousAssigneeClassPK(previousAssigneeClassPK);
		}

		String currentAssigneeClassName = (String)attributes.get(
			"currentAssigneeClassName");

		if (currentAssigneeClassName != null) {
			setCurrentAssigneeClassName(currentAssigneeClassName);
		}

		Long currentAssigneeClassPK = (Long)attributes.get(
			"currentAssigneeClassPK");

		if (currentAssigneeClassPK != null) {
			setCurrentAssigneeClassPK(currentAssigneeClassPK);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String comment = (String)attributes.get("comment");

		if (comment != null) {
			setComment(comment);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		String workflowContext = (String)attributes.get("workflowContext");

		if (workflowContext != null) {
			setWorkflowContext(workflowContext);
		}
	}

	@Override
	public KaleoLog cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this kaleo log.
	 *
	 * @return the ct collection ID of this kaleo log
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the kaleo definition ID of this kaleo log.
	 *
	 * @return the kaleo definition ID of this kaleo log
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
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
	 * Returns the mvcc version of this kaleo log.
	 *
	 * @return the mvcc version of this kaleo log
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this kaleo log.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo log
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the kaleo definition ID of this kaleo log.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo log
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
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
	 * Sets the mvcc version of this kaleo log.
	 *
	 * @param mvccVersion the mvcc version of this kaleo log
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<KaleoLog, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoLog, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoLogWrapper wrap(KaleoLog kaleoLog) {
		return new KaleoLogWrapper(kaleoLog);
	}

}