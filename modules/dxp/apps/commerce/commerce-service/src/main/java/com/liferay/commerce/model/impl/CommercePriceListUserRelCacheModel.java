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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommercePriceListUserRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceListUserRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRel
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelCacheModel implements CacheModel<CommercePriceListUserRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListUserRelCacheModel)) {
			return false;
		}

		CommercePriceListUserRelCacheModel commercePriceListUserRelCacheModel = (CommercePriceListUserRelCacheModel)obj;

		if (commercePriceListUserRelId == commercePriceListUserRelCacheModel.commercePriceListUserRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePriceListUserRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListUserRelId=");
		sb.append(commercePriceListUserRelId);
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
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListUserRel toEntityModel() {
		CommercePriceListUserRelImpl commercePriceListUserRelImpl = new CommercePriceListUserRelImpl();

		if (uuid == null) {
			commercePriceListUserRelImpl.setUuid(StringPool.BLANK);
		}
		else {
			commercePriceListUserRelImpl.setUuid(uuid);
		}

		commercePriceListUserRelImpl.setCommercePriceListUserRelId(commercePriceListUserRelId);
		commercePriceListUserRelImpl.setGroupId(groupId);
		commercePriceListUserRelImpl.setCompanyId(companyId);
		commercePriceListUserRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListUserRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			commercePriceListUserRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListUserRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListUserRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListUserRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListUserRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePriceListUserRelImpl.setCommercePriceListId(commercePriceListId);
		commercePriceListUserRelImpl.setClassNameId(classNameId);
		commercePriceListUserRelImpl.setClassPK(classPK);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListUserRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListUserRelImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commercePriceListUserRelImpl.resetOriginalValues();

		return commercePriceListUserRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commercePriceListUserRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commercePriceListUserRelId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commercePriceListUserRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public long classNameId;
	public long classPK;
	public long lastPublishDate;
}