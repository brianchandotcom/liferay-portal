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
 * This class is a wrapper for {@link AccountGroupRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRel
 * @generated
 */
public class AccountGroupRelWrapper
	extends BaseModelWrapper<AccountGroupRel>
	implements AccountGroupRel, ModelWrapper<AccountGroupRel> {

	public AccountGroupRelWrapper(AccountGroupRel accountGroupRel) {
		super(accountGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("accountGroupRelId", getAccountGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("accountGroupId", getAccountGroupId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long accountGroupRelId = (Long)attributes.get("accountGroupRelId");

		if (accountGroupRelId != null) {
			setAccountGroupRelId(accountGroupRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long accountGroupId = (Long)attributes.get("accountGroupId");

		if (accountGroupId != null) {
			setAccountGroupId(accountGroupId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	/**
	 * Returns the account group ID of this account group rel.
	 *
	 * @return the account group ID of this account group rel
	 */
	@Override
	public long getAccountGroupId() {
		return model.getAccountGroupId();
	}

	/**
	 * Returns the account group rel ID of this account group rel.
	 *
	 * @return the account group rel ID of this account group rel
	 */
	@Override
	public long getAccountGroupRelId() {
		return model.getAccountGroupRelId();
	}

	/**
	 * Returns the fully qualified class name of this account group rel.
	 *
	 * @return the fully qualified class name of this account group rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this account group rel.
	 *
	 * @return the class name ID of this account group rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this account group rel.
	 *
	 * @return the class pk of this account group rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this account group rel.
	 *
	 * @return the company ID of this account group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this account group rel.
	 *
	 * @return the mvcc version of this account group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this account group rel.
	 *
	 * @return the primary key of this account group rel
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
	 * Sets the account group ID of this account group rel.
	 *
	 * @param accountGroupId the account group ID of this account group rel
	 */
	@Override
	public void setAccountGroupId(long accountGroupId) {
		model.setAccountGroupId(accountGroupId);
	}

	/**
	 * Sets the account group rel ID of this account group rel.
	 *
	 * @param accountGroupRelId the account group rel ID of this account group rel
	 */
	@Override
	public void setAccountGroupRelId(long accountGroupRelId) {
		model.setAccountGroupRelId(accountGroupRelId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this account group rel.
	 *
	 * @param classNameId the class name ID of this account group rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this account group rel.
	 *
	 * @param classPK the class pk of this account group rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this account group rel.
	 *
	 * @param companyId the company ID of this account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this account group rel.
	 *
	 * @param mvccVersion the mvcc version of this account group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this account group rel.
	 *
	 * @param primaryKey the primary key of this account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected AccountGroupRelWrapper wrap(AccountGroupRel accountGroupRel) {
		return new AccountGroupRelWrapper(accountGroupRel);
	}

}