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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WorkflowDefinitionLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLink
 * @generated
 */
@ProviderType
public class WorkflowDefinitionLinkWrapper extends BaseModelWrapper<WorkflowDefinitionLink>
	implements WorkflowDefinitionLink, ModelWrapper<WorkflowDefinitionLink> {
	public WorkflowDefinitionLinkWrapper(
		WorkflowDefinitionLink workflowDefinitionLink) {
		super(workflowDefinitionLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("workflowDefinitionLinkId", getWorkflowDefinitionLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("typePK", getTypePK());
		attributes.put("workflowDefinitionName", getWorkflowDefinitionName());
		attributes.put("workflowDefinitionVersion",
			getWorkflowDefinitionVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long workflowDefinitionLinkId = (Long)attributes.get(
				"workflowDefinitionLinkId");

		if (workflowDefinitionLinkId != null) {
			setWorkflowDefinitionLinkId(workflowDefinitionLinkId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long typePK = (Long)attributes.get("typePK");

		if (typePK != null) {
			setTypePK(typePK);
		}

		String workflowDefinitionName = (String)attributes.get(
				"workflowDefinitionName");

		if (workflowDefinitionName != null) {
			setWorkflowDefinitionName(workflowDefinitionName);
		}

		Integer workflowDefinitionVersion = (Integer)attributes.get(
				"workflowDefinitionVersion");

		if (workflowDefinitionVersion != null) {
			setWorkflowDefinitionVersion(workflowDefinitionVersion);
		}
	}

	/**
	* Returns the fully qualified class name of this workflow definition link.
	*
	* @return the fully qualified class name of this workflow definition link
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this workflow definition link.
	*
	* @return the class name ID of this workflow definition link
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this workflow definition link.
	*
	* @return the class pk of this workflow definition link
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this workflow definition link.
	*
	* @return the company ID of this workflow definition link
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this workflow definition link.
	*
	* @return the create date of this workflow definition link
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this workflow definition link.
	*
	* @return the group ID of this workflow definition link
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the modified date of this workflow definition link.
	*
	* @return the modified date of this workflow definition link
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this workflow definition link.
	*
	* @return the mvcc version of this workflow definition link
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this workflow definition link.
	*
	* @return the primary key of this workflow definition link
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the type pk of this workflow definition link.
	*
	* @return the type pk of this workflow definition link
	*/
	@Override
	public long getTypePK() {
		return model.getTypePK();
	}

	/**
	* Returns the user ID of this workflow definition link.
	*
	* @return the user ID of this workflow definition link
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this workflow definition link.
	*
	* @return the user name of this workflow definition link
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this workflow definition link.
	*
	* @return the user uuid of this workflow definition link
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the workflow definition link ID of this workflow definition link.
	*
	* @return the workflow definition link ID of this workflow definition link
	*/
	@Override
	public long getWorkflowDefinitionLinkId() {
		return model.getWorkflowDefinitionLinkId();
	}

	/**
	* Returns the workflow definition name of this workflow definition link.
	*
	* @return the workflow definition name of this workflow definition link
	*/
	@Override
	public String getWorkflowDefinitionName() {
		return model.getWorkflowDefinitionName();
	}

	/**
	* Returns the workflow definition version of this workflow definition link.
	*
	* @return the workflow definition version of this workflow definition link
	*/
	@Override
	public int getWorkflowDefinitionVersion() {
		return model.getWorkflowDefinitionVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this workflow definition link.
	*
	* @param classNameId the class name ID of this workflow definition link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this workflow definition link.
	*
	* @param classPK the class pk of this workflow definition link
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this workflow definition link.
	*
	* @param companyId the company ID of this workflow definition link
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this workflow definition link.
	*
	* @param createDate the create date of this workflow definition link
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this workflow definition link.
	*
	* @param groupId the group ID of this workflow definition link
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this workflow definition link.
	*
	* @param modifiedDate the modified date of this workflow definition link
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this workflow definition link.
	*
	* @param mvccVersion the mvcc version of this workflow definition link
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this workflow definition link.
	*
	* @param primaryKey the primary key of this workflow definition link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the type pk of this workflow definition link.
	*
	* @param typePK the type pk of this workflow definition link
	*/
	@Override
	public void setTypePK(long typePK) {
		model.setTypePK(typePK);
	}

	/**
	* Sets the user ID of this workflow definition link.
	*
	* @param userId the user ID of this workflow definition link
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this workflow definition link.
	*
	* @param userName the user name of this workflow definition link
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this workflow definition link.
	*
	* @param userUuid the user uuid of this workflow definition link
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the workflow definition link ID of this workflow definition link.
	*
	* @param workflowDefinitionLinkId the workflow definition link ID of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		model.setWorkflowDefinitionLinkId(workflowDefinitionLinkId);
	}

	/**
	* Sets the workflow definition name of this workflow definition link.
	*
	* @param workflowDefinitionName the workflow definition name of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		model.setWorkflowDefinitionName(workflowDefinitionName);
	}

	/**
	* Sets the workflow definition version of this workflow definition link.
	*
	* @param workflowDefinitionVersion the workflow definition version of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		model.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	@Override
	protected WorkflowDefinitionLinkWrapper wrap(
		WorkflowDefinitionLink workflowDefinitionLink) {
		return new WorkflowDefinitionLinkWrapper(workflowDefinitionLink);
	}
}