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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.account.service.http.AccountRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountRoleSoap implements Serializable {

	public static AccountRoleSoap toSoapModel(AccountRole model) {
		AccountRoleSoap soapModel = new AccountRoleSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAccountRoleId(model.getAccountRoleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAccountEntryId(model.getAccountEntryId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static AccountRoleSoap[] toSoapModels(AccountRole[] models) {
		AccountRoleSoap[] soapModels = new AccountRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AccountRoleSoap[][] toSoapModels(AccountRole[][] models) {
		AccountRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AccountRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AccountRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AccountRoleSoap[] toSoapModels(List<AccountRole> models) {
		List<AccountRoleSoap> soapModels = new ArrayList<AccountRoleSoap>(
			models.size());

		for (AccountRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AccountRoleSoap[soapModels.size()]);
	}

	public AccountRoleSoap() {
	}

	public long getPrimaryKey() {
		return _accountRoleId;
	}

	public void setPrimaryKey(long pk) {
		setAccountRoleId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAccountRoleId() {
		return _accountRoleId;
	}

	public void setAccountRoleId(long accountRoleId) {
		_accountRoleId = accountRoleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	private long _mvccVersion;
	private long _accountRoleId;
	private long _companyId;
	private long _accountEntryId;
	private long _roleId;

}