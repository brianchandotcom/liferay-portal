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

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AccountEntryOrganizationRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryOrganizationRelCacheModel
	implements CacheModel<AccountEntryOrganizationRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountEntryOrganizationRelCacheModel)) {
			return false;
		}

		AccountEntryOrganizationRelCacheModel
			accountEntryOrganizationRelCacheModel =
				(AccountEntryOrganizationRelCacheModel)obj;

		if ((accountEntryOrganizationRelId ==
				accountEntryOrganizationRelCacheModel.
					accountEntryOrganizationRelId) &&
			(mvccVersion ==
				accountEntryOrganizationRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountEntryOrganizationRelId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", accountEntryOrganizationRelId=");
		sb.append(accountEntryOrganizationRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", organizationId=");
		sb.append(organizationId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountEntryOrganizationRel toEntityModel() {
		AccountEntryOrganizationRelImpl accountEntryOrganizationRelImpl =
			new AccountEntryOrganizationRelImpl();

		accountEntryOrganizationRelImpl.setMvccVersion(mvccVersion);
		accountEntryOrganizationRelImpl.setAccountEntryOrganizationRelId(
			accountEntryOrganizationRelId);
		accountEntryOrganizationRelImpl.setCompanyId(companyId);
		accountEntryOrganizationRelImpl.setAccountEntryId(accountEntryId);
		accountEntryOrganizationRelImpl.setOrganizationId(organizationId);

		accountEntryOrganizationRelImpl.resetOriginalValues();

		return accountEntryOrganizationRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		accountEntryOrganizationRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		accountEntryId = objectInput.readLong();

		organizationId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(accountEntryOrganizationRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(accountEntryId);

		objectOutput.writeLong(organizationId);
	}

	public long mvccVersion;
	public long accountEntryOrganizationRelId;
	public long companyId;
	public long accountEntryId;
	public long organizationId;

}