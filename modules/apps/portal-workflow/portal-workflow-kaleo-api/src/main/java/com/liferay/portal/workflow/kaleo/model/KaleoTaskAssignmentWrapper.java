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
 * This class is a wrapper for {@link KaleoTaskAssignment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignment
 * @generated
 */
@ProviderType
public class KaleoTaskAssignmentWrapper extends BaseModelWrapper<KaleoTaskAssignment>
	implements KaleoTaskAssignment, ModelWrapper<KaleoTaskAssignment> {
	public KaleoTaskAssignmentWrapper(KaleoTaskAssignment kaleoTaskAssignment) {
		super(kaleoTaskAssignment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("kaleoTaskAssignmentId", getKaleoTaskAssignmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoClassName", getKaleoClassName());
		attributes.put("kaleoClassPK", getKaleoClassPK());
		attributes.put("kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("assigneeClassName", getAssigneeClassName());
		attributes.put("assigneeClassPK", getAssigneeClassPK());
		attributes.put("assigneeActionId", getAssigneeActionId());
		attributes.put("assigneeScript", getAssigneeScript());
		attributes.put("assigneeScriptLanguage", getAssigneeScriptLanguage());
		attributes.put("assigneeScriptRequiredContexts",
			getAssigneeScriptRequiredContexts());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long kaleoTaskAssignmentId = (Long)attributes.get(
				"kaleoTaskAssignmentId");

		if (kaleoTaskAssignmentId != null) {
			setKaleoTaskAssignmentId(kaleoTaskAssignmentId);
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

		Long kaleoDefinitionVersionId = (Long)attributes.get(
				"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
		}

		String assigneeClassName = (String)attributes.get("assigneeClassName");

		if (assigneeClassName != null) {
			setAssigneeClassName(assigneeClassName);
		}

		Long assigneeClassPK = (Long)attributes.get("assigneeClassPK");

		if (assigneeClassPK != null) {
			setAssigneeClassPK(assigneeClassPK);
		}

		String assigneeActionId = (String)attributes.get("assigneeActionId");

		if (assigneeActionId != null) {
			setAssigneeActionId(assigneeActionId);
		}

		String assigneeScript = (String)attributes.get("assigneeScript");

		if (assigneeScript != null) {
			setAssigneeScript(assigneeScript);
		}

		String assigneeScriptLanguage = (String)attributes.get(
				"assigneeScriptLanguage");

		if (assigneeScriptLanguage != null) {
			setAssigneeScriptLanguage(assigneeScriptLanguage);
		}

		String assigneeScriptRequiredContexts = (String)attributes.get(
				"assigneeScriptRequiredContexts");

		if (assigneeScriptRequiredContexts != null) {
			setAssigneeScriptRequiredContexts(assigneeScriptRequiredContexts);
		}
	}

	/**
	* Returns the assignee action ID of this kaleo task assignment.
	*
	* @return the assignee action ID of this kaleo task assignment
	*/
	@Override
	public String getAssigneeActionId() {
		return model.getAssigneeActionId();
	}

	/**
	* Returns the assignee class name of this kaleo task assignment.
	*
	* @return the assignee class name of this kaleo task assignment
	*/
	@Override
	public String getAssigneeClassName() {
		return model.getAssigneeClassName();
	}

	/**
	* Returns the assignee class pk of this kaleo task assignment.
	*
	* @return the assignee class pk of this kaleo task assignment
	*/
	@Override
	public long getAssigneeClassPK() {
		return model.getAssigneeClassPK();
	}

	/**
	* Returns the assignee script of this kaleo task assignment.
	*
	* @return the assignee script of this kaleo task assignment
	*/
	@Override
	public String getAssigneeScript() {
		return model.getAssigneeScript();
	}

	/**
	* Returns the assignee script language of this kaleo task assignment.
	*
	* @return the assignee script language of this kaleo task assignment
	*/
	@Override
	public String getAssigneeScriptLanguage() {
		return model.getAssigneeScriptLanguage();
	}

	/**
	* Returns the assignee script required contexts of this kaleo task assignment.
	*
	* @return the assignee script required contexts of this kaleo task assignment
	*/
	@Override
	public String getAssigneeScriptRequiredContexts() {
		return model.getAssigneeScriptRequiredContexts();
	}

	/**
	* Returns the company ID of this kaleo task assignment.
	*
	* @return the company ID of this kaleo task assignment
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo task assignment.
	*
	* @return the create date of this kaleo task assignment
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this kaleo task assignment.
	*
	* @return the group ID of this kaleo task assignment
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo class name of this kaleo task assignment.
	*
	* @return the kaleo class name of this kaleo task assignment
	*/
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	* Returns the kaleo class pk of this kaleo task assignment.
	*
	* @return the kaleo class pk of this kaleo task assignment
	*/
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo task assignment.
	*
	* @return the kaleo definition version ID of this kaleo task assignment
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo node ID of this kaleo task assignment.
	*
	* @return the kaleo node ID of this kaleo task assignment
	*/
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	/**
	* Returns the kaleo task assignment ID of this kaleo task assignment.
	*
	* @return the kaleo task assignment ID of this kaleo task assignment
	*/
	@Override
	public long getKaleoTaskAssignmentId() {
		return model.getKaleoTaskAssignmentId();
	}

	/**
	* Returns the modified date of this kaleo task assignment.
	*
	* @return the modified date of this kaleo task assignment
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this kaleo task assignment.
	*
	* @return the mvcc version of this kaleo task assignment
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this kaleo task assignment.
	*
	* @return the primary key of this kaleo task assignment
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo task assignment.
	*
	* @return the user ID of this kaleo task assignment
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo task assignment.
	*
	* @return the user name of this kaleo task assignment
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task assignment.
	*
	* @return the user uuid of this kaleo task assignment
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the assignee action ID of this kaleo task assignment.
	*
	* @param assigneeActionId the assignee action ID of this kaleo task assignment
	*/
	@Override
	public void setAssigneeActionId(String assigneeActionId) {
		model.setAssigneeActionId(assigneeActionId);
	}

	/**
	* Sets the assignee class name of this kaleo task assignment.
	*
	* @param assigneeClassName the assignee class name of this kaleo task assignment
	*/
	@Override
	public void setAssigneeClassName(String assigneeClassName) {
		model.setAssigneeClassName(assigneeClassName);
	}

	/**
	* Sets the assignee class pk of this kaleo task assignment.
	*
	* @param assigneeClassPK the assignee class pk of this kaleo task assignment
	*/
	@Override
	public void setAssigneeClassPK(long assigneeClassPK) {
		model.setAssigneeClassPK(assigneeClassPK);
	}

	/**
	* Sets the assignee script of this kaleo task assignment.
	*
	* @param assigneeScript the assignee script of this kaleo task assignment
	*/
	@Override
	public void setAssigneeScript(String assigneeScript) {
		model.setAssigneeScript(assigneeScript);
	}

	/**
	* Sets the assignee script language of this kaleo task assignment.
	*
	* @param assigneeScriptLanguage the assignee script language of this kaleo task assignment
	*/
	@Override
	public void setAssigneeScriptLanguage(String assigneeScriptLanguage) {
		model.setAssigneeScriptLanguage(assigneeScriptLanguage);
	}

	/**
	* Sets the assignee script required contexts of this kaleo task assignment.
	*
	* @param assigneeScriptRequiredContexts the assignee script required contexts of this kaleo task assignment
	*/
	@Override
	public void setAssigneeScriptRequiredContexts(
		String assigneeScriptRequiredContexts) {
		model.setAssigneeScriptRequiredContexts(assigneeScriptRequiredContexts);
	}

	/**
	* Sets the company ID of this kaleo task assignment.
	*
	* @param companyId the company ID of this kaleo task assignment
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo task assignment.
	*
	* @param createDate the create date of this kaleo task assignment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this kaleo task assignment.
	*
	* @param groupId the group ID of this kaleo task assignment
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo class name of this kaleo task assignment.
	*
	* @param kaleoClassName the kaleo class name of this kaleo task assignment
	*/
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	* Sets the kaleo class pk of this kaleo task assignment.
	*
	* @param kaleoClassPK the kaleo class pk of this kaleo task assignment
	*/
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo task assignment.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo task assignment
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo node ID of this kaleo task assignment.
	*
	* @param kaleoNodeId the kaleo node ID of this kaleo task assignment
	*/
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	* Sets the kaleo task assignment ID of this kaleo task assignment.
	*
	* @param kaleoTaskAssignmentId the kaleo task assignment ID of this kaleo task assignment
	*/
	@Override
	public void setKaleoTaskAssignmentId(long kaleoTaskAssignmentId) {
		model.setKaleoTaskAssignmentId(kaleoTaskAssignmentId);
	}

	/**
	* Sets the modified date of this kaleo task assignment.
	*
	* @param modifiedDate the modified date of this kaleo task assignment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this kaleo task assignment.
	*
	* @param mvccVersion the mvcc version of this kaleo task assignment
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this kaleo task assignment.
	*
	* @param primaryKey the primary key of this kaleo task assignment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo task assignment.
	*
	* @param userId the user ID of this kaleo task assignment
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task assignment.
	*
	* @param userName the user name of this kaleo task assignment
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task assignment.
	*
	* @param userUuid the user uuid of this kaleo task assignment
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected KaleoTaskAssignmentWrapper wrap(
		KaleoTaskAssignment kaleoTaskAssignment) {
		return new KaleoTaskAssignmentWrapper(kaleoTaskAssignment);
	}
}