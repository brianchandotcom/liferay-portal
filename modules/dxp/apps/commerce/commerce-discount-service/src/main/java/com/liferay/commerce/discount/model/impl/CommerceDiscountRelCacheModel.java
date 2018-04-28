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

package com.liferay.commerce.discount.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscountRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceDiscountRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceDiscountRel
 * @generated
 */
@ProviderType
public class CommerceDiscountRelCacheModel implements CacheModel<CommerceDiscountRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountRelCacheModel)) {
			return false;
		}

		CommerceDiscountRelCacheModel commerceDiscountRelCacheModel = (CommerceDiscountRelCacheModel)obj;

		if (commerceDiscountRelId == commerceDiscountRelCacheModel.commerceDiscountRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDiscountRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceDiscountRelId=");
		sb.append(commerceDiscountRelId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountRel toEntityModel() {
		CommerceDiscountRelImpl commerceDiscountRelImpl = new CommerceDiscountRelImpl();

		commerceDiscountRelImpl.setCommerceDiscountRelId(commerceDiscountRelId);
		commerceDiscountRelImpl.setGroupId(groupId);
		commerceDiscountRelImpl.setCompanyId(companyId);
		commerceDiscountRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountRelImpl.setUserName("");
		}
		else {
			commerceDiscountRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceDiscountRelImpl.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRelImpl.setClassNameId(classNameId);
		commerceDiscountRelImpl.setClassPK(classPK);

		commerceDiscountRelImpl.resetOriginalValues();

		return commerceDiscountRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceDiscountRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceDiscountRelId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long commerceDiscountRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long classNameId;
	public long classPK;
}