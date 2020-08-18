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

import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAccountGroupRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupRelCacheModel
	implements CacheModel<CommerceAccountGroupRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountGroupRelCacheModel)) {
			return false;
		}

		CommerceAccountGroupRelCacheModel commerceAccountGroupRelCacheModel =
			(CommerceAccountGroupRelCacheModel)object;

		if (commerceAccountGroupRelId ==
				commerceAccountGroupRelCacheModel.commerceAccountGroupRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAccountGroupRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceAccountGroupRelId=");
		sb.append(commerceAccountGroupRelId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAccountGroupRel toEntityModel() {
		CommerceAccountGroupRelImpl commerceAccountGroupRelImpl =
			new CommerceAccountGroupRelImpl();

		commerceAccountGroupRelImpl.setCommerceAccountGroupRelId(
			commerceAccountGroupRelId);
		commerceAccountGroupRelImpl.setCompanyId(companyId);
		commerceAccountGroupRelImpl.setUserId(userId);

		if (userName == null) {
			commerceAccountGroupRelImpl.setUserName("");
		}
		else {
			commerceAccountGroupRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAccountGroupRelImpl.setCreateDate(null);
		}
		else {
			commerceAccountGroupRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAccountGroupRelImpl.setModifiedDate(null);
		}
		else {
			commerceAccountGroupRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceAccountGroupRelImpl.setClassNameId(classNameId);
		commerceAccountGroupRelImpl.setClassPK(classPK);
		commerceAccountGroupRelImpl.setCommerceAccountGroupId(
			commerceAccountGroupId);

		commerceAccountGroupRelImpl.resetOriginalValues();

		return commerceAccountGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceAccountGroupRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceAccountGroupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceAccountGroupRelId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(commerceAccountGroupId);
	}

	public long commerceAccountGroupRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceAccountGroupId;

}