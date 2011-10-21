/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Account in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Account
 * @generated
 */
public class AccountCacheModel implements CacheModel<Account>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{accountId=");
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

	public Account toEntityModel() {
		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setAccountId(accountId);
		accountImpl.setCompanyId(companyId);
		accountImpl.setUserId(userId);

		if (userName == null) {
			accountImpl.setUserName(StringPool.BLANK);
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
			accountImpl.setName(StringPool.BLANK);
		}
		else {
			accountImpl.setName(name);
		}

		if (legalName == null) {
			accountImpl.setLegalName(StringPool.BLANK);
		}
		else {
			accountImpl.setLegalName(legalName);
		}

		if (legalId == null) {
			accountImpl.setLegalId(StringPool.BLANK);
		}
		else {
			accountImpl.setLegalId(legalId);
		}

		if (legalType == null) {
			accountImpl.setLegalType(StringPool.BLANK);
		}
		else {
			accountImpl.setLegalType(legalType);
		}

		if (sicCode == null) {
			accountImpl.setSicCode(StringPool.BLANK);
		}
		else {
			accountImpl.setSicCode(sicCode);
		}

		if (tickerSymbol == null) {
			accountImpl.setTickerSymbol(StringPool.BLANK);
		}
		else {
			accountImpl.setTickerSymbol(tickerSymbol);
		}

		if (industry == null) {
			accountImpl.setIndustry(StringPool.BLANK);
		}
		else {
			accountImpl.setIndustry(industry);
		}

		if (type == null) {
			accountImpl.setType(StringPool.BLANK);
		}
		else {
			accountImpl.setType(type);
		}

		if (size == null) {
			accountImpl.setSize(StringPool.BLANK);
		}
		else {
			accountImpl.setSize(size);
		}

		accountImpl.resetOriginalValues();

		return accountImpl;
	}

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