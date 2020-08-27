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

package com.liferay.commerce.account.model.impl;

import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAccountUserRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountUserRelCacheModel
	implements CacheModel<CommerceAccountUserRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountUserRelCacheModel)) {
			return false;
		}

		CommerceAccountUserRelCacheModel commerceAccountUserRelCacheModel =
			(CommerceAccountUserRelCacheModel)object;

		if (commerceAccountUserRelPK.equals(
				commerceAccountUserRelCacheModel.commerceAccountUserRelPK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAccountUserRelPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commerceAccountUserId=");
		sb.append(commerceAccountUserId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAccountUserRel toEntityModel() {
		CommerceAccountUserRelImpl commerceAccountUserRelImpl =
			new CommerceAccountUserRelImpl();

		commerceAccountUserRelImpl.setCommerceAccountId(commerceAccountId);
		commerceAccountUserRelImpl.setCommerceAccountUserId(
			commerceAccountUserId);
		commerceAccountUserRelImpl.setCompanyId(companyId);
		commerceAccountUserRelImpl.setUserId(userId);

		if (userName == null) {
			commerceAccountUserRelImpl.setUserName("");
		}
		else {
			commerceAccountUserRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAccountUserRelImpl.setCreateDate(null);
		}
		else {
			commerceAccountUserRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAccountUserRelImpl.setModifiedDate(null);
		}
		else {
			commerceAccountUserRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceAccountUserRelImpl.resetOriginalValues();

		return commerceAccountUserRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceAccountId = objectInput.readLong();

		commerceAccountUserId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountUserRelPK = new CommerceAccountUserRelPK(
			commerceAccountId, commerceAccountUserId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commerceAccountUserId);

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
	}

	public long commerceAccountId;
	public long commerceAccountUserId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public transient CommerceAccountUserRelPK commerceAccountUserRelPK;

}