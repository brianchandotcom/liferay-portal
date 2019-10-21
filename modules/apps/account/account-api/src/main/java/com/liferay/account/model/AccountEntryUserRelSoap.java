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
 * This class is used by SOAP remote services, specifically {@link com.liferay.account.service.http.AccountEntryUserRelServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryUserRelSoap implements Serializable {

	public static AccountEntryUserRelSoap toSoapModel(
		AccountEntryUserRel model) {

		AccountEntryUserRelSoap soapModel = new AccountEntryUserRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAccountEntryUserRelId(model.getAccountEntryUserRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAccountEntryId(model.getAccountEntryId());
		soapModel.setAccountUserId(model.getAccountUserId());

		return soapModel;
	}

	public static AccountEntryUserRelSoap[] toSoapModels(
		AccountEntryUserRel[] models) {

		AccountEntryUserRelSoap[] soapModels =
			new AccountEntryUserRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AccountEntryUserRelSoap[][] toSoapModels(
		AccountEntryUserRel[][] models) {

		AccountEntryUserRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AccountEntryUserRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AccountEntryUserRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AccountEntryUserRelSoap[] toSoapModels(
		List<AccountEntryUserRel> models) {

		List<AccountEntryUserRelSoap> soapModels =
			new ArrayList<AccountEntryUserRelSoap>(models.size());

		for (AccountEntryUserRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AccountEntryUserRelSoap[soapModels.size()]);
	}

	public AccountEntryUserRelSoap() {
	}

	public long getPrimaryKey() {
		return _accountEntryUserRelId;
	}

	public void setPrimaryKey(long pk) {
		setAccountEntryUserRelId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAccountEntryUserRelId() {
		return _accountEntryUserRelId;
	}

	public void setAccountEntryUserRelId(long accountEntryUserRelId) {
		_accountEntryUserRelId = accountEntryUserRelId;
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

	public long getAccountUserId() {
		return _accountUserId;
	}

	public void setAccountUserId(long accountUserId) {
		_accountUserId = accountUserId;
	}

	private long _mvccVersion;
	private long _accountEntryUserRelId;
	private long _companyId;
	private long _accountEntryId;
	private long _accountUserId;

}