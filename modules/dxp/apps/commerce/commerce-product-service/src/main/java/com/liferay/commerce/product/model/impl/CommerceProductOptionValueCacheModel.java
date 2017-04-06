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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductOptionValue;

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
 * The cache model class for representing CommerceProductOptionValue in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductOptionValue
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueCacheModel implements CacheModel<CommerceProductOptionValue>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductOptionValueCacheModel)) {
			return false;
		}

		CommerceProductOptionValueCacheModel commerceProductOptionValueCacheModel =
			(CommerceProductOptionValueCacheModel)obj;

		if (commerceProductOptionValueId == commerceProductOptionValueCacheModel.commerceProductOptionValueId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductOptionValueId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceProductOptionValueId=");
		sb.append(commerceProductOptionValueId);
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
		sb.append(", commerceProductOptionId=");
		sb.append(commerceProductOptionId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductOptionValue toEntityModel() {
		CommerceProductOptionValueImpl commerceProductOptionValueImpl = new CommerceProductOptionValueImpl();

		commerceProductOptionValueImpl.setCommerceProductOptionValueId(commerceProductOptionValueId);
		commerceProductOptionValueImpl.setGroupId(groupId);
		commerceProductOptionValueImpl.setCompanyId(companyId);
		commerceProductOptionValueImpl.setUserId(userId);

		if (userName == null) {
			commerceProductOptionValueImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductOptionValueImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductOptionValueImpl.setCreateDate(null);
		}
		else {
			commerceProductOptionValueImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductOptionValueImpl.setModifiedDate(null);
		}
		else {
			commerceProductOptionValueImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceProductOptionValueImpl.setCommerceProductOptionId(commerceProductOptionId);

		if (title == null) {
			commerceProductOptionValueImpl.setTitle(StringPool.BLANK);
		}
		else {
			commerceProductOptionValueImpl.setTitle(title);
		}

		commerceProductOptionValueImpl.setPriority(priority);

		commerceProductOptionValueImpl.resetOriginalValues();

		return commerceProductOptionValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceProductOptionValueId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceProductOptionId = objectInput.readLong();
		title = objectInput.readUTF();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceProductOptionValueId);

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

		objectOutput.writeLong(commerceProductOptionId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(priority);
	}

	public long commerceProductOptionValueId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceProductOptionId;
	public String title;
	public int priority;
}