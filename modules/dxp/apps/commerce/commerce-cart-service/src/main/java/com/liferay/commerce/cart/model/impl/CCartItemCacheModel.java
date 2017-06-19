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

import com.liferay.commerce.cart.model.CCartItem;

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
 * The cache model class for representing CCartItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CCartItem
 * @generated
 */
@ProviderType
public class CCartItemCacheModel implements CacheModel<CCartItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CCartItemCacheModel)) {
			return false;
		}

		CCartItemCacheModel cCartItemCacheModel = (CCartItemCacheModel)obj;

		if (CCartItemId == cCartItemCacheModel.CCartItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CCartItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CCartItemId=");
		sb.append(CCartItemId);
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
		sb.append(", CCartId=");
		sb.append(CCartId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CCartItem toEntityModel() {
		CCartItemImpl cCartItemImpl = new CCartItemImpl();

		if (uuid == null) {
			cCartItemImpl.setUuid(StringPool.BLANK);
		}
		else {
			cCartItemImpl.setUuid(uuid);
		}

		cCartItemImpl.setCCartItemId(CCartItemId);
		cCartItemImpl.setGroupId(groupId);
		cCartItemImpl.setCompanyId(companyId);
		cCartItemImpl.setUserId(userId);

		if (userName == null) {
			cCartItemImpl.setUserName(StringPool.BLANK);
		}
		else {
			cCartItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cCartItemImpl.setCreateDate(null);
		}
		else {
			cCartItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cCartItemImpl.setModifiedDate(null);
		}
		else {
			cCartItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		cCartItemImpl.setCCartId(CCartId);
		cCartItemImpl.setCPDefinitionId(CPDefinitionId);
		cCartItemImpl.setCPInstanceId(CPInstanceId);
		cCartItemImpl.setQuantity(quantity);

		if (json == null) {
			cCartItemImpl.setJson(StringPool.BLANK);
		}
		else {
			cCartItemImpl.setJson(json);
		}

		cCartItemImpl.resetOriginalValues();

		return cCartItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CCartItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CCartId = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		quantity = objectInput.readInt();
		json = objectInput.readUTF();
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

		objectOutput.writeLong(CCartItemId);

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

		objectOutput.writeLong(CCartId);

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeInt(quantity);

		if (json == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(json);
		}
	}

	public String uuid;
	public long CCartItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CCartId;
	public long CPDefinitionId;
	public long CPInstanceId;
	public int quantity;
	public String json;
}