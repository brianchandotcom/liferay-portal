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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.persistence.AccountEntryUserRelPK;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing AccountEntryUserRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AccountEntryUserRelCacheModel
	implements CacheModel<AccountEntryUserRel>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountEntryUserRelCacheModel)) {
			return false;
		}

		AccountEntryUserRelCacheModel accountEntryUserRelCacheModel =
			(AccountEntryUserRelCacheModel)obj;

		if (accountEntryUserRelPK.equals(
				accountEntryUserRelCacheModel.accountEntryUserRelPK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, accountEntryUserRelPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountEntryUserRel toEntityModel() {
		AccountEntryUserRelImpl accountEntryUserRelImpl =
			new AccountEntryUserRelImpl();

		accountEntryUserRelImpl.setAccountEntryId(accountEntryId);
		accountEntryUserRelImpl.setUserId(userId);
		accountEntryUserRelImpl.setCompanyId(companyId);

		accountEntryUserRelImpl.resetOriginalValues();

		return accountEntryUserRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		accountEntryId = objectInput.readLong();

		userId = objectInput.readLong();

		companyId = objectInput.readLong();

		accountEntryUserRelPK = new AccountEntryUserRelPK(
			accountEntryId, userId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(accountEntryId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(companyId);
	}

	public long accountEntryId;
	public long userId;
	public long companyId;
	public transient AccountEntryUserRelPK accountEntryUserRelPK;

}