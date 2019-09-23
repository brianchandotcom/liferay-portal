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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
public class UserGroupWrapper
	extends BaseModelWrapper<UserGroup>
	implements ModelWrapper<UserGroup>, UserGroup {

	public UserGroupWrapper(UserGroup userGroup) {
		super(userGroup);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("userGroupId", getUserGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentUserGroupId", getParentUserGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("addedByLDAPImport", isAddedByLDAPImport());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long userGroupId = (Long)attributes.get("userGroupId");

		if (userGroupId != null) {
			setUserGroupId(userGroupId);
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

		Boolean addedByLDAPImport = (Boolean)attributes.get(
			"addedByLDAPImport");

		if (addedByLDAPImport != null) {
			setAddedByLDAPImport(addedByLDAPImport);
		}
	}

	/**
	 * Returns the added by ldap import of this user group.
	 *
	 * @return the added by ldap import of this user group
	 */
	@Override
	public boolean getAddedByLDAPImport() {
		return model.getAddedByLDAPImport();
	}

	/**
	 * Returns the company ID of this user group.
	 *
	 * @return the company ID of this user group
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this user group.
	 *
	 * @return the create date of this user group
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this user group.
	 *
	 * @return the description of this user group
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this user group.
	 *
	 * @return the external reference code of this user group
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	@Override
	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this user group.
	 *
	 * @return the modified date of this user group
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this user group.
	 *
	 * @return the mvcc version of this user group
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this user group.
	 *
	 * @return the name of this user group
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent user group ID of this user group.
	 *
	 * @return the parent user group ID of this user group
	 */
	@Override
	public long getParentUserGroupId() {
		return model.getParentUserGroupId();
	}

	/**
	 * Returns the primary key of this user group.
	 *
	 * @return the primary key of this user group
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPublicLayoutsPageCount();
	}

	/**
	 * Returns the user group ID of this user group.
	 *
	 * @return the user group ID of this user group
	 */
	@Override
	public long getUserGroupId() {
		return model.getUserGroupId();
	}

	/**
	 * Returns the user ID of this user group.
	 *
	 * @return the user ID of this user group
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this user group.
	 *
	 * @return the user name of this user group
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this user group.
	 *
	 * @return the user uuid of this user group
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this user group.
	 *
	 * @return the uuid of this user group
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.hasPublicLayouts();
	}

	/**
	 * Returns <code>true</code> if this user group is added by ldap import.
	 *
	 * @return <code>true</code> if this user group is added by ldap import; <code>false</code> otherwise
	 */
	@Override
	public boolean isAddedByLDAPImport() {
		return model.isAddedByLDAPImport();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user group model instance should use the <code>UserGroup</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this user group is added by ldap import.
	 *
	 * @param addedByLDAPImport the added by ldap import of this user group
	 */
	@Override
	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		model.setAddedByLDAPImport(addedByLDAPImport);
	}

	/**
	 * Sets the company ID of this user group.
	 *
	 * @param companyId the company ID of this user group
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this user group.
	 *
	 * @param createDate the create date of this user group
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this user group.
	 *
	 * @param description the description of this user group
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this user group.
	 *
	 * @param externalReferenceCode the external reference code of this user group
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this user group.
	 *
	 * @param modifiedDate the modified date of this user group
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this user group.
	 *
	 * @param mvccVersion the mvcc version of this user group
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this user group.
	 *
	 * @param name the name of this user group
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent user group ID of this user group.
	 *
	 * @param parentUserGroupId the parent user group ID of this user group
	 */
	@Override
	public void setParentUserGroupId(long parentUserGroupId) {
		model.setParentUserGroupId(parentUserGroupId);
	}

	/**
	 * Sets the primary key of this user group.
	 *
	 * @param primaryKey the primary key of this user group
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user group ID of this user group.
	 *
	 * @param userGroupId the user group ID of this user group
	 */
	@Override
	public void setUserGroupId(long userGroupId) {
		model.setUserGroupId(userGroupId);
	}

	/**
	 * Sets the user ID of this user group.
	 *
	 * @param userId the user ID of this user group
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this user group.
	 *
	 * @param userName the user name of this user group
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this user group.
	 *
	 * @param userUuid the user uuid of this user group
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this user group.
	 *
	 * @param uuid the uuid of this user group
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected UserGroupWrapper wrap(UserGroup userGroup) {
		return new UserGroupWrapper(userGroup);
	}

}