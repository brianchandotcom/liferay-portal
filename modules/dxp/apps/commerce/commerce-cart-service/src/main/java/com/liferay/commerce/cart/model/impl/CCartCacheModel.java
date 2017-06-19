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

package com.liferay.commerce.cart.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.model.CCart;

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
 * The cache model class for representing CCart in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CCart
 * @generated
 */
@ProviderType
public class CCartCacheModel implements CacheModel<CCart>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CCartCacheModel)) {
			return false;
		}

		CCartCacheModel cCartCacheModel = (CCartCacheModel)obj;

		if (CCartId == cCartCacheModel.CCartId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CCartId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CCartId=");
		sb.append(CCartId);
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
		sb.append(", cartUserId=");
		sb.append(cartUserId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CCart toEntityModel() {
		CCartImpl cCartImpl = new CCartImpl();

		if (uuid == null) {
			cCartImpl.setUuid(StringPool.BLANK);
		}
		else {
			cCartImpl.setUuid(uuid);
		}

		cCartImpl.setCCartId(CCartId);
		cCartImpl.setGroupId(groupId);
		cCartImpl.setCompanyId(companyId);
		cCartImpl.setUserId(userId);

		if (userName == null) {
			cCartImpl.setUserName(StringPool.BLANK);
		}
		else {
			cCartImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cCartImpl.setCreateDate(null);
		}
		else {
			cCartImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cCartImpl.setModifiedDate(null);
		}
		else {
			cCartImpl.setModifiedDate(new Date(modifiedDate));
		}

		cCartImpl.setCartUserId(cartUserId);

		if (title == null) {
			cCartImpl.setTitle(StringPool.BLANK);
		}
		else {
			cCartImpl.setTitle(title);
		}

		cCartImpl.setType(type);

		cCartImpl.resetOriginalValues();

		return cCartImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CCartId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		cartUserId = objectInput.readLong();
		title = objectInput.readUTF();

		type = objectInput.readInt();
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

		objectOutput.writeLong(CCartId);

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

		objectOutput.writeLong(cartUserId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(type);
	}

	public String uuid;
	public long CCartId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long cartUserId;
	public String title;
	public int type;
}