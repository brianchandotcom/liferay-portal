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

import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceDiscountUserSegmentRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRel
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelCacheModel implements CacheModel<CommerceDiscountUserSegmentRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountUserSegmentRelCacheModel)) {
			return false;
		}

		CommerceDiscountUserSegmentRelCacheModel commerceDiscountUserSegmentRelCacheModel =
			(CommerceDiscountUserSegmentRelCacheModel)obj;

		if (commerceDiscountUserSegmentRelId == commerceDiscountUserSegmentRelCacheModel.commerceDiscountUserSegmentRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDiscountUserSegmentRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceDiscountUserSegmentRelId=");
		sb.append(commerceDiscountUserSegmentRelId);
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
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountUserSegmentRel toEntityModel() {
		CommerceDiscountUserSegmentRelImpl commerceDiscountUserSegmentRelImpl = new CommerceDiscountUserSegmentRelImpl();

		commerceDiscountUserSegmentRelImpl.setCommerceDiscountUserSegmentRelId(commerceDiscountUserSegmentRelId);
		commerceDiscountUserSegmentRelImpl.setGroupId(groupId);
		commerceDiscountUserSegmentRelImpl.setCompanyId(companyId);
		commerceDiscountUserSegmentRelImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountUserSegmentRelImpl.setUserName("");
		}
		else {
			commerceDiscountUserSegmentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountUserSegmentRelImpl.setCreateDate(null);
		}
		else {
			commerceDiscountUserSegmentRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountUserSegmentRelImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountUserSegmentRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceDiscountUserSegmentRelImpl.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountUserSegmentRelImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		commerceDiscountUserSegmentRelImpl.resetOriginalValues();

		return commerceDiscountUserSegmentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceDiscountUserSegmentRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		commerceUserSegmentEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceDiscountUserSegmentRelId);

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

		objectOutput.writeLong(commerceUserSegmentEntryId);
	}

	public long commerceDiscountUserSegmentRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long commerceUserSegmentEntryId;
}