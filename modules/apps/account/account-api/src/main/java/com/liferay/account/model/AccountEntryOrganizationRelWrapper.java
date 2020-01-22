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
 * This class is a wrapper for {@link AccountEntryOrganizationRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRel
 * @generated
 */
public class AccountEntryOrganizationRelWrapper
	extends BaseModelWrapper<AccountEntryOrganizationRel>
	implements AccountEntryOrganizationRel,
			   ModelWrapper<AccountEntryOrganizationRel> {

	public AccountEntryOrganizationRelWrapper(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		super(accountEntryOrganizationRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"accountEntryOrganizationRelId",
			getAccountEntryOrganizationRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("organizationId", getOrganizationId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long accountEntryOrganizationRelId = (Long)attributes.get(
			"accountEntryOrganizationRelId");

		if (accountEntryOrganizationRelId != null) {
			setAccountEntryOrganizationRelId(accountEntryOrganizationRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}
	}

	/**
	 * Returns the account entry ID of this account entry organization rel.
	 *
	 * @return the account entry ID of this account entry organization rel
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the account entry organization rel ID of this account entry organization rel.
	 *
	 * @return the account entry organization rel ID of this account entry organization rel
	 */
	@Override
	public long getAccountEntryOrganizationRelId() {
		return model.getAccountEntryOrganizationRelId();
	}

	/**
	 * Returns the company ID of this account entry organization rel.
	 *
	 * @return the company ID of this account entry organization rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this account entry organization rel.
	 *
	 * @return the mvcc version of this account entry organization rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the organization ID of this account entry organization rel.
	 *
	 * @return the organization ID of this account entry organization rel
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	/**
	 * Returns the primary key of this account entry organization rel.
	 *
	 * @return the primary key of this account entry organization rel
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
	 * Sets the account entry ID of this account entry organization rel.
	 *
	 * @param accountEntryId the account entry ID of this account entry organization rel
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the account entry organization rel ID of this account entry organization rel.
	 *
	 * @param accountEntryOrganizationRelId the account entry organization rel ID of this account entry organization rel
	 */
	@Override
	public void setAccountEntryOrganizationRelId(
		long accountEntryOrganizationRelId) {

		model.setAccountEntryOrganizationRelId(accountEntryOrganizationRelId);
	}

	/**
	 * Sets the company ID of this account entry organization rel.
	 *
	 * @param companyId the company ID of this account entry organization rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this account entry organization rel.
	 *
	 * @param mvccVersion the mvcc version of this account entry organization rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the organization ID of this account entry organization rel.
	 *
	 * @param organizationId the organization ID of this account entry organization rel
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the primary key of this account entry organization rel.
	 *
	 * @param primaryKey the primary key of this account entry organization rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected AccountEntryOrganizationRelWrapper wrap(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		return new AccountEntryOrganizationRelWrapper(
			accountEntryOrganizationRel);
	}

}