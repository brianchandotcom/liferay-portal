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
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.account.service.http.AccountGroupRelServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountGroupRelSoap implements Serializable {

	public static AccountGroupRelSoap toSoapModel(AccountGroupRel model) {
		AccountGroupRelSoap soapModel = new AccountGroupRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAccountGroupRelId(model.getAccountGroupRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAccountGroupId(model.getAccountGroupId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static AccountGroupRelSoap[] toSoapModels(AccountGroupRel[] models) {
		AccountGroupRelSoap[] soapModels =
			new AccountGroupRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AccountGroupRelSoap[][] toSoapModels(
		AccountGroupRel[][] models) {

		AccountGroupRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AccountGroupRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AccountGroupRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AccountGroupRelSoap[] toSoapModels(
		List<AccountGroupRel> models) {

		List<AccountGroupRelSoap> soapModels =
			new ArrayList<AccountGroupRelSoap>(models.size());

		for (AccountGroupRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AccountGroupRelSoap[soapModels.size()]);
	}

	public AccountGroupRelSoap() {
	}

	public long getPrimaryKey() {
		return _accountGroupRelId;
	}

	public void setPrimaryKey(long pk) {
		setAccountGroupRelId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAccountGroupRelId() {
		return _accountGroupRelId;
	}

	public void setAccountGroupRelId(long accountGroupRelId) {
		_accountGroupRelId = accountGroupRelId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getAccountGroupId() {
		return _accountGroupId;
	}

	public void setAccountGroupId(long accountGroupId) {
		_accountGroupId = accountGroupId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _mvccVersion;
	private long _accountGroupRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _accountGroupId;
	private long _classNameId;
	private long _classPK;

}