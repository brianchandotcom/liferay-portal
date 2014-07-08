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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WorkflowInstanceLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLink
 * @generated
 */
@ProviderType
public class WorkflowInstanceLinkWrapper implements WorkflowInstanceLink,
	ModelWrapper<WorkflowInstanceLink> {
	public WorkflowInstanceLinkWrapper(
		WorkflowInstanceLink workflowInstanceLink) {
		_workflowInstanceLink = workflowInstanceLink;
	}

	@Override
	public Class<?> getModelClass() {
		return WorkflowInstanceLink.class;
	}

	@Override
	public String getModelClassName() {
		return WorkflowInstanceLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("workflowInstanceLinkId", getWorkflowInstanceLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("workflowInstanceId", getWorkflowInstanceId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long workflowInstanceLinkId = (Long)attributes.get(
				"workflowInstanceLinkId");

		if (workflowInstanceLinkId != null) {
			setWorkflowInstanceLinkId(workflowInstanceLinkId);
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

		Long workflowInstanceId = (Long)attributes.get("workflowInstanceId");

		if (workflowInstanceId != null) {
			setWorkflowInstanceId(workflowInstanceId);
		}
	}

	/**
	* Returns the primary key of this workflow instance link.
	*
	* @return the primary key of this workflow instance link
	*/
	@Override
	public long getPrimaryKey() {
		return _workflowInstanceLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this workflow instance link.
	*
	* @param primaryKey the primary key of this workflow instance link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_workflowInstanceLink.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this workflow instance link.
	*
	* @return the mvcc version of this workflow instance link
	*/
	@Override
	public long getMvccVersion() {
		return _workflowInstanceLink.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this workflow instance link.
	*
	* @param mvccVersion the mvcc version of this workflow instance link
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_workflowInstanceLink.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the workflow instance link ID of this workflow instance link.
	*
	* @return the workflow instance link ID of this workflow instance link
	*/
	@Override
	public long getWorkflowInstanceLinkId() {
		return _workflowInstanceLink.getWorkflowInstanceLinkId();
	}

	/**
	* Sets the workflow instance link ID of this workflow instance link.
	*
	* @param workflowInstanceLinkId the workflow instance link ID of this workflow instance link
	*/
	@Override
	public void setWorkflowInstanceLinkId(long workflowInstanceLinkId) {
		_workflowInstanceLink.setWorkflowInstanceLinkId(workflowInstanceLinkId);
	}

	/**
	* Returns the group ID of this workflow instance link.
	*
	* @return the group ID of this workflow instance link
	*/
	@Override
	public long getGroupId() {
		return _workflowInstanceLink.getGroupId();
	}

	/**
	* Sets the group ID of this workflow instance link.
	*
	* @param groupId the group ID of this workflow instance link
	*/
	@Override
	public void setGroupId(long groupId) {
		_workflowInstanceLink.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this workflow instance link.
	*
	* @return the company ID of this workflow instance link
	*/
	@Override
	public long getCompanyId() {
		return _workflowInstanceLink.getCompanyId();
	}

	/**
	* Sets the company ID of this workflow instance link.
	*
	* @param companyId the company ID of this workflow instance link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_workflowInstanceLink.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this workflow instance link.
	*
	* @return the user ID of this workflow instance link
	*/
	@Override
	public long getUserId() {
		return _workflowInstanceLink.getUserId();
	}

	/**
	* Sets the user ID of this workflow instance link.
	*
	* @param userId the user ID of this workflow instance link
	*/
	@Override
	public void setUserId(long userId) {
		_workflowInstanceLink.setUserId(userId);
	}

	/**
	* Returns the user uuid of this workflow instance link.
	*
	* @return the user uuid of this workflow instance link
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _workflowInstanceLink.getUserUuid();
	}

	/**
	* Sets the user uuid of this workflow instance link.
	*
	* @param userUuid the user uuid of this workflow instance link
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_workflowInstanceLink.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this workflow instance link.
	*
	* @return the user name of this workflow instance link
	*/
	@Override
	public java.lang.String getUserName() {
		return _workflowInstanceLink.getUserName();
	}

	/**
	* Sets the user name of this workflow instance link.
	*
	* @param userName the user name of this workflow instance link
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_workflowInstanceLink.setUserName(userName);
	}

	/**
	* Returns the create date of this workflow instance link.
	*
	* @return the create date of this workflow instance link
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _workflowInstanceLink.getCreateDate();
	}

	/**
	* Sets the create date of this workflow instance link.
	*
	* @param createDate the create date of this workflow instance link
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_workflowInstanceLink.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this workflow instance link.
	*
	* @return the modified date of this workflow instance link
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _workflowInstanceLink.getModifiedDate();
	}

	/**
	* Sets the modified date of this workflow instance link.
	*
	* @param modifiedDate the modified date of this workflow instance link
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowInstanceLink.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this workflow instance link.
	*
	* @return the fully qualified class name of this workflow instance link
	*/
	@Override
	public java.lang.String getClassName() {
		return _workflowInstanceLink.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_workflowInstanceLink.setClassName(className);
	}

	/**
	* Returns the class name ID of this workflow instance link.
	*
	* @return the class name ID of this workflow instance link
	*/
	@Override
	public long getClassNameId() {
		return _workflowInstanceLink.getClassNameId();
	}

	/**
	* Sets the class name ID of this workflow instance link.
	*
	* @param classNameId the class name ID of this workflow instance link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_workflowInstanceLink.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this workflow instance link.
	*
	* @return the class p k of this workflow instance link
	*/
	@Override
	public long getClassPK() {
		return _workflowInstanceLink.getClassPK();
	}

	/**
	* Sets the class p k of this workflow instance link.
	*
	* @param classPK the class p k of this workflow instance link
	*/
	@Override
	public void setClassPK(long classPK) {
		_workflowInstanceLink.setClassPK(classPK);
	}

	/**
	* Returns the workflow instance ID of this workflow instance link.
	*
	* @return the workflow instance ID of this workflow instance link
	*/
	@Override
	public long getWorkflowInstanceId() {
		return _workflowInstanceLink.getWorkflowInstanceId();
	}

	/**
	* Sets the workflow instance ID of this workflow instance link.
	*
	* @param workflowInstanceId the workflow instance ID of this workflow instance link
	*/
	@Override
	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);
	}

	@Override
	public boolean isNew() {
		return _workflowInstanceLink.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_workflowInstanceLink.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _workflowInstanceLink.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_workflowInstanceLink.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _workflowInstanceLink.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowInstanceLink.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_workflowInstanceLink.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowInstanceLink.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_workflowInstanceLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_workflowInstanceLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowInstanceLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WorkflowInstanceLinkWrapper((WorkflowInstanceLink)_workflowInstanceLink.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		return _workflowInstanceLink.compareTo(workflowInstanceLink);
	}

	@Override
	public int hashCode() {
		return _workflowInstanceLink.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.WorkflowInstanceLink> toCacheModel() {
		return _workflowInstanceLink.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink toEscapedModel() {
		return new WorkflowInstanceLinkWrapper(_workflowInstanceLink.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink toUnescapedModel() {
		return new WorkflowInstanceLinkWrapper(_workflowInstanceLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _workflowInstanceLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _workflowInstanceLink.toXmlString();
	}

	@Override
	public void persist() {
		_workflowInstanceLink.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowInstanceLinkWrapper)) {
			return false;
		}

		WorkflowInstanceLinkWrapper workflowInstanceLinkWrapper = (WorkflowInstanceLinkWrapper)obj;

		if (Validator.equals(_workflowInstanceLink,
					workflowInstanceLinkWrapper._workflowInstanceLink)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public WorkflowInstanceLink getWrappedWorkflowInstanceLink() {
		return _workflowInstanceLink;
	}

	@Override
	public WorkflowInstanceLink getWrappedModel() {
		return _workflowInstanceLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _workflowInstanceLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _workflowInstanceLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_workflowInstanceLink.resetOriginalValues();
	}

	private WorkflowInstanceLink _workflowInstanceLink;
}