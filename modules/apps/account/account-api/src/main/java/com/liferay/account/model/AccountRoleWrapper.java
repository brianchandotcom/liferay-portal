/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AccountRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountRole
 * @generated
 */
public class AccountRoleWrapper
	extends BaseModelWrapper<AccountRole>
	implements AccountRole, ModelWrapper<AccountRole> {

	public AccountRoleWrapper(AccountRole accountRole) {
		super(accountRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("accountRoleId", getAccountRoleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("roleId", getRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long accountRoleId = (Long)attributes.get("accountRoleId");

		if (accountRoleId != null) {
			setAccountRoleId(accountRoleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}
	}

	@Override
	public AccountRole cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the account entry ID of this account role.
	 *
	 * @return the account entry ID of this account role
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the account role ID of this account role.
	 *
	 * @return the account role ID of this account role
	 */
	@Override
	public long getAccountRoleId() {
		return model.getAccountRoleId();
	}

	/**
	 * Returns the company ID of this account role.
	 *
	 * @return the company ID of this account role
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this account role.
	 *
	 * @return the mvcc version of this account role
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this account role.
	 *
	 * @return the primary key of this account role
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public com.liferay.portal.kernel.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRole();
	}

	/**
	 * Returns the role ID of this account role.
	 *
	 * @return the role ID of this account role
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	@Override
	public String getRoleName()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRoleName();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this account role.
	 *
	 * @param accountEntryId the account entry ID of this account role
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the account role ID of this account role.
	 *
	 * @param accountRoleId the account role ID of this account role
	 */
	@Override
	public void setAccountRoleId(long accountRoleId) {
		model.setAccountRoleId(accountRoleId);
	}

	/**
	 * Sets the company ID of this account role.
	 *
	 * @param companyId the company ID of this account role
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this account role.
	 *
	 * @param mvccVersion the mvcc version of this account role
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this account role.
	 *
	 * @param primaryKey the primary key of this account role
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this account role.
	 *
	 * @param roleId the role ID of this account role
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected AccountRoleWrapper wrap(AccountRole accountRole) {
		return new AccountRoleWrapper(accountRole);
	}

}