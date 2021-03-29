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

package com.liferay.account.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AccountGroup}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroup
 * @generated
 */
public class AccountGroupWrapper
	extends BaseModelWrapper<AccountGroup>
	implements AccountGroup, ModelWrapper<AccountGroup> {

	public AccountGroupWrapper(AccountGroup accountGroup) {
		super(accountGroup);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("accountGroupId", getAccountGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("defaultAccountGroup", isDefaultAccountGroup());
		attributes.put("description", getDescription());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long accountGroupId = (Long)attributes.get("accountGroupId");

		if (accountGroupId != null) {
			setAccountGroupId(accountGroupId);
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

		Boolean defaultAccountGroup = (Boolean)attributes.get(
			"defaultAccountGroup");

		if (defaultAccountGroup != null) {
			setDefaultAccountGroup(defaultAccountGroup);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	/**
	 * Returns the account group ID of this account group.
	 *
	 * @return the account group ID of this account group
	 */
	@Override
	public long getAccountGroupId() {
		return model.getAccountGroupId();
	}

	/**
	 * Returns the company ID of this account group.
	 *
	 * @return the company ID of this account group
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this account group.
	 *
	 * @return the create date of this account group
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default account group of this account group.
	 *
	 * @return the default account group of this account group
	 */
	@Override
	public boolean getDefaultAccountGroup() {
		return model.getDefaultAccountGroup();
	}

	/**
	 * Returns the description of this account group.
	 *
	 * @return the description of this account group
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this account group.
	 *
	 * @return the external reference code of this account group
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this account group.
	 *
	 * @return the modified date of this account group
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this account group.
	 *
	 * @return the mvcc version of this account group
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this account group.
	 *
	 * @return the name of this account group
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this account group.
	 *
	 * @return the primary key of this account group
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this account group.
	 *
	 * @return the type of this account group
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this account group.
	 *
	 * @return the user ID of this account group
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this account group.
	 *
	 * @return the user name of this account group
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this account group.
	 *
	 * @return the user uuid of this account group
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this account group is default account group.
	 *
	 * @return <code>true</code> if this account group is default account group; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultAccountGroup() {
		return model.isDefaultAccountGroup();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account group ID of this account group.
	 *
	 * @param accountGroupId the account group ID of this account group
	 */
	@Override
	public void setAccountGroupId(long accountGroupId) {
		model.setAccountGroupId(accountGroupId);
	}

	/**
	 * Sets the company ID of this account group.
	 *
	 * @param companyId the company ID of this account group
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this account group.
	 *
	 * @param createDate the create date of this account group
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this account group is default account group.
	 *
	 * @param defaultAccountGroup the default account group of this account group
	 */
	@Override
	public void setDefaultAccountGroup(boolean defaultAccountGroup) {
		model.setDefaultAccountGroup(defaultAccountGroup);
	}

	/**
	 * Sets the description of this account group.
	 *
	 * @param description the description of this account group
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this account group.
	 *
	 * @param externalReferenceCode the external reference code of this account group
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this account group.
	 *
	 * @param modifiedDate the modified date of this account group
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this account group.
	 *
	 * @param mvccVersion the mvcc version of this account group
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this account group.
	 *
	 * @param name the name of this account group
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this account group.
	 *
	 * @param primaryKey the primary key of this account group
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this account group.
	 *
	 * @param type the type of this account group
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this account group.
	 *
	 * @param userId the user ID of this account group
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this account group.
	 *
	 * @param userName the user name of this account group
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this account group.
	 *
	 * @param userUuid the user uuid of this account group
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AccountGroupWrapper wrap(AccountGroup accountGroup) {
		return new AccountGroupWrapper(accountGroup);
	}

}