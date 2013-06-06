/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

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
public class WorkflowDefinitionLinkWrapper implements WorkflowDefinitionLink,
	ModelWrapper<WorkflowDefinitionLink> {
	public WorkflowDefinitionLinkWrapper(
		WorkflowDefinitionLink workflowDefinitionLink) {
		_workflowDefinitionLink = workflowDefinitionLink;
	}

	@Override
	public Class<?> getModelClass() {
		return WorkflowDefinitionLink.class;
	}

	@Override
	public String getModelClassName() {
		return WorkflowDefinitionLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
	* Returns the primary key of this workflow definition link.
	*
	* @return the primary key of this workflow definition link
	*/
	@Override
	public long getPrimaryKey() {
		return _workflowDefinitionLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this workflow definition link.
	*
	* @param primaryKey the primary key of this workflow definition link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_workflowDefinitionLink.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the workflow definition link ID of this workflow definition link.
	*
	* @return the workflow definition link ID of this workflow definition link
	*/
	@Override
	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLink.getWorkflowDefinitionLinkId();
	}

	/**
	* Sets the workflow definition link ID of this workflow definition link.
	*
	* @param workflowDefinitionLinkId the workflow definition link ID of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLink.setWorkflowDefinitionLinkId(workflowDefinitionLinkId);
	}

	/**
	* Returns the group ID of this workflow definition link.
	*
	* @return the group ID of this workflow definition link
	*/
	@Override
	public long getGroupId() {
		return _workflowDefinitionLink.getGroupId();
	}

	/**
	* Sets the group ID of this workflow definition link.
	*
	* @param groupId the group ID of this workflow definition link
	*/
	@Override
	public void setGroupId(long groupId) {
		_workflowDefinitionLink.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this workflow definition link.
	*
	* @return the company ID of this workflow definition link
	*/
	@Override
	public long getCompanyId() {
		return _workflowDefinitionLink.getCompanyId();
	}

	/**
	* Sets the company ID of this workflow definition link.
	*
	* @param companyId the company ID of this workflow definition link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_workflowDefinitionLink.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this workflow definition link.
	*
	* @return the user ID of this workflow definition link
	*/
	@Override
	public long getUserId() {
		return _workflowDefinitionLink.getUserId();
	}

	/**
	* Sets the user ID of this workflow definition link.
	*
	* @param userId the user ID of this workflow definition link
	*/
	@Override
	public void setUserId(long userId) {
		_workflowDefinitionLink.setUserId(userId);
	}

	/**
	* Returns the user uuid of this workflow definition link.
	*
	* @return the user uuid of this workflow definition link
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLink.getUserUuid();
	}

	/**
	* Sets the user uuid of this workflow definition link.
	*
	* @param userUuid the user uuid of this workflow definition link
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_workflowDefinitionLink.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this workflow definition link.
	*
	* @return the user name of this workflow definition link
	*/
	@Override
	public java.lang.String getUserName() {
		return _workflowDefinitionLink.getUserName();
	}

	/**
	* Sets the user name of this workflow definition link.
	*
	* @param userName the user name of this workflow definition link
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_workflowDefinitionLink.setUserName(userName);
	}

	/**
	* Returns the create date of this workflow definition link.
	*
	* @return the create date of this workflow definition link
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _workflowDefinitionLink.getCreateDate();
	}

	/**
	* Sets the create date of this workflow definition link.
	*
	* @param createDate the create date of this workflow definition link
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_workflowDefinitionLink.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this workflow definition link.
	*
	* @return the modified date of this workflow definition link
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _workflowDefinitionLink.getModifiedDate();
	}

	/**
	* Sets the modified date of this workflow definition link.
	*
	* @param modifiedDate the modified date of this workflow definition link
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowDefinitionLink.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this workflow definition link.
	*
	* @return the fully qualified class name of this workflow definition link
	*/
	@Override
	public java.lang.String getClassName() {
		return _workflowDefinitionLink.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_workflowDefinitionLink.setClassName(className);
	}

	/**
	* Returns the class name ID of this workflow definition link.
	*
	* @return the class name ID of this workflow definition link
	*/
	@Override
	public long getClassNameId() {
		return _workflowDefinitionLink.getClassNameId();
	}

	/**
	* Sets the class name ID of this workflow definition link.
	*
	* @param classNameId the class name ID of this workflow definition link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_workflowDefinitionLink.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this workflow definition link.
	*
	* @return the class p k of this workflow definition link
	*/
	@Override
	public long getClassPK() {
		return _workflowDefinitionLink.getClassPK();
	}

	/**
	* Sets the class p k of this workflow definition link.
	*
	* @param classPK the class p k of this workflow definition link
	*/
	@Override
	public void setClassPK(long classPK) {
		_workflowDefinitionLink.setClassPK(classPK);
	}

	/**
	* Returns the type p k of this workflow definition link.
	*
	* @return the type p k of this workflow definition link
	*/
	@Override
	public long getTypePK() {
		return _workflowDefinitionLink.getTypePK();
	}

	/**
	* Sets the type p k of this workflow definition link.
	*
	* @param typePK the type p k of this workflow definition link
	*/
	@Override
	public void setTypePK(long typePK) {
		_workflowDefinitionLink.setTypePK(typePK);
	}

	/**
	* Returns the workflow definition name of this workflow definition link.
	*
	* @return the workflow definition name of this workflow definition link
	*/
	@Override
	public java.lang.String getWorkflowDefinitionName() {
		return _workflowDefinitionLink.getWorkflowDefinitionName();
	}

	/**
	* Sets the workflow definition name of this workflow definition link.
	*
	* @param workflowDefinitionName the workflow definition name of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionName(
		java.lang.String workflowDefinitionName) {
		_workflowDefinitionLink.setWorkflowDefinitionName(workflowDefinitionName);
	}

	/**
	* Returns the workflow definition version of this workflow definition link.
	*
	* @return the workflow definition version of this workflow definition link
	*/
	@Override
	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionLink.getWorkflowDefinitionVersion();
	}

	/**
	* Sets the workflow definition version of this workflow definition link.
	*
	* @param workflowDefinitionVersion the workflow definition version of this workflow definition link
	*/
	@Override
	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionLink.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	@Override
	public boolean isNew() {
		return _workflowDefinitionLink.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_workflowDefinitionLink.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _workflowDefinitionLink.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_workflowDefinitionLink.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _workflowDefinitionLink.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowDefinitionLink.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_workflowDefinitionLink.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowDefinitionLink.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_workflowDefinitionLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_workflowDefinitionLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowDefinitionLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WorkflowDefinitionLinkWrapper((WorkflowDefinitionLink)_workflowDefinitionLink.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		return _workflowDefinitionLink.compareTo(workflowDefinitionLink);
	}

	@Override
	public int hashCode() {
		return _workflowDefinitionLink.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.WorkflowDefinitionLink> toCacheModel() {
		return _workflowDefinitionLink.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink toEscapedModel() {
		return new WorkflowDefinitionLinkWrapper(_workflowDefinitionLink.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink toUnescapedModel() {
		return new WorkflowDefinitionLinkWrapper(_workflowDefinitionLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _workflowDefinitionLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _workflowDefinitionLink.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_workflowDefinitionLink.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public WorkflowDefinitionLink getWrappedWorkflowDefinitionLink() {
		return _workflowDefinitionLink;
	}

	@Override
	public WorkflowDefinitionLink getWrappedModel() {
		return _workflowDefinitionLink;
	}

	@Override
	public void resetOriginalValues() {
		_workflowDefinitionLink.resetOriginalValues();
	}

	private WorkflowDefinitionLink _workflowDefinitionLink;
}