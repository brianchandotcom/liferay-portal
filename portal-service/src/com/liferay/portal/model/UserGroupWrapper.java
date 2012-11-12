/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroup
 * @generated
 */
public class UserGroupWrapper implements UserGroup, ModelWrapper<UserGroup> {
	public UserGroupWrapper(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	public Class<?> getModelClass() {
		return UserGroup.class;
	}

	public String getModelClassName() {
		return UserGroup.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userGroupId", getUserGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("parentUserGroupId", getParentUserGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("addedByLDAPImport", getAddedByLDAPImport());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long userGroupId = (Long)attributes.get("userGroupId");

		if (userGroupId != null) {
			setUserGroupId(userGroupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long parentUserGroupId = (Long)attributes.get("parentUserGroupId");

		if (parentUserGroupId != null) {
			setParentUserGroupId(parentUserGroupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean addedByLDAPImport = (Boolean)attributes.get("addedByLDAPImport");

		if (addedByLDAPImport != null) {
			setAddedByLDAPImport(addedByLDAPImport);
		}
	}

	/**
	* Returns the primary key of this user group.
	*
	* @return the primary key of this user group
	*/
	public long getPrimaryKey() {
		return _userGroup.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user group.
	*
	* @param primaryKey the primary key of this user group
	*/
	public void setPrimaryKey(long primaryKey) {
		_userGroup.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the user group ID of this user group.
	*
	* @return the user group ID of this user group
	*/
	public long getUserGroupId() {
		return _userGroup.getUserGroupId();
	}

	/**
	* Sets the user group ID of this user group.
	*
	* @param userGroupId the user group ID of this user group
	*/
	public void setUserGroupId(long userGroupId) {
		_userGroup.setUserGroupId(userGroupId);
	}

	/**
	* Returns the company ID of this user group.
	*
	* @return the company ID of this user group
	*/
	public long getCompanyId() {
		return _userGroup.getCompanyId();
	}

	/**
	* Sets the company ID of this user group.
	*
	* @param companyId the company ID of this user group
	*/
	public void setCompanyId(long companyId) {
		_userGroup.setCompanyId(companyId);
	}

	/**
	* Returns the parent user group ID of this user group.
	*
	* @return the parent user group ID of this user group
	*/
	public long getParentUserGroupId() {
		return _userGroup.getParentUserGroupId();
	}

	/**
	* Sets the parent user group ID of this user group.
	*
	* @param parentUserGroupId the parent user group ID of this user group
	*/
	public void setParentUserGroupId(long parentUserGroupId) {
		_userGroup.setParentUserGroupId(parentUserGroupId);
	}

	/**
	* Returns the name of this user group.
	*
	* @return the name of this user group
	*/
	public java.lang.String getName() {
		return _userGroup.getName();
	}

	/**
	* Sets the name of this user group.
	*
	* @param name the name of this user group
	*/
	public void setName(java.lang.String name) {
		_userGroup.setName(name);
	}

	/**
	* Returns the description of this user group.
	*
	* @return the description of this user group
	*/
	public java.lang.String getDescription() {
		return _userGroup.getDescription();
	}

	/**
	* Sets the description of this user group.
	*
	* @param description the description of this user group
	*/
	public void setDescription(java.lang.String description) {
		_userGroup.setDescription(description);
	}

	/**
	* Returns the added by l d a p import of this user group.
	*
	* @return the added by l d a p import of this user group
	*/
	public boolean getAddedByLDAPImport() {
		return _userGroup.getAddedByLDAPImport();
	}

	/**
	* Returns <code>true</code> if this user group is added by l d a p import.
	*
	* @return <code>true</code> if this user group is added by l d a p import; <code>false</code> otherwise
	*/
	public boolean isAddedByLDAPImport() {
		return _userGroup.isAddedByLDAPImport();
	}

	/**
	* Sets whether this user group is added by l d a p import.
	*
	* @param addedByLDAPImport the added by l d a p import of this user group
	*/
	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		_userGroup.setAddedByLDAPImport(addedByLDAPImport);
	}

	public boolean isNew() {
		return _userGroup.isNew();
	}

	public void setNew(boolean n) {
		_userGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroup.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroup.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroup.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupWrapper((UserGroup)_userGroup.clone());
	}

	public int compareTo(com.liferay.portal.model.UserGroup userGroup) {
		return _userGroup.compareTo(userGroup);
	}

	@Override
	public int hashCode() {
		return _userGroup.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserGroup> toCacheModel() {
		return _userGroup.toCacheModel();
	}

	public com.liferay.portal.model.UserGroup toEscapedModel() {
		return new UserGroupWrapper(_userGroup.toEscapedModel());
	}

	public com.liferay.portal.model.UserGroup toUnescapedModel() {
		return new UserGroupWrapper(_userGroup.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroup.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroup.persist();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getGroup();
	}

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPublicLayoutsPageCount();
	}

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPublicLayouts();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public UserGroup getWrappedUserGroup() {
		return _userGroup;
	}

	public UserGroup getWrappedModel() {
		return _userGroup;
	}

	public void resetOriginalValues() {
		_userGroup.resetOriginalValues();
	}

	private UserGroup _userGroup;
}