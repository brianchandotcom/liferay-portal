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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionOptionValueRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionOptionValueRelCacheModel
	implements CacheModel<CPDefinitionOptionValueRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionOptionValueRelCacheModel)) {
			return false;
		}

		CPDefinitionOptionValueRelCacheModel
			cpDefinitionOptionValueRelCacheModel =
				(CPDefinitionOptionValueRelCacheModel)object;

		if ((CPDefinitionOptionValueRelId ==
				cpDefinitionOptionValueRelCacheModel.
					CPDefinitionOptionValueRelId) &&
			(mvccVersion == cpDefinitionOptionValueRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionOptionValueRelId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionOptionValueRelId=");
		sb.append(CPDefinitionOptionValueRelId);
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
		sb.append(", CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);
		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", key=");
		sb.append(key);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", preselected=");
		sb.append(preselected);
		sb.append(", price=");
		sb.append(price);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionOptionValueRel toEntityModel() {
		CPDefinitionOptionValueRelImpl cpDefinitionOptionValueRelImpl =
			new CPDefinitionOptionValueRelImpl();

		cpDefinitionOptionValueRelImpl.setMvccVersion(mvccVersion);
		cpDefinitionOptionValueRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpDefinitionOptionValueRelImpl.setUuid("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setUuid(uuid);
		}

		cpDefinitionOptionValueRelImpl.setCPDefinitionOptionValueRelId(
			CPDefinitionOptionValueRelId);
		cpDefinitionOptionValueRelImpl.setGroupId(groupId);
		cpDefinitionOptionValueRelImpl.setCompanyId(companyId);
		cpDefinitionOptionValueRelImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionOptionValueRelImpl.setUserName("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionOptionValueRelImpl.setCreateDate(null);
		}
		else {
			cpDefinitionOptionValueRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionOptionValueRelImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionOptionValueRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionOptionValueRelImpl.setCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);

		if (CPInstanceUuid == null) {
			cpDefinitionOptionValueRelImpl.setCPInstanceUuid("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setCPInstanceUuid(CPInstanceUuid);
		}

		cpDefinitionOptionValueRelImpl.setCProductId(CProductId);

		if (name == null) {
			cpDefinitionOptionValueRelImpl.setName("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setName(name);
		}

		cpDefinitionOptionValueRelImpl.setPriority(priority);

		if (key == null) {
			cpDefinitionOptionValueRelImpl.setKey("");
		}
		else {
			cpDefinitionOptionValueRelImpl.setKey(key);
		}

		cpDefinitionOptionValueRelImpl.setQuantity(quantity);
		cpDefinitionOptionValueRelImpl.setPreselected(preselected);
		cpDefinitionOptionValueRelImpl.setPrice(price);

		cpDefinitionOptionValueRelImpl.resetOriginalValues();

		return cpDefinitionOptionValueRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionOptionValueRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionOptionRelId = objectInput.readLong();
		CPInstanceUuid = objectInput.readUTF();

		CProductId = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
		key = objectInput.readUTF();

		quantity = objectInput.readInt();

		preselected = objectInput.readBoolean();
		price = (BigDecimal)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionOptionValueRelId);

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

		objectOutput.writeLong(CPDefinitionOptionRelId);

		if (CPInstanceUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPInstanceUuid);
		}

		objectOutput.writeLong(CProductId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeInt(quantity);

		objectOutput.writeBoolean(preselected);
		objectOutput.writeObject(price);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPDefinitionOptionValueRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionOptionRelId;
	public String CPInstanceUuid;
	public long CProductId;
	public String name;
	public double priority;
	public String key;
	public int quantity;
	public boolean preselected;
	public BigDecimal price;

}