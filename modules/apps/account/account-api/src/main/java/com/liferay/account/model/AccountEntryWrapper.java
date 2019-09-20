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
 * This class is a wrapper for {@link AccountEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntry
 * @generated
 */
public class AccountEntryWrapper
	extends BaseModelWrapper<AccountEntry>
	implements AccountEntry, ModelWrapper<AccountEntry> {

	public AccountEntryWrapper(AccountEntry accountEntry) {
		super(accountEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentAccountEntryId", getParentAccountEntryId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("logoId", getLogoId());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
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

		Long parentAccountEntryId = (Long)attributes.get(
			"parentAccountEntryId");

		if (parentAccountEntryId != null) {
			setParentAccountEntryId(parentAccountEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the account entry ID of this account entry.
	 *
	 * @return the account entry ID of this account entry
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the company ID of this account entry.
	 *
	 * @return the company ID of this account entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this account entry.
	 *
	 * @return the create date of this account entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this account entry.
	 *
	 * @return the description of this account entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the logo ID of this account entry.
	 *
	 * @return the logo ID of this account entry
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this account entry.
	 *
	 * @return the modified date of this account entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this account entry.
	 *
	 * @return the mvcc version of this account entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this account entry.
	 *
	 * @return the name of this account entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent account entry ID of this account entry.
	 *
	 * @return the parent account entry ID of this account entry
	 */
	@Override
	public long getParentAccountEntryId() {
		return model.getParentAccountEntryId();
	}

	/**
	 * Returns the primary key of this account entry.
	 *
	 * @return the primary key of this account entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this account entry.
	 *
	 * @return the status of this account entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this account entry.
	 *
	 * @return the user ID of this account entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this account entry.
	 *
	 * @return the user name of this account entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this account entry.
	 *
	 * @return the user uuid of this account entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account entry model instance should use the <code>AccountEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this account entry.
	 *
	 * @param accountEntryId the account entry ID of this account entry
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the company ID of this account entry.
	 *
	 * @param companyId the company ID of this account entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this account entry.
	 *
	 * @param createDate the create date of this account entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this account entry.
	 *
	 * @param description the description of this account entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the logo ID of this account entry.
	 *
	 * @param logoId the logo ID of this account entry
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this account entry.
	 *
	 * @param modifiedDate the modified date of this account entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this account entry.
	 *
	 * @param mvccVersion the mvcc version of this account entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this account entry.
	 *
	 * @param name the name of this account entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent account entry ID of this account entry.
	 *
	 * @param parentAccountEntryId the parent account entry ID of this account entry
	 */
	@Override
	public void setParentAccountEntryId(long parentAccountEntryId) {
		model.setParentAccountEntryId(parentAccountEntryId);
	}

	/**
	 * Sets the primary key of this account entry.
	 *
	 * @param primaryKey the primary key of this account entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this account entry.
	 *
	 * @param status the status of this account entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this account entry.
	 *
	 * @param userId the user ID of this account entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this account entry.
	 *
	 * @param userName the user name of this account entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this account entry.
	 *
	 * @param userUuid the user uuid of this account entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AccountEntryWrapper wrap(AccountEntry accountEntry) {
		return new AccountEntryWrapper(accountEntry);
	}

}