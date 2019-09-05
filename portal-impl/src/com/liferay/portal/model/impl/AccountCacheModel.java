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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Account in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountCacheModel
	implements CacheModel<Account>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountCacheModel)) {
			return false;
		}

		AccountCacheModel accountCacheModel = (AccountCacheModel)obj;

		if ((accountId == accountCacheModel.accountId) &&
			(mvccVersion == accountCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", parentAccountId=");
		sb.append(parentAccountId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", legalName=");
		sb.append(legalName);
		sb.append(", legalId=");
		sb.append(legalId);
		sb.append(", legalType=");
		sb.append(legalType);
		sb.append(", sicCode=");
		sb.append(sicCode);
		sb.append(", tickerSymbol=");
		sb.append(tickerSymbol);
		sb.append(", industry=");
		sb.append(industry);
		sb.append(", type=");
		sb.append(type);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Account toEntityModel() {
		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setMvccVersion(mvccVersion);
		accountImpl.setAccountId(accountId);
		accountImpl.setCompanyId(companyId);
		accountImpl.setUserId(userId);

		if (userName == null) {
			accountImpl.setUserName("");
		}
		else {
			accountImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			accountImpl.setCreateDate(null);
		}
		else {
			accountImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			accountImpl.setModifiedDate(null);
		}
		else {
			accountImpl.setModifiedDate(new Date(modifiedDate));
		}

		accountImpl.setParentAccountId(parentAccountId);

		if (name == null) {
			accountImpl.setName("");
		}
		else {
			accountImpl.setName(name);
		}

		if (legalName == null) {
			accountImpl.setLegalName("");
		}
		else {
			accountImpl.setLegalName(legalName);
		}

		if (legalId == null) {
			accountImpl.setLegalId("");
		}
		else {
			accountImpl.setLegalId(legalId);
		}

		if (legalType == null) {
			accountImpl.setLegalType("");
		}
		else {
			accountImpl.setLegalType(legalType);
		}

		if (sicCode == null) {
			accountImpl.setSicCode("");
		}
		else {
			accountImpl.setSicCode(sicCode);
		}

		if (tickerSymbol == null) {
			accountImpl.setTickerSymbol("");
		}
		else {
			accountImpl.setTickerSymbol(tickerSymbol);
		}

		if (industry == null) {
			accountImpl.setIndustry("");
		}
		else {
			accountImpl.setIndustry(industry);
		}

		if (type == null) {
			accountImpl.setType("");
		}
		else {
			accountImpl.setType(type);
		}

		if (size == null) {
			accountImpl.setSize("");
		}
		else {
			accountImpl.setSize(size);
		}

		accountImpl.resetOriginalValues();

		return accountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		accountId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentAccountId = objectInput.readLong();
		name = objectInput.readUTF();
		legalName = objectInput.readUTF();
		legalId = objectInput.readUTF();
		legalType = objectInput.readUTF();
		sicCode = objectInput.readUTF();
		tickerSymbol = objectInput.readUTF();
		industry = objectInput.readUTF();
		type = objectInput.readUTF();
		size = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(accountId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(parentAccountId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (legalName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalName);
		}

		if (legalId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalId);
		}

		if (legalType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalType);
		}

		if (sicCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sicCode);
		}

		if (tickerSymbol == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tickerSymbol);
		}

		if (industry == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(industry);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (size == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(size);
		}
	}

	public long mvccVersion;
	public long accountId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentAccountId;
	public String name;
	public String legalName;
	public String legalId;
	public String legalType;
	public String sicCode;
	public String tickerSymbol;
	public String industry;
	public String type;
	public String size;

}