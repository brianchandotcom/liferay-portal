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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;

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
 * The cache model class for representing CommerceProductDefinitionOptionValueRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRel
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelCacheModel
	implements CacheModel<CommerceProductDefinitionOptionValueRel>,
		Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionOptionValueRelCacheModel)) {
			return false;
		}

		CommerceProductDefinitionOptionValueRelCacheModel commerceProductDefinitionOptionValueRelCacheModel =
			(CommerceProductDefinitionOptionValueRelCacheModel)obj;

		if (commerceProductDefinitionOptionValueRelId == commerceProductDefinitionOptionValueRelCacheModel.commerceProductDefinitionOptionValueRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceProductDefinitionOptionValueRelId=");
		sb.append(commerceProductDefinitionOptionValueRelId);
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
		sb.append(", commerceProductDefinitionOptionRelId=");
		sb.append(commerceProductDefinitionOptionRelId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductDefinitionOptionValueRel toEntityModel() {
		CommerceProductDefinitionOptionValueRelImpl commerceProductDefinitionOptionValueRelImpl =
			new CommerceProductDefinitionOptionValueRelImpl();

		if (uuid == null) {
			commerceProductDefinitionOptionValueRelImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionValueRelImpl.setUuid(uuid);
		}

		commerceProductDefinitionOptionValueRelImpl.setCommerceProductDefinitionOptionValueRelId(commerceProductDefinitionOptionValueRelId);
		commerceProductDefinitionOptionValueRelImpl.setGroupId(groupId);
		commerceProductDefinitionOptionValueRelImpl.setCompanyId(companyId);
		commerceProductDefinitionOptionValueRelImpl.setUserId(userId);

		if (userName == null) {
			commerceProductDefinitionOptionValueRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionValueRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductDefinitionOptionValueRelImpl.setCreateDate(null);
		}
		else {
			commerceProductDefinitionOptionValueRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductDefinitionOptionValueRelImpl.setModifiedDate(null);
		}
		else {
			commerceProductDefinitionOptionValueRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceProductDefinitionOptionValueRelImpl.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);

		if (title == null) {
			commerceProductDefinitionOptionValueRelImpl.setTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionValueRelImpl.setTitle(title);
		}

		commerceProductDefinitionOptionValueRelImpl.setPriority(priority);

		commerceProductDefinitionOptionValueRelImpl.resetOriginalValues();

		return commerceProductDefinitionOptionValueRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceProductDefinitionOptionValueRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceProductDefinitionOptionRelId = objectInput.readLong();
		title = objectInput.readUTF();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(commerceProductDefinitionOptionValueRelId);

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

		objectOutput.writeLong(commerceProductDefinitionOptionRelId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(priority);
	}

	public String uuid;
	public long commerceProductDefinitionOptionValueRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceProductDefinitionOptionRelId;
	public String title;
	public int priority;
}