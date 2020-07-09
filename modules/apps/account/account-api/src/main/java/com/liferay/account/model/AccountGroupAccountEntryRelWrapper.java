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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AccountGroupAccountEntryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRel
 * @generated
 */
public class AccountGroupAccountEntryRelWrapper
	extends BaseModelWrapper<AccountGroupAccountEntryRel>
	implements AccountGroupAccountEntryRel,
			   ModelWrapper<AccountGroupAccountEntryRel> {

	public AccountGroupAccountEntryRelWrapper(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		super(accountGroupAccountEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"AccountGroupAccountEntryRelId",
			getAccountGroupAccountEntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("accountGroupId", getAccountGroupId());
		attributes.put("accountEntryId", getAccountEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long AccountGroupAccountEntryRelId = (Long)attributes.get(
			"AccountGroupAccountEntryRelId");

		if (AccountGroupAccountEntryRelId != null) {
			setAccountGroupAccountEntryRelId(AccountGroupAccountEntryRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long accountGroupId = (Long)attributes.get("accountGroupId");

		if (accountGroupId != null) {
			setAccountGroupId(accountGroupId);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
		}
	}

	/**
	 * Returns the account entry ID of this account group account entry rel.
	 *
	 * @return the account entry ID of this account group account entry rel
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the account group account entry rel ID of this account group account entry rel.
	 *
	 * @return the account group account entry rel ID of this account group account entry rel
	 */
	@Override
	public long getAccountGroupAccountEntryRelId() {
		return model.getAccountGroupAccountEntryRelId();
	}

	/**
	 * Returns the account group ID of this account group account entry rel.
	 *
	 * @return the account group ID of this account group account entry rel
	 */
	@Override
	public long getAccountGroupId() {
		return model.getAccountGroupId();
	}

	/**
	 * Returns the company ID of this account group account entry rel.
	 *
	 * @return the company ID of this account group account entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this account group account entry rel.
	 *
	 * @return the mvcc version of this account group account entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this account group account entry rel.
	 *
	 * @return the primary key of this account group account entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this account group account entry rel.
	 *
	 * @param accountEntryId the account entry ID of this account group account entry rel
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the account group account entry rel ID of this account group account entry rel.
	 *
	 * @param AccountGroupAccountEntryRelId the account group account entry rel ID of this account group account entry rel
	 */
	@Override
	public void setAccountGroupAccountEntryRelId(
		long AccountGroupAccountEntryRelId) {

		model.setAccountGroupAccountEntryRelId(AccountGroupAccountEntryRelId);
	}

	/**
	 * Sets the account group ID of this account group account entry rel.
	 *
	 * @param accountGroupId the account group ID of this account group account entry rel
	 */
	@Override
	public void setAccountGroupId(long accountGroupId) {
		model.setAccountGroupId(accountGroupId);
	}

	/**
	 * Sets the company ID of this account group account entry rel.
	 *
	 * @param companyId the company ID of this account group account entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this account group account entry rel.
	 *
	 * @param mvccVersion the mvcc version of this account group account entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this account group account entry rel.
	 *
	 * @param primaryKey the primary key of this account group account entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected AccountGroupAccountEntryRelWrapper wrap(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		return new AccountGroupAccountEntryRelWrapper(
			accountGroupAccountEntryRel);
	}

}