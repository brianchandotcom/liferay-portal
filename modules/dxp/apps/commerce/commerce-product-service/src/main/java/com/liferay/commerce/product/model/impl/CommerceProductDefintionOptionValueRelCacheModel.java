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

import com.liferay.commerce.product.model.CommerceProductDefintionOptionValueRel;

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
 * The cache model class for representing CommerceProductDefintionOptionValueRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionValueRel
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionValueRelCacheModel
	implements CacheModel<CommerceProductDefintionOptionValueRel>,
		Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefintionOptionValueRelCacheModel)) {
			return false;
		}

		CommerceProductDefintionOptionValueRelCacheModel commerceProductDefintionOptionValueRelCacheModel =
			(CommerceProductDefintionOptionValueRelCacheModel)obj;

		if (commerceProductDefintionOptionValueRelId == commerceProductDefintionOptionValueRelCacheModel.commerceProductDefintionOptionValueRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductDefintionOptionValueRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceProductDefintionOptionValueRelId=");
		sb.append(commerceProductDefintionOptionValueRelId);
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
		sb.append(", commerceProductDefintionOptionRelId=");
		sb.append(commerceProductDefintionOptionRelId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductDefintionOptionValueRel toEntityModel() {
		CommerceProductDefintionOptionValueRelImpl commerceProductDefintionOptionValueRelImpl =
			new CommerceProductDefintionOptionValueRelImpl();

		commerceProductDefintionOptionValueRelImpl.setCommerceProductDefintionOptionValueRelId(commerceProductDefintionOptionValueRelId);
		commerceProductDefintionOptionValueRelImpl.setGroupId(groupId);
		commerceProductDefintionOptionValueRelImpl.setCompanyId(companyId);
		commerceProductDefintionOptionValueRelImpl.setUserId(userId);

		if (userName == null) {
			commerceProductDefintionOptionValueRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionValueRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductDefintionOptionValueRelImpl.setCreateDate(null);
		}
		else {
			commerceProductDefintionOptionValueRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductDefintionOptionValueRelImpl.setModifiedDate(null);
		}
		else {
			commerceProductDefintionOptionValueRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceProductDefintionOptionValueRelImpl.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);

		if (title == null) {
			commerceProductDefintionOptionValueRelImpl.setTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionValueRelImpl.setTitle(title);
		}

		commerceProductDefintionOptionValueRelImpl.setPriority(priority);

		commerceProductDefintionOptionValueRelImpl.resetOriginalValues();

		return commerceProductDefintionOptionValueRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceProductDefintionOptionValueRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceProductDefintionOptionRelId = objectInput.readLong();
		title = objectInput.readUTF();

		priority = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceProductDefintionOptionValueRelId);

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

		objectOutput.writeLong(commerceProductDefintionOptionRelId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeLong(priority);
	}

	public long commerceProductDefintionOptionValueRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceProductDefintionOptionRelId;
	public String title;
	public long priority;
}